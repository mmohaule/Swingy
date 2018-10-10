package com.mmohaule.swingy.Controller;

import com.mmohaule.swingy.Database.DataBase;
import com.mmohaule.swingy.Model.GameCharacter;
import com.mmohaule.swingy.Model.Knight;
import com.mmohaule.swingy.Model.Map;
import com.mmohaule.swingy.Model.Ninja;

/**
 * GameCharacterFactory
 */
public class GameCharacterFactory {

    public GameCharacter createHero(String name, int type) throws Exception {

        GameCharacter hero;

        try {
            switch(type) {
                case 1:
                    hero = new Ninja(name);
                break ;
                case 2:
                    hero = new Knight(name);
                break ;
                default:
                    hero = null;
                break ;
            }
            hero.validateHero();
            
        } catch (Exception e) {
            throw new Exception(e.toString());
        }
        int id = DataBase.insert(hero.getName(), hero.getHeroType(), (int) hero.getLevel(), (int) hero.getExperience(), (int) hero.getAttack(), (int) hero.getDefense(), (int) hero.getHitPoints());
        hero.setId(id);
        return hero;

        
    }

    
    public GameCharacter getAllHeroes() {
        GameCharacter hero = null;
        boolean loop = true;
        System.out.println("Availabe heroes:");
        int i = 0;
        for (String e: DataBase.selectAll()){
            System.out.println(Map.ANSI_GREEN + e + Map.ANSI_RESET);
            i++;
        }
        if (i == 0) {
            System.out.println(Map.ANSI_RED + "No heroes are available, Please create one" + Map.ANSI_RESET);
            return hero;
        }
        while (loop) {
            System.out.print("Enter your hero by ID: ");
            int choice = Misc.scan.nextInt();
            hero = DataBase.selectHeroById(choice);

            if (hero != null) {
                System.out.println(Map.ANSI_GREEN + hero.getName() + "'s " + Map.ANSI_RESET + "stats:\n"+hero.toString());
                System.out.println("Do you accept " + Map.ANSI_GREEN + hero.getName() + Map.ANSI_RESET + " as your hero?\n1 - Yes\n2 - No");
                int accept = Misc.scan.nextInt();
                if (accept == 1) {
                    loop = false;
                }
            }
        }
        
        return hero;
    }
}