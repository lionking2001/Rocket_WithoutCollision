package gameui;

import game.GameCharacter;
import gameui.GameField;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.image.ImageObserver;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GamePanel
        extends JPanel
        implements GameField {
    Image backgroud;
    List<GameCharacter> characterList;

    public GamePanel() {
        this.setLayout(null);
        this.setBackgroud();
        this.initGame();
    }

    private void initGame() {
        this.characterList = new ArrayList<GameCharacter>();
    }

    protected void setBackgroud() {
        ImageIcon icon = new ImageIcon(GamePanel.class.getResource("/space.jpg"));
        this.backgroud = icon.getImage();
        this.setSize(this.backgroud.getWidth(null), this.backgroud.getHeight(null));
    }

    @Override
    public synchronized void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.backgroud, 0, 0, this.getWidth(), this.getHeight(), this);
        for (GameCharacter gameCharacter : this.characterList) {
            if (!gameCharacter.isVisible()) continue;
            gameCharacter.draw(g);
        }
    }

    @Override
    public List<GameCharacter> getCharacterList() {
        return this.characterList;
    }

    public void addCharacter(GameCharacter character) {
        this.characterList.add(character);
    }
}

