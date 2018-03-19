package modele;

import java.awt.Color;
import utils.Params;

/**
 *
 * @author theo
 */
public class PlayerSnake extends Snake {

    public float zoom = 1.0f;

    public PlayerSnake(int x, int y, Color color) {
        super(x, y, color);
    }
    
    @Override
    public void growSize(int growSize)
    {
        if(this.size < Params.read("sizeLimit"))
        {
            this.size += growSize;
            this.head.growSize(growSize);
            if(zoom > 0.6f)this.zoom -= 0.05f;

            System.out.println("growing size");
            for(SnakeBodyPart s : body)
            {
                s.growSize(growSize);
            }
        } 
    }
}
