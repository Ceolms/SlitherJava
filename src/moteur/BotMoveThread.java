package moteur;

import java.util.ArrayList;
import modele.BotSnake;
import modele.Food;
import modele.SnakeHead;
import utils.Coords;
import utils.Params;
import vue.Screen;

/**
 * Thread for bot behavior
 *
 * @author theo
 */
public class BotMoveThread extends Thread {
    
    private double targetAngle = 180;
    private double directionAngle = 500; // impossible angle for init
    private int rotationSpeed;
    public Core c;
    public Screen screen;
    public boolean running = true;
    
    private BotSnake snake;

    public BotMoveThread(BotSnake bs) {
        this.snake = bs;
        try {
            rotationSpeed = Params.read("rotationSpeed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void rotateLeft() {
        if (!(directionAngle - 3 < targetAngle && targetAngle < directionAngle + 3)) {
            directionAngle += rotationSpeed;
            if (directionAngle > 180) {
                directionAngle = -180;
            }
        }
    }

    public void rotateRight() {
        if (!(directionAngle - 3 < targetAngle && targetAngle < directionAngle + 3)) {
            directionAngle -= rotationSpeed;
            if (directionAngle < -180) {
                directionAngle = 180;
            }
        }
    }
    
    /**
     * check if the bot is in danger
     * */
    public Coords isFoeNear()
    { 
        int range  = Params.read("botDetectionRange");
        SnakeHead playerH = this.c.player.head;
        SnakeHead bot = this.snake.head;
        
        if(bot.x - range < playerH.x && playerH.x < bot.x + range)
        {
           if(bot.y - range < playerH.y && playerH.y < bot.y + range)
            {
                return new Coords(playerH.x, playerH.y);
            } 
        }
        
        for(BotSnake botE : c.botsList)
        {
            if (bot != botE.head)
            {
                if(bot.x - range < botE.head.x && botE.head.x < bot.x + range)
                {
                   if(bot.y - range < botE.head.y && botE.head.y < bot.y + range)
                    {
                        return new Coords(botE.head.x, botE.head.y);
                    } 
                }
            }
        }
        return new Coords(-1, -1);
    }
   
    public Food getNearestFood()
    {  
        Food fC = new Food(100000,1000000);
        double minDistance = 100000;
        
        try {
            ArrayList<Food> foodList = new ArrayList<Food>();
        foodList.addAll(c.foodList);
        for(Food f : foodList)
        {
            synchronized(f)
            {
                double distance = Math.hypot(snake.head.x-f.x, snake.head.y-f.y);
                if (distance < minDistance)
                {
                     fC = f;
                     minDistance = distance;
                }
            }    
        }
        return fC;
        } catch (Exception e) {
            return fC;
        }
    }
    
    public void chooseBehavior()
    {
        Coords coords = isFoeNear();
        if(coords.x == -1 && coords.y == -1) // eat behavior
        {
            Food f = getNearestFood();
            targetAngle = (180 * Math.atan2(f.x - snake.head.x, f.y - snake.head.y) / Math.PI);
        }
        else
        {
            targetAngle = (180 * Math.atan2( snake.head.x - coords.x,snake.head.y - coords.y) / Math.PI);
        }
    }
    //rotate to go to the targetAngle
    public void chooseDirection() {
        if (directionAngle == 500) {
            directionAngle = targetAngle;
        } else if (directionAngle != targetAngle) {
            if (-180 <= targetAngle && targetAngle <= -90) // NW direction
            {
                if (directionAngle > targetAngle && directionAngle < targetAngle + 180) {
                    rotateRight();
                } else {
                    rotateLeft();
                }
            } else if (90 <= targetAngle && targetAngle <= 180) // NE direction
            {
                if (directionAngle < targetAngle && directionAngle > targetAngle - 180) {
                    rotateLeft();
                } else {
                    rotateRight();
                }
            } else if (0 <= targetAngle && targetAngle <= 90) // SE direction
            {
                if (directionAngle > targetAngle && directionAngle > targetAngle - 180) {
                    rotateRight();
                } else {
                    rotateLeft();
                }
            } else // SW direction
            {
                if (directionAngle > targetAngle && directionAngle < targetAngle + 180) {
                    rotateRight();
                } else {
                    rotateLeft();
                }
            }
        }
    }
            
    
    @Override
    public void run() {
        try {
            while (snake.alive && running) {
                chooseBehavior();
                chooseDirection();
     
                double dirX;
                double dirY;
                synchronized (this) {

                    int speed;
                    if (snake.isBoosting) {
                        speed = snake.boostSpeed;
                    } else {
                        speed = snake.speed;
                    }
                    dirX = (speed * (float) Math.cos(Math.toRadians(directionAngle - 90)));
                    dirY = (speed * (float) Math.sin(Math.toRadians(directionAngle + 90)));
                    snake.head.x += dirX;
                    snake.head.y += dirY;
                    snake.head.updateHitBox();
                    snake.updateCoords();
                }
                sleep(Core.cycle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
