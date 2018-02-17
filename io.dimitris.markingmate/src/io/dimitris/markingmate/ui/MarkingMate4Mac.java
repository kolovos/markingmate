package io.dimitris.markingmate.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.UIManager;

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
	protected void createToolbarAndMenus() {
		UnifiedToolBar toolbar = new UnifiedToolBar();
		toolbar.installWindowDraggerOnWindow(this);
		toolbar.disableBackgroundPainter();
		add(toolbar.getComponent(), BorderLayout.NORTH);
		toolbar.addComponentToLeft(getUnifiedToolBarButton(new OpenAction()));
		toolbar.addComponentToLeft(getUnifiedToolBarButton(new SaveAction()));
		toolbar.addComponentToRight(getUnifiedToolBarButton(new ExportAction()));
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
}
