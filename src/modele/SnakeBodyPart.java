/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.awt.Color;
import utils.Hitbox;

/**
 *
 * @author theo
 */
public class SnakeBodyPart {

    public int x;
    public int y;
    public int r;
    public Hitbox hb;
    public Color color;

    SnakeBodyPart(int x, int y, int r, Color color) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.color = color;
        hb = new Hitbox(x, y, r);
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
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
