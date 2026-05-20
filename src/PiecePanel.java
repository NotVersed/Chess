import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

public class PiecePanel extends JPanel {
    private final Image IMAGE;

    public PiecePanel(PieceInfo piece) {
        this.IMAGE = PieceImage.getPieceImage(piece).getImage();
        setOpaque(false);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (IMAGE != null) {
            int width = getWidth();
            int height = getHeight();
            int padding = Math.min(width, height) / 4;

            double scale = Math.min((double)(width - padding) / this.IMAGE.getWidth(null),
                                    (double)(height - padding) / this.IMAGE.getHeight(null));
            int newWidth = (int)(this.IMAGE.getWidth(null) * scale);
            int newHeight = (int)(this.IMAGE.getHeight(null) * scale);

            int x = (width - newWidth) / 2;
            int y = (height - newHeight) / 2;

            g.drawImage(this.IMAGE, x, y, newWidth, newHeight, null);
        }
    }

}
