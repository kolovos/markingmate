package io.dimitris.markingmate.ui;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public abstract class DocumentChangeListener implements DocumentListener{
	
	@Override
	public void insertUpdate(DocumentEvent e) {
		textChanged();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		textChanged();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		textChanged();
	}
	
	public abstract void textChanged();
	
}
