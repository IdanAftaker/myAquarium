import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

/**
 * Created by idanaftaker on 06/04/2016.
 */
public class AddAnimalDialog extends JDialog implements ActionListener{

    //===Members===

    HashSet<SeaCreature> seaItems;
    private JFrame frame;   //main frame
    private AquaPanel aqua;

    private JPanel panel;    //main panel
    private JPanel kindPanel;    //kind panel
    private JPanel propPanel;    //properties panel
    private JPanel colorPanel;    //color panel
    private JPanel applyPanel;    //apply panel

    private JRadioButton fish;
    private JRadioButton jellyFish;

    private JLabel sizeLable;
    private JSlider sliderSize;
    private JLabel horLable;
    private JSlider horSpeed;
    private JLabel verLable;
    private JSlider varSpeed;

    private JLabel colorLabel;
    private JColorChooser color;

    private JButton confirm;
    private JButton cancel;

    private AbstractSeaFactory abstractSeaFactory;
    private SeaCreature seaCreature;
    private String factoryType;


    //===Methods===
    public AddAnimalDialog(AquaPanel aqua, HashSet<SeaCreature> seaItems){
        this.aqua = aqua;
        this.seaItems = seaItems;

        this.abstractSeaFactory = new AnimalFactory(); //init the factory for animal factory

        this.frame = new JFrame("Add Animal:");
        //main panel
        this.panel = new JPanel();
        panel.setLayout(new BorderLayout());

        //kind of animal panel
        this.kindPanel = new JPanel();
        kindPanel.setBorder(BorderFactory.createTitledBorder("What Kind?"));
        this.fish = new JRadioButton("Fish");
        fish.setSelected(true);
        this.jellyFish = new JRadioButton("Jelly Fish");
        ButtonGroup group = new ButtonGroup();
        group.add(fish);
        group.add(jellyFish);
        kindPanel.add(fish);
        kindPanel.add(jellyFish);

        //properties panel
        this.propPanel = new JPanel();
        propPanel.setLayout(new FlowLayout());
        propPanel.setBorder(BorderFactory.createEmptyBorder());
        this.sizeLable = new JLabel("Which Size?");

        this.sliderSize = new JSlider(20,320);
        sliderSize.setMajorTickSpacing(80);
        sliderSize.setMinorTickSpacing(20);
        sliderSize.setPaintTicks(true);
        sliderSize.setSnapToTicks(true);
        propPanel.add(sizeLable);
        propPanel.add(sliderSize);

        this.horLable = new JLabel("Horizontal Speed:");
        this.horSpeed = new JSlider(1,10);
        horSpeed.setMajorTickSpacing(4);
        horSpeed.setMinorTickSpacing(1);
        horSpeed.setPaintTicks(true);
        horSpeed.setSnapToTicks(true);
        this.verLable = new JLabel("Vertical Speed");
        this.varSpeed = new JSlider(1,10);
        varSpeed.setMajorTickSpacing(4);
        varSpeed.setMinorTickSpacing(1);
        varSpeed.setPaintTicks(true);
        varSpeed.setSnapToTicks(true);
        propPanel.add(horLable);
        propPanel.add(horSpeed);
        propPanel.add(verLable);
        propPanel.add(varSpeed);

        //color panel
        this.colorPanel = new JPanel();
        colorPanel.setLayout(new BorderLayout());
        this.colorLabel = new JLabel("Color:");
        this.color = new JColorChooser();
        colorPanel.add(colorLabel,BorderLayout.NORTH);
        colorPanel.add(color,BorderLayout.CENTER);

        //apply Panel
        this.applyPanel = new JPanel(new GridLayout());
        this.confirm = new JButton("Apply");
        confirm.addActionListener(this);
        this.cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        applyPanel.add(cancel);
        applyPanel.add(confirm);

        //add to color panel
        colorPanel.add(applyPanel,BorderLayout.SOUTH);

        panel.add(kindPanel,BorderLayout.NORTH);
        panel.add(propPanel,BorderLayout.CENTER);
        panel.add(colorPanel,BorderLayout.SOUTH);

        panel.setVisible(true);

        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == cancel){
            frame.dispose();
            return;
        }
        if(e.getSource() == confirm){
            if(fish.isSelected()){
                factoryType = "fish";
                this.seaCreature = abstractSeaFactory.produceSeaCreature(factoryType); // init with default c'tor Fish()
                this.seaCreature = new Fish(aqua,sliderSize.getValue(), color.getColor(), horSpeed.getValue(), varSpeed.getValue());//change reference to cuncorete object
                aqua.addToHash(this.seaCreature); //add to HashSet
                frame.dispose();//close frame
            }
            else if(jellyFish.isSelected()){
                factoryType = "jellyfish";
                this.seaCreature = abstractSeaFactory.produceSeaCreature(factoryType);// init with default c'tor Jellyfish()
                this.seaCreature = new Jellyfish(aqua,sliderSize.getValue(), color.getColor(), horSpeed.getValue(), varSpeed.getValue()); //change reference to cuncorete object
                aqua.addToHash(this.seaCreature); //add to HashSet
                frame.dispose(); //close frame
            }
        }
    }
}
