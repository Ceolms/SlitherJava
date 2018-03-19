package moteur;

import utils.Params;
import modele.*;
import vue.*;

/**
 * control the player movements
 * @author theo
 */
public class PlayerMoveThread extends Thread {

    private double mouseAngle = 0;
    private double directionAngle = 500; // impossible angle for init
    private int rotationSpeed;
    public boolean running = true;

    private Screen screen;

    PlayerSnake player;

    public PlayerMoveThread(Core c) {
        this.player = c.player;
        screen = c.screen;
        try {
            rotationSpeed = Params.read("rotationSpeed");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * calcutate the angle between the mouse and the player's head ( at the center of the screen )
     */
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
        }

        mouseAngle = (180 * Math.atan2(xM - xS, yM - yS) / Math.PI);
    }

    public void rotateLeft() {
        if (!(directionAngle - 3 < mouseAngle && mouseAngle < directionAngle + 3)) {
            directionAngle += rotationSpeed;
            if (directionAngle > 180) {
                directionAngle = -180;
            }
        }
    }

    public void rotateRight() {
        if (!(directionAngle - 3 < mouseAngle && mouseAngle < directionAngle + 3)) {
            directionAngle -= rotationSpeed;
            if (directionAngle < -180) {
                directionAngle = 180;
            }
        }
    }

    public void chooseDirection() {
        if (directionAngle == 500) {
            directionAngle = mouseAngle;
        } else if (directionAngle != mouseAngle) {
            if (-180 <= mouseAngle && mouseAngle <= -90) // NW direction
            {
                if (directionAngle > mouseAngle && directionAngle < mouseAngle + 180) {
                    rotateRight();
                } else {
                    rotateLeft();
                }
            } else if (90 <= mouseAngle && mouseAngle <= 180) // NE direction
            {
                if (directionAngle < mouseAngle && directionAngle > mouseAngle - 180) {
                    rotateLeft();
                } else {
                    rotateRight();
                }
            } else if (0 <= mouseAngle && mouseAngle <= 90)// SE direction
            {
                if (directionAngle > mouseAngle && directionAngle > mouseAngle - 180) {
                    rotateRight();
                } else {
                    rotateLeft();
                }
            } else // SW direction
            {
                if (directionAngle > mouseAngle && directionAngle < mouseAngle + 180) {
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
            while (player.alive && running) {
                calculateAngleMouse();
                chooseDirection();
                double dirX;
                double dirY;
                synchronized (player) {

                    int speed;
                    if (player.isBoosting) {
                        speed = player.boostSpeed;
                    } else {
                        speed = player.speed;
                    }

                    dirX = (speed * (float) Math.cos(Math.toRadians(directionAngle - 90)));
                    dirY = (speed * (float) Math.sin(Math.toRadians(directionAngle + 90)));
                    player.head.x += dirX;
                    player.head.y += dirY;
                    player.head.updateHitBox();
                    player.updateCoords();
                }
                sleep(Core.cycle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
