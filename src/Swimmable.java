
import java.awt.*;


/**
 * Created by idanaftaker on 06/04/2016.
 */
public abstract class Swimmable extends AnimalFactory implements MarineAnimal,SeaCreature, Cloneable{

    //===Members===
    protected int horSpeed;
    protected int verSpeed;
    protected static boolean isBarrier;
    //==Methods===

    public Swimmable(int hor, int ver) {
        setHorSpeed(hor);
        setVerSpeed(ver);
        isBarrier = false;
    }

    public int getHorSpeed() { return horSpeed; }

    public int getVerSpeed() { return verSpeed; }

    public void setHorSpeed(int hor) { horSpeed = hor; }

    public void setVerSpeed(int ver) { verSpeed = ver; }

    abstract public String getAnimalName();

    abstract public void drawAnimal(Graphics g);

    abstract public void setSuspend();

    abstract public void setResume();

    abstract public int getSize();

    abstract public void eatInc();

    abstract public int getEatCount();

    abstract public String getColor();

    abstract public void setAnimalName(int num);

    public abstract void addListener(Observer l);

    public abstract void removeListener(Observer l);

    public abstract void setState(HungerState state);

    public abstract int getFrequency();

    public abstract String getStringState();

    public abstract void setColor(Color col);

    public abstract Color getColorAsColorObject();

    public abstract int get_x_front();
    public abstract int get_y_front();

    public abstract double get_x_dir();
    public abstract double get_y_dir();

    public abstract HungerState getHungerState();

    public abstract void setSize(int size);

    public abstract void setFrequency();
}
