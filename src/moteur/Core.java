package moteur;

import utils.Params;
import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import modele.*;
import vue.GameWindow;
import vue.Screen;

/**
 * Main thread
 *
 * @author theo
 */
public class Core extends Thread {

    public GameWindow gw;
    public Screen screen;
    public PlayerSnake player;
    public ArrayList<Food> foodList;
    public ArrayList<BotSnake> botsList;
    public boolean running = true;

    public PlayerMoveThread pmt;
    public OnScreenThread ost;
    public HitFoodThread hft;

    public static final int cycle = Params.read("cycle"); // sleep (cycle)

    public Core(Screen screen) {
        player = new PlayerSnake(Params.read("screenWidth") / 2, Params.read("screenHeight") / 2, Color.RED);
        player.hst = new HitSnakeThread(player);
        player.hst.c = this;
        this.screen = screen;
        
        // controllers creation
        pmt = new PlayerMoveThread(this);
        ost = new OnScreenThread(this);
        hft = new HitFoodThread(this);
        
        botsList = new ArrayList<>();
        foodList = new ArrayList<>();

        //bots creation
        int posX=Params.read("screenWidth") / 2;
        int posY=Params.read("screenHeight") / 2;
                
        for(int i = 0 ; i < Params.read("initBotNumber") ; i++)
        {
            posX += 100;
            
            BotSnake bs = new BotSnake(posX, posY, Color.yellow);
            bs.hst.c = this;
            botsList.add(bs);
        }
        
        
        //food creation
        for (int i = 0; i < ThreadLocalRandom.current().nextInt(Params.read("minFood"), Params.read("maxFood") + 1); i++) {
            foodList.add(new Food());
        }
    }
    /**
     * remove the given snake from the list
     * send a Game Over if it's the player
     * @param snake 
     */
    public void die(Snake snake)
    {
        if (snake instanceof BotSnake)
        {
            botsList.remove(snake);
            foodList.add(new Food(snake.head.x  , snake.head.y));
            foodList.add(new Food(snake.head.x+2, snake.head.y+2));
            foodList.add(new Food(snake.head.x-2, snake.head.y-2));
            foodList.add(new Food(snake.head.x+2, snake.head.y-2));
            foodList.add(new Food(snake.head.x-2, snake.head.y+2));

            for(SnakeBodyPart b : snake.body)
            {
                foodList.add(new Food(b.x  , b.x));
                foodList.add(new Food(b.x+2, b.y+2));
                foodList.add(new Food(b.x-2, b.y-2));
                foodList.add(new Food(b.x+2, b.y-2));
                foodList.add(new Food(b.x-2, b.y+2));
            }
                    
        }
        else 
        {
            gw.gameOver();
        }
    }
    
    /**
     * 
     * @param playerX
     * @param playerY 
     */
    public void spawnBot(int playerX,int playerY)
    {
        int rayonX=Params.read("screenWidth") / 2;
        int rayonY=Params.read("screenHeight") / 2;
        
        int chance = ThreadLocalRandom.current().nextInt(0, 1000);
        if(chance < Params.read("spawnRate"))
        {
            System.out.println("spawnBot()");
            int x = ThreadLocalRandom.current().nextInt(playerX+rayonX, playerX+rayonX+200);
            int y = ThreadLocalRandom.current().nextInt(playerY, playerY+rayonY+200);
            int xN = ThreadLocalRandom.current().nextInt(playerX-rayonX-200,playerX-rayonX);
            int yN = ThreadLocalRandom.current().nextInt(playerY-rayonY-200,playerY-rayonY);
            
            int position = ThreadLocalRandom.current().nextInt(0, 4);
            BotSnake bs;
            switch(position)
            {
                
                case(0):          
                    bs = new BotSnake(x, y, Color.yellow);
                    bs.hst.c = this;  
                    bs.bmt.c = this; 
                    botsList.add(bs);
                    bs.bmt.start();
                    bs.hst.start();
                    System.out.println("x: "+x+" y: "+y);
                    break;
                case(1):
                    bs = new BotSnake(x, yN, Color.yellow);
                    bs.hst.c = this;
                    bs.bmt.c = this; 
                    botsList.add(bs);
                    bs.bmt.start();
                    bs.hst.start();
                    System.out.println("x: "+x+" y: "+yN);
                    break;
                case(2):
                    bs = new BotSnake(xN, y, Color.yellow);
                    bs.hst.c = this;
                    bs.bmt.c = this; 
                    botsList.add(bs);
                    bs.bmt.start();
                    bs.hst.start();
                    System.out.println("x: "+xN+" y: "+y);
                    break;
                case(3):
                    bs = new BotSnake(xN, yN, Color.yellow);
                    bs.hst.c = this;
                    bs.bmt.c = this; 
                    botsList.add(bs);
                    bs.bmt.start();
                    bs.hst.start();
                    System.out.println("x: "+xN+" y: "+yN);
                    break;          
            }
        }
    }
    
    @Override
    public void run() {
        try {
            while (running) {
                screen.repaint();
                screen.scorePanel.repaint();
                spawnBot(player.head.x, player.head.x);
                sleep(Core.cycle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
