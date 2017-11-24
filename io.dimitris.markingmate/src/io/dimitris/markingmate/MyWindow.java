package io.dimitris.markingmate;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

public class MyWindow extends JFrame {

    public MyWindow() {
        this.setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.BLUE);
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.CYAN);
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.GREEN);

        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, centerPanel);
        JSplitPane sp2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sp, rightPanel);

        this.add(sp2, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(500, 500);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run() {
                new MyWindow();
            }
        });
    }
}