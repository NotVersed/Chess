import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

public class PiecePanel extends JPanel {
    private Image image;

    public PiecePanel(Piece piece) {
        this.image = piece.getPieceImage().getImage();
        setOpaque(false);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            int width = getWidth();
            int height = getHeight();
            int padding = Math.min(width, height) / 4;

            double scale = Math.min((double)(width - padding) / image.getWidth(null),
                                    (double)(height - padding) / image.getHeight(null));
            int newWidth = (int)(image.getWidth(null) * scale);
            int newHeight = (int)(image.getHeight(null) * scale);

            int x = (width - newWidth) / 2;
            int y = (height - newHeight) / 2;

            g.drawImage(image, x, y, newWidth, newHeight, null);
        }
    }

}
