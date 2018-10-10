package com.mmohaule.swingy;

import com.mmohaule.swingy.View.clview.PlayCli;
import com.mmohaule.swingy.View.guiview.PlayGui;

public class Swingy 
{
    public static void main( String[] args )
    {
        try {
            switch (args[0]) {
                case "console":
                    PlayCli consoleGame = new PlayCli();
                    consoleGame.runGame();
                    break;
                case "gui":
                    PlayGui guiGame = new PlayGui();
                    guiGame.runGame();
                    break;
                default:
                    System.out.println("Usage: java -jar target/swingy [console/gui]");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Usage: java -jar target/swingy [console/gui]");
        }
    }
}
