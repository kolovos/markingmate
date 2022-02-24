package io.dimitris.markingmate.ui;

import java.awt.BorderLayout;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatLightLaf;

public class MarkingMate4Mac extends MarkingMate {
	
	public static void main(String[] args) throws Exception {
		new MarkingMate4Win().run();
	}
	
	@Override
	protected void createToolbar() {
		JToolBar toolbar = new JToolBar();
		toolbar.setMargin(new Insets(3, 3, 3, 3));
		//toolbar.setRollover(true);
		toolbar.setFloatable(false);
		add(toolbar, BorderLayout.NORTH);
		toolbar.add(new OpenAction(true));
		toolbar.add(new SaveAction(true));
		// toolbar.add(new MergeAction(true));
		toolbar.add(new ExportAction(true));
	}
	
	@Override
	protected void setupLookAndFeel() throws Exception {
		FlatLightLaf.setup();
		UIManager.setLookAndFeel(new FlatLightLaf());
		//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	}
	
	@Override
	protected void finetuneUI() {
		//studentsTable.setShowGrid(false);
		//studentsTable.setRowHeight(24);
	}
	
	@Override
	protected JComponent getQuestionsComboxPanel() {
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(0, 2, 2, 2));
		panel.setLayout(new BorderLayout());
		panel.add(questionsComboBox, BorderLayout.CENTER);
		return panel;
		//return questionsComboBox;
	}
	
	@Override
	public void finetuneMarksPanel(JPanel marksPanel) {
		marksPanel.setBorder(new EmptyBorder(3,0,0,0));		
	}

	@Override
	public void finetuneFeedbackTextAreaScrollPane(JScrollPane feedbackTextAreaScrollPane) {}
	
}
