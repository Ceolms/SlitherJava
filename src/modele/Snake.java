/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.awt.Color;
import java.util.ArrayList;
import utils.Coords;
import utils.Params;

/**
 *
 * @author theo
 */
public class Snake {
    public SnakeHead head;
    public ArrayList<SnakeBodyPart> body;
    public ArrayList<Coords> coordsList;
    public boolean onScreen;
    public boolean isBoosting = false;
    public static final int speed = Params.read("speed");
    public static final int boostSpeed = Params.read("boostSpeed");
    public int size =  20 ; // le rayon des cercles
    

    public Snake(int x , int y , Color color) {
        head = new SnakeHead(x,y,color);
        
        int initLength = Params.read("initLength");
        body = new ArrayList<>();
        coordsList = new ArrayList<>();
        
        coordsList.add(new Coords(this.head.x,this.head.y));
        
        for(int i = 0 ; i <= initLength-1 ; i++)
        {
            y+= Params.read("bodyPartDist");
            body.add(new SnakeBodyPart(x, y,size ,color));
            coordsList.add(new Coords(x, y));
        }        
    }
    
    public void updateCoords()
    {
        coordsList.add(0, new Coords(this.head.x, this.head.y));
        coordsList.remove(coordsList.size()-1);
        
        for(int i = 0 ; i < body.size() ; i++)
        {
            int x = coordsList.get(i+1).x;
            int y = coordsList.get(i+1).y;
            
            body.get(i).setPos(x ,y);
        }
    }
    
    public void addSection(int x , int y , Color c)
    {
        this.body.add(new SnakeBodyPart(x, y,size, c));
    }
            
    
}
