import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import javax.swing.table.DefaultTableModel;


/**
 * Created by idanaftaker on 06/04/2016.
 */
public class AquaPanel extends JPanel implements ActionListener,Observer {

    //===Members===
    protected HashSet<SeaCreature> seaItems;
    private static final int maxAnimals = 5;
    private static int animalsNum = 0;
    private static final int maxPlants = 5;
    private static int plantsNum = 0;
    private AquaFrame aquaFrame;
    private JPanel menu;
    private JFrame frame = new JFrame("Info");

    private JButton btn_addItem;
    private JButton btn_sleep;
    private JButton btn_wakeUp;
    private JButton btn_reset;
    private JButton btn_food;
    private JButton btn_info;
    private JButton btn_lower_exit;
    private JButton btn_duplicate;
    private JButton btn_decorator;

    private JPopupMenu popMenu;
    private JMenuItem animalChoose;
    private JMenuItem seaPlotChoose;

    //common members
    private static BufferedImage img = null;
    private int totalEatCounter = 0;

    private DefaultTableModel tbl_model;
    private JTable tbl_info;

    //Singelton
    private Singleton singleton;

    //Factories Members
    private String factoryType;
    private AbstractSeaFactory abstractSeaFactory;
    private SeaCreature seaCreature;

    private Caretaker carTakerMemento;

    CloneFactory cloneFactory;


    //===Methods===
    /**
     * AquaPanel constructor
     */
    public AquaPanel(AquaFrame aquaFrame){
        super();
        this.aquaFrame = aquaFrame; //get the frame
        seaItems = new HashSet<SeaCreature>();
        BorderLayout border = new BorderLayout(); //set layout
        this.setBackground(Color.white);
        aquaFrame.add(this,border.CENTER); //place the panel in the frame

        menu = new JPanel(); //init the menu
        setMenu();
        aquaFrame.add(menu, border.SOUTH); //add the menu to the frame

        this.singleton = Singleton.getInstance();
        abstractSeaFactory = null;
        seaCreature = null;
        factoryType = null;
        carTakerMemento = new Caretaker();
    }

    /**
     * set the menu in the buttom
     */
    public void setMenu(){
        /**
         * create the lower panel menu bar with all the buttons
         * [Add Animal][Sleep][Wake Up][Reset][Food][Info][Exit]
         * for each button, add action listener
         */
        menu.setLayout(new GridLayout());
        //buttons for upper menu
        //add Animal button
        this.btn_addItem = new JButton("Add New Item");
        btn_addItem.setFont(new Font("Helvetica",Font.ITALIC,14));
        btn_addItem.addActionListener(this);

        //add duplicate animal button
        this.btn_duplicate = new JButton("Duplicate Animal");
        btn_duplicate.setFont(new Font("Helvetica",Font.ITALIC,14));
        btn_duplicate.addActionListener(this);

        //add duplicate animal button
        this.btn_decorator = new JButton("Decorator");
        btn_decorator.setFont(new Font("Helvetica",Font.ITALIC,14));
        btn_decorator.addActionListener(this);

        //sleep button
        this.btn_sleep = new JButton("Sleep");
        btn_sleep.setFont(new Font("Helvetica",Font.ITALIC,14));
        btn_sleep.addActionListener(this);

        //wake up button
        this.btn_wakeUp = new JButton("Wake Up");
        btn_wakeUp.setFont(new Font("Helvetica",Font.ITALIC,14));
        btn_wakeUp.addActionListener(this);

        //reset button
        this.btn_reset = new JButton("Reset");
        btn_reset.setFont(new Font("Helvetica",Font.ITALIC,14));
        btn_reset.addActionListener(this);

        //food button
        this.btn_food = new JButton("Food");
        btn_food.setFont(new Font("Helvetica",Font.ITALIC,14));
        btn_food.addActionListener(this);

        //info button
        this.btn_info = new JButton("Info");
        btn_info.setFont(new Font("Helvetica",Font.ITALIC,14));
        btn_info.addActionListener(this);

        //exit button
        this.btn_lower_exit = new JButton("Exit");
        btn_lower_exit.setFont(new Font("Helvetica",Font.ITALIC,14));
        btn_lower_exit.addActionListener(this);

        //add the buttons to the panel
        menu.add(btn_addItem);
        menu.add(btn_duplicate);
        menu.add(btn_decorator);
        menu.add(btn_sleep);
        menu.add(btn_wakeUp);
        menu.add(btn_reset);
        menu.add(btn_food);
        menu.add(btn_info);
        menu.add(btn_lower_exit);
        menu.setVisible(true);
    }

