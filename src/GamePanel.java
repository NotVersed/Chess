import java.awt.*;
import javax.swing.*;

public class GamePanel extends JPanel {
    private int gameWidth;
    private int gameHeight;
    private int tileWidth;
    private int tileHeight;

    private Board board;

    public GamePanel(int width, int height) {
        this.gameWidth = width;
        this.gameHeight = height;
        updateTileSize();
        this.board = new Board(tileWidth, tileHeight);
        this.setPreferredSize(new Dimension(gameWidth, gameHeight));
        this.setFocusable(true);
        this.addMouseListener(new MouseListener());
    }

    private void updateTileSize() {
        this.tileWidth = gameWidth / 8;
        this.tileHeight = gameHeight / 8;
    }

    @Override
    public void paint(Graphics g) {
        Image image = createImage(getWidth(), getHeight());
        Graphics graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    private void draw(Graphics g) {
        Tile[][] tiles = board.getTiles();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                tiles[i][j].draw(g);
            }
        }
    }

    public int getGameWidth() {
        return gameWidth;
    }

    public int getGameHeight() {
        return gameHeight;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public void resizePanel(Dimension newSize) {
        this.gameWidth = this.getWidth();
        this.gameHeight = this.getHeight();
        updateTileSize();
        board.updateTileSizes(tileWidth, tileHeight);
        this.setPreferredSize(newSize);
        this.revalidate();
        this.repaint();
    }
}
