/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.HeadlessException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import modele.BotSnake;
import moteur.Core;
import utils.Params;

/**
 *
 * @author theo
 */
public class GameWindow{
    Core core;
    Screen screen;
    JFrame frame;
    public ScorePanel sp; 
    public String pseudo;

    public GameWindow() throws HeadlessException {
        frame= new JFrame();
        frame.setTitle("JSnake");
        int width = Params.read("screenWidth");
        int height = Params.read("screenHeight");
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     
    }
    
    public void startGame(String pseudo)
    {
        // core controller and screen creation
        screen = new Screen();
        core = new Core(screen);
        screen.core = core;
        screen.player = core.player;
        sp = new ScorePanel();
        frame.add(sp);
        screen.scorePanel = sp;
        
        frame.add(screen);
        
        core.screen = screen;
        core.gw = this;
        core.start();
        
        // start controllers
        core.pmt.start();
        
        for(BotSnake bs : core.botsList)
        {
            bs.bmt.c = core;
            bs.bmt.screen = screen;
            bs.bmt.start();
            bs.hst.start();
        }
        core.ost.start();
        core.hft.start();
        core.player.hst.start();
        
        frame.setVisible(true);
        frame.validate();
    }
    
    public void gameOver()
    {
        core.pmt.running = false;
        int finalScore = core.player.score;
        
        String scoreLine = pseudo + "=" + finalScore;
        
        Params.write(scoreLine);
        
        int reponse = JOptionPane.showConfirmDialog(frame, "Game Over ! Final Score: " + finalScore+"\n Play again?", "Game Over!", JOptionPane.YES_NO_OPTION);
        if(reponse == JOptionPane.YES_OPTION){
           reset();
           		
        }else if(reponse == JOptionPane.NO_OPTION){
			System.exit(0);
        }
    }
    
    public void reset()
    {
        if(core != null)
        { // stopping old threads
            core.hft.running = false;
            core.ost.running = false;

            for(BotSnake bs : core.botsList)
            {
                bs.bmt.running = false;
                bs.hst.running = false;
            }
        }
        
        frame.dispose();
        frame= new JFrame();
        frame.setTitle("JSnake");
        int width = Params.read("screenWidth");
        int height = Params.read("screenHeight");
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // core controller and screen creation
        screen = new Screen();
        core = new Core(screen);
        screen.core = core;
        screen.player = core.player;
        ScorePanel sp = new ScorePanel();
        frame.add(sp);
        screen.scorePanel = sp;
        
        frame.add(screen);
        
        core.screen = screen;
        core.gw = this;
        core.start();
        
        // start controllers
        core.pmt.start();
        
        for(BotSnake bs : core.botsList)
        {
            bs.bmt.c = core;
            bs.bmt.screen = screen;
            bs.bmt.start();
            bs.hst.start();
        }
        core.ost.start();
        core.hft.start();
        core.player.hst.start();
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.validate();
    }
    
    
   public static void main(String[] args) {
        GameWindow gw = new GameWindow();
        gw.frame.add(new MenuPanel(gw));
        gw.frame.setLocationRelativeTo(null);
        gw.frame.pack();
        gw.frame.setVisible(true);
        gw.frame.validate();
    }
}
