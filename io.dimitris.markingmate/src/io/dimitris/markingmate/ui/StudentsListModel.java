package io.dimitris.markingmate.ui;

import javax.swing.DefaultListModel;

import io.dimitris.markingmate.Question;
import io.dimitris.markingmate.Student;

public class StudentsListModel extends DefaultListModel<Student> {
	
	protected App app;
	
	public StudentsListModel(App app) {
		this.app = app;
	}
	
	@Override
	public Student getElementAt(int index) {
		return app.getExam().getStudents().get(index);
	}
	
	@Override
	public int getSize() {
		return app.getExam().getStudents().size();
	}
}
