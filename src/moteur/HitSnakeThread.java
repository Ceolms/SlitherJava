package moteur;

import java.util.ArrayList;
import java.util.Iterator;
import modele.BotSnake;
import modele.Snake;
import modele.SnakeBodyPart;

/**
 * Snake/Snake collision
 *
 * @author theo
 */
public class HitSnakeThread extends Thread {

    Core c;
    Snake snake;
    public boolean running = true;
    
    public HitSnakeThread(Snake s) {
        snake =s;
    }
    
    @Override
    public void run() {
        
        while (running)
        {  
            try {
                // collision snake/bot
                for (Iterator<BotSnake> it = c.botsList.iterator(); it.hasNext();)
                {
                    BotSnake bs = it.next();
                    
                    if(bs != snake)
                    {
                       ArrayList<SnakeBodyPart> listeBody = new ArrayList();
                       listeBody.addAll(bs.body);

                       for(SnakeBodyPart bp : listeBody)
                       {
                            if(bp != null && snake.head.hb.intersect(bp.hb)) 
                            {
                               snake.alive = false;
                               c.die(snake);
                               running = false;
                            }
                       } 
                    }
               }
                //collision snake / joueur ( if snake is not the player)
               if(snake != c.player)
               {
                    ArrayList<SnakeBodyPart> listeBody = new ArrayList();
                    listeBody.addAll(c.player.body);

                    for(SnakeBodyPart bp : listeBody)
                    {
                        if(bp != null && snake.head.hb.intersect(bp.hb)) 
                        {
                            snake.alive = false;
                            c.die(snake);
                            running = false;
                        }
                    } 
               }
            } 
            catch (Exception e)
            {
                //e.printStackTrace();
            }
        }
    }
}
