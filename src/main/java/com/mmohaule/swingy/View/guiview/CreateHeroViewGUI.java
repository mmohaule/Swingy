package com.mmohaule.swingy.View.guiview;

import javax.swing.*;

import com.mmohaule.swingy.Controller.GuiManager;
import com.mmohaule.swingy.Model.GameCharacter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by zgodongw on 18.06.2018.
 */
public class CreateHeroViewGUI extends JPanel {

    private static final long serialVersionUID = 1L;
	private JLabel heroNameLabel = new JLabel("Hero name:");
    private JTextField heroNameField = new JTextField(10);
    private JButton createHeroButton = new JButton("Create Hero");
    private String[] heroClasses = {"Knight", "Ninja"};
    private JLabel heroClass = new JLabel("Class:");
    private JComboBox<String> classesComboBox = new JComboBox<>(heroClasses);
    private JEditorPane infoPane = new JEditorPane();

    private GuiManager guiManager;
    //private CreateHeroController controller;

    public void start() {
        guiManager = new GuiManager();
        
        buildUI();
    }

    private void buildUI() {
        PlayGui.getFrame().setTitle("Create Hero");
        this.setLayout(new GridBagLayout());
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JPanel createHeroPanel = new JPanel();
        createHeroPanel.add(heroNameLabel);
        createHeroPanel.add(heroNameField);
        createHeroPanel.setVisible(true);
        this.add(createHeroPanel, gbc);

        JPanel classPannel = new JPanel();
        classPannel.add(heroClass);
        classesComboBox.setSelectedIndex(0);
        classPannel.add(classesComboBox);
        classPannel.setVisible(true);
        this.add(classPannel, gbc);

        infoPane.setEditable(false);
        infoPane.setFont(new Font("monospaced", Font.PLAIN, 12));
        infoPane.setText("      attack  defense  hp\n" +
                "Knight  30    15    150\n" +
                "Ninja   50    10     90\n");
        infoPane.setPreferredSize(new Dimension(200, 120));
        infoPane.setMinimumSize(new Dimension(200, 120));
        this.add(infoPane, gbc);

        this.add(createHeroButton, gbc);
        this.setVisible(true);

        PlayGui.getFrame().setContentPane(this);
        PlayGui.getFrame().revalidate();
        PlayGui.showFrame();

        createHeroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
					guiManager.onCreateButtonPressed(heroNameField.getText(), (String) classesComboBox.getSelectedItem());
				} catch (Exception e1) {
					showErrorMessage(e1.getMessage());
				}
            }
        });
    }

    
    
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(PlayGui.getFrame(), message);
    }

    public void openGame(GameCharacter hero) {
        this.setVisible(false);
        new GameViewGUI().start(hero);
    }
}
