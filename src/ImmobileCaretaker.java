import java.util.ArrayList;

/**
 * Created by idanaftaker on 27/05/2016.
 */
public class ImmobileCaretaker {
    //===Members===
    protected ArrayList<ImmobileMemento> statesList = new ArrayList<ImmobileMemento>();
    //===Methods===
    public void addMemento(ImmobileMemento m) {statesList.add(m);}
    public ImmobileMemento getMemento(int index) {return statesList.get(index);}
}
