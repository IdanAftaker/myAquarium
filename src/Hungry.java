/**
 * Created by idanaftaker on 17/05/2016.
 */
public class Hungry implements HungerState{
    //===Methods===
    @Override
    public void doAction(Swimmable obj) {
        ((Swimmable) obj).setState(new Hungry());
    }
}
