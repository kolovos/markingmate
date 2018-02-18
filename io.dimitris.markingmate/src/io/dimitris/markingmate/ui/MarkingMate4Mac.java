package io.dimitris.markingmate.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;

import com.explodingpixels.macwidgets.MacButtonFactory;
import com.explodingpixels.macwidgets.MacUtils;
import com.explodingpixels.macwidgets.UnifiedToolBar;

public class MarkingMate4Mac extends MarkingMate {
	
	public static void main(String[] args) throws Exception {
		new MarkingMate4Mac().run();
	}
	
	@Override
	protected void setupLookAndFeel() throws Exception {
		System.setProperty("Quaqua.tabLayoutPolicy", "wrap");
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		MacUtils.makeWindowLeopardStyle(getRootPane());
		getRootPane().putClientProperty("apple.awt.brushMetalLook", true);
	}
	
	@Override
	protected void createToolbar() {
		UnifiedToolBar toolbar = new UnifiedToolBar();
		toolbar.installWindowDraggerOnWindow(this);
		toolbar.disableBackgroundPainter();
		add(toolbar.getComponent(), BorderLayout.NORTH);
		toolbar.addComponentToLeft(getUnifiedToolBarButton(openAction));
		toolbar.addComponentToLeft(getUnifiedToolBarButton(saveAction));
		toolbar.addComponentToRight(getUnifiedToolBarButton(exportAction));
	}
	
	protected AbstractButton getUnifiedToolBarButton(AbstractAction action) {
		JButton button = new JButton(action);
		button.setActionCommand("pressed");
		Dimension d = new Dimension(48,48);
		button.setPreferredSize(d);
		button.setMinimumSize(d);
		button.setMaximumSize(d);
		button.putClientProperty("JButton.buttonType", "textured");
		return MacButtonFactory.makeUnifiedToolBarButton(button);
	}
	
	@Override
	protected void finetuneUI() {
		
	}
	
	@Override
	protected JComponent getQuestionsComboxPanel() {
		return questionsComboBox;
	}
	
	@Override
	public void finetuneMarksPanel(JPanel marksPanel) {
		
	}
	
	@Override
	public void finetuneFeedbackTextAreaScrollPane(JScrollPane feedbackTextAreaScrollPane) {
		feedbackTextAreaScrollPane.setBorder(new EtchedBorder());	
	}
}
