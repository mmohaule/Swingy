package com.mmohaule.swingy.Model;

import java.util.Random;

/**
 * Map
 */
public class Map {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private int[][] map;
    public int mapSize;
    private GameCharacter hero;
    private int[] oldPos = new int[] { -1, -1};;

    public Map(int mapSize) {
        this.mapSize = mapSize;
        this.map = new int[mapSize][mapSize];
    }

    public void updateHeroPosition() {
        this.map[oldPos[0]][oldPos[1]] = 0;
        oldPos[0] = hero.getX();
        oldPos[1] = hero.getY();
        if (this.map[hero.getX()][hero.getY()] == 2) {
            this.map[hero.getX()][hero.getY()] = 8;
        } else {
            this.map[hero.getX()][hero.getY()] = 1;
        }
    }

    public void registerHero(GameCharacter hero) {
        this.hero = hero;
        //this.hero.register(this);
        this.hero.setX(mapSize / 2);
        this.hero.setY(mapSize / 2);
        oldPos[0] = this.hero.getX();
        oldPos[1] = this.hero.getY();
        this.map[mapSize / 2][mapSize / 2] = 1;
    }

    public void generateFoes() {
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                if (map[i][j] != 1) {
                    int random = new Random().nextInt(3);
                    if (random == 0) {
                        map[i][j] = 2;
                    }
                }
            }
        }
    }

    public void printMap() {
        for (int[] line : map) {
            for (int col : line) {
                String box = col + " ";
                switch (col) {
                    case 1:
                        System.out.print(ANSI_GREEN + box + ANSI_RESET);
                        break;
                    case 2:
                        System.out.print(ANSI_RED + box + ANSI_RESET);
                        break;
                    case 8:
                        System.out.print(ANSI_YELLOW + box + ANSI_RESET);
                        break;
                    default:
                        System.out.print(box);
                        break;
                }
            }
            System.out.println();
        }
    }


    /**
     * @return the map
     */
    public int[][] getMap() {
        return map;
    }

    /**
     * @return the mapSize
     */
    public int getMapSize() {
        return mapSize;
    }
}