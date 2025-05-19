package io.dimitris.markingmate.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.eclipse.epsilon.eol.EolModule;

import com.inet.jortho.SpellChecker;

import io.dimitris.markingmate.Answer;

public class FeedbackPanel extends JPanel {

	protected enum MarksFieldContentMode {
		SHOW_MARKS, SHOW_EXPRESSION;

		protected void styleMarksField(JTextField marksTextField) {
			Font originalFont = marksTextField.getFont();
			marksTextField.setFont(new Font(
				originalFont.getName(),
				this == SHOW_MARKS ? Font.ITALIC : Font.PLAIN,
				originalFont.getSize()));

			marksTextField.setToolTipText(this == SHOW_MARKS
				? "Computed marks (click to enter source expression)"
				: "Marks expression (supports + - * /, truncated to an integer)"
			);
		}
	}

	protected class ExpressionMarksSwitchingListener implements FocusListener {
		@Override
		public void focusLost(FocusEvent e) {
			if (answer != null) {
				notificationsEnabled = false;
				marksTextField.setText(answer.getMarks() + "");
				MarksFieldContentMode.SHOW_MARKS.styleMarksField(marksTextField);
				notificationsEnabled = true;
			}
		}
		@Override
		public void focusGained(FocusEvent e) {
			if (answer != null) {
				notificationsEnabled = false;
				marksTextField.setText(answer.getMarksExpression());
				MarksFieldContentMode.SHOW_EXPRESSION.styleMarksField(marksTextField);
				notificationsEnabled = true;
			}
		}
	}

	protected class CalculateMarksListener extends DocumentChangeListener {
		@Override
		public void textChanged() {
			if (notificationsEnabled && answer != null) {
				try {
					String expr = marksTextField.getText();
					answer.setMarksExpression(expr);

					if (!expr.trim().isEmpty()) {
						EolModule mod = new EolModule();
						mod.parse("return (" + expr + ").asInteger();");
						Integer result = (Integer) mod.execute();
						if (result != null) {
							answer.setMarks(result);
						}
					}
				}
				catch (Exception ex) {}
			}
		}
	}

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
		marksTextField.getDocument().addDocumentListener(new CalculateMarksListener());
		marksTextField.addFocusListener(new ExpressionMarksSwitchingListener());
		marksTextField.setToolTipText("Computed marks for the question (click to enter arithmetic expression)");
		marksPanel.add(marksTextField, BorderLayout.CENTER);

		JScrollPane feedbackTextAreaScrollPane = new JScrollPane(feedbackTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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
			MarksFieldContentMode.SHOW_MARKS.styleMarksField(marksTextField);
			notificationsEnabled = true;
		}
	}
	
	public Answer getAnswer() {
		return answer;
	}
}
