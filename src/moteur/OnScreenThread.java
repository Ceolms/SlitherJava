/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moteur;

import modele.Food;

/**
 * Thread qui vérifie si les élements sont a l'écran ou non
 * @author theo
 */
public class OnScreenThread extends Thread{
    Core core;

    public OnScreenThread(Core c) {
        core = c;
    }

    @Override
    public void run() {
        try {
            while(true)
            {
                for(Food f : core.foodList)
                {
                    synchronized(f)
                    {   
                        if (f.x >= 0  && f.x < core.screen.getWidth() && f.y >= 0 && f.y <= core.screen.getHeight())
                        {
                            f.onScreen = true;
                        }
                        else
                        {
                            f.onScreen = false;
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
    }
    
    
    
    
    
}
