package com.mmohaule.swingy.View.guiview;


import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import com.mmohaule.swingy.Controller.GuiManager;
import com.mmohaule.swingy.Model.GameCharacter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by zgodongw on 21.06.2018.
 */
public class GameViewGUI extends JPanel {

    private static final long serialVersionUID = 1L;
	private String[] directions = {"North", "East", "South", "West"};
    private JComboBox<String> directionsComboBox = new JComboBox<>(directions);
    private JButton moveButton = new JButton("Move");
    private JButton switchButton = new JButton("Switch to console");

    private JEditorPane infoPane = new JEditorPane();
    private JEditorPane mapPane = new JEditorPane();
    private GuiManager guiManager;
    private View game;


    public void start(GameCharacter hero) {
        guiManager = new GuiManager(this, 2);

        buildUI();

        game = new View(hero, this);
        game.runGame();
    }

    private void buildUI() {
        PlayGui.getFrame().setTitle("Game");
        this.setLayout(new GridBagLayout());
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        infoPane.setEditable(false);
        infoPane.setText("Game started\n");
        infoPane.setPreferredSize(new Dimension(220, 190));
        infoPane.setMinimumSize(new Dimension(200, 200));
        
        gbc.insets = new Insets(5, 5, 5, 5);

        mapPane.setEditable(false);
        mapPane.setText("Map");
        JScrollPane mapScroll = new JScrollPane(infoPane);
        mapScroll.setPreferredSize(new Dimension(300, 300));
        mapScroll.setMinimumSize(new Dimension(200, 200));
        // this.add(infoPane, gbc);
        this.add(mapScroll, gbc);

        directionsComboBox.setSelectedIndex(0);
        this.add(directionsComboBox, gbc);
        this.add(moveButton, gbc);
        this.add(switchButton, gbc);

        this.setVisible(true);
        PlayGui.getFrame().setContentPane(this);
        PlayGui.getFrame().revalidate();
        PlayGui.showFrame();

        moveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiManager.onMove((String) directionsComboBox.getSelectedItem(), game);
            }
        });
        switchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiManager.onSwitchButtonPressed();
            }
        });
    }

    public void gameFinished() {
        PlayGui.hideFrame();
        PlayGui.getFrame().dispose();
        PlayGui.closeConnections();
        System.exit(0);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(PlayGui.getFrame(), message);
    }

    public void getVillainCollisionInput() {
        Object options[] = {"Fight", "Run"};

        int result = JOptionPane.showOptionDialog(PlayGui.getFrame(),
                "You moved to position occupied by villain",
                "Fight or run?", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (result == JOptionPane.YES_OPTION)
            guiManager.onFight(game);
        else
            guiManager.onRun(game);
    }

    public boolean replaceArtifact(String replaceMessage) {
        Object options[] = {"Replace", "Leave"};

        int result = JOptionPane.showOptionDialog(PlayGui.getFrame(),
                "Would you like to replace " + replaceMessage + "?",
                "Replace or leave?", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        return result == JOptionPane.YES_OPTION;
    }

    public void pushMessage(String s) {
        try {
           Document doc = infoPane.getDocument();
           doc.insertString(doc.getLength(), "\n" + s, null);
        } catch(BadLocationException exc) {
           exc.printStackTrace();
        }
     }

    public void switchView() {
        PlayGui.hideFrame();
    }
}
