package io.dimitris.markingmate.ui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JPanel;

import org.eclipse.emf.common.util.EList;

import io.dimitris.markingmate.Answer;

public class RelatedFeedbackPanel extends JPanel {
	
	protected List<JEditorPane> editors = new ArrayList<JEditorPane>();
	protected MarkingMate markingMate = null;
	
	public RelatedFeedbackPanel(MarkingMate markingMate) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.markingMate = markingMate;
	}
	
	public void setAnswer(Answer answer) {
		EList<Answer> relatedAnswers = answer.getQuestion().getAnswers();
		List<Answer> answers = new ArrayList<Answer>(relatedAnswers);
		answers.remove(answer);
		answers.sort(new Comparator<Answer>() {
			@Override
			public int compare(Answer a1, Answer a2) {
				return Math.abs(answer.getMarks() - a2.getMarks()) - Math.abs(answer.getMarks() - a1.getMarks());
			}
		});
		setRelatedAnswers(answer, relatedAnswers);
	}

	public void setRelatedAnswers(Answer answer, Iterable<Answer> relatedAnswers) {
		removeAll();
		for (Answer a : relatedAnswers) {
			if (a.getFeedback().length() > 0) {
				JPanel groupPanel = new JPanel();
				groupPanel.setBorder(BorderFactory.createTitledBorder(a.getStudent().getNumber()));
				groupPanel.setLayout(new BorderLayout());
				groupPanel.add(new FeedbackPanel(markingMate, a), BorderLayout.CENTER);
				
				add(groupPanel, 0);
			}
		}
		markingMate.updateUI(this);
	}
}
