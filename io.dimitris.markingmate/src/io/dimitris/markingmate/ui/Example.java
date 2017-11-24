package io.dimitris.markingmate.ui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.xml.stream.Location;

public class Example {

    public void createPlayground() {
        JFrame frame = new JFrame("ForFun Maze");
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1));
        buttonPanel.setPreferredSize(new Dimension(100, 600));
        buttonPanel.setMaximumSize(new Dimension(100, 600));

        JButton reset = new JButton();
        reset.setText("Reset");
        reset.setSize(100, 180);
        JButton pause = new JButton();
        pause.setText("Pause");
        pause.setSize(100, 180);
        JButton quit = new JButton();
        quit.setText("Quit");
        quit.setSize(100, 180);

        buttonPanel.add(pause);
        buttonPanel.add(reset);
        buttonPanel.add(quit);

        Location[][] array = null;
        JPanel centerPanel = new JPanel();
        centerPanel.setPreferredSize(new Dimension(600, 600));
        centerPanel.setMinimumSize(new Dimension(600, 600));

        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(100, 600));
        JPanel northPanel = new JPanel();
        northPanel.setPreferredSize(new Dimension(800, 100));
        JPanel bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(800, 100));

        frame.add(northPanel, BorderLayout.NORTH);
        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.EAST);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        System.out.println("Size of centerpane" + centerPanel.getWidth() + "x" + centerPanel.getHeight());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new Example().createPlayground();
            }
        });
    }
}