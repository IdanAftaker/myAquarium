import java.awt.*;

/**
 * Created by idanaftaker on 18/05/2016.
 */
public class Memento {
    //===Members===
    private int size;
    private Color col;
    private int x_front;
    private int y_front;
    private int horSpeed;
    private int varSpeed;
    private double x_dir;
    private double y_dir;
    private Swimmable obj;
    private HungerState hungry;
    private int eat;
    private int freq;
    //===Methods===
    public Memento(Swimmable state){
        this.obj = state;
        this.size = state.getSize();
        this.col = state.getColorAsColorObject();
        this.x_front = state.get_x_front();
        this.y_front = state.get_y_front();
        this.x_dir = state.get_x_dir();
        this.y_dir = state.get_y_dir();
        this.horSpeed = state.getHorSpeed();
        this.varSpeed = state.getVerSpeed();
        this.eat = state.getEatCount();
        this.hungry = state.getHungerState();
        this.freq = state.getFrequency();
    }
    public Swimmable getState(){return obj;}

    public void setState(Swimmable state){
        this.obj = state;
        this.size = state.getSize();
        this.col = state.getColorAsColorObject();
        this.x_front = state.get_x_front();
        this.y_front = state.get_y_front();
        this.x_dir = state.get_x_dir();
        this.y_dir = state.get_y_dir();
        this.horSpeed = state.getHorSpeed();
        this.varSpeed = state.getVerSpeed();
        this.eat = state.getEatCount();
        this.hungry = state.getHungerState();
        this.freq = state.getFrequency();
    }

    public int getSize(){return this.size;}
    public Color getCol(){return this.col;}
    public int getX_front(){return this.x_front;}
    public int getY_front(){return this.y_front;}
    public int getHorSpeed(){return this.horSpeed;}
    public int getVarSpeed(){return this.varSpeed;}
    public double getX_dir(){return this.x_dir;}
    public double getY_dir(){return  this.y_dir;}
    public HungerState getHungryState(){return this.hungry;}
    public int getCounter(){return this.eat;}
    public int getFreq(){return this.freq;}

}
