/**
 * Created by idanaftaker on 10/05/2016.
 */
public class Singleton {
    private static Singleton instance = null;
    private static Worm worm;

    /* A private Constructor prevents any other
    * class from instantiating.*/
    private Singleton(){ }

    /* Static 'instance' method */
    public static Singleton getInstance(){
        if(instance == null) {
            instance = new Singleton();
            worm = new Worm();
        }
        return instance;
    }

    /**
     * Set the instance
     */
    public static void setInstance(){
        worm.changeFood();
    }
}
