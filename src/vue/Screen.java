package vue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPanel;
import modele.Food;
import modele.PlayerSnake;
import modele.SnakeBodyPart;
import moteur.Core;

/**
 * Panel Class that draw visible elements on each cycle
 *
 * @author theo
 */
public class Screen extends JPanel {

    public Core core;
    public PlayerSnake player;
    public int mouseX;
    public int mouseY;

    public Screen() {
        this.setBackground(Color.DARK_GRAY);
        
        this.addMouseMotionListener(new MouseMotionAdapter()
        {
            public void mouseMoved(MouseEvent me) {
                
                synchronized(this)
                {
                     mouseX = me.getX();
                     mouseY = me.getY();
                }  
            }
        });
        this.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {
                synchronized(player)
                {
                    player.isBoosting = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                player.isBoosting = false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // food drawing
        for (Food f : core.foodList) {
            synchronized (f) {
                if (f.onScreen) {
                    drawCenteredCircle(g2, f.x, f.y, f.size, f.color);
                }
            }
        }
        
        //draw player body
        for(SnakeBodyPart sb : core.player.body)
        {
            drawCenteredCircle(g2, sb.x % this.getWidth()/2 + this.getWidth()/2  ,sb.y % this.getHeight()/2 + this.getHeight()/2, sb.r,sb.color);
        }
        
        //draw player head
        drawCenteredCircle(g2,this.getWidth()/2 ,this.getHeight()/2 ,player.size,player.head.color);
    }

    public void drawCenteredCircle(Graphics2D g, int x, int y, int r, Color c)
    {
        int d = r * 2;
        
        g.setColor(Color.GRAY);
        g.fillOval(x - r, y - r, d+1, d+1);
        
        g.setColor(c);
        g.fillOval(x - r, y - r, d, d);
        
    }
}
