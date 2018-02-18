package io.dimitris.markingmate.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import com.inet.jortho.SpellChecker;

import io.dimitris.markingmate.Answer;

public class FeedbackPanel extends JPanel {
	
	protected Answer answer = null;
	protected FeedbackTextArea feedbackTextArea;
	protected JTextField marksTextField;
	protected boolean notificationsEnabled = true;
	
	public FeedbackPanel(MarkingMate markingMate, Answer answer) {
		this.setLayout(new BorderLayout());
		this.setBorder(new EmptyBorder(2,2,0,2));
		feedbackTextArea = new FeedbackTextArea(answer);
		feedbackTextArea.setFont(new JEditorPane().getFont());
		feedbackTextArea.setLineWrap(true);
		feedbackTextArea.setWrapStyleWord(true);
		feedbackTextArea.setMinimumSize(new Dimension(0, 0));
		feedbackTextArea.setBorder(new EmptyBorder(7,7,7,7));
		feedbackTextArea.getDocument().addDocumentListener(new DocumentChangeListener() {
			
			@Override
			public void textChanged() {
				if (notificationsEnabled && FeedbackPanel.this.answer != null) {
					FeedbackPanel.this.answer.setFeedback(feedbackTextArea.getText());
				}
			}
		});
		
		JPanel marksPanel = new JPanel(new BorderLayout());
		markingMate.finetuneMarksPanel(marksPanel);
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
		
		JScrollPane feedbackTextAreaScrollPane = new JScrollPane(feedbackTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		markingMate.finetuneFeedbackTextAreaScrollPane(feedbackTextAreaScrollPane);
		add(feedbackTextAreaScrollPane, BorderLayout.CENTER);
		add(marksPanel, BorderLayout.SOUTH);
		setOpaque(false);
		
		SpellChecker.register(feedbackTextArea);
		
		setAnswer(answer);
		
	}
	
	public void setAnswer(Answer answer) {
		this.answer = answer;
		if (answer != null) {
			notificationsEnabled = false;
			feedbackTextArea.setAnswer(answer);
			marksTextField.setText(answer.getMarks() + "");
			notificationsEnabled = true;
		}
	}
	
	public Answer getAnswer() {
		return answer;
	}
	
}
