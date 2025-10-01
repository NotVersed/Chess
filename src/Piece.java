import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public abstract class Piece {
    private boolean isWhite;
    private static final int PADDING = 30;
    private ImageIcon icon;
    private Image iconScaled;
    private int lastSize = -1;

    public Piece(boolean isWhite, ImageIcon image) {
        this.isWhite = isWhite;
        this.icon = image;
    }

    public boolean isWhitePiece() {
        return this.isWhite;
    }

    public void draw(Graphics g, Tile tile) {
        int size = (int) Math.min(tile.getWidth(), tile.getHeight()) - PADDING;
        if (size != lastSize) {
            this.iconScaled = scale(icon, size).getImage();
        }
        Image pieceImage = iconScaled;
        g.drawImage(pieceImage, tile.x + ((tile.width - size) / 2), tile.y + ((tile.height - size) / 2), null);
    };

    public void dragDraw(Graphics g, int x, int y, GamePanel panel) {
        // Make sure the piece is scaled
        int size = (int) Math.min(panel.getTileWidth(), panel.getTileHeight()) - PADDING;
        if (iconScaled == null || size != lastSize) {
            this.iconScaled = scale(icon, size).getImage();
            lastSize = size;
        }

        // Draw centered on the mouse position
        g.drawImage(iconScaled, x - size / 2, y - size / 2, null);
    }

    public static ImageIcon scale(ImageIcon image, int sideLength) {
        Image original = image.getImage();
        Image scaledImage = original.getScaledInstance(sideLength, sideLength, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
}
