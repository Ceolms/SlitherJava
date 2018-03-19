/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import utils.Params;

/**
 *
 * @author theo
 */
public class ScorePanel extends JPanel{
    static JLabel scoreLabel;
    
    public ScorePanel() {
        super();
        scoreLabel = new JLabel("Taille: "+Params.read("initLength"));
        scoreLabel.setForeground(Color.WHITE);
        this.add(scoreLabel);
        this.setBounds(0,0,400,400);
        this.setOpaque(false);         
    }
    
    public static void updateScore(int score)
    {
        scoreLabel.setText("Taille : " + score);
    }
    
    
}
