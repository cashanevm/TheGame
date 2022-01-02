package the_game.input;

import java.awt.*;

import static the_game.config.DisplayConfig.DISPLAY_HEIGHT;
import static the_game.config.DisplayConfig.DISPLAY_WIDTH;

public class MouseHandler {
    private int newX = 0;
    private int oldX = 0;
    private int newY = 0;
    private int oldY = 0;

    private Robot robot;

    public MouseHandler() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void readMouseInput(){
        newY = InputHandler.MOUSE_Y;
        int boostY = Math.abs(oldY - newY);

        int halfHeight = DISPLAY_HEIGHT / 2;
        Controller.rotationSpeedBoostY = boostY;
        newX = InputHandler.MOUSE_X;
        int boostX = Math.abs(oldX - newX);
        Controller.rotationSpeedBoostX = boostX;
        int halfWidth = DISPLAY_WIDTH / 2;

        if (newY > (DISPLAY_HEIGHT * 0.8)) {
            robot.mouseMove(newX, halfHeight);
            newY = halfHeight;
            oldY = halfHeight - boostY;
            System.out.println("moved to x:"+newX+" y:"+halfHeight);
        }
        if (newY < DISPLAY_HEIGHT * 0.2) {
            robot.mouseMove(newX, halfHeight);
            newY = halfHeight;
            oldY = halfHeight + boostY;
            System.out.println("moved to x:"+newX+" y:"+halfHeight);
        }

        if (newY < oldY) {

            Controller.rotationSpeedBoostY = boostY;
            Controller.turnDown = true;
        }

        if (newY > oldY) {
            Controller.rotationSpeedBoostY = boostY;
            Controller.turnUp = true;

        }
        if (newY == oldY) {

            Controller.turnDown = false;
            Controller.turnUp = false;
        }

        oldY = newY;


        if (newX > (DISPLAY_WIDTH * 0.9)) {
            robot.mouseMove(halfWidth, newY);
            newX = halfWidth;
            oldX = halfWidth - boostX;
            System.out.println("moved to x:"+halfWidth+" y:"+newY);
        }
        if (newX < DISPLAY_WIDTH * 0.1) {
            robot.mouseMove(halfWidth, newY);
            newX = halfWidth;
            oldX = halfWidth + boostX;
            System.out.println("moved to x:"+halfWidth+" y:"+newY);
        }
        if (newX < oldX) {

            Controller.rotationSpeedBoostX = boostX;
            Controller.turnRight = true;
        }

        if (newX > oldX) {
            Controller.rotationSpeedBoostX = boostX;
            Controller.turnLeft = true;

        }
        if (newX == oldX) {

            Controller.turnRight = false;
            Controller.turnLeft = false;
        }
        oldX = newX;
    }
    public void moveToCenter() {
        oldX = DISPLAY_WIDTH / 2;
        oldY = DISPLAY_HEIGHT / 2;
        robot.mouseMove(DISPLAY_WIDTH / 2, DISPLAY_HEIGHT / 2);
    }
}
