package game;

import game.Direction;
import game.GameCharacter;
import game.util.Vector;
import game.weapon.Rocket;
import gameui.GameField;
import gameui.GamePanel;
import java.awt.event.KeyEvent;
import game.Direction;
import game.GameCharacter;
import game.util.Vector;
import game.weapon.Rocket;
import gameui.GameField;
import gameui.GamePanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameControl
        extends Thread {
    private GamePanel gamePanel;
    private Rocket currentRocket;
    Logger logger = LoggerFactory.getLogger(GameControl.class);
    Random random = new Random();
    boolean isCharging = false;
    int velocity;

    @Override
    public void run() {
        boolean isRun = true;
        while (isRun) {
            try {
                this.gamePanel.repaint();
                Thread.sleep(40);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public GameControl(GamePanel panel) {
        this.gamePanel = panel;
        this.gamePanel.setFocusable(true);
        this.gamePanel.addMouseListener(new MouseListener(){

            @Override
            public void mouseClicked(MouseEvent e) {
                GameControl.this.onMouseClicked(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        this.gamePanel.addKeyListener(new KeyListener(){

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                GameControl.this.onKeyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                GameControl.this.onKeyReleased(e);
            }
        });
    }

    private void onKeyReleased(KeyEvent e) {
        block5 : {
            int keyCode = e.getKeyCode();
            try {
                switch (keyCode) {
                    case 32: {
                        this.setCharging(false);
                        this.currentRocket.launchRocket(this.velocity);
                    }
                }
            }
            catch (NullPointerException exception) {
                if (this.currentRocket == null) break block5;
                exception.printStackTrace();
            }
        }
    }

    private void onKeyPressed(KeyEvent e) {
        block8 : {
            int keyCode = e.getKeyCode();
            try {
                switch (keyCode) {
                    case 38: {
                        this.currentRocket.rotateRocket(Direction.UP);
                        break;
                    }
                    case 40: {
                        this.currentRocket.rotateRocket(Direction.DOWN);
                        break;
                    }
                    case 32: {
                        this.chargeRocket();
                        break;
                    }
                    case 10: {
                        this.randomCreateRocket();
                    }
                }
            }
            catch (NullPointerException exception) {
                if (this.currentRocket == null) break block8;
                exception.printStackTrace();
            }
        }
    }

    private void onMouseClicked(MouseEvent e) {
        if (e.getButton() == 1 && e.isControlDown()) {
            this.createRocket(e.getX(), e.getY());
        }
    }

    private void createRocket(int x, int y) {
        this.currentRocket = new Rocket(this.gamePanel);
        this.currentRocket.setLocation(x, y);
        this.gamePanel.addCharacter(this.currentRocket);
    }

    private void randomCreateRocket() {
        new Thread(new Runnable(){

            @Override
            public void run() {
                double speed = GameControl.this.random.nextDouble() * 18.0 + 2.0;
                boolean radAngle = GameControl.this.random.nextBoolean();
                double radSize = GameControl.this.random.nextDouble() * 3.141592653589793 / 2.0;
                if (!radAngle) {
                    radSize *= -1.0;
                }
                int x = GameControl.this.random.nextInt(GameControl.this.gamePanel.getWidth());
                int y = GameControl.this.random.nextInt(GameControl.this.gamePanel.getHeight());
                Rocket rocket = new Rocket(GameControl.this.gamePanel);
                rocket.setLocation(x, y);
                GameControl.this.gamePanel.addCharacter(rocket);
                GameControl.this.logger.debug("x = {}, y = {}, velocity = {}, in {} radias", x, y, speed, radSize);
                rocket.launchRocket(new Vector(speed, radSize));
            }
        }).start();
    }

    public void setCharging(boolean charging) {
        this.isCharging = charging;
    }

    public void chargeRocket() {
        new Thread(new Runnable(){

            @Override
            public void run() {
                GameControl.this.isCharging = true;
                GameControl.this.velocity = 1;
                boolean chargeUp = true;
                try {
                    while (GameControl.this.isCharging) {
                        if (chargeUp) {
                            if (GameControl.this.velocity < 100) {
                                ++GameControl.this.velocity;
                            } else {
                                chargeUp = false;
                            }
                        } else if (GameControl.this.velocity > 0) {
                            --GameControl.this.velocity;
                        } else {
                            chargeUp = true;
                        }
                        Thread.sleep(10);
                    }
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}

