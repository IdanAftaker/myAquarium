import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by idanaftaker on 26/05/2016.
 */

public class CloneFactory extends JDialog implements ActionListener {
    //===Members===
    private AquaPanel aqua;
    private JFrame frame;   //main frame
    private JPanel panel;    //main panel
    private JPanel propPanel;    //properties panel
    private JPanel colorPanel;    //color panel
    private JPanel applyPanel;    //apply panel

    private JLabel sizeLable;
    private JSlider sliderSize;
    private JLabel horLable;
    private JSlider horSpeed;
    private JLabel verLable;
    private JSlider varSpeed;

    private JLabel colorLabel;
    private JColorChooser color;

    private JButton confirm;

    Swimmable obj;

    //===Methods===
    public CloneFactory(Swimmable obj){
        this.frame = new JFrame("Prototype:");
        //main panel
        this.panel = new JPanel();
        panel.setLayout(new BorderLayout());

        this.obj = obj;

        //properties panel
        this.propPanel = new JPanel();
        propPanel.setLayout(new FlowLayout());
        propPanel.setBorder(BorderFactory.createEmptyBorder());
        this.sizeLable = new JLabel("Which Size?");

        this.sliderSize = new JSlider(20,320);
        sliderSize.setMajorTickSpacing(80);
        sliderSize.setMinorTickSpacing(20);
        sliderSize.setValue(obj.getSize());
        sliderSize.setPaintTicks(true);
        sliderSize.setSnapToTicks(true);
        propPanel.add(sizeLable);
        propPanel.add(sliderSize);

        this.horLable = new JLabel("Horizontal Speed:");
        this.horSpeed = new JSlider(1,10);
        horSpeed.setMajorTickSpacing(4);
        horSpeed.setMinorTickSpacing(1);
        horSpeed.setValue(obj.getHorSpeed());
        horSpeed.setPaintTicks(true);
        horSpeed.setSnapToTicks(true);
        this.verLable = new JLabel("Vertical Speed");
        this.varSpeed = new JSlider(1,10);
        varSpeed.setMajorTickSpacing(4);
        varSpeed.setMinorTickSpacing(1);
        varSpeed.setValue(obj.getVerSpeed());
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
        this.color = new JColorChooser(obj.getColorAsColorObject());
        colorPanel.add(colorLabel,BorderLayout.NORTH);
        colorPanel.add(color,BorderLayout.CENTER);

        //apply Panel
        this.applyPanel = new JPanel(new GridLayout());
        this.confirm = new JButton("Apply");
        confirm.addActionListener(this);
        applyPanel.add(confirm);

        //add to color panel
        colorPanel.add(applyPanel,BorderLayout.SOUTH);

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
        if(e.getSource() == confirm){
            obj.setSize(sliderSize.getValue());
            obj.setColor(color.getColor());
            obj.setHorSpeed(horSpeed.getValue());
            obj.setVerSpeed(varSpeed.getValue());
            obj.setFrequency();
            frame.dispose();
        }
    }
}
