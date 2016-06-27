import java.awt.*;

/**
 * Created by idanaftaker on 10/05/2016.
 *
 * The interface, that will be returned as the final end product from the factories
 */
public interface SeaCreature{
    //===Methods===
    public void drawCreature(Graphics g);

    public void setMemento();
    public void getMemento();
}
