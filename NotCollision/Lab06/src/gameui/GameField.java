package gameui;

import game.GameCharacter;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.List;

public interface GameField
        extends ImageObserver {
    public Rectangle getBounds();

    public List<GameCharacter> getCharacterList();
}

