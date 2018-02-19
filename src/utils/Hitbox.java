/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import static java.lang.Math.sqrt;
import javafx.scene.shape.Circle;

/**
 *
 * @author theo
 */
public class Hitbox {

    public int x;
    public int y;
    public int r;

    public Hitbox(int x, int y, int r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public boolean intersect(Hitbox hb) {
        boolean collision = false;
        
        if ((hb.x-this.x)*(hb.x-this.x) + (this.y-hb.y)*(this.y-hb.y) <= (this.r + hb.r)*(this.r + hb.r)) {
            collision = true;
        }

        return collision;
    }
}
