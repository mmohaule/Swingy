package com.mmohaule.swingy.Controller;

import java.util.Scanner;

import com.mmohaule.swingy.View.guiview.GameViewGUI;

/**
 * Misc
 */
public class Misc {

    public static Scanner scan = new Scanner(System.in);


    public static void closeScanner() {
		scan.close();
    }

    public static void Log(String message, Boolean gui, GameViewGUI gameViewGUI) {
      if (gui == true) {
        gameViewGUI.pushMessage(message);
      } else {
        System.out.println(message);
      }
    }
}