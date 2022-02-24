package io.dimitris.markingmate.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.SystemColor;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.inet.jortho.SpellChecker;

import io.dimitris.markingmate.Answer;

public class FeedbackPanel extends JPanel {
	
	protected Answer answer = null;
	protected FeedbackTextArea feedbackTextArea;
	protected JTextField marksTextField;
	protected boolean notificationsEnabled = true;
	protected JLabel descriptionLabel;
	protected boolean showDescription;
	protected JPanel marksPanel;
	
	public FeedbackPanel(MarkingMate markingMate, Answer answer) {
		this(markingMate, answer, false);
	}	
	
	public FeedbackPanel(MarkingMate markingMate, Answer answer, boolean showDescription) {
		this.showDescription = showDescription;
		this.setLayout(new BorderLayout());
		this.setBorder(new EmptyBorder(2,2,0,2));
		
		if (showDescription) {
			descriptionLabel = new JLabel();
		}
		
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
		
		marksPanel = new JPanel(new BorderLayout());
		markingMate.finetuneMarksPanel(marksPanel);
		JLabel marksLabel = new JLabel("Marks:");
		marksPanel.add(marksLabel, BorderLayout.WEST);
		marksLabel.setBorder(new EmptyBorder(0, 0, 0, 3));
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
		
		JScrollPane feedbackTextAreaScrollPane = new JScrollPane(feedbackTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		markingMate.finetuneFeedbackTextAreaScrollPane(feedbackTextAreaScrollPane);
		if (showDescription) add(descriptionLabel, BorderLayout.NORTH);
		add(feedbackTextAreaScrollPane, BorderLayout.CENTER);
		add(marksPanel, BorderLayout.SOUTH);
		
		SpellChecker.register(feedbackTextArea);
		
		setAnswer(answer);
		
	}
	
	public void setAnswer(Answer answer) {
		this.answer = answer;
		if (answer != null) {
			notificationsEnabled = false;
			
			marksPanel.setVisible(answer.getQuestion().getMarks() > 0);
			
			if (showDescription) {
				if (answer.getQuestion().getDescription() != null && answer.getQuestion().getDescription().length() > 0) { 
					descriptionLabel.setBorder(new EmptyBorder(0, 4, 8, 4));
					descriptionLabel.setText("<html>" + answer.getQuestion().getDescription() + "</html>");
				}
				else { 
					descriptionLabel.setBorder(new EmptyBorder(0, 0, 0, 0));
					descriptionLabel.setText("");
				}
			}
			feedbackTextArea.setAnswer(answer);
			marksTextField.setText(answer.getMarks() + "");
			notificationsEnabled = true;
		}
	}
	
	public Answer getAnswer() {
		return answer;
	}
	
}
