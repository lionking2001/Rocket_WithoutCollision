package gameui;

import java.awt.Dimension;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.net.URL;
import javax.swing.ImageIcon;

public abstract class Sprite {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean vis;
    protected Image image;

    public Sprite() {
        this.vis = true;
    }

    public Sprite(int x, int y) {
        this.x = x;
        this.y = y;
        this.vis = true;
    }

    protected Dimension getImageDimensions() {
        this.width = this.image.getWidth(null);
        this.height = this.image.getHeight(null);
        return new Dimension(this.width, this.height);
    }

    protected void loadImage(String imageName) {
        ImageIcon ii = new ImageIcon(Sprite.class.getResource(imageName));
        this.image = ii.getImage();
        this.getImageDimensions();
    }

    public Image getImage() {
        return this.image;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public boolean isVisible() {
        return this.vis;
    }

    public void setVisible(Boolean visible) {
        this.vis = visible;
    }

    public Rectangle getBounds() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }

    public void setLocation(Point point) {
        this.x = (int)point.getX();
        this.y = (int)point.getY();
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

