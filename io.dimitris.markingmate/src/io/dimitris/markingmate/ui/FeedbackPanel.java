package io.dimitris.markingmate.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.eclipse.epsilon.eol.EolModule;

import com.inet.jortho.SpellChecker;

import io.dimitris.markingmate.Answer;
import io.dimitris.markingmate.MarkingmatePackage;

public class FeedbackPanel extends JPanel {
	
	protected Answer answer = null;
	protected FeedbackTextArea feedbackTextArea;
	protected JTextField marksTextField, expressionTextField;
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

        // Create the JPanel with Grid Layout
        marksPanel = new JPanel(new GridLayout(2, 2, 8, 4)); // 2 rows, 2 columns, 10px horizontal/vertical gap

        // Add labels and text fields for "Marks"
        JLabel marksLabel = new JLabel("Marks:");
        marksTextField = new JTextField();

        // Add labels and text fields for "Marks Expression"
        JLabel expressionLabel = new JLabel("Expression:");
        expressionTextField = new JTextField();

        // Add components to the panel
        marksPanel.add(marksLabel);
        marksPanel.add(expressionLabel);
        marksPanel.add(marksTextField);
        marksPanel.add(expressionTextField);

		marksTextField.getDocument().addDocumentListener(new DocumentChangeListener() {
			@Override
			public void textChanged() {
				if (notificationsEnabled && FeedbackPanel.this.answer != null) {
					try {
						int newMarks = Integer.parseInt(marksTextField.getText());
						FeedbackPanel.this.answer.setMarks(newMarks);
					}
					catch (Exception ex) {}

					updateEditableFields();
				}
			}
		});
		expressionTextField.getDocument().addDocumentListener(new DocumentChangeListener() {
			@Override
			public void textChanged() {
				if (notificationsEnabled && FeedbackPanel.this.answer != null) {
					try {
						String expr = expressionTextField.getText();
						FeedbackPanel.this.answer.setMarksExpression(expr);
						if (!expr.trim().isEmpty()) {
							EolModule mod = new EolModule();
							mod.parse("return (" + expr + ").asInteger();");
							Integer result = (Integer) mod.execute();
							if (result != null) {
								marksTextField.setText(result.toString());
							}
						}
					}
					catch (Exception ex) {}

					updateEditableFields();
				}
			}
		});

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
			expressionTextField.setText(answer.getMarksExpression());
			updateEditableFields();

			notificationsEnabled = true;
		}
	}
	
	public Answer getAnswer() {
		return answer;
	}

	protected void updateEditableFields() {
		String expr = answer.getMarksExpression();
		boolean hasExpression = expr != null && !expr.trim().isEmpty();

		// For marks to be editable, the expression must be blank or missing
		marksTextField.setEditable(!hasExpression);

		// For the expression to be editable, we must have an expression or have no marks / default zero mark
		expressionTextField.setEditable(
			hasExpression || marksTextField.getText().trim().isEmpty() || answer.getMarks() == 0);
	}

}
