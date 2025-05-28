package io.dimitris.markingmate.ui;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JEditorPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import org.apache.commons.lang3.StringUtils;

import edu.stanford.nlp.fsm.AutomatonMinimizer;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.SentenceUtils;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.util.CoreMap;
import io.dimitris.markingmate.Answer;

public class FeedbackTextArea extends JTextArea {
	
	protected UndoHandler undoHandler = new UndoHandler();
	protected UndoManager undoManager = new UndoManager();
	protected UndoAction undoAction = null;
	protected RedoAction redoAction = null;
	
	protected Answer answer = null;
	
	public static void main(String[] args) {
		
		String paragraph = "The metamodel is expressed in valid Ecore. The purpose of the \"satisfied\" attribute is still unclear to me. It's unclear why there's a need for \"BasisOfRequirement\" and \"Requirement\" in the metamodel. The decision to model decomposition, assignment etc. as separate metaclasses (as opposed to references is not justified. Names of metaclasses should be nouns e.g. \"Decomposition\" instead of \"Decompose\". The presence/absence of opposite references is not justified. Naming of properties could be better e.g. textualDescription -> description, source/target -> something more meaningful in the context. The purpose of the name attributes of the \"gmf.link\" classes is not clear.";
		
		Reader reader = new StringReader(paragraph);
		DocumentPreprocessor dp = new DocumentPreprocessor(reader);
		List<String> sentenceList = new ArrayList<String>();

		for (List<HasWord> sentence : dp) {
			String sentenceString = paragraph.substring(((CoreLabel)sentence.get(0)).beginPosition(), ((CoreLabel)sentence.get(sentence.size()-1)).endPosition()); 
		   //String sentenceString = SentenceUtils.listToOriginalTextString(sentence);
		   sentenceList.add(sentenceString);
		}

		for (String sentence : sentenceList) {
		   System.out.println(sentence);
		}
		
	}
	
	public FeedbackTextArea(Answer answer) {
		super();
		getDocument().addUndoableEditListener(undoHandler);

		// META_MASK for Mac, CTRL_MASK for Windows
		KeyStroke undoKeystroke = KeyStroke.getKeyStroke(KeyEvent.VK_Z, Event.META_MASK);
		KeyStroke redoKeystroke = KeyStroke.getKeyStroke(KeyEvent.VK_Y, Event.META_MASK);
		KeyStroke undoCtrlKeystroke = KeyStroke.getKeyStroke(KeyEvent.VK_Z, Event.CTRL_MASK);
		KeyStroke redoCtrlKeystroke = KeyStroke.getKeyStroke(KeyEvent.VK_Y, Event.CTRL_MASK);

		// Ctrl+Space is the usual default, and Alt+Space is needed when using multi-language keyboards
		// (since both Windows and MacOS use Ctrl+Space to change keyboard languages).
		KeyStroke autocompleteKeystroke = KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, Event.CTRL_MASK);
		KeyStroke autocompleteAltKeystroke = KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, Event.ALT_MASK);

		undoAction = new UndoAction();
		getInputMap().put(undoKeystroke, "undoKeystroke");
		getInputMap().put(undoCtrlKeystroke, "undoKeystroke");
		getActionMap().put("undoKeystroke", undoAction);

		redoAction = new RedoAction();
		getInputMap().put(redoKeystroke, "redoKeystroke");
		getInputMap().put(redoCtrlKeystroke, "redoKeystroke");
		getActionMap().put("redoKeystroke", redoAction);
		
		getInputMap().put(autocompleteKeystroke, "autocompleteKeystroke");
		getInputMap().put(autocompleteAltKeystroke, "autocompleteKeystroke");
		getActionMap().put("autocompleteKeystroke", new AutocompleteAction());
		
		setAnswer(answer);
	}
	
	public void setAnswer(Answer answer) {
		this.answer = answer;
		if (answer != null) {
			setText(answer.getFeedback());
		}
		else {
			setText("");
		}
	}
	
	protected Collection<String> getSuggestions(String hint) {
		hint = hint.trim();

		Set<String> sentences = new HashSet<String>();
		if (answer != null) {
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
		}
		return sentences;
	}
	
	class UndoHandler implements UndoableEditListener {
		public void undoableEditHappened(UndoableEditEvent e) {
			undoManager.addEdit(e.getEdit());
			undoAction.update();
			redoAction.update();
		}
	}
	
	class AutocompleteAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			JPopupMenu menu = new JPopupMenu();
			
			int offset = getCaretPosition()-1;
			String hint = "";
			while (offset >= 0 && getText().charAt(offset) != '.') {
				hint = getText().charAt(offset) + hint;
				offset --;
			}
			
			Collection<String> suggestions = getSuggestions(hint);
			for (final String suggestion : suggestions) {
				menu.add(new AutocompleteItemAction(suggestion, offset + 1));
			}
			if (!suggestions.isEmpty()) {
				menu.show(FeedbackTextArea.this, getCaret().getMagicCaretPosition().x, 
						getCaret().getMagicCaretPosition().y);
			}
		}
		
	}
	
	class AutocompleteItemAction extends AbstractAction {

		protected String suggestion;
		protected int replaceOffset;
		
		public AutocompleteItemAction(String suggestion, int replaceOffset) {
			super(StringUtils.abbreviate(suggestion, 60));
			this.suggestion = suggestion;
			this.replaceOffset = replaceOffset;
			this.putValue(TOOL_TIP_TEXT_KEY, suggestion);
			this.putValue(SHORT_DESCRIPTION, suggestion);
			this.putValue(LONG_DESCRIPTION, suggestion);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String replacement = suggestion;
			if (replaceOffset > 0) { replacement = " " + replacement; }
			replaceRange(replacement, replaceOffset, getCaretPosition());
		}
		
	}
	

	class UndoAction extends AbstractAction {
		public UndoAction() {
			super("Undo");
			setEnabled(false);
		}

		public void actionPerformed(ActionEvent e) {
			try {
				undoManager.undo();
			} catch (CannotUndoException ex) {}
			update();
			redoAction.update();
		}

		protected void update() {
			if (undoManager.canUndo()) {
				setEnabled(true);
				putValue(Action.NAME, undoManager.getUndoPresentationName());
			} else {
				setEnabled(false);
				putValue(Action.NAME, "Undo");
			}
		}
	}

	class RedoAction extends AbstractAction {
		public RedoAction() {
			super("Redo");
			setEnabled(false);
		}

		public void actionPerformed(ActionEvent e) {
			try {
				undoManager.redo();
			} catch (CannotRedoException ex) {}
			update();
			undoAction.update();
		}

		protected void update() {
			if (undoManager.canRedo()) {
				setEnabled(true);
				putValue(Action.NAME, undoManager.getRedoPresentationName());
			} else {
				setEnabled(false);
				putValue(Action.NAME, "Redo");
			}
		}
	}
}
