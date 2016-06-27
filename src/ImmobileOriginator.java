/**
 * Created by idanaftaker on 27/05/2016.
 */
public class ImmobileOriginator {
    //===Members===
    private Immobile state;

    //===Methods===
    public void setState(Immobile state){this.state = state;}
    public Immobile getState(){return this.state;}
    public ImmobileMemento createMemento() {return new ImmobileMemento(state);}
    public void setMemento(ImmobileMemento memento) {state = memento.getState();}
}
