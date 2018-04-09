package modele;

import java.awt.Color;
import java.util.ArrayList;
import moteur.HitSnakeThread;
import utils.Coords;
import utils.Params;

/**
 * Snake controlled by the player and the IA ( superclass only)
 * @author theo
 */
public abstract class Snake {

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
    public boolean alive =true;
    public HitSnakeThread hst;

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
        hst = new HitSnakeThread(this);
    }

    /**
     * move the snake depending of the head position
     * add a new body part if possible
     */
    public void updateCoords() {
        //we add the head position a the beginning of the coords list
        coordsList.add(0, new Coords(this.head.x, this.head.y));
       
        // we move the bodyparts
        for(int i = 0 ; i < body.size();i++)
        {
            int x = coordsList.get(i+1).x;
            int y = coordsList.get(i+1).y;
            body.get(i).setPos(x, y);
        }
        // we add one if the snake can grow 
        if(lengthQueue >0)
        {
            body.add(new SnakeBodyPart(coordsList.get(coordsList.size()-1).x, coordsList.get(coordsList.size()-1).y, size, head.color));
            lengthQueue--;
            //System.out.println("new body part");
        }
        else coordsList.remove(coordsList.size()-1); 
        
        // hitbox position update
        for(SnakeBodyPart s : this.body)
        {
            s.updateHitBox();
        }
        
    }
    /**
     * the snake's score increase depending of the food eaten
     * the snake will grow if he reach a certain score
     * @param f Food
     */
    public void eat(Food f){
        
        score += f.score;
        
        int scoreSection = Params.read("scoreSection");
        int scoreGrow = Params.read("scoreGrow");
        
        if(score % scoreGrow ==0) growSize(Params.read("growRatio"));
        if(score % scoreSection ==0) lengthQueue++;  
    }
    
    /**
     * The radius of the snake will augment when a certain score is reached
     * @param growSize 
     */
    public void growSize(int growSize)
    {
        this.size += growSize;
        this.head.growSize(growSize);
        
        //System.out.println("growing size");
        for(SnakeBodyPart s : body)
        {
            s.growSize(growSize);
        }
    }

}
