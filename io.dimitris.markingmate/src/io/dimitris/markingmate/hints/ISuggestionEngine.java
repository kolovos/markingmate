package io.dimitris.markingmate.hints;

import java.util.Collection;

import io.dimitris.markingmate.Answer;

@FunctionalInterface
public interface ISuggestionEngine {

	Collection<String> getSuggestions(String hint, Answer answerBeingEdited);

}
