import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by idanaftaker on 10/05/2016.
 *
 * Second factory from a predefined set which will instantiate the AbstractSeaFactory interface
 */
public class PlantFactory implements AbstractSeaFactory{
    //===Methods===

    @Override
    public SeaCreature produceSeaCreature(String type) {
        Immobile obj = null;

        if("Laminaria".equalsIgnoreCase(type)){
            /*
             *  Return new Laminaria
             */
            obj = new Laminaria();
        }
        if("Zostera".equalsIgnoreCase(type)){
            /*
             *  Return new Jelly Zostera
             */
            obj = new Zostera();
        }
        return obj;
    }
}
