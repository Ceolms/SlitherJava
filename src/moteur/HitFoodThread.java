package moteur;

import java.util.Iterator;
import modele.Food;
import modele.Snake;

/**
 * collisions snake/food
 *
 * @author theo
 */
public class HitFoodThread extends Thread {

    Core c;

    public HitFoodThread(Core c) {
        this.c = c;
    }

    @Override
    public void run() {

        try {
            while (true) {     
                    Iterator<Food> iter = c.foodList.iterator();

                    while (iter.hasNext()) {
                        Food f = iter.next();

                        if (c.player.head.hb.intersect(f.hb)) {
                            System.out.println("collision player/food");
                            c.player.eat(f);
                            iter.remove();
                        }
                        /*
                        for (Snake s : c.botsList) {
                            if (s.head.hb.intersect(f.hb)) {
                                s.eat(f);
                                iter.remove();
                            }
                        }*/   
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
