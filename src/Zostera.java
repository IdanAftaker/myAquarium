import javax.swing.*;
import java.awt.*;

/**
 * Created by idanaftaker on 10/05/2016.
 * 
 */
public class Zostera extends Immobile{
    //===Members===
    private int size;
    private int x;
    private int y;
    private Color colorr;
    private AquaPanel aquaPanel;

    //Memento
    ImmobileOriginator originator = null;
    ImmobileCaretaker caretaker = null;


    public Zostera(){
        //default values for first init
        super("Zostera");
        this.colorr = Color.decode("#197546"); //#197546 Color Hex
        this.size = 300;
        this.x = 500;
        this.y = 400;
    }

    public Zostera(AquaPanel aquaPanel,String name, int x, int size){
        super(name);
        this.aquaPanel = aquaPanel;
        this.x = x;
        this.y = aquaPanel.getHeight();
        this.size = size;
        this.colorr = Color.decode("#197546"); //#197546 Color Hex
        this.originator = new ImmobileOriginator();
        this.caretaker = new ImmobileCaretaker();
    }

    //===Methods===

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
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(3));
        g2.setColor(colorr);
        g.drawLine(x, y, x, y - size);
        g.drawLine(x-2, y, x-10, y-size*9/10);
        g.drawLine(x+2, y, x+10, y-size*9/10);
        g.drawLine(x-4, y, x-20, y-size*4/5);
        g.drawLine(x+4, y, x+20, y-size*4/5);
        g.drawLine(x-6, y, x-30, y-size*7/10);
        g.drawLine(x+6, y, x+30, y-size*7/10);
        g.drawLine(x-8, y, x-40, y-size*4/7);
        g.drawLine(x+8, y, x+40, y-size*4/7);
        g2.setStroke(new BasicStroke(1));
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
