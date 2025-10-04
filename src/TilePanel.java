import java.awt.*;
import javax.swing.*;

public class TilePanel extends JPanel {
    private Color color;
    private Piece piece;

    public TilePanel(Color color) {
        this.color = color;
        setLayout(null);
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        repaint();
    }

    public void removePiece() {
        this.piece = null;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(color);
        g.fillRect(0, 0, getWidth(), getHeight());

        if (piece != null) {
            Image img = piece.getPieceImage().getImage();
            int width = getWidth();
            int height = getHeight();
            int padding = Math.min(width, height) / 4;

            double scale = Math.min((double)(width - padding) / img.getWidth(null),
                                    (double)(height - padding) / img.getHeight(null));

            int newWidth = (int)(img.getWidth(null) * scale);
            int newHeight = (int)(img.getHeight(null) * scale);

            int x = (width - newWidth) / 2;
            int y = (height - newHeight) / 2;

            g.drawImage(img, x, y, newWidth, newHeight, null);
        }
    }

    public Piece getPiece() {
        return piece;
    }
}
