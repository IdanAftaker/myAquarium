import java.util.ArrayList;

/**
 * Created by idanaftaker on 18/05/2016.
 */
public class Caretaker {
    //===Members===
    protected ArrayList<Memento> statesList = new ArrayList<Memento>();
    //===Methods===
    public void addMemento(Memento m) {statesList.add(m);}
    public Memento getMemento(int index) {return statesList.get(index);}
}
