import java.awt.*;

/**
 * Created by idanaftaker on 15/05/2016.
 */
public class MarineAnimalDecorator implements MarineAnimal{

    //===Members
    protected MarineAnimal marineAnimal;

    //===Methods===

    public  MarineAnimalDecorator(MarineAnimal obj){
        this.marineAnimal = obj;
    }
    @Override
    public void PaintFish(Color col) {
        marineAnimal.PaintFish(col);
    }




}
