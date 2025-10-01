import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public abstract class Piece {
    private boolean isWhite;

    public Piece(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public boolean isWhitePiece() {
        return this.isWhite;
    }

    public abstract void draw(Graphics g, Tile tile);

    public static ImageIcon scale(ImageIcon image, int sideLength){
        Image original = image.getImage();
        Image scaledImage = original.getScaledInstance(sideLength, sideLength, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
}
