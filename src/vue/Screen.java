package vue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import javax.swing.JPanel;
import modele.BotSnake;
import modele.Food;
import modele.PlayerSnake;
import modele.SnakeBodyPart;
import moteur.Core;
import utils.Params;

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
    ScorePanel scorePanel;

    public Screen() {
        this.setBackground(Color.DARK_GRAY);

        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent me) {

                synchronized (this) {
                    mouseX = me.getX();
                    mouseY = me.getY();
                }
            }
        });
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                synchronized (player) {
                    player.isBoosting = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                player.isBoosting = false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        //g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double viewSizeX = this.getWidth();
        double viewSizeY = this.getHeight();

        double offsetMaxX = Params.read("worldWidth") - viewSizeX;
        double offsetMaxY = Params.read("worldHeight") - viewSizeY;

        double camX = player.head.x - viewSizeX /2;
        double camY = player.head.y - viewSizeY /2;

        if (camX > offsetMaxX) {
            camX = offsetMaxX;
        }
        if (camY > offsetMaxY) {
            camY = offsetMaxY;
        }
        g2.translate(-camX, -camY);

        this.scorePanel.repaint();
        
        // food drawing
        ArrayList<Food> listeF = new ArrayList<Food>();
        // to avoid collision exception
        listeF.addAll(core.foodList);
        
        for (Food f : listeF) {
            if(f!= null) drawCenteredCircle(g2, f.x, f.y, f.size, f.color);
        }
        
        //bots drawing
        ArrayList<BotSnake> listeSnake = new ArrayList<BotSnake>();
        // to avoid collision exception
        listeSnake.addAll(core.botsList);
        
        for (BotSnake b : listeSnake) {
            if(b!= null)
            {
                for (int i = b.body.size() - 1; i >= 0; i--) {
                drawCenteredCircle(g2, b.body.get(i).x, b.body.get(i).y, b.body.get(i).r, b.body.get(i).color);
                }
            drawCenteredCircle(g2, b.head.x, b.head.y, b.size, b.head.color);
            }
        }
        
        // Player drawing
        //draw player body
        if(player.alive)
        {
             for (int i = player.body.size() - 1; i >= 0; i--) {
            drawCenteredCircle(g2, player.body.get(i).x, player.body.get(i).y, player.body.get(i).r, player.body.get(i).color);
            }
            //draw player head
            drawCenteredCircle(g2, player.head.x, player.head.y, player.size, player.head.color);
        }
    }

    public void drawCenteredCircle(Graphics2D g, int x, int y, int r, Color c) {
        
        // reduire r selon player zoom !
        double rZ = r * player.zoom;
        double d = rZ * 2;

        g.setColor(Color.GRAY);
        g.fillOval(x - (int)rZ, y - (int)rZ, (int)d + 1, (int)d + 1);

        g.setColor(c);
        g.fillOval(x - (int)rZ, y - (int)rZ, (int)d, (int)d);
    }
}
