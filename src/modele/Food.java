package modele;

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;
import utils.Hitbox;
import utils.Params;

/**
 * 
 * @author theo
 */
public class Food {

    public boolean onScreen = true;
    public int x;
    public int y;
    public int size;
    public int score;
    public Color color;
    public Hitbox hb;
    
    public Food() {
        // position
        x = ThreadLocalRandom.current().nextInt(0, Params.read("worldWidth") + 1);
        y = ThreadLocalRandom.current().nextInt(0, Params.read("worldHeight") + 1);
        // size is random
        size = ThreadLocalRandom.current().nextInt(Params.read("minFoodSize") + 1, Params.read("maxFoodSize") + 1);
        // the bigger is the food , the more it give points
        score = size * Params.read("ratioFoodSize");
        int couleur = ThreadLocalRandom.current().nextInt(1, 9);
        hb = new Hitbox(x, y, size);

        switch (couleur) {
            case 1:
                color = Color.RED;
                break;
            case 2:
                color = Color.BLUE;
                break;
            case 3:
                color = Color.GREEN;
                break;
            case 4:
                color = Color.YELLOW;
                break;
            case 5:
                color = Color.ORANGE;
                break;
            case 6:
                color = Color.CYAN;
                break;
            case 7:
                color = Color.MAGENTA;
                break;
            case 8:
                color = Color.PINK;
                break;
        }
    }
    
    public Food(int x , int y) {
        this.x = x;
        this.y = y;
        
        size = ThreadLocalRandom.current().nextInt(Params.read("minFoodSize") + 1, Params.read("maxFoodSize") + 1);
        // the bigger is the food , the more it give points
        score = size * Params.read("ratioFoodSize");
        int couleur = ThreadLocalRandom.current().nextInt(1, 9);
        hb = new Hitbox(x, y, size);

        switch (couleur) {
            case 1:
                color = Color.RED;
                break;
            case 2:
                color = Color.BLUE;
                break;
            case 3:
                color = Color.GREEN;
                break;
            case 4:
                color = Color.YELLOW;
                break;
            case 5:
                color = Color.ORANGE;
                break;
            case 6:
                color = Color.CYAN;
                break;
            case 7:
                color = Color.MAGENTA;
                break;
            case 8:
                color = Color.PINK;
                break;
        }
    }
}
