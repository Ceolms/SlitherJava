package modele;

import java.awt.Color;

/**
 *
 * @author theo
 */
public class PlayerSnake extends Snake{
    public boolean alive;
    
   
    
    public PlayerSnake(int x, int y, Color color) {
        super(x, y, color);
        alive = true;
      
    }
    
}
