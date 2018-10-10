package com.mmohaule.swingy.View.guiview;

import javax.swing.*;

import com.mmohaule.swingy.Controller.GuiManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by zgodongw on 18.06.2018.
 */
public class StartViewGUI extends JPanel {

    private static final long serialVersionUID = 1L;
	private JButton createHeroButton = new JButton("Create Hero");
    private JButton selectHeroButton = new JButton("Select Hero");
    private JButton switchViewButton = new JButton("Switch to console");
    private GuiManager guiManager; 

    public void start() {
        guiManager = new GuiManager();

        buildUI();
    }

    public void buildUI() {
        PlayGui.getFrame().setTitle("Start");
        this.setLayout(new GridBagLayout());
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        this.add(createHeroButton, gbc);
        this.add(selectHeroButton, gbc);
        this.add(switchViewButton, gbc);

        this.setVisible(true);
        PlayGui.getFrame().setContentPane(this);
        PlayGui.getFrame().revalidate();
        PlayGui.showFrame();

        createHeroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiManager.onCreateHeroButtonPressed();
            }
        });
        selectHeroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiManager.onSelectHeroButtonPressed();
            }
        });
        switchViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiManager.onSwitchButtonPressed();
            }
        });
    }

    public void openCreateHero() {
        this.setVisible(false);
        new CreateHeroViewGUI().start();
    }


    public void switchView() {
        PlayGui.hideFrame();
    }

 
    public void openSelectHero() {
        this.setVisible(false);
        new SelectHeroViewGUI().start();
    }
}
