package io.dimitris.markingmate.ui;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import io.dimitris.markingmate.Student;

public class StudentsTableModel implements TableModel {
	
	protected MarkingMate app;
	
	public StudentsTableModel(MarkingMate app) {
		this.app = app;
	}

	@Override
	public int getRowCount() {
		return app.getExam().getStudents().size();
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public String getColumnName(int columnIndex) {
		if (columnIndex ==0) return "Number";
		else if (columnIndex == 2) return "Total Marks";
		else return "Marked";
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Student student = app.getExam().getStudents().get(rowIndex);
		if (columnIndex == 0) return student.getNumber();
		else if (columnIndex == 2) return student.getAnswers().stream().mapToInt(f -> f.getMarks()).sum();
		else return student.getAnswers().stream().filter(f -> !f.getFeedback().isEmpty()).count();
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {}

	@Override
	public void addTableModelListener(TableModelListener l) {}

	@Override
	public void removeTableModelListener(TableModelListener l) {}
	
	
}
