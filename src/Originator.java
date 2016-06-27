import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by idanaftaker on 18/05/2016.
 */
public class Originator {

    //===Members===
    private Swimmable state;

    //===Methods===
    public void setState(Swimmable state){this.state = state;}
    public Swimmable getState(){return this.state;}
    public Memento createMemento() {return new Memento(state);}
    public void setMemento(Memento memento) {state = memento.getState();}
}
