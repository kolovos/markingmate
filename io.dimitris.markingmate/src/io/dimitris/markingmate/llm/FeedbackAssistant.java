package io.dimitris.markingmate.llm;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import io.dimitris.markingmate.MarkingmateFactory;
import io.dimitris.markingmate.Question;

public class FeedbackAssistant {

	private static final String LLM_TOOL_SYS_PROPERTY = "llm.path";
	private static final String[] LLM_TOOL_FILENAMES = { "llm", "llm.bat", "llm.exe" };
	private static final String CONCISE_TEMPLATE = "make-concise.yaml";

	private static final String LLM_UNAVAILABLE = "llm not found in PATH";
	private static final String LLM_ERROR = "Error during llm execution: ";

	private Optional<File> toolFile;

	public static void main(String[] args) {
		FeedbackAssistant asst = new FeedbackAssistant();
		String questionTitle = "Metamodel design";
		String questionDescription = "Write a metamodel for the described domain in the Emfatic language.";

		Question q = MarkingmateFactory.eINSTANCE.createQuestion();
		q.setTitle(questionTitle);
		q.setDescription(questionDescription);
		try {
			String feedback = "The metamodel was missing a concept for projects. The metamodel was missing a concept for workers. Some of the Emfatic syntax was incorrect.";
			
			System.out.println("Question: " + questionTitle);
			System.out.println(questionDescription);
			System.out.println();
			System.out.println("Original feedback:");
			System.out.println(feedback);
			System.out.println();
			System.out.println("Revised feedback:");
			System.out.println(asst.makeConcise(q, feedback));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public FeedbackAssistant() {
		toolFile = detectToolPath();
	}

	public boolean isAvailable() {
		return toolFile.isPresent();
	}

	public String makeConcise(Question question, String feedback) throws IOException {
		return runPrompt(CONCISE_TEMPLATE, feedback, Map.of(
			"question", question.getTitle() + ": " + question.getDescription()
		));
	}

	protected String runPrompt(String templateResource, String feedback, Map<String, String> params) throws IOException {
		if (!isAvailable()) {
			return LLM_UNAVAILABLE;
		}

		File fTemp = File.createTempFile("template", ".yaml");
		try {
			InputStream isTemplate = getClass().getResourceAsStream(templateResource);
			if (isTemplate == null) {
				return "Could not find template " + templateResource;
			}

			Files.copy(isTemplate, fTemp.toPath(), StandardCopyOption.REPLACE_EXISTING);
			List<String> args = new ArrayList<>();
			args.add(toolFile.get().getCanonicalPath());
			args.add("-t");
			args.add(fTemp.getCanonicalPath());
			for (Entry<String, String> entry : params.entrySet()) {
				args.add("-p");
				args.add(entry.getKey());
				args.add(entry.getValue());
			}
			Process p = new ProcessBuilder(args).start();

			// Send in the feedback
			try (OutputStream outputStream = p.getOutputStream()) {
				outputStream.write(feedback.getBytes(StandardCharsets.UTF_8));
			}

			// Read the output from the process
			StringBuilder sbOut = readInputStream(p.getInputStream(), new StringBuilder());

			// Check for errors
			int exitCode = p.waitFor();
			if (exitCode != 0) {
				StringBuilder sbErr = readInputStream(
					p.getErrorStream(), new StringBuilder(LLM_ERROR));
				return sbErr.toString();
			}

			return sbOut.toString();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return LLM_ERROR + e.getMessage();
		} finally {
			fTemp.delete();
		}
	}

	private StringBuilder readInputStream(InputStream is, StringBuilder sb) throws IOException {
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(is, StandardCharsets.UTF_8))) {
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
			}
		}
		return sb;
	}

	protected Optional<File> detectToolPath() {
		// First try with the user-specified path (if any)
		String llmPath = System.getProperty(LLM_TOOL_SYS_PROPERTY);
		if (llmPath != null) {
			File f = new File(llmPath);
			if (f.exists() && f.isFile() & f.canExecute()) {
				System.out.println("Found 'llm' from llm.path: " + f);
				return Optional.of(f);
			}
		}

		String sPath = System.getenv("PATH");
		if (sPath != null) {
			String[] pathElements = sPath.split(File.pathSeparator);
			for (String pathElem : pathElements) {
				File pathFolder = new File(pathElem);
				if (pathFolder.isDirectory()) {
					// Try various possible filenames for the 'llm' tool
					for (String llmFilename : LLM_TOOL_FILENAMES) {
						File[] candidates = pathFolder.listFiles(
							file -> file.canExecute() && file.getName().equals(llmFilename));
						if (candidates.length > 0) {
							return Optional.of(candidates[0]);
						}
					}
				}
			}
			System.out.println("Could not find 'llm' in: " + pathElements);
		}

		return Optional.empty();
	}

}
