import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

/**
 * Created by idanaftaker on 18/05/2016.
 */
public class JPanelDecorator extends JPanel{

    //===Members===
    private HashSet<SeaCreature> list;
    private AquaPanel aquaPanel;
    private Color col;

    private JFrame frame;   //main frame

    private JPanel panel; //main panel
    private JPanel apply_panel; //apply panel

    private JButton btn_cancel;
    private JButton btn_apply;

    private DefaultTableModel tbl_model;
    private JTable tbl_info;

    private String animalName;


    //===Methods===
    public JPanelDecorator(AquaPanel aquaPanel,HashSet<SeaCreature> list){
        this.aquaPanel = aquaPanel;
        this.list = list;
        //main frame
        this.frame = new JFrame("Decoretor");

        //main panel
        this.panel = new JPanel();
        this.panel.setLayout(new BorderLayout());

        //apply panel
        this.apply_panel = new JPanel();
        this.apply_panel.setLayout(new FlowLayout());

        //buttons
        this.btn_apply = new JButton("Change Color");
        this.btn_cancel = new JButton("Cancel");


        this.tbl_info = aquaPanel.getAnimalList();

        this.btn_cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == btn_cancel)
                frame.dispose();
            }
        });

        this.btn_apply.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == btn_apply){
                    frame.dispose();
                    //open JColorChooser
                    selection();
                    animalName = tbl_info.getModel().getValueAt(tbl_info.getSelectedRow(),0).toString(); //save the name of animal from selected row

                }
            }
        });

        this.apply_panel.add(btn_cancel);
        this.apply_panel.add(btn_apply);

        this.panel.add(tbl_info,BorderLayout.NORTH);
        this.panel.add(apply_panel,BorderLayout.SOUTH);

        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }

    /**
     * make JColorChooser and set the new color for object
     */
    public void selection(){
        JFrame frame = new JFrame("Color Chooser:");
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JColorChooser chooser = new JColorChooser();
        JButton select = new JButton("Select");
        select.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                col = chooser.getColor();
                for(SeaCreature obj : list){ //run over seaitems hashset
                    if(obj instanceof Swimmable){ //check for swimmable object
                        if(((Swimmable)obj).getAnimalName().equalsIgnoreCase(animalName)){
                            //Comparison with name
                            MarineAnimalDecorator objDraw=new MarineAnimalDecorator((Swimmable)obj);
                            objDraw.PaintFish(col);
                        }
                    }
                }
            }
        });

        panel.add(chooser, BorderLayout.NORTH);
        panel.add(select,BorderLayout.SOUTH);

        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
