package vue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPanel;
import moteur.Core;

/**
 * Panel Class that draw visible elements on each cycle
 *
 * @author theo
 */
public class Screen extends JPanel {
    public Core core;
    public int mouseX;
    public int mouseY;

    public Screen() {
        this.setBackground(Color.DARK_GRAY);
        this.addMouseMotionListener(new MouseMotionAdapter() {
        public void mouseMoved(MouseEvent me)
        {
            mouseX = me.getX();
            mouseY = me.getY();
        }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        
        //draw player head
        drawCenteredCircle(g2, core.player.head.x + this.getSize().width/2 , core.player.head.y + this.getSize().height/2, core.player.size,Color.red);
    }

    public void drawCenteredCircle(Graphics2D g, int x, int y, int r,Color c) {
        
        int d = r * 2;

        g.setColor(c);
        g.fillOval(x - r, y - r, d, d);
    }
}
