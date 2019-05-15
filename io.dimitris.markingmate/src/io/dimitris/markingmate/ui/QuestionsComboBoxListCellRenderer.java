package io.dimitris.markingmate.ui;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import io.dimitris.markingmate.Question;

public class QuestionsComboBoxListCellRenderer extends DefaultListCellRenderer {
	
	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		
		Question question = (Question) value;
		
		JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		if (question != null) {
			String text = question.getTitle() + " - " + question.getMarks() + " marks";
			if (question.getWeight() != 100) text += " - " + question.getWeight() + "%";
			label.setText(text);
		}
		return label;
	}
	
}
