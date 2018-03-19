package modele;

import java.awt.Color;
import moteur.BotMoveThread;

/**
 * Snake controlled by the IA
 * extends Snake
 * has a thread for his behavior
 * @author theo
 */
public class BotSnake extends Snake {

    public BotMoveThread bmt;
    
    public BotSnake(int x, int y, Color color) {
        super(x, y, color);
        bmt = new BotMoveThread(this);
    }

}
