package io.dimitris.markingmate.ui;

import java.awt.Dimension;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import io.dimitris.markingmate.Answer;

public class RelatedFeedbackPanel extends JPanel {
	
	protected List<JEditorPane> editors = new ArrayList<JEditorPane>();
	
	public RelatedFeedbackPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setOpaque(false);
	}
	
	public void setAnswer(Answer answer) {
		ArrayList<Answer> answers = new ArrayList<Answer>(answer.getQuestion().getAnswers());
		answers.remove(answer);
		editors.clear();
		removeAll();
		
		//setBackground(new Color(223, 228, 234));
		//setOpaque(true);
		for (Answer a : answers) {
			
			JEditorPane editor = new JEditorPane();
			editor.setEditable(true);
			editor.setText("(" + a.getMarks() + " marks) " + a.getFeedback());
			editor.setBorder(new CompoundBorder(new CompoundBorder(new LineBorder(getBackground(), 5), new EtchedBorder()), new EmptyBorder(7,7,7,7)));
			editor.setMargin(new Insets(10, 10, 10, 10));
			//editor.setMaximumSize(getEditorDimension());
			//editor.setPreferredSize(getEditorDimension());
			editor.setMinimumSize(new Dimension(0, 0));
			add(editor);
			editors.add(editor);
			
		}
		updateUI();
	}
	
	protected Dimension getEditorDimension() {
		return new Dimension(getWidth(), 400);
	}
	
}
