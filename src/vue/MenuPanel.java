package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/**
 *
 * @author theo
 */
public class MenuPanel extends JPanel{
    GameWindow gw;
    JButton btnJouer;
    JTextArea zonePseudo;
    JLabel titre;
    

    public MenuPanel(GameWindow gw) {
        this.gw = gw;
        this.setBackground(Color.BLACK);
        
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        
        titre = new JLabel("JSnake", SwingConstants.CENTER);
        titre.setFont(new Font("built titling", Font.PLAIN, 50));
        titre.setForeground(Color.red);
        
        zonePseudo = new JTextArea(1, 20);
        zonePseudo.setText("Pseudo");
        btnJouer = new JButton("Jouer!");
        btnJouer.setMinimumSize(new Dimension(100, 100));
        btnJouer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gw.pseudo = zonePseudo.getText();
                gw.reset();
            }
        });
        
        c.insets = new Insets(0, 70, 0, 0);
        this.add(titre,c);
        c.insets = new Insets(10, 10, 0, 0);
        c.anchor  = GridBagConstraints.LINE_START;
        c.gridy = 1;
        c.gridx = 0;
        this.add(zonePseudo,c);
        c.anchor  = GridBagConstraints.LINE_END;
        c.weighty = 1.0;
        c.gridy = 1;
        c.gridx = 3;
        this.add(btnJouer,c);
        c.insets = new Insets(0,0,0,0);
        c.anchor  = GridBagConstraints.LINE_START;
        c.gridy = 2;
        c.gridx = 0;
        this.add(new BestScores(),c);
    }  
}
