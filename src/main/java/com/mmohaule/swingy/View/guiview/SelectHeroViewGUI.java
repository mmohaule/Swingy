package com.mmohaule.swingy.View.guiview;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.mmohaule.swingy.Controller.GuiManager;
import com.mmohaule.swingy.Model.GameCharacter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by zgodongw on 22.06.2018.
 */
public class SelectHeroViewGUI extends JPanel {

    private static final long serialVersionUID = 1L;
	private JEditorPane infoPane = new JEditorPane();
    private JButton selectButton = new JButton("Select");
    private JButton createButton = new JButton("Create");
    private GuiManager guiManager;

    private int lastSelectedIdx;

    public void start() {
        guiManager = new GuiManager(this, 1);

        buildUI();
    }

    /**
	 * @return the lastSelectedIdx
	 */
	public int getLastSelectedIdx() {
		return lastSelectedIdx;
	}

	/**
	 * @param lastSelectedIdx the lastSelectedIdx to set
	 */
	public void setLastSelectedIdx(int lastSelectedIdx) {
		this.lastSelectedIdx = lastSelectedIdx;
	}

	private void buildUI() {
        PlayGui.getFrame().setTitle("Select Hero");
        this.setLayout(new GridBagLayout());
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        String[] data = guiManager.getListData();
        final JList list = new JList(data);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        JScrollPane listScroll = new JScrollPane(list);
        listScroll.setPreferredSize(new Dimension(200, 200));
        listScroll.setMinimumSize(new Dimension(150, 150));
        this.add(listScroll);

        infoPane.setEditable(false);
        infoPane.setText("Select hero to see information");
        if (data.length == 0)
            infoPane.setText("No saved heroes");
        JScrollPane infoScroll = new JScrollPane(infoPane);
        infoScroll.setPreferredSize(new Dimension(200, 200));
        infoScroll.setMinimumSize(new Dimension(150, 150));
        this.add(infoScroll, gbc);

        this.add(selectButton, gbc);
        this.add(createButton, gbc);
        selectButton.setEnabled(false);

        this.setVisible(true);

        PlayGui.getFrame().setContentPane(this);
        PlayGui.getFrame().revalidate();
        PlayGui.showFrame();

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    if (list.getSelectedIndex() != -1) {
                        guiManager.onListElementSelected(list.getSelectedIndex());
                        selectButton.setEnabled(true);
                        setLastSelectedIdx(list.getSelectedIndex());
                    } else
                        selectButton.setEnabled(false);
                }
            }
        });
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiManager.onSelectButtonPressed(lastSelectedIdx);
            }
        });
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiManager.onCreateButtonPressed();
            }
        });
    }

    public void updateInfo(String info) {
        infoPane.setText(info);
    }

   
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(PlayGui.getFrame(), message);
    }

    
    public void openGame(GameCharacter hero) {
        this.setVisible(false);
        new GameViewGUI().start(hero);
    }

    
    public void openCreateHero() {
        this.setVisible(false);
        new CreateHeroViewGUI().start();
    }
}
