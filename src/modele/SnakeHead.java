package modele;

import java.awt.Color;
import java.awt.image.BufferedImage;
import utils.Hitbox;

/**
 *
 * @author theo
 */
public class SnakeHead {

    public int angle;
    public int x;
    public int y;
    public int r;
    public Color color;
    public Hitbox hb;

    SnakeHead(int x, int y,int r, Color color) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.color = color;
        hb = new Hitbox(x, y, r);
    }

    void growSize(int growSize) {
        this.r += growSize;
        hb = new Hitbox(x, y, r);
    }
    
    public void updateHitBox()
    {
        this.hb = new Hitbox(x, y, r);
    }

}
