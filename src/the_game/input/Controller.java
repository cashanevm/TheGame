package the_game.input;

import java.awt.*;

public class Controller {
    public double x, y, z, rotation, xa, za, rotationa;
    public static boolean turnLeft = false;
    public static boolean turnRight = false;
    public static double rotationSpeedBoost = 0;

    public void tick(boolean forward, boolean back, boolean right, boolean left, boolean jump, boolean crouch, boolean run, boolean exit) {
        double rotationSpeed = 0.01;
        double walkSpeed = 1;
        double xMove = 0;
        double zMove = 0;

        double jumpHeight = 1;
        double crouchHeight = 0.5;

        if (exit) {
            System.exit(1);
        }

        if (forward) {
            zMove++;
        }

        if (back) {
            zMove--;
        }

        if (left) {
            xMove--;
        }

        if (right) {
            xMove++;
        }

        if (turnLeft) {
            rotationa += (rotationSpeed * rotationSpeedBoost);
        }

        if (turnRight) {
            rotationa -= (rotationSpeed * rotationSpeedBoost);
        }

        if (jump) {
            y += jumpHeight;
        }

        else if (crouch) {
            y -= crouchHeight;
            walkSpeed = walkSpeed/2;
        }

        else if (run) {
            walkSpeed = walkSpeed*2;
        }

        xa += (xMove * Math.cos(rotation) + zMove * Math.sin(rotation)) * walkSpeed;
        za += (zMove * Math.cos(rotation) - xMove * Math.sin(rotation)) * walkSpeed;

        x += xa;
        y *= 0.9;
        z += za;

        xa *= 0.1;
        za *= 0.1;
        rotation += rotationa;
        rotationa *= 0.00000001;
    }
}
