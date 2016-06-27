/**
 * Created by idanaftaker on 10/05/2016.
 *
 * The interface for which the factory implementation should be done. All abstract factories will return this type
 */
public  interface AbstractSeaFactory {
    //===Methods===
    public SeaCreature produceSeaCreature(String type);
}
