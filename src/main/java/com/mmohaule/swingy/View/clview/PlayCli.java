

package com.mmohaule.swingy.View.clview;

import com.mmohaule.swingy.Controller.GameCharacterFactory;
import com.mmohaule.swingy.Controller.GameManager;
import com.mmohaule.swingy.Controller.MapGenerator;
import com.mmohaule.swingy.Controller.Misc;
import com.mmohaule.swingy.Database.DataBase;
import com.mmohaule.swingy.Model.GameCharacter;
import com.mmohaule.swingy.Model.Map;

/**
 * Play
 */
public class PlayCli extends GameManager {

    

    private boolean running = false;

    public PlayCli() {
        
    }
    
    @Override
    public void runGame() {
        int choice = 0;
        getOrCreateHero();

        if (hero == null) {
            System.out.println("No hero set");
            System.exit(1);
        }
        running = true;

        gameMap = MapGenerator.generateMap(hero);
        while (running) {
            gameMap.printMap();
            running = winCondition();
            if (running == false) {
                System.exit(0);
            }
            Print.printDirections();
            while (Misc.scan.hasNextLine()) {
                String arg = Misc.scan.next();
                if (arg.matches("\\s*[1-5]\\s*")) {
                    choice = Integer.parseInt(arg.trim());
                    break ;
                } else {
                    System.out.println(Map.ANSI_RED + ">" + Map.ANSI_RESET + " Incorrect choice");
                }
            }
            move(choice);
            gameMap.updateHeroPosition();
            DataBase.updateHero(hero);
        }
        
    }

    private void getOrCreateHero() {
        GameCharacterFactory factoryChar = new GameCharacterFactory();

        System.out.println("1 - Create Hero\n2 - Choose Existing Hero");
        int choice = 0;
        while (Misc.scan.hasNextLine()) {
            String arg = Misc.scan.next();
            if (arg.matches("\\s*[1-2]\\s*")) {
                choice = Integer.parseInt(arg.trim());
                break ;
            } else {
                System.out.println(Map.ANSI_RED + ">" + Map.ANSI_RESET + " Incorrect choice");
            }
        }
        switch(choice) {
            case 1:
                String name;
                int type = 0;
                System.out.println("Select Hero Type:\n1 - Ninja\n2 - Knight");
                while (Misc.scan.hasNextLine()) {
                    String arg = Misc.scan.next();
                    if (arg.matches("\\s*[1-2]\\s*")) {
                        type = Integer.parseInt(arg.trim());
                        break ;
                    } else {
                        System.out.println(Map.ANSI_RED + ">" + Map.ANSI_RESET + " Incorrect choice");
                    }
                }
        
                System.out.print("Give your hero a name: ");
                name = Misc.scan.next();

                try {
                    setHero(factoryChar.createHero(name, type));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 2:
                setHero(factoryChar.getAllHeroes());
                break;
            default:
                break;
        }
    }






    

    /**
     * @param hero the hero to set
     */
    public void setHero(GameCharacter hero) {
        this.hero = hero;
    }

    /**
     * @return the hero
     */
    public GameCharacter getHero() {
        return hero;
    }
}