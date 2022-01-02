package the_game.graphics;

import the_game.Game;

import java.util.Random;
import java.util.WeakHashMap;

public class Render3D extends Render{

    private double forward, right, cosine, sine, up;

    public Render3D(int width, int height) {
        super(width, height);
    }

    public void floor(Game game) {
        double test = Math.cos(game.time / 25.0);

        double floorPosition = 10;
        double ceilingPosition = 20;

        forward = game.controllers.z;
        right = game.controllers.x;
        up = game.controllers.y;

        double rotation = game.controllers.rotation;
        cosine = Math.cos(rotation);
        sine = Math.sin(rotation);

        for (int y = 0; y < height; y++) {
            double ceiling = (y + -height / 2.0) / height;

            double z = (floorPosition + up) / ceiling;

            if (ceiling < 0) z = (ceilingPosition - up) / -ceiling;

            for (int x = 0; x < width; x++) {
                int pixIndex = x + y * width;

                double depth = (x - width / 2.0) / height;
                depth *= z;
                //+
                double xx = (depth * cosine + z * sine) ;
                double yy = (z * cosine - depth * sine) ;




                int xPix = (int) (xx + right) & 15;
                int yPix = (int) (yy + forward) & 15;

                pixels[pixIndex] = - Texture.floor.pixels[(xPix & 7) + (yPix & 7) * 8];

                if (z > height / 4.0 ) {
                    pixels[pixIndex] = 1;
                }
            }
        }
    }

    public void renderWall(double xLeft, double xRight, double zDistanceLeft, double zDistanceRight, double yHeight) {
        double upCorrect = 0.062;
        double rightCorrect = 0.062;
        double forwardCorrect = 0.062;

        double xcLeft = ((xLeft) - (right * rightCorrect)) * 2;
        double zcLeft = ((zDistanceLeft) - (forward * forwardCorrect)) * 2;

        double rotLeftSideX = xcLeft * cosine - zcLeft * sine;
        double yCornerTL = ((-yHeight) - (-up * upCorrect)) * 2;
        double yCornerBL = ((+0.5 - yHeight) - (-up * upCorrect)) * 2;
        double rotLeftSideZ = zcLeft * cosine + xcLeft * sine;

        double xcRight = ((xRight) - (right * rightCorrect)) * 2;
        double zcRight = ((zDistanceRight) - (forward * forwardCorrect)) * 2;

        double rotRightSideX = xcRight = xcRight * cosine - zcRight * sine;
        double yCornerTR = ((-yHeight) - (-up * upCorrect)) * 2;
        double yCornerBR = ((+ 0.5 -yHeight) - (-up * upCorrect)) * 2;
        double rotRightSideZ = zcRight * cosine + xcRight * sine;

        double xPixelLeft = (rotLeftSideX / rotLeftSideZ * height + width / 2);
        double xPixelRight = (rotRightSideX / rotRightSideZ * height + width / 2);

        if (xPixelLeft >= xPixelRight) {
            return;
        }

        int xPixelLeftInt = (int) (xPixelLeft);
        int xPixelRightInt = (int) (xPixelRight);

        if (xPixelLeftInt < 0) {
            xPixelLeftInt = 0;
        }
        if (xPixelRightInt > width) {
            xPixelRightInt = width;
        }

        double yPixelLeftTop = yCornerTL/ rotLeftSideZ * height + height / 2;
        double yPixelLeftBottom = yCornerBL/ rotLeftSideZ * height + height / 2;
        double yPixelRightTop = yCornerTR/ rotRightSideZ * height + height / 2;
        double yPixelRightBottom = yCornerBR/ rotRightSideZ * height + height / 2;

        double tex1 = 1/rotLeftSideZ;
        double tex2 = 1/rotRightSideZ;
        double tex3 = 0/rotRightSideZ;
        double tex4 = 8/rotRightSideZ - tex3;

        for (int x = xPixelLeftInt; x < xPixelRightInt; x++) {
            double pixelRotation = (x - xPixelLeft) / (xPixelRight - xPixelLeft);

            int xTexture = (int) ((tex3 + tex4 * pixelRotation) / (tex1 + (tex2 - tex1) * pixelRotation));

            double yPixelTop = yPixelLeftTop + (yPixelRightTop - yPixelLeftTop) * pixelRotation;
            double yPixelBottom = yPixelLeftBottom + (yPixelRightBottom - yPixelLeftBottom) * pixelRotation;

            int yPixelTopInt = (int) (yPixelTop);
            int yPixelBottomInt = (int) (yPixelBottom);

            if (yPixelTopInt < 0) {
                yPixelTopInt = 0;
            }
            if (yPixelBottomInt > height) {
                yPixelBottomInt = height;
            }

            for (int y = yPixelTopInt; y < yPixelBottomInt; y++) {
                double pixelRotationY = (y - yPixelTop) / (yPixelBottom - yPixelTop);
                int yTexture = (int) (8 * pixelRotationY);
                pixels[x + y * width] = xTexture * 100 + yTexture * 100;
            }

        }
    }

    public void walls() {
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            double xx = random.nextDouble();
            double yy = random.nextDouble();
            double zz = 1.5 - forward / 16;

            int xPixel = (int) (xx / zz * height / 2 + width / 2);
            int yPixel = (int) (yy / zz * height / 2 + height / 2);

            if (xPixel >= 0 && yPixel >= 0 && xPixel < width && yPixel < height) {
                pixels[xPixel + yPixel * width] = 0xfffff;
            }
        }
    }
}
