import javax.swing.*;
import java.awt.*;
import java.util.Vector;
import java.lang.Math;



/**
 * Created by idanaftaker on 06/04/2016.
 */
public class Fish extends Swimmable implements MarineAnimal{

    //===Members===
    private Vector<Observer> list = new Vector<Observer>(); //Observer
    private String name;
    private Color col;
    private int size;
    private int x_front;
    private int y_front;
    private int eat;
    private double x_dir;
    private double y_dir;
    private double newHorSpeed;
    private double newVerSpeed;
    private AquaPanel panel = null;
    private static boolean isSuspended = false;

    private int frequency; //frequency of eating will calculate by animal size * 2
    private HungerState state;
    private Hungry hungry;
    private Satiated satiated;

    //Memento
    Originator originator = null;
    Caretaker caretaker = null;

    //===Methods===
    public Fish(){
        super(5, 5);
        this.name = getClass().getName();
        this.panel = null;
        this.size = 170;
        this.col = Color.blue;
        this.eat = 0;
        this.x_front = 0;
        this.y_front = 0;
        this.x_dir = 1;
        this.y_dir = 1;
        this.newHorSpeed = 0;
        this.newVerSpeed = 0;
        this.frequency = this.size *2;
        this.hungry = new Hungry();
        this.satiated = new Satiated();
        this.state = null;
    }

    public Fish(AquaPanel panel, int size, Color col, int horSpeed, int varSpeed) {
        super(horSpeed, varSpeed);
        this.panel = panel;
        this.name = getClass().getName();
        this.size = size;
        this.col = col;
        this.eat = 0;
        this.x_front = 0;
        this.y_front = 0;
        this.x_dir = 1;
        this.y_dir = 1;
        this.newHorSpeed = 0;
        this.newVerSpeed = 0;
        this.frequency = this.size * 2;
        this.hungry = new Hungry();
        this.satiated = new Satiated();
        this.state = new Satiated();
        this.originator = new Originator();
        this.caretaker = new Caretaker();
        start();
    }


    /**
     * return type (class) of the animal
     * @return
     */
    @Override
    public String getAnimalName() {
        return this.name;
    }

    /**
     * drae the animal
     * @param g
     */
    public void drawAnimal(Graphics g) {
        g.setColor(col);
        if (x_dir == 1) {
            // fish swims to right side
            // Body of fish
            g.fillOval(x_front - size, y_front - size / 4, size, size / 2);

            // Tail of fish
            int[] x_t = {x_front - size - size / 4, x_front - size - size / 4, x_front - size};
            int[] y_t = {y_front - size / 4, y_front + size / 4, y_front};
            Polygon t = new Polygon(x_t, y_t, 3);
            g.fillPolygon(t);

            // Eye of fish
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(new Color(255 - col.getRed(), 255 - col.getGreen(), 255 - col.getBlue()));
            g2.fillOval(x_front - size / 5, y_front - size / 10, size / 10, size / 10);

            // Mouth of fish
            if (size > 70) g2.setStroke(new BasicStroke(3));
            else if (size > 30) g2.setStroke(new BasicStroke(2));
            else g2.setStroke(new BasicStroke(1));
            g2.drawLine(x_front, y_front, x_front - size / 10, y_front + size / 10);
            g2.setStroke(new BasicStroke(1));
            
        } else {
            // fish swims to left side
            // Body of fish
            g.fillOval(x_front, y_front - size / 4, size, size / 2);

            // Tail of fish
            int[] x_t = {x_front + size + size / 4, x_front + size + size / 4, x_front + size};
            int[] y_t = {y_front - size / 4, y_front + size / 4, y_front};
            Polygon t = new Polygon(x_t, y_t, 3);
            g.fillPolygon(t);

            // Eye of fish
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(new Color(255 - col.getRed(), 255 - col.getGreen(), 255 - col.getBlue()));
            g2.fillOval(x_front + size / 10, y_front - size / 10, size / 10, size / 10);

            // Mouth of fish
            if (size > 70) g2.setStroke(new BasicStroke(3));
            else if (size > 30) g2.setStroke(new BasicStroke(2));
            else g2.setStroke(new BasicStroke(1));
            g2.drawLine(x_front, y_front, x_front + size / 10, y_front + size / 10);
            g2.setStroke(new BasicStroke(1));
        }

    }

