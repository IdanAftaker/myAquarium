import javax.swing.*;
import java.awt.*;

/**
 * Created by idanaftaker on 10/05/2016.
 */
public class Laminaria extends Immobile {

    //===Members===
    private int size;
    private int x;
    private int y;
    private Color colorr;
    private AquaPanel aquaPanel;

    //Memento
    ImmobileOriginator originator = null;
    ImmobileCaretaker caretaker = null;

    //===Methods===

    public Laminaria(){
        //default values for first init
        super("Laminaria");
        this.colorr = Color.decode("#00FF7F"); //#00FF7F Color Hex Spring Green
        this.size = 100;
        this.x = 100;
        this.y = 0;
    }


    public Laminaria(AquaPanel aquaPanel,String name, int x, int size){
        super(name);
        this.aquaPanel = aquaPanel;
        this.x = x;
        this.y = aquaPanel.getHeight();
        this.size = size;
        this.colorr = Color.decode("#00FF7F"); //#00FF7F Color Hex Spring Green
        this.originator = new ImmobileOriginator();
        this.caretaker = new ImmobileCaretaker();
    }


    /**
     * implementation of seacreature methods
     * @param g
     */
    @Override
    public void drawCreature(Graphics g) {
        draw(g);
    }

    @Override
    public void setMemento() {
        originator.setState(this);
        ImmobileMemento memento = originator.createMemento();
        caretaker.addMemento(memento);
    }

    @Override
    public void getMemento() {
        try{
            ImmobileMemento memento = caretaker.getMemento(caretaker.statesList.size() - 1);
            this.size = memento.getSize();
            this.x = memento.getX();
            this.y = memento.getY();
            this.colorr = memento.getColor();
        }catch (IndexOutOfBoundsException e){
            // if there is no saved states
            JOptionPane.showMessageDialog(null,
                    "No stored states!",
                    "Error!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * draw the Laminaria
     * @param g
     */
    public void draw(Graphics g){
        g.setColor(colorr); //set the color
        g.fillArc(x-size/20, y-size, size/10, size*4/5, 0, 360);
        g.fillArc(x-size*3/20, y-size*13/15, size/10, size*2/3, 0, 360);
        g.fillArc(x+size/20, y-size*13/15, size/10, size*2/3, 0, 360);
        g.drawLine(x, y, x, y-size/5);
        g.drawLine(x, y, x-size/10, y-size/5);
        g.drawLine(x, y, x+size/10, y-size/5);
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public Color getColor() {
        return this.colorr;
    }

    @Override
    public String getColorString() {
        return String.format("#%06x", colorr.getRGB() & 0x00FFFFFF);
    }
}
