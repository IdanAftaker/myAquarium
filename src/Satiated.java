/**
 * Created by idanaftaker on 17/05/2016.
 */
public class Satiated implements HungerState{
    //===Methods===

    @Override
    public void doAction(Swimmable obj) {
        ((Swimmable) obj).setState(new Satiated());
    }
}
