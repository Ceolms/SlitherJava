/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import utils.Params;

/**
 *
 * @author theo
 */
public class BestScores extends JPanel{
    ArrayList<String> topScores;
    String[] pseudos;
    int[]scores;

    public BestScores() {
        this.setLayout(new GridLayout(2, 3));
        this.setBackground(Color.BLACK);
        topScores = new ArrayList<>();
        topScores = Params.readAll();
        
        pseudos = new String[topScores.size()];
        scores = new int[topScores.size()];
        
        for(int i =0 ; i < topScores.size();i++)
        {
           String ligne = topScores.get(i);
           String[] parts = ligne.split("=");
           pseudos[i] = parts[0]; 
           scores[i] = Integer.parseInt(parts[1]); // 034556
        }
        trier();
        
        for(int i =0; i< pseudos.length  && i<6; i++)
        {
            JLabel scoreLabel = new JLabel(pseudos[i] + " : " + scores[i]+"    ");
            scoreLabel.setForeground(Color.YELLOW);
            this.add(scoreLabel);
        }
    }
    
    public void trier()
    {
        int temp;
        String tempS;
        for (int i = 1; i < scores.length; i++) {
            for(int j = i ; j > 0 ; j--){
                if(scores[j] > scores[j-1]){
                    temp = scores[j];
                    scores[j] = scores[j-1];
                    scores[j-1] = temp;
                    
                    tempS = pseudos[j];
                    pseudos[j] = pseudos[j-1];
                    pseudos[j-1] = tempS;
                }
            }
        }
    }
    
    
}
