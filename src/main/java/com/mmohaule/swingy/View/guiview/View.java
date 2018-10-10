

package com.mmohaule.swingy.View.guiview;

import com.mmohaule.swingy.Controller.GameManager;
import com.mmohaule.swingy.Controller.MapGenerator;
import com.mmohaule.swingy.Database.DataBase;
import com.mmohaule.swingy.Model.GameCharacter;

/**
 * View
 */
public class View extends GameManager {

    public View(GameCharacter hero, GameViewGUI gameViewGUI) {
        this.hero = hero;
        this.gui = true;
        this.gameViewGUI = gameViewGUI;
    }

	@Override
	public void runGame() {
        if (hero == null) {
            System.out.println("No hero set");
            System.exit(1);
        }
        gameViewGUI.pushMessage(hero.getName() + " arrived in a new hostile environment");
        gameMap = MapGenerator.generateMap(hero);
    }
    
    public void updateHeroPosition() {
        gameMap.updateHeroPosition();
        DataBase.updateHero(hero);
    }
    
}