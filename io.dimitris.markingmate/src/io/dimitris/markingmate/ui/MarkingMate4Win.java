package io.dimitris.markingmate.ui;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class MarkingMate4Win extends MarkingMate {
	
	public static void main(String[] args) throws Exception {
		new MarkingMate4Win().run();
	}
	
	@Override
	protected void createToolbar() {
		JToolBar toolbar = new JToolBar();
		toolbar.setRollover(true);
		toolbar.setFloatable(false);
		add(toolbar, BorderLayout.NORTH);
		toolbar.add(openAction);
		toolbar.add(saveAction);
		toolbar.add(exportAction);
	}
	
	@Override
	protected void setupLookAndFeel() throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	}
	
	@Override
	protected void finetuneUI() {
		studentsTable.setShowGrid(false);
		studentsTable.setRowHeight(24);
	}
	
	@Override
	protected JComponent getQuestionsComboxPanel() {
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(3, 1, 2, 1));
		panel.setLayout(new BorderLayout());
		panel.add(questionsComboBox, BorderLayout.CENTER);
		return panel;
	}
	
	@Override
	public void finetuneMarksPanel(JPanel marksPanel) {
		marksPanel.setBorder(new EmptyBorder(3,0,0,0));		
	}

	@Override
	public void finetuneFeedbackTextAreaScrollPane(JScrollPane feedbackTextAreaScrollPane) {}

}
