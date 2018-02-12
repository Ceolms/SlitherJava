package modele;

import java.awt.Color;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author theo
 */
public class Food {

    public boolean onScreen = true;
    public int x;
    public int y;
    public int size = 5;
    public Color color;

    public Food() {
        x = ThreadLocalRandom.current().nextInt(-1000, 1000 + 1);
        y = ThreadLocalRandom.current().nextInt(-1000, 1000 + 1);

        int couleur = ThreadLocalRandom.current().nextInt(1, 9);

        switch (couleur) {
            case 1:
                color = Color.RED;
                break;
            case 2:
                color = Color.BLUE;
                break;
            case 3:
                color = Color.GREEN;
                break;
            case 4:
                color = Color.YELLOW;
                break;
            case 5:
                color = Color.ORANGE;
                break;
            case 6:
                color = Color.CYAN;
                break;
            case 7:
                color = Color.MAGENTA;
                break;
            case 8:
                color = Color.PINK;
                break;
        }
    }
}
