package io.dimitris.markingmate.ui;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;

public class MarkingMate4MacSimple extends MarkingMate {
	
	public static void main(String[] args) throws Exception {
		new MarkingMate4MacSimple().run();
	}
	
	@Override
	protected void setupLookAndFeel() throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	}
	
	@Override
	protected void createToolbar() {}
	
	@Override
	protected void finetuneUI() {}
	
	@Override
	protected JComponent getQuestionsComboxPanel() {
		return questionsComboBox;
	}
	
	@Override
	public void finetuneMarksPanel(JPanel marksPanel) {}
	
	@Override
	public void finetuneFeedbackTextAreaScrollPane(JScrollPane feedbackTextAreaScrollPane) {
		feedbackTextAreaScrollPane.setBorder(new EtchedBorder());	
	}
}
