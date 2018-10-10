package com.mmohaule.swingy.Controller;

import java.util.ArrayList;

import javax.swing.JPanel;

import com.mmohaule.swingy.Database.DataBase;
import com.mmohaule.swingy.Model.GameCharacter;
import com.mmohaule.swingy.View.guiview.CreateHeroViewGUI;
import com.mmohaule.swingy.View.guiview.GameViewGUI;
import com.mmohaule.swingy.View.guiview.SelectHeroViewGUI;
import com.mmohaule.swingy.View.guiview.StartViewGUI;
import com.mmohaule.swingy.View.guiview.View;

/**
 * GuiManager
 */
public class GuiManager {

    private StartViewGUI startView;
    private SelectHeroViewGUI selectHeroViewGUI;
    private CreateHeroViewGUI createHeroViewGUI;
    private GameViewGUI gameViewGUI;

    public GuiManager() {
        this.startView = new StartViewGUI();
        this.selectHeroViewGUI = new SelectHeroViewGUI();
        this.createHeroViewGUI = new CreateHeroViewGUI();
        this.gameViewGUI = new GameViewGUI();
    }

    public GuiManager(JPanel view, int choice) {
        switch (choice) {
            case 1:
                this.selectHeroViewGUI = (SelectHeroViewGUI) view;
                break;
            case 2:
                this.gameViewGUI = (GameViewGUI) view;
                break;
            default:
                this.startView = new StartViewGUI();
                this.selectHeroViewGUI = new SelectHeroViewGUI();
                this.createHeroViewGUI = new CreateHeroViewGUI();
                this.gameViewGUI = new GameViewGUI();
                break;
        }
    }

    /*
     * StartView
     */
    public void onCreateHeroButtonPressed() {
        startView.openCreateHero();
    }

    public void onSwitchButtonPressed() {
        //startView.switchView();
    }

    public void onSelectHeroButtonPressed() {
        startView.openSelectHero();
    }

    /*
     * SelectHero
     */

    public void onListElementSelected(int idx) {
        GameCharacter hero = DataBase.selectHeroById(idx + 1);
        selectHeroViewGUI.updateInfo(hero.toString());
    }

    public String[] getListData() {
        ArrayList<String> list = DataBase.selectAll();
        String[] listArr = new String[list.size()];
        listArr = list.toArray(listArr);
        return listArr;
    }

    public void onSelectButtonPressed(int idx) {
        GameCharacter hero;
        try {
            hero = DataBase.selectHeroById(idx + 1);
            hero.validateHero();
        } catch (Exception e) {
            selectHeroViewGUI.showErrorMessage(e.getMessage());
            return;
        }
        selectHeroViewGUI.openGame(hero);
    }

    public void onCreateButtonPressed() {
        selectHeroViewGUI.openCreateHero();
    }

    /*
     * CreateHero 
     */
    public void onCreateButtonPressed(String name, String heroClass) throws Exception {
        GameCharacter hero;

        GameCharacterFactory factoryChar = new GameCharacterFactory();
        int type;
        if (heroClass.equals("Ninja")) {
            type = 1;
        } else {
            type = 2;
        }

        try {
            hero = factoryChar.createHero(name, type);
        }
        catch(Exception e) {
            createHeroViewGUI.showErrorMessage(e.getMessage());
            return ;
        }
        createHeroViewGUI.openGame(hero);
    }
    
    /*
     * GameView 
     */
    public void onStart() {
    }

    public void onMove(String direction, View game) {
       
        switch (direction.toUpperCase()) {

            case "NORTH":
                gameViewGUI.pushMessage("You moved North"); 
                if (game.winCondition() == false) {
                    winGame();
                }

                game.move(1);
                game.updateHeroPosition();
                break ;
            case "EAST":
                gameViewGUI.pushMessage("You moved East");
                if (game.winCondition() == false) {
                    winGame();
                }

                game.move(2);
                game.updateHeroPosition();
                break ;
            case "SOUTH":
                gameViewGUI.pushMessage("You moved South");
                if (game.winCondition() == false) {
                    winGame();
                }
                game.move(3);
                game.updateHeroPosition();
                break ;
            case "WEST":
                gameViewGUI.pushMessage("You moved West");
                if (game.winCondition() == false) {
                    winGame();
                }

                game.move(4);
                game.updateHeroPosition();
                break ;
        }
    }

    private void winGame() {
        gameViewGUI.showMessage("You win!");
        updateDataBase();
        gameViewGUI.gameFinished();
    }

    private void updateDataBase() {
        /*
        Hero hero = game.getHero();
        DataBase.updateHero(hero);*/
    }

    public void onRun(View game) {
        game.run();
        
    }

    public void onFight(View game) {
        game.fight(false);
    }




}