import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

/*
naturally, this is the game loop
*/

public class Game extends Canvas implements Runnable {

    // add serial, to avoid warnings
    private static final long serialVersionUID = 1L;

    // variables for the game loop
    private boolean isRunning = false;
    private Thread thread;
    private Handler handler;
    private Camera camera;
    private SpriteSheet ss;

    private BufferedImage level;
    private BufferedImage spriteSheet;
    private BufferedImage floor;

    public int ammo = 100;
    public int hp = 100;

    // constructor
    public Game() {
        new Window(1000, 565, "Human vs. Goblins!", this);

        start();

        // create the Handler & Camera during Game construction
        handler = new Handler();
        camera = new Camera(0, 0);
        // addKeyListener works with the Canvas
        this.addKeyListener(new KeyInput(handler));

        BufferedImageLoader loader = new BufferedImageLoader();
        level = loader.loadLevel();
        spriteSheet = loader.loadSprite();

        ss = new SpriteSheet(spriteSheet);

        floor = ss.grabImage(3, 2, 32, 32);

        this.addMouseListener(new MouseInput(handler, camera, this, ss));
        loadLevel(level);
    }

    // starts the thread
    private void start() {
        isRunning = true;
        thread = new Thread(this); // this == this class' run (override)
        thread.start();
    }

    // stops the thread
    private void stop () {
        isRunning = false;

        try { // using try+catch because .join() may fail
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override // this gameloop is from minecraft
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                tick();
                // updates++;
                delta--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frames = 0;
                // updates = 0
            }
        }
        stop();
    }

    /*
    updates everything
    (appx. 60 times per second)
    */
    public void tick() {

        for (int i = 0; i < handler.object.size(); i++){
            if (handler.object.get(i).getId() == ID.Player) {
                camera.tick(handler.object.get(i));
            }
        }

        // use one handler, instead of clogging this for every character
        handler.tick();
    }

    /*
    renders everything
    (appx. 1000+ times per second)
    */
    public void render () {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3); // preps three frames
            return; // could add more, but 30 would slow game down
        }

        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g; // this is for the camera!!

        /* ////////////////////////////////////////
        this is where the drawing happens ;P */

        g2d.translate(-camera.getX(), -camera.getY());

        for (int xx = 0; xx<30*72; xx+=32){
            for (int yy = 0; yy<30*72; yy+=32) {
                g.drawImage(floor, xx, yy, null);
            }
        }

        handler.render(g);
        // make sure handler is AFTER background & BEFORE translate
        g2d.translate(camera.getX(), camera.getY());

        // HEALTH BAR, after translate, so always visible
        g.setColor(Color.gray);
        g.fillRect(5, 5, 200, 32);
        g.setColor(Color.green);
        g.fillRect(5, 5, hp*2, 32);
        // below is for outline
        g.setColor(Color.black);
        g.drawRect(5, 5, 200, 32);

        // Ammo count (as text)
        g.setColor(Color.white);
        g.drawString("Ammo: " + ammo, 5, 50);


        ///////////////////////////////////////////
        g.dispose();
        bs.show();
    }

    // loading the level lol
    private void loadLevel(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();

        for (int xx = 0; xx < w; xx++) {
            for (int yy = 0; yy < h; yy++) {
                int pixel = image.getRGB(xx, yy);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                if (red == 255)
                    handler.addObject(new Block(xx*32, yy*32, ID.Block, ss));

                if (blue == 255 & green != 255)
                    handler.addObject(new Human(xx*32, yy*32, ID.Player, handler, this, ss));

                if (green == 255 && blue == 0)
                    handler.addObject(new Goblins(xx*32, yy*32, ID.Enemy, handler, ss));

                if (green == 255 && blue == 255)
                    handler.addObject(new Crate(xx*32, yy*32, ID.Crate, ss));
            }
        }
    }

    // mainnnnnnnnnnnn
    public static void main(String[] args) {

        new Game();
    }

}
