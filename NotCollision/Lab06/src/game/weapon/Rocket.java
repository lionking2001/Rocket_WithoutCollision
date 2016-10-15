package game.weapon;

import game.Direction;
import game.GameCharacter;
import game.GameControl;
import game.util.Vector;
import gameui.AbstractGameCharacter;
import gameui.GameField;
import gameui.Sprite;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Rocket extends AbstractGameCharacter {
    Direction direction = Direction.UP;
    Rectangle boundary = new Rectangle(0, 0, 100, 100);
    boolean isAutoRotate = false;
    Vector accelerator = new Vector(0.0, 0.0);
    Logger logger = LoggerFactory.getLogger(GameControl.class);

    public boolean isAutoRotate() {
        return this.isAutoRotate;
    }

    public void setAutoRotate(boolean autoRotate) {
        this.isAutoRotate = autoRotate;
    }

    public Rocket(GameField gameField) {
        super(gameField, "/rocket4d.png");
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(this.getImage(), (int)((double)this.x - this.boundary.getWidth() / 2.0), (int)((double)this.y - this.boundary.getHeight() / 2.0), (int)((double)this.x + this.boundary.getWidth() / 2.0), (int)((double)this.y + this.boundary.getHeight() / 2.0), 100 * (this.direction.getValue() - 1), 0, 100 * this.direction.getValue(), 100, this.getParent());
    }

    public void rotateAutomatically() {
        new Thread(new Runnable(){

            @Override
            public void run() {
                do {
                    try {
                        do {
                            if (Rocket.this.isAutoRotate()) {
                                if (Rocket.this.direction.getValue() < 8) {
                                    Rocket.this.direction.setValue(Rocket.this.direction.getValue() + 1);
                                } else {
                                    Rocket.this.direction.setValue(1);
                                }
                            }
                            Thread.sleep(500);
                        } while (true);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                        continue;
                    }

                } while (true);
            }
        }).start();
    }

    public synchronized void rotateRocket(Direction direction) {
        int newDirection = 0;
        if (direction == Direction.UP) {
            newDirection = -1;
        } else if (direction == Direction.DOWN) {
            newDirection = 1;
        }
        this.direction = this.direction.getValue() + newDirection > 8 ? Direction.valueOf(1) : (this.direction.getValue() + newDirection < 1 ? Direction.valueOf(8) : Direction.valueOf(this.direction.getValue() + newDirection));
    }

    public void launchRocket(double velocity) {
        this.launchRocket(new Vector(velocity, this.direction.getRadian()));
        this.logger.info("Launch with velocity {} in {} direction", (Object)velocity, (Object)this.direction);
    }

    public void launchRocket(final Vector velocity) {
        new Thread(new Runnable(){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void run() {
                Vector currentVelocity = new Vector(velocity.getSize(), velocity.getRadian());
                try {
                    while (Rocket.this.isVisible()) {
                        int x = (int)Math.round((double)Rocket.this.getX() + currentVelocity.getSize() * Math.cos(currentVelocity.getRadian()));
                        int y = (int)Math.round((double)Rocket.this.getY() - currentVelocity.getSize() * Math.sin(currentVelocity.getRadian()));
                        Rocket.this.changeDirection(Rocket.this.getX(), Rocket.this.getY(), x, y);
                        Rocket.this.setLocation(x, y);
                        if (!Rocket.this.isInOfBound(new Rectangle(Rocket.this.getTopLeftX(), Rocket.this.getTopLeftY(), (int)Rocket.this.boundary.getWidth(), (int)Rocket.this.boundary.getHeight()))) {
                            Rocket.this.setVisible(false);
                            break;
                        }
                        currentVelocity = currentVelocity.add(Rocket.this.accelerator);
//                        Rocket.this.isCrash(Rocket.this.getParent().getCharacterList());
                        Thread.sleep(40);
                    }
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private synchronized void changeDirection(int oldX, int oldY, int newX, int newY) {
        double rad = Math.atan2(oldY - newY, newX - oldX);
        if (rad < -2.748893571891069) {
            this.setDirection(Direction.LEFT);
        } else if (rad < -1.9634954084936207) {
            this.setDirection(Direction.DOWN_LEFT);
        } else if (rad < -1.1780972450961724) {
            this.setDirection(Direction.DOWN);
        } else if (rad < -0.39269908169872414) {
            this.setDirection(Direction.DOWN_RIGHT);
        } else if (rad > 2.748893571891069) {
            this.setDirection(Direction.LEFT);
        } else if (rad > 1.9634954084936207) {
            this.setDirection(Direction.UP_LEFT);
        } else if (rad > 1.1780972450961724) {
            this.setDirection(Direction.UP);
        } else if (rad > 0.39269908169872414) {
            this.setDirection(Direction.UP_RIGHT);
        } else {
            this.setDirection(Direction.RIGHT);
        }
    }

    public boolean isInOfBound(Rectangle rectangle) {
        return this.getParent().getBounds().contains(rectangle);
    }

    private Rectangle getBoundary() {
        return new Rectangle(this.getTopLeftX(), this.getTopLeftY(), (int)this.boundary.getWidth(), (int)this.boundary.getHeight());
    }

    private int getTopLeftX() {
        return this.getX() - this.boundary.width / 2;
    }

    private int getTopLeftY() {
        return this.getY() - this.boundary.height / 2;
    }

//    public synchronized boolean isCrash(List<GameCharacter> others) {
//        Sprite removeRocket = null;
//        for (GameCharacter other : others) {
//            if (!(other instanceof Rocket)) continue;
//            Rocket otherRocket = (Rocket)other;
//            if (this == other || !other.isVisible() || !this.getBoundary().intersects(otherRocket.getBoundary())) continue;
//            removeRocket = otherRocket;
//            break;
//        }
//        if (removeRocket != null) {
//            removeRocket.setVisible(false);
//            this.setVisible(false);
////            this.logger.debug("Crash between r1 [{},{},{},{}] and r2 [{},{},{},{}]", this.getBoundary().getX(), this.getBoundary().getY(), this.getBoundary().getWidth(), this.getBoundary().getHeight(),super.getBoundary().getX(), super.getBoundary().getY(), super.getBoundary().getWidth(), super.getBoundary().getHeight());
//            return true;
//        }
//        return false;
//    }

}