    /**
     * set suspend when sleep pressed
     */
    @Override
    public synchronized void setSuspend() {
        this.isSuspended = true;
    }

    /**
     * cancel susspend when wake up pressed
     */
    @Override
    public void setResume() {
        this.isSuspended = false;
        synchronized (this) {
            notify();
        }
    }

    /**
     * return the size of the animal
     * @return
     */
    @Override
    public int getSize() {

        return this.size;
    }

    /**
     * increase animal counter
     */
    @Override
    public void eatInc() {
        this.eat += 1;
    }

    /**
     * return the number of eat
     * @return
     */
    @Override
    public int getEatCount() {
        return this.eat;
    }

    /**
     * return the color of the fish
     * @return
     */
    @Override
    public String getColor() {
        /**
         * return the color in hex format "0x00FFFFFF"
         */
        return String.format("#%06x", col.getRGB() & 0x00FFFFFF);
    }

    /**
     * calculate new speed when there is food
     */
    public void calcDir(){
        /*
         * set the new vectors
         */
        //(1) v_old = sqrt(v_hor*v_hor + v_ver*v_ver)
        double v_old = Math.sqrt((this.verSpeed * this.verSpeed) + (this.horSpeed * this.horSpeed));
        //(2)v_new = v_old
        //(3)v_new = sqrt(v_hor_new*v_hor_new + v_ver_new*v_ver_new
        //(4) v_ver_new/v_hor_new = abs( (y_front - panel.getHeight()/2) / (x_front - panel.getWidth()/2)) = k
        double k = (Math.abs((double)y_front - (double)(panel.getHeight()) / 2) / Math.abs((double)x_front - (double)(panel.getWidth()) / 2));
        //(5)v_ver_new = v_hor_new * k
        //(6) v_new = sqrt(v_hor_new*k * v_hor_new*k + v_hor_new*v_hor_new) = v_hor_new * sqrt(k*k+1)
        this.newHorSpeed = v_old / Math.sqrt(k * k + 1);
        this.newVerSpeed = this.newHorSpeed * k;
    }

