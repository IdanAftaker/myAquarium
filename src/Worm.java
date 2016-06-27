import java.awt.*;

/**
 * Created by idanaftaker on 10/05/2016.
 */
public class Worm {
    //===Members===
    protected static boolean isFood = false; //animal food static member for paint components

    //===Methods===

    /**
     * c'tor
     * defined as private
     */
    public Worm(){
//        System.out.println("Worm c'tor");
    }

    public static void draw(Graphics g, AquaPanel panel){
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        g2.setColor(Color.red);
        g2.drawArc(panel.getWidth()/2, panel.getHeight()/2-5, 10, 10, 30, 210);
        g2.drawArc(panel.getWidth()/2, panel.getHeight()/2+5, 10, 10, 180, 270);
        g2.setStroke(new BasicStroke(1));
    }

    public static void changeFood(){
        isFood = !isFood;
    }


}
