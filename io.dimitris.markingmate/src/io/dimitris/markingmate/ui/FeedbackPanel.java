package io.dimitris.markingmate.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import com.inet.jortho.SpellChecker;

import io.dimitris.markingmate.Answer;

public class FeedbackPanel extends JPanel {
	
	protected Answer answer = null;
	protected JTextArea feedbackEditorPane;
	protected JTextField marksTextField;
	protected boolean notificationsEnabled = true;
	
	public FeedbackPanel(Answer answer) {
		this.setLayout(new BorderLayout());
		feedbackEditorPane = new JTextAreaWithUndo();
		feedbackEditorPane.setLineWrap(true);
		feedbackEditorPane.setWrapStyleWord(true);
		feedbackEditorPane.setMinimumSize(new Dimension(0, 0));
		feedbackEditorPane.setBorder(new CompoundBorder(new EtchedBorder(), new EmptyBorder(7,7,7,7)));
		feedbackEditorPane.getDocument().addDocumentListener(new DocumentChangeListener() {
			
			@Override
			public void textChanged() {
				if (notificationsEnabled && FeedbackPanel.this.answer != null) {
					FeedbackPanel.this.answer.setFeedback(feedbackEditorPane.getText());
				}
			}
		});
		
		JPanel marksPanel = new JPanel(new BorderLayout());
		JLabel marksLabel = new JLabel("Marks:");
		marksPanel.add(marksLabel, BorderLayout.WEST);
		marksTextField = new JTextField();
		marksTextField.getDocument().addDocumentListener(new DocumentChangeListener() {
			
			@Override
			public void textChanged() {
				if (notificationsEnabled && FeedbackPanel.this.answer != null) {
					try {
						FeedbackPanel.this.answer.setMarks(Integer.parseInt(marksTextField.getText()));
					}
					catch (Exception ex) {}
				}
			}
		});
		marksPanel.add(marksTextField, BorderLayout.CENTER);
		marksPanel.setOpaque(false);
		
		if (answer!= null && answer.getQuestion().getMarks() == 0) {
			marksPanel.setVisible(false);
		}
		
		add(feedbackEditorPane, BorderLayout.CENTER);
		add(marksPanel, BorderLayout.SOUTH);
		setOpaque(false);
		
		SpellChecker.register(feedbackEditorPane);
		
		setAnswer(answer);
		
	}
	
	public void setAnswer(Answer answer) {
		this.answer = answer;
		if (answer != null) {
			notificationsEnabled = false;
			feedbackEditorPane.setText(answer.getFeedback());
			marksTextField.setText(answer.getMarks() + "");
			notificationsEnabled = true;
		}
	}
	
	public Answer getAnswer() {
		return answer;
	}
	
}
