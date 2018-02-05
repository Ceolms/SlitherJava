/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.awt.Color;

/**
 *
 * @author theo
 */
public class SnakeBodyPart {
    public int x;
    public int y;
    public Color color;
    
    SnakeBodyPart(int x , int y, Color color)
    {
        this.x = x;
        this.y = y;
        this.color = color;
    }
}