    /**
     * thread run
     */
    @Override
    public void run() {
        while (true) {
            if (isSuspended) {
                synchronized (this) {
                    //sleep
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                Thread.sleep(80);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            /*
             * check if the animal is hungry
             */
            if (getThisState() instanceof Hungry) {
                /*
                 * check if there is food to eat
                 */
                if (Worm.isFood) {
                    /*
                     * animal move to the worm
                     */
                    if (this.x_front > (panel.getWidth() / 2)) this.x_dir = -1;
                    else this.x_dir = 1;
                    if (this.y_front > (panel.getHeight() / 2)) this.y_dir = -1;
                    else y_dir = 1;

                    this.calcDir();
                    this.x_front += (int) (newHorSpeed * this.x_dir);
                    this.y_front += (int) (newVerSpeed * this.y_dir);
                    panel.repaint();

                    synchronized (this) {
                        //check for eaten worm
                        if ((Math.abs(x_front - panel.getWidth() / 2) <= 5) && (Math.abs(y_front - panel.getHeight() / 2) <= 5)) {
                            // fish eats worm ...
                            this.satiated.doAction(this);
                            setFrequency();
                            Singleton.setInstance();  //remove worm from screen
                            panel.callback(this); //call callback to update data
                            panel.repaint();
                        }
                    }
                }
                /*
                 * no worm, animal move normally
                 */

                /*
                 * check if the animal is on the board of the frame then change the direction
                 */
                if (x_front > panel.getWidth()) // if it arrive to the end of the screen
                    x_dir = -1;//change fish swimming to other side
                if (y_front > panel.getHeight())  //if if arrive to the to the end of the screen
                    y_dir = -1; //change fish swimming to other side
                if (x_front < 0)
                    x_dir = 1;
                if (y_front < 0)
                    y_dir = 1;
                /*
                 * move the animal
                 */
                this.x_front += horSpeed * x_dir;
                this.y_front += verSpeed * y_dir;
                panel.repaint();

            }
            /*
             * animal is satiated
             */
            else {
                if(this.frequency == 0){
                    this.hungry.doAction(this);
                    this.notifyListeners();
                    setFrequency();
                }
                else{
                    frequency -= 1;
                }
                /*
                 * check if the animal is on the board of the frame then change the direction
                 */
                if (x_front > panel.getWidth()) // if it arrive to the end of the screen
                    x_dir = -1;//change fish swimming to other side
                if (y_front > panel.getHeight())  //if if arrive to the to the end of the screen
                    y_dir = -1; //change fish swimming to other side
                if (x_front < 0)
                    x_dir = 1;
                if (y_front < 0)
                    y_dir = 1;
                /*
                 * move the animal
                 */
                this.x_front += horSpeed * x_dir;
                this.y_front += verSpeed * y_dir;
                panel.repaint();
            }
        }
    }

    /**
     * implementation of seacreature methods
     * @param g
     */
    @Override
    public void drawCreature(Graphics g) {
        drawAnimal(g);
    }

    /**
     * set memento
     */
    @Override
    public void setMemento() {
        originator.setState(this);
        Memento memento = originator.createMemento();
        caretaker.addMemento(memento);
    }

    /**
     * get memento and restore attrs
     */
    @Override
    public void getMemento() {
        try{
            Memento memento = caretaker.getMemento(caretaker.statesList.size() - 1);
            this.size = memento.getSize();
            this.col = memento.getCol();
            this.x_front = memento.getX_front();
            this.y_front = memento.getY_front();
            this.horSpeed = memento.getHorSpeed();
            this.verSpeed = memento.getVarSpeed();
            this.x_dir = memento.getX_dir();
            this.y_dir = memento.getY_dir();
            this.state = memento.getHungryState();
            this.eat = memento.getCounter();
            this.frequency = memento.getFreq();
        }catch (IndexOutOfBoundsException e){
            // if there is no saved states
            JOptionPane.showMessageDialog(null,
                    "No stored states!",
                    "Error!",
                    JOptionPane.ERROR_MESSAGE);
        }

    }


    @Override
    public void PaintFish(Color col) {
        this.setColor(col);
    }

    /**
     *
     * @return clone object
     */
//    @Override
//    public SeaCreature makeCopy() {
//        return this.clone();
//    }

    /**
     * make clone to object
     * @return clone object
     */
    public Fish clone(){
        Fish obj = new Fish(this.panel, this.size, this.col, this.horSpeed, this.verSpeed);
        return obj;
    }

    /**
     * set new name to animal with id
     * @param num
     */
    @Override
    public void setAnimalName(int num) {
        this.name = (name +" " + num).toString();
    }

    /**
     * add Listener to Observer list
     * @param l Observer object
     */
    @Override
    public void addListener(Observer l){list.add(l);}

    /**
     * remove Listener
     * CRITICAL SECTION!!!
     * Must be synchronized!
     * @param l Observer object
     */
    @Override
    public synchronized void removeListener(Observer l) {
        int index = list.indexOf(l); //get index of object
        list.set(index, list.lastElement()); // set the object in the end list
        list.remove(list.size()-1); //decrease the list by 1
    }

    /**
     * notify all Listeners
     */
    private void notifyListeners(){
        for (Observer l : list) {
            l.notify(this.getAnimalName());
        }
    }

    @Override
    public void setState(HungerState state){
        this.state = state;
    }

    public HungerState getThisState(){
        return this.state;
    }

    @Override
    public String getStringState(){
        if(this.state instanceof Hungry) return "Hungry";
        else return "Satiated";
    }

    @Override
    public void setFrequency(){
        this.frequency = this.size * 2;
    }
    @Override
    public int getFrequency(){return this.size * 2;}

    /**
     * set color to object
     * @param col
     */
    @Override
    public void setColor(Color col) {
        this.col = col;
    }

    /**
     * return color as color object
     * @return
     */
    @Override
    public Color getColorAsColorObject() {
        return this.col;
    }

    /**
     * return x_front
     * @return
     */
    @Override
    public int get_x_front() {
        return this.x_front;
    }

    /**
     * return y_front
     * @return
     */
    @Override
    public int get_y_front() {
        return this.y_front;
    }

    /**
     * return x_dir
     * @return
     */
    @Override
    public double get_x_dir() {
        return this.x_dir;
    }

    /**
     * return y_dir
     * @return
     */
    @Override
    public double get_y_dir() {
        return this.y_dir;
    }

    /**
     * return object state
     * @return
     */
    @Override
    public HungerState getHungerState() {
        return this.state;
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    }

}

