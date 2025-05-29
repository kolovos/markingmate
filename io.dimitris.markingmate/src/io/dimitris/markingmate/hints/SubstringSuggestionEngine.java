package io.dimitris.markingmate.hints;

import java.io.Reader;
import java.io.StringReader;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.DocumentPreprocessor;
import io.dimitris.markingmate.Answer;

public class SubstringSuggestionEngine implements ISuggestionEngine {

	@Override
	public Collection<String> getSuggestions(String hint, Answer answer) {
		if (answer == null) {
			return Collections.emptySet();
		}

		hint = hint.trim();
		Set<String> sentences = new TreeSet<>();
		for (Answer other : answer.getQuestion().getAnswers()) {
			if (other == answer)
				continue;

			Reader reader = new StringReader(other.getFeedback());
			DocumentPreprocessor processor = new DocumentPreprocessor(reader);

			for (List<HasWord> words : processor) {
				if (words.isEmpty())
					continue;

				String sentence = other.getFeedback().substring(((CoreLabel) words.get(0)).beginPosition(),
						((CoreLabel) words.get(words.size() - 1)).endPosition());
				if (sentence.toLowerCase().indexOf(hint.toLowerCase()) > -1) {
					sentences.add(sentence);
				}
			}
		}

		return sentences;
	}

}
