package com.mmohaule.swingy.View.guiview;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.mmohaule.swingy.Database.DataBase;

/**
 * PlayGui
 */
public class PlayGui {

    private static JFrame frame;
    StartViewGUI startViewGUI;

    public void runGame() {
        System.out.println("Starting Game... ");
        startViewGUI = new StartViewGUI();
        startViewGUI.start();
    }

    public static JFrame getFrame() {
        if (frame == null) {
            frame = new JFrame();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setSize(500, 400);
            frame.setLocationRelativeTo(null);
            frameListener();
        }
        return frame;
    }

    public static void showFrame() {
        if (frame != null)
            frame.setVisible(true);
    }

    public static void hideFrame() {
        if (frame != null)
            frame.setVisible(false);
    }

    private static void frameListener() {
        getFrame().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeConnections();
                super.windowClosing(e);
            }
        });
    }

    public static void closeConnections() {
        DataBase.close();
    }
}