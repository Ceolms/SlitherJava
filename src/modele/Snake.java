/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author theo
 */
public class Snake {
    public SnakeHead head;
    public ArrayList<SnakeBodyPart> body;
    public boolean onScreen;
    public int size =  20 ; // le rayon des cercles
    

    public Snake(int x , int y , Color color) {
        head = new SnakeHead(x,y,color);
    }
    
    
}
