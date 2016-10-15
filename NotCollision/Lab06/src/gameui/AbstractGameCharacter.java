package gameui;
import game.GameCharacter;
import gameui.GameField;
import gameui.Sprite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

public class AbstractGameCharacter extends Sprite implements GameCharacter {
    int speed = 10;
    protected GameField parent;

    public AbstractGameCharacter(GameField gameField, String resourceName) {
        this.parent = gameField;
        this.loadImage(resourceName);
    }

    public GameField getParent() {
        return this.parent;
    }

    @Override
    public void moveLeft() {
        Point newPoint = new Point(this.getX() - this.speed, this.getY());
        if (this.isInOfBound(newPoint)) {
            this.setLocation(newPoint);
        }
    }

    @Override
    public void moveRight() {
        Point newPoint = new Point(this.getX() + this.speed, this.getY());
        if (this.isInOfBound(newPoint)) {
            this.setLocation(newPoint);
        }
    }

    @Override
    public void moveUp() {
        Point newPoint = new Point(this.getX(), this.getY() - this.speed);
        if (this.isInOfBound(newPoint)) {
            this.setLocation(newPoint);
        }
    }

    @Override
    public void moveDown() {
        Point newPoint = new Point(this.getX(), this.getY() + this.speed);
        if (this.isInOfBound(newPoint)) {
            this.setLocation(newPoint);
        }
    }

    private boolean isInOfBound(Point newPoint) {
        Rectangle gameBounds = this.getParent().getBounds();
        Rectangle newPosition = new Rectangle(newPoint, this.getImageDimensions());
        return gameBounds.contains(newPosition);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(this.getImage(), this.getX(), this.getY(), this.parent);
    }
}

