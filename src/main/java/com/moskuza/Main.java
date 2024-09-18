package com.moskuza;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            int meteorAmount = 2;

            if (args.length > 0) {
                meteorAmount = Integer.parseInt(args[0]);
            }
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocation(300, 100);

            frame.add(new MyPanel(meteorAmount));

            frame.setVisible(true);
        });
    }
}