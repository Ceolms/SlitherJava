package moteur;

import modele.BotSnake;
import modele.Food;

/**
 * Check if an object is visible , to optimize graphics.draw
 *
 * @author theo
 */
public class OnScreenThread extends Thread {

    Core core;
    public boolean running = true;

    public OnScreenThread(Core c) {
        core = c;
    }

    @Override
    public void run() 
    {
        try {
            while (running) {
                for (Food f : core.foodList) {
                    synchronized (f) {
                        if (f.x >= 0 && f.x < core.screen.getWidth() && f.y >= 0 && f.y <= core.screen.getHeight()) {
                            f.onScreen = true;
                        } else {
                            f.onScreen = false;
                        }
                    }
                }
                for (BotSnake bs : core.botsList) {
                    synchronized (bs) {
                        if (bs.head.x >= 0 && bs.head.x < core.screen.getWidth() && bs.head.y >= 0 && bs.head.y <= core.screen.getHeight()) {
                            bs.onScreen = true;
                        } else {
                            bs.onScreen = false;
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
    }

}
