package io.dimitris.markingmate.ui;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

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
		
		for (Answer a : answers) {
			
			JEditorPane editor = new JEditorPane();
			editor.setEditable(true);
			editor.setText(a.getFeedback());
			editor.setBorder(new EtchedBorder());
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
