package the_game.input;

import java.awt.*;

import static the_game.config.DisplayConfig.DISPLAY_WIDTH;

public class MouseHandler {
    private int newX = 0;
    private int oldX = 0;
    private int newY = 0;

    private Robot robot;

    public MouseHandler() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void readMouseInput(){
        int boost = (int) (Controller.rotationSpeedBoost = Math.abs(oldX - newX));
        int halfWidth = DISPLAY_WIDTH / 2;

        newX = InputHandler.MOUSE_X;
        if (newX > (DISPLAY_WIDTH * 0.9)) {
            robot.mouseMove(halfWidth, InputHandler.MOUSE_Y);
            newX = halfWidth;
            oldX = halfWidth - boost;
        }
        if (newX < DISPLAY_WIDTH * 0.1) {
            robot.mouseMove(halfWidth, InputHandler.MOUSE_Y);
            newX = halfWidth;
            oldX = halfWidth + boost;
        }
        if (newX < oldX) {

            Controller.rotationSpeedBoost = (oldX - newX);
            Controller.turnRight = true;
        }

        if (newX > oldX) {
            Controller.rotationSpeedBoost = (newX - oldX);
            Controller.turnLeft = true;

        }
        if (newX == oldX) {

            Controller.turnRight = false;
            Controller.turnLeft = false;
        }
        oldX = newX;
    }
}
