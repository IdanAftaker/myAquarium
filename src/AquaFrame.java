import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import static java.lang.Thread.sleep;

/**
 * Created by idanaftaker on 06/04/2016.
 */
public class AquaFrame extends JFrame implements ActionListener{

//    //===Members===
    private AquaPanel panel;
    private JMenuBar menuBar;

    private JMenu btn_bg;
    private JMenu btn_file;
    private JMenu btn_help;
    private JMenu btn_memento;

    private JMenuItem btn_exit;
    private JMenuItem btn_img;
    private JMenuItem btn_blue;
    private JMenuItem btn_none;
    private JMenuItem btn_helppress;
    private JButton btn_memento_save;
    private JButton btn_memento_restore;
    private JButton btn_plants_memento_save;
    private JButton btn_plants_memento_restore;
    private JMenuItem btn_animal_memento;
    private JMenuItem btn_plants_memento;

    //===Methods===

    AquaFrame(){
        super("Wired Aquarium");
        BorderLayout border = new BorderLayout();
        //get the screen resolution for display frame in full screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //make the content of the frame
        this.panel = new AquaPanel(this);
        //add main window panel
        this.add(panel,BorderLayout.CENTER);
        //init the menu bar
        menuBar = new JMenuBar();
        setMenuBar();
        this.setJMenuBar(this.menuBar);

        //setSize(screenSize.width,screenSize.height);
        this.setBounds(0,0,(int)screenSize.getWidth(),(int)screenSize.getHeight());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * set the menu bar
     * [File][Background][Help]
     */
    public void setMenuBar(){
        //buttons for upper menu
        //file button
        this.btn_file = new JMenu("File");
        //exit button
        this.btn_exit = new JMenuItem("Exit");
        //add actionListener to exit button
        btn_exit.addActionListener(this);
        btn_file.add(btn_exit);

        //background button
        this.btn_bg = new JMenu("Background");
        this.btn_img = new JMenuItem("Image");
        btn_img.addActionListener(this);
        btn_bg.add(btn_img);

        this.btn_blue = new JMenuItem("Blue");
        btn_blue.addActionListener(this);
        btn_bg.add(btn_blue);

        this.btn_none = new JMenuItem("None");
        btn_none.addActionListener(this);
        btn_bg.add(btn_none);

        //help button
        this.btn_help = new JMenu("Help");
        this.btn_helppress = new JMenuItem("Help");
        btn_helppress.addActionListener(this);
        btn_help.add(btn_helppress);

        //memento button
        this.btn_memento = new JMenu("Memento");
        this.btn_animal_memento = new JMenuItem("Animal");
        this.btn_animal_memento.addActionListener(this);
        this.btn_plants_memento = new JMenuItem("Plants");
        this.btn_plants_memento.addActionListener(this);


        btn_memento.add(btn_animal_memento);
        btn_memento.add(btn_plants_memento);

        //add the buttons to the panel
        menuBar.add(btn_file);
        menuBar.add(btn_bg);
        menuBar.add(btn_memento);
        menuBar.add(btn_help);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btn_exit ){
            System.exit(0);
        }

        if(e.getSource() == btn_img){
            panel.changeBackground("img");
        }
        if(e.getSource() == btn_blue){
            panel.changeBackground("blue");
        }
        if(e.getSource() == btn_none){
            panel.changeBackground("none");
        }

        if(e.getSource() == btn_helppress) {
            panel.showMessageDialog();
        }
        if(e.getSource() == btn_animal_memento){
            panel.selectAnimalMemento();
        }
        if(e.getSource() == btn_plants_memento){
            panel.selectPlantsMemento();
        }
    }

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        AquaFrame main = new AquaFrame();

    }


}