    /**
     * add the object to hash table
     * check the type of the object then incease the counter of each type
     */
    public void addToHash(SeaCreature obj){
        if(obj instanceof Swimmable){
            ((Swimmable) obj).addListener(this); //add this panel as observer
            //increase the number of animals
            this.animalsNum += 1;
            ((Swimmable) obj).setAnimalName(animalsNum);
        }
        if(obj instanceof Immobile){
            //increase the number of plants
            this.plantsNum += 1;
        }
        //add to hashset
        seaItems.add(obj);
        this.repaint();

    }

    /**
     * make table with data
     */
    public void getInfo(){
        if(this.frame.isVisible()){
            /*
             * check if the frame is visible then close him
             */
            this.frame.dispose();
        }
        else{
            /*
             * make new frame
             */
            frame = new JFrame("Info");
            frame.setLayout(new BorderLayout());
            tbl_info = getAnimalList();
            JPanel counter = new JPanel();
            JLabel countLabel = new JLabel("Total Count:" + totalEatCounter);
            counter.setLayout(new BorderLayout());
            counter.add(countLabel,BorderLayout.CENTER);

            frame.add(tbl_info,BorderLayout.CENTER);
            frame.add(counter,BorderLayout.SOUTH);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }

    }

    /**
     * paintComponent function is the function who draw all the items in the aquarium
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (img != null){
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        }
        if(Worm.isFood){
            //draw worm
            Worm.draw(g,this);
        }

        for(SeaCreature obj : seaItems){
            //draw the items
            obj.drawCreature(g);
        }
    }

    /**
     * addAnimal function open a dialog of animal properties.
     */
    public void addAnimal(){
        AddAnimalDialog addAnimal = new AddAnimalDialog(this,seaItems);
    }

