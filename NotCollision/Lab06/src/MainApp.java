//Colision

import game.GameControl;
import gameui.GamePanel;
import java.awt.Container;
import java.awt.EventQueue;
import javax.swing.JFrame;

public class MainApp
        extends JFrame {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                try {
                    MainApp frame = new MainApp();
                    frame.setVisible(true);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MainApp() {
        this.setDefaultCloseOperation(3);
        this.setBounds(0, 0, 960, 540);
        GamePanel gamePanel = new GamePanel();
        gamePanel.setBounds(0, 0, 960, 540);
        GameControl gameControl = new GameControl(gamePanel);
        this.setContentPane(gamePanel);
        gameControl.start();
    }

}
