package moteur;

import utils.Params;
import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JFrame;
import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;
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
    public OnScreenThread ost;
    public HitFoodThread hft;

    public static final int cycle = Params.read("cycle"); // sleep ( cycle)

    public Core(Screen screen) {
        player = new PlayerSnake(Params.read("screenWidth") / 2, Params.read("screenHeight") / 2, Color.RED);
        this.screen = screen;
        
        // controllers creation
        pmt = new PlayerMoveThread(this);
        ost = new OnScreenThread(this);
        hft = new HitFoodThread(this);
        
        botsList = new ArrayList<>();
        foodList = new ArrayList<>();

        for (int i = 0; i < ThreadLocalRandom.current().nextInt(Params.read("minFood"), Params.read("maxFood") + 1); i++) {
            foodList.add(new Food());
        }
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
        int width = Params.read("screenWidth");
        int height = Params.read("screenHeight");
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // core controller and screen creation
        Screen screen = new Screen();
        Core core = new Core(screen);
        screen.core = core;
        screen.player = core.player;
        frame.add(screen);
        frame.setVisible(true);
        core.screen = screen;
        
        // controllers start
        core.start();
        core.pmt.start();
        core.ost.start();
        core.hft.start();
    }
}
