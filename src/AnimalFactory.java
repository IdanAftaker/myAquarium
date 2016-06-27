/**
 * Created by idanaftaker on 10/05/2016.
 *
 * One of the factory from a predefined set which will instantiate the above interface
 */
public class AnimalFactory extends Thread implements AbstractSeaFactory{

    //===Methods===
    @Override
    public SeaCreature produceSeaCreature(String type) {
        Swimmable obj = null;

        if("Fish".equalsIgnoreCase(type)){
            /*
             *  Return new Fish
             */
            obj = new Fish();
        }
        if("JellyFish".equalsIgnoreCase(type)){
            /*
             *  Return new Jelly Fish
             */
            obj =  new Jellyfish();
        }
        return obj;
    }

}
