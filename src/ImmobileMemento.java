import java.awt.*;

/**
 * Created by idanaftaker on 27/05/2016.
 */
public class ImmobileMemento {
    //===Members===
    private int size;
    private int x;
    private int y;
    private Color colorr;
    private Immobile obj;

    //===Methods===
    public ImmobileMemento(Immobile state){
        this.obj = state;
        this.size = state.getSize();
        this.x = state.getX();
        this.y = state.getY();
        this.colorr = state.getColor();
    }

    public Immobile getState(){return obj;}

    public void setState(Immobile state){
        this.obj = state;
        this.size = state.getSize();
        this.x = state.getX();
        this.y = state.getY();
        this.colorr = state.getColor();
    }

    public int getSize(){return this.size;}
    public int getX(){return this.x;}
    public int getY(){return this.y;}
    public Color getColor(){return this.colorr;}


}
