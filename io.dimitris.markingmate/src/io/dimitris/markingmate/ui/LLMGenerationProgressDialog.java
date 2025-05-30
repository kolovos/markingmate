package io.dimitris.markingmate.ui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Frame;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JProgressBar;

public class LLMGenerationProgressDialog extends JDialog {

    private JProgressBar progressBar;

    public LLMGenerationProgressDialog(Frame owner) {
        super(owner, "LLM Generation", Dialog.ModalityType.APPLICATION_MODAL);
        initializeComponents();
        setupLayout();
        configureDialog();
        setSize(300, 100);
    }

    private void initializeComponents() {
        progressBar = new JProgressBar(JProgressBar.HORIZONTAL);
        progressBar.setIndeterminate(true); // Set to indeterminate mode
        progressBar.setStringPainted(true); // Show text (e.g., "Loading...")
        progressBar.setString("Calling LLM...");

        // Add an empty border for padding around the progress bar
        progressBar.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Add the progress bar to the center of the dialog
        add(progressBar, BorderLayout.CENTER);
        
        pack(); // Adjusts size based on contents
    }

    private void configureDialog() {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(getOwner());
        setResizable(false);
    }

}
