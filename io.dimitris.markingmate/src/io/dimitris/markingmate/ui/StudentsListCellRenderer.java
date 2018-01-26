package io.dimitris.markingmate.ui;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import io.dimitris.markingmate.Exam;
import io.dimitris.markingmate.Student;

public class StudentsListCellRenderer extends DefaultListCellRenderer {
	
	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		
		Student student = (Student) value;
		
		JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		if (student != null) {
			
			String marks = "(" + student.getAnswers().stream().mapToInt(f -> f.getMarks()).sum() + "/" + ((Exam) student.eContainer()).getQuestions().stream().mapToInt(f -> f.getMarks()).sum() + ")";
			String answered = "(" + student.getAnswers().stream().filter(f -> !f.getFeedback().isEmpty()).count() + "/" + ((Exam) student.eContainer()).getQuestions().size() + ")";
			
			label.setText(student.getNumber() + " " + answered + " " + marks);
		}
		return label;
	}
	
}
