package the_game;

import the_game.input.Controller;

import java.awt.event.KeyEvent;

public class Game {
    public int time;
    public Controller controllers;

    public Game() {
        controllers = new Controller();
    }

    public void tick(boolean[] key) {
        time++;
        boolean forward = key[KeyEvent.VK_W];
        boolean back = key[KeyEvent.VK_S];
        boolean left = key[KeyEvent.VK_A];
        boolean right = key[KeyEvent.VK_D];
        boolean jump = key[KeyEvent.VK_SPACE];
        boolean crouch = key[KeyEvent.VK_CONTROL];
        boolean run = key[KeyEvent.VK_SHIFT];
        boolean exit = key[KeyEvent.VK_ESCAPE];

        controllers.tick(forward,back,right,left, jump, crouch, run, exit);
    }
}
