package io.dimitris.markingmate.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import io.dimitris.markingmate.Answer;

public class AnswersPanel extends JPanel {
	
	protected List<JEditorPane> editors = new ArrayList<JEditorPane>();
	
	public AnswersPanel() {
		// this.setLayout(new RelativeLayout(RelativeLayout.Y_AXIS));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		/*
		this.addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent e) {}
			
			@Override
			public void componentResized(ComponentEvent e) {
				System.out.println(e.getSource());
				for (JEditorPane editor : editors) {
					editor.setMaximumSize(getEditorDimension());
					editor.setPreferredSize(getEditorDimension());
					editor.setMinimumSize(getEditorDimension());
					editor.updateUI();
					System.out.println(getWidth() + " - " + editor.getWidth());
				}
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {}
			
			@Override
			public void componentHidden(ComponentEvent e) {}
		});*/
	}
	
	public void setAnswer(Answer answer) {
		ArrayList<Answer> answers = new ArrayList<Answer>(answer.getQuestion().getAnswers());
		System.out.println(answers.size());
		answers.remove(answer);
		editors.clear();
		removeAll();
		JLabel label = new JLabel("Other answers");
		label.setFont(new Font(label.getFont().getName(), Font.BOLD, label.getFont().getSize()));
		label.setBorder(new EmptyBorder(7, 7, 0, 0));
		add(label);
		
		setBackground(new Color(223, 228, 234));
		setOpaque(true);
		for (Answer a : answers) {
			
			JEditorPane editor = new JEditorPane();
			editor.setEditable(true);
			editor.setText("(" + a.getMarks() + " marks) " + a.getFeedback());
			editor.setBorder(new CompoundBorder(new CompoundBorder(new LineBorder(new Color(223, 228, 234), 5), new LineBorder(new Color(165, 165, 165), 1, true)), new EmptyBorder(7,7,7,7)));
			editor.setMargin(new Insets(10, 10, 10, 10));
			//editor.setMaximumSize(getEditorDimension());
			//editor.setPreferredSize(getEditorDimension());
			editor.setMinimumSize(getEditorDimension());
			add(editor);
			editors.add(editor);
			
		}
		updateUI();
	}
	
	protected Dimension getEditorDimension() {
		return new Dimension(getWidth(), 400);
	}
	
}
