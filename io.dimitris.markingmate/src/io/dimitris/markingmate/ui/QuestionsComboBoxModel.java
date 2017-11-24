package io.dimitris.markingmate.ui;

import javax.swing.DefaultComboBoxModel;

import io.dimitris.markingmate.Question;

public class QuestionsComboBoxModel extends DefaultComboBoxModel<Question> {
	
	protected App app;
	
	public QuestionsComboBoxModel(App app) {
		this.app = app;
	}
	
	@Override
	public Question getElementAt(int index) {
		return app.getExam().getQuestions().get(index);
	}
	
	@Override
	public int getSize() {
		return app.getExam().getQuestions().size();
	}
}
