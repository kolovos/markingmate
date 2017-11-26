package io.dimitris.markingmate.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import net.infonode.docking.properties.RootWindowProperties;
import net.infonode.docking.theme.DockingWindowsTheme;
import net.infonode.tabbedpanel.TabSelectTrigger;
import net.infonode.tabbedpanel.TabbedPanel;
import net.infonode.tabbedpanel.TabbedPanelProperties;
import net.infonode.tabbedpanel.TabbedUtils;
import net.infonode.tabbedpanel.theme.ClassicTheme;
import net.infonode.tabbedpanel.titledtab.TitledTabProperties;
import net.infonode.util.Direction;

/**
 * A theme with a "classic" look with round edges for the tabs.
 *
 * @author $Author: mpue $
 * @version $Revision: 1.3 $
 * @since IDW 1.2.0
 */
public class CustomDockingTheme extends DockingWindowsTheme {

	private RootWindowProperties rootWindowProperties = new RootWindowProperties();

	public CustomDockingTheme() {
		
		rootWindowProperties.getWindowAreaProperties().setBackgroundColor(null).setInsets(new Insets(0, 0, 0, 0))
		.setBorder(new EmptyBorder(10, 10, 10, 10));
		//https://github.com/mpue/blackboard/blob/master/src/net/infonode/docking/theme/ClassicDockingTheme.java
		/*
		
		ClassicTheme theme = new ClassicTheme();

		TabbedPanelProperties tabbedPanelProperties = theme.getTabbedPanelProperties();
		TitledTabProperties titledTabProperties = theme.getTitledTabProperties();

		Border insetsBorder = new Border() {
			public boolean isBorderOpaque() {
				return false;
			}

			public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
			}

			public Insets getBorderInsets(Component c) {
				TabbedPanel tp = TabbedUtils.getParentTabbedPanel(c);
				if (tp != null) {
					Direction d = tp.getProperties().getTabAreaOrientation();
					return new Insets(d == Direction.UP ? 2 : 0, d == Direction.LEFT ? 2 : 0,
							d == Direction.DOWN ? 2 : 0, d == Direction.RIGHT ? 2 : 0);
				}
				return new Insets(0, 0, 0, 0);
			}
		};

		// Tab window
		rootWindowProperties.getTabWindowProperties().getTabbedPanelProperties()
				.setTabSelectTrigger(TabSelectTrigger.MOUSE_PRESS).addSuperObject(tabbedPanelProperties)
				.getTabAreaComponentsProperties().getComponentProperties().setInsets(new Insets(0, 0, 0, 0));
		rootWindowProperties.getTabWindowProperties().getTabProperties().getTitledTabProperties()
				.addSuperObject(titledTabProperties);

		// Window bar
		rootWindowProperties.getWindowBarProperties().getTabWindowProperties().getTabbedPanelProperties()
				.addSuperObject(tabbedPanelProperties);
		rootWindowProperties.getWindowBarProperties().getTabWindowProperties().getTabProperties()
				.getTitledTabProperties().addSuperObject(titledTabProperties);

		rootWindowProperties.getWindowBarProperties().getTabWindowProperties().getTabbedPanelProperties()
				.getTabAreaComponentsProperties().getComponentProperties()
				.setBorder(new CompoundBorder(insetsBorder, theme.createTabBorder(true, false, true)));

		rootWindowProperties.getWindowBarProperties().getComponentProperties().setInsets(new Insets(0, 0, 2, 0));

		rootWindowProperties.getWindowBarProperties().getTabWindowProperties().getTabProperties()
				.getTitledTabProperties().getNormalProperties().getComponentProperties()
				.setBorder(theme.createInsetsTabBorder(true, false, true));

		// Tweak root window
		rootWindowProperties.getWindowAreaProperties().setBackgroundColor(null).setInsets(new Insets(0, 0, 0, 0))
				.setBorder(new EmptyBorder(10, 10, 10,
						10));
		rootWindowProperties.setDragRectangleBorderWidth(3);
		// rootWindowProperties.getViewProperties().getViewTitleBarProperties().getNormalProperties()
		// .getShapedPanelProperties()
		// .setDirection(Direction.DOWN);
		 * 
		 */
	}

	/**
	 * Gets the theme name
	 *
	 * @return name
	 */
	public String getName() {
		return "Classic Theme";
	}

	public RootWindowProperties getRootWindowProperties() {
		return rootWindowProperties;
	}
}