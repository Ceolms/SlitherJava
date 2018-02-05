/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moteur;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import modele.PlayerSnake;
import vue.Screen;

/**
 *
 * @author theo
 */
public class PlayerMoveThread extends Thread {
    public double mouseAngle;
    private Screen screen;

    PlayerSnake player;

    public PlayerMoveThread(PlayerSnake player,Screen s) {
        this.player = player;
        screen =s;
    }
    
    public void calculateAngle()
    {

        // head position
        double xS = player.head.x;
        double yS = player.head.y;
        //mouse position
        double xM = screen.mouseX - screen.getWidth() / 2;
        double yM = screen.mouseY - screen.getHeight() / 2;
        yM = - yM;

        synchronized(this)
        {
            mouseAngle = (180*Math.atan2(xM-xS,yM-yS)/Math.PI);
        }
       
        //System.out.println("MouseX: " + xM  + " MouseY: " + yM);
        System.out.println("Angle = " + mouseAngle);
    }
            

    @Override
    public void run() {
        try {
            while (player.alive)
            {
                calculateAngle();

                sleep(Core.cycle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
