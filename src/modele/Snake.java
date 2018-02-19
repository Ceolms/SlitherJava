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
    public int size = Params.read("initSize"); // le rayon des cercles
    private int lengthQueue = 0;
    public int score;

    public Snake(int x, int y, Color color) {
        head = new SnakeHead(x, y,size,color);

        int initLength = Params.read("initLength");
        body = new ArrayList<>();
        coordsList = new ArrayList<>();

        coordsList.add(new Coords(this.head.x, this.head.y));
        
        for (int i = 0; i <= initLength - 1; i++) {
            y += 1;
            body.add(new SnakeBodyPart(x, y, size, color));
            coordsList.add(new Coords(x, y));
        }
    }

    public void updateCoords() {
        // on ajoute la position de la tete a l'avant
        coordsList.add(0, new Coords(this.head.x, this.head.y));
       
        for(int i = 0 ; i < body.size();i++)
        {
            int x = coordsList.get(i+1).x;
            int y = coordsList.get(i+1).y;
            body.get(i).setPos(x, y);
        }
        if(lengthQueue >0)
        {
            body.add(new SnakeBodyPart(coordsList.get(coordsList.size()-1).x, coordsList.get(coordsList.size()-1).y, size, Color.red));
            lengthQueue--;
            System.out.println("new body part");
        }
        else coordsList.remove(coordsList.size()-1); 
        
        for(SnakeBodyPart s : this.body)
        {
            s.updateHitBox();
        }
    }
    public void eat(Food f){
        
        score += f.score;
        
        int scoreSection = Params.read("scoreSection");
        int scoreGrow = Params.read("scoreGrow");
        
        if(score % scoreGrow ==0) growSize(Params.read("growRatio"));
        if(score % scoreSection ==0) lengthQueue++;
    }
    
    public void growSize(int growSize)
    {
        this.size += growSize;
        this.head.growSize(growSize);
        
        System.out.println("growing size");
        for(SnakeBodyPart s : body)
        {
            s.growSize(growSize);
        }
    }

}
