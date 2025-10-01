import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel {
    private static final int GAME_WIDTH = 1000;
    private static final int GAME_HEIGHT = 1000;
    private static final int TILE_WIDTH = GAME_WIDTH / 8;
    private static final int TILE_HEIGHT = GAME_HEIGHT / 8;
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    Image image;
    Graphics graphics;
    Board board;

    public GamePanel() {
        addBoard();
        this.setFocusable(true);
        this.addMouseListener(new MouseListener());
        this.setPreferredSize(SCREEN_SIZE);
    }

    public void addBoard() {
        this.board = new Board();
    }

    public void paint(Graphics g) {
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g) {
        Tile[][] tiles = board.getTiles();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                tiles[i][j].draw(g);
            }
        }
    }

    public static int getGameHeight() {
        return GAME_HEIGHT;
    }

    public static int getGameWidth() {
        return GAME_WIDTH;
    }
    public static int getTileHeight() {
        return TILE_HEIGHT;
    }

    public static int getTileWidth() {
        return TILE_WIDTH;
    }

}