    /**
     * addPlant function open a dialog of plant properties.
     */
    public void addPlant(){
        AddPlantDialog addPlant = new AddPlantDialog(this,seaItems);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == btn_lower_exit){
            System.exit(0);
        }
        if(e.getSource() == btn_addItem){
            //make menu
            this.popMenu = new JPopupMenu("Menu");
            this.popMenu.setPopupSize(btn_addItem.getWidth(),btn_addItem.getHeight()*2);
            //add animal
            this.animalChoose = new JMenuItem("Animal");
            this.animalChoose.setFont(new Font("Helvetica",Font.ITALIC,14));
            this.animalChoose.addActionListener(this);
            popMenu.add(animalChoose);
            // Separator
            this.popMenu.addSeparator();
            //add see plot
            this.seaPlotChoose = new JMenuItem("Sea Plot");
            this.seaPlotChoose.setFont(new Font("Helvetica",Font.ITALIC,14));
            this.seaPlotChoose.addActionListener(this);
            //add to menu
            popMenu.add(seaPlotChoose);
            popMenu.pack();
            popMenu.setLocation(20,900);

            popMenu.setVisible(true);
            this.btn_addItem.setComponentPopupMenu(popMenu);

        }
        if(e.getSource() == animalChoose){
            popMenu.setVisible(false);
            //set the factory type
            factoryType = "animal";


            if(animalsNum < maxAnimals){// check if there is no max number animal
                addAnimal();
            }
            else
            {
                JOptionPane.showMessageDialog(null,
                        "You have got the maximum amount of animals!.",
                        "Sorry!",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource() == seaPlotChoose){
            popMenu.setVisible(false);
            //set the factory type
            factoryType = "plants";
            if(plantsNum < maxPlants){
                addPlant();
            }
            else{
                JOptionPane.showMessageDialog(null,
                        "You have got the maximum amount of plants!.",
                        "Sorry!",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        if(e.getSource() == btn_duplicate){
            /* open animal list and let the user select animal to duplicate */
            makeClone();
        }
        if(e.getSource() == btn_decorator){
            decorator();
        }
        if(e.getSource() == btn_sleep){
            if(animalsNum == 0){
                JOptionPane.showMessageDialog(null,
                        "It must be at least 1 animal!.",
                        "Sorry!",
                        JOptionPane.ERROR_MESSAGE);
            }
            else{
              /* suspend all the animal */
                for(SeaCreature obj : seaItems){
                    if(obj instanceof Swimmable){
                        ((Swimmable) obj).setSuspend();
                    }
                }
            }

        }
        if(e.getSource() == btn_wakeUp){
            if(animalsNum == 0){
                JOptionPane.showMessageDialog(null,
                        "It must be at least 1 animal!.",
                        "Sorry!",
                        JOptionPane.ERROR_MESSAGE);
            }
            else{
              /* resume all the animal */
                for(SeaCreature obj : seaItems){
                    if(obj instanceof Swimmable){
                        ((Swimmable) obj).setResume();
                    }
                }
            }
        }
        if(e.getSource() == btn_reset) {
            /*
             * remove this panal as listener from all swimmable objects
             */
            for (SeaCreature obj : seaItems){
                if(obj instanceof Swimmable){
                    ((Swimmable)obj).removeListener(this);
                }
            }
            changeBackground("none"); //remove the background
            Singleton.setInstance();  //remove worm from screen
            this.seaItems.clear(); //remove all animals
            this.totalEatCounter = 0; //reset the total counter
            this.animalsNum = 0; //reset animals number
            this.plantsNum = 0; //reset plants number
        }
        if(e.getSource() == btn_food){
            if(!Worm.isFood) //check if the worm already set
            singleton.setInstance();
            repaint();
        }
        if(e.getSource() == btn_info){
            getInfo();
        }
    }
    /**
     * the function get a string as message then recognize what background the user ask for
     */
    public void changeBackground(String msg){
        if(msg == "img"){
            this.removeAll();
            //set picture as background
            FileDialog fd=new FileDialog(new Frame(),"please choose a file:",FileDialog.LOAD);
            fd.setVisible(true);
            File f;
            if(fd.getFile() != null){
                f = new File(fd.getDirectory(), fd.getFile());
                try{
                    img = ImageIO.read(f);
                    repaint();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        if(msg == "blue"){
            this.removeAll();
            img = null; //remove image
            //change the background to blue
            this.setBackground(Color.blue);
            SwingUtilities.updateComponentTreeUI(this);
        }
        if(msg == "none"){
            this.removeAll();
            img = null; //remove image
            //change the background to white
            this.setBackground(Color.white);
            SwingUtilities.updateComponentTreeUI(this);
        }
    }

    /**
     * display a message dialog after the user press help in the menu bar
     */
    public void showMessageDialog(){

        JOptionPane.showMessageDialog(null, "My Aquarium \nGUI@Threads@Design Patterns\nMade by Idan Aftaker");
    }

    /**
     * update object data and total counter
     * @param obj
     */
    public void callback(Swimmable obj){
        obj.eatInc(); //increse the animal eat counter
        /*
         * run over the animal and calculate the total animal eat
         */
        this.totalEatCounter = 0; //init the counter
        for(SeaCreature item : seaItems){
            if(item instanceof Swimmable){
                totalEatCounter += ((Swimmable) item).getEatCount();
            }
        }
    }

    /**
     * makeClone make panel and display all the animal in the aqua frame
     * user can select an animal (select row) and the animal will clone
     */
    public void makeClone(){

        if(animalsNum < maxAnimals) {
            // check if there is no max number animal
            JFrame frame = new JFrame("Which Animal To Duplicate?");
            JTable info = getAnimalList();

            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());

            JPanel apply_panel = new JPanel();
            JButton clone_select = new JButton("Select");
            clone_select.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(e.getSource() == clone_select){
                        frame.dispose();
                        String name = info.getModel().getValueAt(info.getSelectedRow(),0).toString(); //get the name of animal from selected row
                        for(SeaCreature obj : seaItems){ //run over seaitems hashset
                            if(obj instanceof Swimmable){ //check for swimmable object
                                if(((Swimmable)obj).getAnimalName().equalsIgnoreCase(name)){ //Comparison with name
                                    if(obj instanceof Fish){// check for fish object
                                        SeaCreature clone = ((Fish)obj).clone(); //clone
                                        cloneFactory = new CloneFactory(((Fish)clone));
                                        addToHash(clone); //add to list
                                        break;
                                    }
                                    if(obj instanceof Jellyfish){ //jelly Fish object
                                        SeaCreature clone = ((Jellyfish)obj).clone(); //clone
                                        cloneFactory = new CloneFactory(((Jellyfish)clone));
                                        addToHash(clone); //add to list
                                        break;
                                    }

                                }
                            }
                        }
                    }
                }
            });
            JButton clone_cancel = new JButton("Cancel");
            clone_cancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(e.getSource() == clone_cancel){
                        frame.dispose();
                    }
                }
            });
            apply_panel.setLayout(new GridLayout());

            apply_panel.add(clone_cancel);
            apply_panel.add(clone_select);

            panel.add(info,BorderLayout.NORTH);
            panel.add(apply_panel, BorderLayout.SOUTH);
            frame.add(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
        else
        {
            JOptionPane.showMessageDialog(null,
                    "You have got the maximum amount of animals!.",
                    "Sorry!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * notify function get animal name and popup message with animal name and his hungry state
     * @param msg
     */
    @Override
    public void notify(String msg) {
        JOptionPane.showMessageDialog(null,
                msg + " is Hungry!",
                "notify!",
                JOptionPane.PLAIN_MESSAGE);
    }

    public void decorator(){
        JPanelDecorator panel = new JPanelDecorator(this,seaItems);
    }

    /**
     * return list of the animals
     * @return JTable
     */
    public JTable getAnimalList(){
        DefaultTableModel tbl = new DefaultTableModel();
        tbl.addColumn("Animal Name");
        tbl.addColumn("Color");
        tbl.addColumn("Size");
        tbl.addColumn("Hor.Speed");
        tbl.addColumn("Var.Speed");
        tbl.addColumn("Eat Count");
        tbl.addColumn("Eat Frequency (steps)");
        tbl.addColumn("State");

        /*
         * add titles to columns
         */
        Object[] columns = {"Name", "Color", "Size", "Hor.Speed", "Var.Speed",
                "Eat Count", "Frequency", "State"};
        tbl.addRow(columns);

        Object[] space = {"----------", "----------", "----------", "----------", "----------",
                "----------", "----------", "----------"};
        tbl.addRow(space);

            /*
             * get data from sea items
             */
        for (SeaCreature obj : this.seaItems) {
            if (obj instanceof Swimmable) { //check for animals
                Object[] row = {((Swimmable) obj).getAnimalName(), ((Swimmable) obj).getColor(), ((Swimmable) obj).getSize(),
                        ((Swimmable) obj).getHorSpeed(), ((Swimmable) obj).getVerSpeed(), ((Swimmable) obj).getEatCount()
                        , ((Swimmable) obj).getFrequency(), ((Swimmable) obj).getStringState()};
                tbl.addRow(row);
            }
        }
        JTable info = new JTable(tbl);
        return info;
    }

    public JTable getPlantsList(){
        DefaultTableModel tbl = new DefaultTableModel();
        tbl.addColumn("Plant Name");
        tbl.addColumn("Color");
        tbl.addColumn("Size");
        tbl.addColumn("X Position");
        tbl.addColumn("Y Position");

        Object[] columns = {"Name", "Color", "Size", "X Position", "Y Position"};
        tbl.addRow(columns);
        Object[] space = {"----------", "----------", "----------", "----------", "----------"};
        tbl.addRow(space);

        for (SeaCreature obj : this.seaItems) {
            if (obj instanceof Immobile) { //check for animals
                Object[] row = {((Immobile) obj).getClass().getName(), ((Immobile) obj).getColorString(), ((Immobile) obj).getSize(),
                        ((Immobile) obj).getX(), ((Immobile) obj).getY()};
                tbl.addRow(row);
            }
        }
        JTable info = new JTable(tbl);
        return info;
    }

    /**
     * saveMemento menu
     */
    public void saveMemento() {
        JFrame newframe = new JFrame("Which Animal To Save?");
        JTable info = getAnimalList();

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel apply_panel = new JPanel();
        JButton memento_select = new JButton("Memento");
        memento_select.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == memento_select){
                    newframe.dispose();
                    try {
                        String name = info.getModel().getValueAt(info.getSelectedRow(), 0).toString(); //get the name of animal from selected row
                        for (SeaCreature obj : seaItems) { //run over seaitems hashset
                            if (obj instanceof Swimmable) { //check for swimmable object
                                if (((Swimmable) obj).getAnimalName().equalsIgnoreCase(name)) { //Comparison with name
                                    obj.setMemento();
                                }
                            }
                        }
                    }catch (ArrayIndexOutOfBoundsException e1){}
                }
            }
        });
        JButton memento_cancel = new JButton("Cancel");
        memento_cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == memento_cancel){
                    newframe.dispose();
                }
            }
        });
        apply_panel.setLayout(new GridLayout());

        apply_panel.add(memento_cancel);
        apply_panel.add(memento_select);

        panel.add(info,BorderLayout.NORTH);
        panel.add(apply_panel, BorderLayout.SOUTH);
        newframe.add(panel);
        newframe.pack();
        newframe.setLocationRelativeTo(null);
        newframe.setVisible(true);
    }

    public void savePlantsMemento(){
        JFrame newframe = new JFrame("Which Plant To Save?");
        JTable info = getPlantsList();

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel apply_panel = new JPanel();
        JButton memento_select = new JButton("Memento");
        memento_select.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == memento_select) {
                    newframe.dispose();
                    try {
                        String name = info.getModel().getValueAt(info.getSelectedRow(), 0).toString(); //get the name
                        int x = (int) info.getModel().getValueAt(info.getSelectedRow(), 3); // get x position
                        for (SeaCreature obj : seaItems) { //run over seaitems hashset
                            if (obj instanceof Immobile) { //check for swimmable object
                                if (((Immobile) obj).getClass().getName().equalsIgnoreCase(name) && ((Immobile) obj).getX() == x) {
                                    obj.setMemento();
                                }
                            }
                        }
                    }catch (ArrayIndexOutOfBoundsException e1){}
                }
            }
        });

        JButton memento_cancel = new JButton("Cancel");
        memento_cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == memento_cancel){
                    newframe.dispose();
                }
            }
        });
        apply_panel.setLayout(new GridLayout());

        apply_panel.add(memento_cancel);
        apply_panel.add(memento_select);

        panel.add(info,BorderLayout.NORTH);
        panel.add(apply_panel, BorderLayout.SOUTH);
        newframe.add(panel);
        newframe.pack();
        newframe.setLocationRelativeTo(null);
        newframe.setVisible(true);
    }
    /**
     * restoreMemento menu
     */
    public void restoreMemento() {
        JFrame newframe = new JFrame("Which Animal To Restore?");
        JTable info = getAnimalList();

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel apply_panel = new JPanel();
        JButton restore_select = new JButton("Restore");
        restore_select.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == restore_select){
                    newframe.dispose();
                    try {
                        String name = info.getModel().getValueAt(info.getSelectedRow(), 0).toString(); //get the name of animal from selected row
                        for (SeaCreature obj : seaItems) { //run over seaitems hashset
                            if (obj instanceof Swimmable) { //check for swimmable object
                                if (((Swimmable) obj).getAnimalName().equalsIgnoreCase(name)) { //Comparison with name
                                    obj.getMemento();
                                }
                            }
                        }
                    }catch (ArrayIndexOutOfBoundsException e1){}
                }
            }
        });
        JButton memento_cancel = new JButton("Cancel");
        memento_cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == memento_cancel){
                    newframe.dispose();
                }
            }
        });
        apply_panel.setLayout(new GridLayout());

        apply_panel.add(memento_cancel);
        apply_panel.add(restore_select);

        panel.add(info,BorderLayout.NORTH);
        panel.add(apply_panel, BorderLayout.SOUTH);
        newframe.add(panel);
        newframe.pack();
        newframe.setLocationRelativeTo(null);
        newframe.setVisible(true);
    }

    public void restorePlantsMemento(){
        JFrame newframe = new JFrame("Which Plant To Restore?");
        JTable info = getPlantsList();

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel apply_panel = new JPanel();
        JButton memento_select = new JButton("Restore");
        memento_select.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == memento_select) {
                    newframe.dispose();
                    try{
                        String name = info.getModel().getValueAt(info.getSelectedRow(), 0).toString(); //get the name
                        int x = (int) info.getModel().getValueAt(info.getSelectedRow(), 3); // get x position
                        for (SeaCreature obj : seaItems) { //run over seaitems hashset
                            if (obj instanceof Immobile) { //check for swimmable object
                                if (((Immobile) obj).getClass().getName().equalsIgnoreCase(name) && ((Immobile) obj).getX() == x) {
                                    obj.getMemento();
                                }
                            }
                        }
                    }catch (ArrayIndexOutOfBoundsException e1){}
                }
            }
        });

        JButton memento_cancel = new JButton("Cancel");
        memento_cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == memento_cancel){
                    newframe.dispose();
                }
            }
        });
        apply_panel.setLayout(new GridLayout());

        apply_panel.add(memento_cancel);
        apply_panel.add(memento_select);

        panel.add(info,BorderLayout.NORTH);
        panel.add(apply_panel, BorderLayout.SOUTH);
        newframe.add(panel);
        newframe.pack();
        newframe.setLocationRelativeTo(null);
        newframe.setVisible(true);
    }

    /**
     * update counter after changes
     */
    public void updateCounter(){
        this.totalEatCounter = 0;
        for(SeaCreature obj : seaItems){
            if(obj instanceof Swimmable){
                this.totalEatCounter += ((Swimmable) obj).getEatCount();
            }
        }

    }


    public void selectAnimalMemento(){
        JFrame newframe = new JFrame("Select Action:");
        JPanel newpanel = new JPanel();
        JButton btn_memento_restore = new JButton("Restore Object State");
        JButton btn_memento_save = new JButton("Save Object State");;
        newpanel.add(btn_memento_restore);
        btn_memento_restore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == btn_memento_restore){
                    newframe.dispose();
                    restoreMemento();
                }
            }
        });
        newpanel.add(btn_memento_save);
        btn_memento_save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == btn_memento_save){
                    newframe.dispose();
                    saveMemento();
                }
            }
        });

        newframe.add(newpanel);
        newframe.pack();
        newframe.setLocationRelativeTo(null);
        newframe.setVisible(true);
    }

    public void selectPlantsMemento(){
        JFrame newframe = new JFrame("Select Action:");
        JPanel newpanel = new JPanel();
        JButton btn_plants_memento_save = new JButton("Save Object State");
        JButton btn_plants_memento_restore = new JButton("Restore Object State");

        btn_plants_memento_restore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == btn_plants_memento_restore){
                    newframe.dispose();
                    restorePlantsMemento();
                }
            }
        });
        newpanel.add(btn_plants_memento_restore);

        btn_plants_memento_save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == btn_plants_memento_save){
                    newframe.dispose();
                    savePlantsMemento();
                }
            }
        });
        newpanel.add(btn_plants_memento_save);

        newframe.add(newpanel);
        newframe.pack();
        newframe.setLocationRelativeTo(null);
        newframe.setVisible(true);
    }
}
