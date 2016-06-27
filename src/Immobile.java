import java.awt.*;

/**
 * Created by idanaftaker on 10/05/2016.
 */
public abstract class Immobile implements SeaCreature{
    //===Members===
    private String name;

    //==Methods===

    public Immobile(String name){
        this.name = name;
    }
    public abstract int getSize();
    public abstract int getX();
    public abstract int getY();
    public abstract Color getColor();
    public abstract String getColorString();
}
