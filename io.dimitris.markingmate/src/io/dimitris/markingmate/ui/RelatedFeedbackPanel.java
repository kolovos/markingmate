package io.dimitris.markingmate.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JPanel;

import io.dimitris.markingmate.Answer;

public class RelatedFeedbackPanel extends JPanel {
	
	protected List<JEditorPane> editors = new ArrayList<JEditorPane>();
	protected MarkingMate markingMate = null;
	
	public RelatedFeedbackPanel(MarkingMate markingMate) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setOpaque(true);
		this.setBackground(new Color(238, 238, 238));
		this.markingMate = markingMate;
	}
	
	public void setAnswer(Answer answer) {
		ArrayList<Answer> answers = new ArrayList<Answer>(answer.getQuestion().getAnswers());
		answers.remove(answer);
		removeAll();

		answers.sort(new Comparator<Answer>() {
			@Override
			public int compare(Answer a1, Answer a2) {
				return Math.abs(answer.getMarks() - a2.getMarks()) - Math.abs(answer.getMarks() - a1.getMarks());
			}
		});
		
		for (Answer a : answers) {
			if (a.getFeedback().length() > 0) {
				JPanel groupPanel = new JPanel();
				groupPanel.setBorder(BorderFactory.createTitledBorder(a.getStudent().getNumber()));
				groupPanel.setLayout(new BorderLayout());
				groupPanel.add(new FeedbackPanel(markingMate, a), BorderLayout.CENTER);
				groupPanel.setOpaque(false);
				
				add(groupPanel, 0);
			}
		}
		markingMate.updateUI(this);
	}
	
}
