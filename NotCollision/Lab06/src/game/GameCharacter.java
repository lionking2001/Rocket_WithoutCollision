package game;

import java.awt.*;
import java.awt.Graphics;

public interface GameCharacter {
    public void moveLeft();

    public void moveRight();

    public void moveUp();

    public void moveDown();

    public void draw(Graphics var1);

    public boolean isVisible();
}

