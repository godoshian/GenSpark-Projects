import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Goblins extends GameObject {

    private Handler handler;
    Random r = new Random();
    int choose = 0;
    int hp = 100;
    private BufferedImage goblin_image;

    public Goblins(int x, int y, ID id, Handler handler, SpriteSheet ss) {
        super(x, y, id, ss);
        this.handler = handler;

        goblin_image = ss.grabImage(3, 1, 32, 32);
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        choose = r.nextInt(10);

        for (int i = 0; i <handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ID.Block) {
                if (getBoundsBig().intersects(tempObject.getBounds())) {
                    x += (velX*5) * -1;
                    y += (velY*5) * -1;
                    velX *= -1;
                    velY *= -1;
                } else if (choose==0) {
                    velX = (r.nextInt(4 - -4) + -4);
                    velY = (r.nextInt(4 - -4) + -4);
                }
            }

            if (tempObject.getId() == ID.Bullet) {
                if (getBounds().intersects(tempObject.getBounds())){
                    hp -= 50;
                    handler.removeObject(tempObject);
                }
            }
        }

        if (hp<=0) handler.removeObject(this);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(goblin_image, x, y, null);
    }

    @Override // this collision is for bullets
    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }

    // ensures goblins arent too close to ID.Block
    public Rectangle getBoundsBig() {
        return new Rectangle(x-16, y-16, 64, 64);
    }
}
