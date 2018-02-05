package moteur;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JFrame;
import modele.*;
import vue.Screen;

/**
 * Classe principale qui gère les threads et les éléments du jeu
 *
 * @author theo
 */
public class Core extends Thread {

    Screen screen;
    public PlayerSnake player;
    public ArrayList<Food> foodList;
    public ArrayList<BotSnake> botsList;
    public PlayerMoveThread pmt;
    public static final int speed = 10;
    public static final int boostSpeed = 20;
    public static final int cycle = 200; // sleep ( cycle)

    public Core(Screen screen) {
        player = new PlayerSnake(0, 0, Color.RED);
        this.screen = screen;
        pmt = new PlayerMoveThread(player, screen);
    }

    @Override
    public void run() {
        try {
            while (player.alive) {
                screen.repaint();
                sleep(Core.cycle);
            }
        } catch (Exception e) {
        }

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("SlitherJava");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        Screen screen = new Screen();
        Core core = new Core(screen);
        screen.core = core;
        frame.add(screen);
        
        frame.setVisible(true);
        core.screen = screen;
        core.start();
        core.pmt.start();
        
    }
}
