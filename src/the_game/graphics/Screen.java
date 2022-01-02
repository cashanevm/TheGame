package the_game.graphics;

import the_game.Game;

import java.util.Random;

public class Screen extends Render{
    private Render test;
    private Render3D render;

    public Screen(int width, int height) {
        super(width, height);

        render = new Render3D(width, height);

        Random random = new Random();
        test = new Render(256,256);

        for (int i = 0; i < 256 * 256; i++) {
            test.pixels[i] = random.nextInt();
        }
    }

    public void render(Game game) {
        for (int pixIndex = 0; pixIndex < width * height; pixIndex++) {
            pixels[pixIndex] = 0;
        }

        //int animX = (int) (Math.sin(game.time % 2000.0 / 2000 * Math.PI * 2) * 200);
        //int animY = (int) (Math.cos(game.time % 2000.0 / 2000 * Math.PI * 2) * 200);

        render.floor(game);
        render.renderWall(0, 0.5, 1.5, 1.5,0);
        draw(render,0,0);
        //draw(test,  (width - 256) / 2 + animX , (height - 256) / 2 + animY);

    }
}
