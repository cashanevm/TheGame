package the_game.display;

import the_game.Game;
import the_game.input.Chaker;
import the_game.input.Controller;
import the_game.input.InputHandler;
import the_game.graphics.Render;
import the_game.graphics.Screen;
import the_game.input.MouseHandler;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.*;

import static the_game.config.DisplayConfig.*;

public class Display extends Canvas implements Runnable{
    private Thread thread;
    private Screen screen;
    private Game game;
    private BufferedImage img;
    private boolean running = false;
    private Render render;
    private int[] pixels;

    private InputHandler input;
    private MouseHandler mouse;

    private String fps = "";

    public Display() {
        Dimension size = new Dimension(DISPLAY_WIDTH, DISPLAY_HEIGHT);
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);


        screen = new Screen(DISPLAY_WIDTH, DISPLAY_HEIGHT);
        game = new Game();
        img = new BufferedImage(DISPLAY_WIDTH, DISPLAY_HEIGHT, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
        input = new InputHandler();
        mouse = new MouseHandler();
        addKeyListener(input);
        addFocusListener(input);
        addMouseListener(input);
        addMouseMotionListener(input);
    }

    private void start() {
        if (running) return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    private void stop() {
        if (!running) return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        BufferedImage cursor = new BufferedImage(16, 16, BufferedImage.TYPE_4BYTE_ABGR);
        Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0,0), "blank");


        Display display = new Display();
        JFrame frame = new JFrame();
        frame.add(display);
        frame.pack();
        frame.getContentPane().setCursor(blank);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle(DISPLAY_TITLE);
        frame.setResizable(false);
        frame.setVisible(true);
        display.start();
        display.requestFocusInWindow();
    }

    @Override
    public void run() {
        int frames = 0;
        double upSeconds = 0;
        long prevTime = System.nanoTime();
        double secondsPerTick = 1/60.0;
        int tickCount = 0;
        boolean ticked = false;

//        Chaker anotherRun = new Chaker();
//        Thread childTread = new Thread(anotherRun);
//        childTread.start();

        mouse.moveToCenter();

        while (running) {
            long currentTime =  System.nanoTime();
            long passedTime = currentTime - prevTime;
            prevTime = currentTime;
            upSeconds += passedTime / 1000000000.0;

            requestFocus();

            while (upSeconds > secondsPerTick) {
                tick();
                upSeconds -= secondsPerTick;
                ticked = true;
                tickCount++;
                if (tickCount % 60 == 0) {
                    fps = frames + "fps";
                    prevTime += 1000;
                    frames = 0;
                }
            }
            if (ticked) {
                render();
                frames++;
            }
            render();
            frames++;

            mouse.readMouseInput();
        }
    }

    private void tick() {
        game.tick(input.key);
    }

    private void render() {
        BufferStrategy bufferStrategy = this.getBufferStrategy();
        if (bufferStrategy == null) {
            createBufferStrategy(3);
            return;
        }

        screen.render(game);

        for (int i = 0; i < DISPLAY_WIDTH * DISPLAY_HEIGHT; i++) {
            pixels[i] = screen.pixels[i];
        }

        Graphics graphics = bufferStrategy.getDrawGraphics();
        graphics.drawImage(img, 0, 0, DISPLAY_WIDTH, DISPLAY_HEIGHT,null);
        graphics.setFont(new Font("Verdana", 2, 50));
        graphics.setColor(Color.yellow);
        graphics.drawString(fps,20, 50);
        graphics.dispose();
        bufferStrategy.show();
    }
}
