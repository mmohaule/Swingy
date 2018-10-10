
package com.mmohaule.swingy.Controller;

import com.mmohaule.swingy.Model.GameCharacter;
import com.mmohaule.swingy.Model.Map;

/**
 * MapGenerator
 */
public class MapGenerator {
    
    
    public static Map generateMap(GameCharacter hero) {
        int mapSize = (hero.getLevel() - 1) * 5 + 10 - (hero.getLevel() % 2);

        if (mapSize > 19) {
            mapSize = 19;
        }
        Map squareMap = new Map(mapSize);
        squareMap.registerHero(hero);
        System.out.println(hero.getName() + " arrived in a new hostile environment");
        squareMap.generateFoes();
        return (squareMap);
    }
}