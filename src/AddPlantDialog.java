import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

/**
 * Created by idanaftaker on 15/05/2016.
 */
public class AddPlantDialog extends JDialog implements ActionListener{

    //===Members===
    HashSet<SeaCreature> seaItems;
    private JFrame frame;   //main frame
    private AquaPanel aqua;
    private JPanel panel;    //main panel
    private JPanel kindPanel;    //kind panel
    private JPanel applyPanel;    //apply panel
    private JPanel propPanel;    //properties panel

    private JLabel posLable;
    private JSlider position;

    private JRadioButton laminaria;
    private JRadioButton zostera;

    private JLabel sizeLable;
    private JSlider sliderSize;

    private JButton confirm;
    private JButton cancel;

    private AbstractSeaFactory abstractSeaFactory;
    private SeaCreature seaCreature;
    private String factoryType;

    //===Methods===


    public AddPlantDialog(AquaPanel aqua, HashSet<SeaCreature> seaItems) {
        this.aqua = aqua;
        this.seaItems = seaItems;

        this.abstractSeaFactory = new PlantFactory(); //init the factory for Plant Factory

        this.frame = new JFrame("Add Plant:");
        //main panel
        this.panel = new JPanel();
        panel.setLayout(new BorderLayout());
        this.abstractSeaFactory = new PlantFactory(); // init the factory

        //kind of plant panel
        this.kindPanel = new JPanel();
        kindPanel.setBorder(BorderFactory.createTitledBorder("What Kind?"));
        this.laminaria = new JRadioButton("Laminaria");
        laminaria.setSelected(true);
        this.zostera = new JRadioButton("Zostera");
        ButtonGroup group = new ButtonGroup();
        group.add(laminaria);
        group.add(zostera);
        kindPanel.add(laminaria);
        kindPanel.add(zostera);

        //properties panel
        this.propPanel = new JPanel();
        propPanel.setLayout(new FlowLayout());
        propPanel.setBorder(BorderFactory.createEmptyBorder());
        this.sizeLable = new JLabel("Which Size?");

        this.sliderSize = new JSlider(20,aqua.getHeight()/2);
        sliderSize.setMajorTickSpacing(80);
        sliderSize.setMinorTickSpacing(20);
        sliderSize.setPaintTicks(true);
        sliderSize.setSnapToTicks(true);
        propPanel.add(sizeLable);
        propPanel.add(sliderSize);


        this.posLable = new JLabel("Position:");
        this.position = new JSlider(0,aqua.getWidth());
        position.setMajorTickSpacing(100);
        position.setMinorTickSpacing(50);
        position.setPaintTicks(true);
        position.setSnapToTicks(true);
        propPanel.add(posLable);
        propPanel.add(position);

        //apply Panel
        this.applyPanel = new JPanel(new GridLayout());
        this.confirm = new JButton("Apply");
        confirm.addActionListener(this);
        this.cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        applyPanel.add(cancel);
        applyPanel.add(confirm);

        panel.add(kindPanel,BorderLayout.NORTH);
        panel.add(propPanel,BorderLayout.CENTER);
        panel.add(applyPanel,BorderLayout.SOUTH);

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
            if(laminaria.isSelected()){
                factoryType = "laminaria";
                this.seaCreature = abstractSeaFactory.produceSeaCreature(factoryType); // init with default c'tor Laminaria()
                this.seaCreature = new Laminaria(aqua,"Laminaria",position.getValue(),sliderSize.getValue());
                aqua.addToHash(this.seaCreature);
                frame.dispose();
            }
            else if(zostera.isSelected()){
                factoryType = "zostera";
                this.seaCreature = abstractSeaFactory.produceSeaCreature(factoryType); // init with default c'tor Laminaria()
                this.seaCreature = new Zostera(aqua,"Zostera",position.getValue(),sliderSize.getValue());
                aqua.addToHash(this.seaCreature);
                frame.dispose();
            }
        }
    }
}
