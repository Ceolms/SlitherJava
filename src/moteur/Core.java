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
        }
        else 
        {
            gw.gameOver();
        }
    }

    @Override
    public void run() {
        try {
            while (running) {
                screen.repaint();
                sleep(Core.cycle);
            }
        } catch (Exception e) {
        }

    }
}
