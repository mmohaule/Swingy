
package com.mmohaule.swingy.Controller;

import java.util.Random;

import com.mmohaule.swingy.Model.Foe;
import com.mmohaule.swingy.Model.GameCharacter;
import com.mmohaule.swingy.Model.Map;
import com.mmohaule.swingy.View.clview.Print;
import com.mmohaule.swingy.View.guiview.GameViewGUI;

/**
 * GameManager
 */
public abstract class GameManager {

    private final static int NORTH = 1;
    private final static int EAST = 2;
    private final static int SOUTH = 3;
    private final static int WEST = 4;
    private static int[] oldMove = new int[2];

    protected GameCharacter hero;
    protected GameCharacter foe;
    protected Map gameMap;
    protected boolean gui = false;
    protected GameViewGUI gameViewGUI = null;

    public abstract void runGame();

    public boolean winCondition() {
        if (hero.getX() == gameMap.getMapSize() - 1 ||
                hero.getY() == gameMap.getMapSize() - 1 ||
                hero.getX() == 0 || hero.getY() == 0) {
            if (gui == true) {
                gameViewGUI.showMessage("You reached your goal!!");
            } else {
                System.out.println("You reached your goal!!");
            }
            
            return false;
        }
        return true;
    }

    public void fight(boolean fled) {
        if (fled) {
            Misc.Log("Enemy starts !", gui, gameViewGUI);
            while (hero.getHitPoints() > 0 && foe.getHitPoints() > 0) {
                Misc.Log(foe.attack(hero), gui, gameViewGUI);
                Misc.Log(foe.attack(hero), gui, gameViewGUI);
                if (hero.getHitPoints() > 0) {
                    Misc.Log(hero.attack(foe), gui, gameViewGUI);
                }
            }
        } else {
            Misc.Log(hero.getName() + " starts!", gui, gameViewGUI);
            while (hero.getHitPoints() > 0 && foe.getHitPoints() > 0) {
                Misc.Log(hero.attack(foe), gui, gameViewGUI);
                if (foe.getHitPoints() > 0) {
                    Misc.Log(foe.attack(hero), gui, gameViewGUI);
                }
            }
        }
        if (hero.getHitPoints() <= 0) {
            hero.setHitPoints(0);
            if (gui == true) {
                gameViewGUI.showMessage("Game Over");
                gameViewGUI.gameFinished();
            } else {
                System.out.println("Game Over");
                System.exit(0);
            }
            
            
            
        } else if (foe.getHitPoints() <= 0) {
            hero.setPosition(0, 0);
            if (gui == true) {
                gameViewGUI.showMessage("You win!");
            } else {
                System.out.println("You win!");
            }
            
            hero.setExperience(hero.getExperience() + 100);
            hero.setExperience(hero.getHitPoints() + 10);
        }
        if (hero.getExperience() >= (hero.getLevel() * 1000 + Math.pow(hero.getLevel() - 1, 2) * 450)) {
            hero.setLevel(hero.getLevel() + 1);
            hero.setAttack(hero.getAttack() + 2);
            hero.setHitPoints(hero.getHitPoints() + 100);
            hero.setExperience(hero.getExperience() + 200);

            Misc.Log("Hero leveled up!\nhp + 100\nxp + 200", gui, gameViewGUI);
        }
    }

    public void run() {
        int random = new Random().nextInt(2);
        switch (random) { 
            case 0:
                Misc.Log("Escape failed!", gui, gameViewGUI);
                fight(true);
                break;
            case 1:
                Misc.Log("You fled from battle.", gui, gameViewGUI);
                hero.setPosition(oldMove[0] * -1, oldMove[1] * -1);
                break;
        }
    }

    private void fightOrRun() {
        Print.printFightOptions();
            while (Misc.scan.hasNextLine()) {
                String arg = Misc.scan.next();
                if (arg.matches("\\s*[1-2]\\s*")) {
                    Integer nb = Integer.parseInt(arg.trim());
                    switch (nb) {
                        case 1:
                            fight(false);
                            return;
                        case 2:
                            run();
                            return;
                    }
                } else {
                    System.out.println(Map.ANSI_RED + ">" + Map.ANSI_RESET + " Incorrect choice");
                    Print.printFightOptions();
                }
            }
        }

    public void move(int direction) {
        switch (direction) {
            case NORTH:
                hero.setPosition(-1, 0);
                oldMove[0] = -1;
                oldMove[1] = 0;
                break;
            case EAST:
                hero.setPosition(0, 1);
                oldMove[0] = 0;
                oldMove[1] = 1;
                break;
            case SOUTH:
                hero.setPosition(1, 0);
                oldMove[0] = 1;
                oldMove[1] = 0;
                break;
            case WEST:
                hero.setPosition(0, -1);
                oldMove[0] = 0;
                oldMove[1] = -1;
                break;
            case 5:
                System.out.println(hero.toString());
        }
        if (gameMap.getMap()[hero.getX()][hero.getY()] == 2) {
            foe = new Foe();
            foe.setLevel(hero.getLevel());
            if (gui == false) {
                gameMap.updateHeroPosition();
                gameMap.printMap();
                
                System.out.println("Enemy encounter : \"" + foe.getName() + "\" level " + foe.getLevel() + " !");
                fightOrRun();
            }
            else if (gui == true){

                if (gameViewGUI != null) {
                    gameViewGUI.pushMessage("Enemy encounter : \"" + foe.getName() + "\" level " + foe.getLevel() + " !");
                    gameViewGUI.getVillainCollisionInput();
                }
            }
        }
    }

}