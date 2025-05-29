package io.dimitris.markingmate.suggestions;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.ByteBuffersDirectory;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.DocumentPreprocessor;
import io.dimitris.markingmate.Answer;

public class LuceneSuggestionEngine implements ISuggestionEngine {

	protected static final String LUCENE_SENTENCE_FIELD = "sentence";
	protected static final int SEARCH_LIMIT = 10;

	@Override
	public Collection<String> getSuggestions(String hint, Answer answer) {
		if (answer != null) {
			try (ByteBuffersDirectory directory = new ByteBuffersDirectory()) {
				Analyzer analyzer = new EnglishAnalyzer();
				index(directory, answer, analyzer);
				Set<String> sentences = search(hint, analyzer, directory);
				return sentences;
			} catch (IOException | ParseException ex) {
				ex.printStackTrace();
			}
		}
		return Collections.emptySet();
	}

	private Set<String> search(String hint, Analyzer analyzer, ByteBuffersDirectory directory) throws IOException, ParseException {
		hint = hint.trim();
		Set<String> sentences = new LinkedHashSet<>();
		try (IndexReader reader = DirectoryReader.open(directory)) {
			IndexSearcher searcher = new IndexSearcher(reader);
			QueryParser parser = new QueryParser(LUCENE_SENTENCE_FIELD, analyzer);
			Query query = parser.parse(hint);

			ScoreDoc[] hits = searcher.search(query, SEARCH_LIMIT).scoreDocs;
			for (ScoreDoc hit : hits) {
				Document hitDoc = searcher.storedFields().document(hit.doc);
				sentences.add(hitDoc.get(LUCENE_SENTENCE_FIELD));
			}
		}
		return sentences;
	}

	private void index(ByteBuffersDirectory directory, Answer answer, Analyzer analyzer) throws IOException {
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		try (IndexWriter writer = new IndexWriter(directory, config)) {
			for (Answer other : answer.getQuestion().getAnswers()) {
				if (other == answer) continue;
				Reader reader = new StringReader(other.getFeedback());
				DocumentPreprocessor processor = new DocumentPreprocessor(reader);

				for (List<HasWord> words : processor) {
					int beginPosition = ((CoreLabel) words.get(0)).beginPosition();
					int endPosition = ((CoreLabel) words.get(words.size() - 1)).endPosition();
					String sentence = other.getFeedback().substring(beginPosition, endPosition);
					addDocument(writer, sentence);
				}
			}
		}
	}

	protected void addDocument(IndexWriter w, String sentence) throws IOException {
		Document doc = new Document();
		doc.add(new TextField(LUCENE_SENTENCE_FIELD, sentence, Field.Store.YES));
		w.addDocument(doc);
	}

}
