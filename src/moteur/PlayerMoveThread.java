package moteur;

import utils.Params;
import modele.*;
import vue.*;

/**
 *
 * @author theo
 */
public class PlayerMoveThread extends Thread {

    private double mouseAngle = 0;
    private double directionAngle = 500; // angle impossible pour l'initialisation
    private int rotationSpeed;
    private Core c;

    private Screen screen;

    PlayerSnake player;

    public PlayerMoveThread(Core c) {
        this.player = c.player;
        screen = c.screen;
        this.c = c;
        try {
            rotationSpeed = Params.read("rotationSpeed");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void calculateAngleMouse() {
        double xS;
        double yS;
        double xM;
        double yM;

        xS = screen.getWidth() / 2;
        yS = screen.getHeight() / 2;

        //mouse position
        synchronized (screen) {
            xM = screen.mouseX;
            yM = screen.mouseY;
        }/*
        xM -= screen.getWidth() / 2;
        yM -= screen.getHeight() / 2;
        yM = -yM;*/

        mouseAngle = (180 * Math.atan2(xM - xS, yM - yS) / Math.PI);
        //System.out.println("Angle = " + mouseAngle);
    }

    public void rotateLeft() {
        if (!(directionAngle - 3 < mouseAngle && mouseAngle < directionAngle + 3)) {
            directionAngle += rotationSpeed;
            if (directionAngle > 180) {
                directionAngle = -180;
            }
        }
        //System.out.println(" LeftR mouseAngle: " + mouseAngle + " directionAngle " + directionAngle);

    }

    public void rotateRight() {
        if (!(directionAngle - 3 < mouseAngle && mouseAngle < directionAngle + 3)) {
            directionAngle -= rotationSpeed;
            if (directionAngle < -180) {
                directionAngle = 180;
            }
        }
        //System.out.println(" RightR mouseAngle: " + mouseAngle + " directionAngle " + directionAngle);
    }

    public void chooseDirection() {
        if (directionAngle == 500) {
            directionAngle = mouseAngle;
        } else if (directionAngle != mouseAngle) {
            if (-180 <= mouseAngle && mouseAngle <= -90) // NW direction
            {
                if (directionAngle > mouseAngle && directionAngle < mouseAngle + 180) {
                    rotateRight();
                    //System.out.print("NW RIGHT");
                } else {
                    rotateLeft();
                    //System.out.print("NW LEFT");
                }
            } else if (90 <= mouseAngle && mouseAngle <= 180) // NE direction
            {
                if (directionAngle < mouseAngle && directionAngle > mouseAngle - 180) {
                    rotateLeft();
                    //System.out.print("NE");
                } else {
                    rotateRight();
                    //System.out.print("NE");
                }
            } else if (0 <= mouseAngle && mouseAngle <= 90)// SE
            {
                if (directionAngle > mouseAngle && directionAngle > mouseAngle - 180) {
                    rotateRight();
                    //System.out.print("SE");
                } else {
                    rotateLeft();
                    //System.out.print("SE");
                }
            } else // SW
            {
                if (directionAngle > mouseAngle && directionAngle < mouseAngle + 180) {
                    rotateRight();
                    //System.out.print("SW");
                } else {
                    rotateLeft();
                    //System.out.print("SW");
                }
            }
        }
    }

    @Override
    public void run() {
        try {
            while (player.alive) {
                calculateAngleMouse();

                chooseDirection();
                double dirX;
                double dirY;
                synchronized (player) {

                    int speed;
                    if(player.isBoosting) speed = player.boostSpeed;
                    else speed = player.speed;

                    dirX = (speed * (float) Math.cos(Math.toRadians(directionAngle - 90)));
                    dirY = (speed * (float) Math.sin(Math.toRadians(directionAngle + 90)));
                    player.head.x += dirX;
                    player.head.y += dirY;
                    System.out.println("dirX = " + dirX + " dirY = " + dirY);
                    player.updateCoords();
                }
                for (Food f : c.foodList) {
                    synchronized (f) {
                        f.x -= dirX;
                        f.y -= dirY;
                    }
                }

                sleep(Core.cycle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
