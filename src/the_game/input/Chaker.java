package the_game.input;

public class Chaker implements Runnable{
    private MouseHandler mouse;

    public Chaker() {
        mouse = new MouseHandler();
        mouse.moveToCenter();
    }

    @Override
    public void run() {
        while (true) {
            mouse.readMouseInput();
        }
    }
}
