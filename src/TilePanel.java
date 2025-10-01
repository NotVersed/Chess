import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class TilePanel extends JPanel {

    private int tileDimension = 75;
    private ImageIcon pieceImage;

    public TilePanel(Tile tile) {
        if (tile.isLightSquare()) {
            this.setBackground(new Color(65, 105, 225));
        } else {
            this.setBackground(new Color(211, 211, 211));
        }
        if (tile.getPiece() != null) {
            if (tile.getPiece() instanceof Pawn && tile.getPiece().isWhitePiece()) {
                addImage("images/whitepawn.png");
            }
            if (tile.getPiece() instanceof Pawn && !tile.getPiece().isWhitePiece()) {
                addImage("images/blackpawn.png");
            }
        }
        this.setPreferredSize(new Dimension(tileDimension, tileDimension));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (pieceImage != null) {
            int imgWidth = pieceImage.getIconWidth();
            int imgHeight = pieceImage.getIconHeight();
            double scale = Math.min((double) (tileDimension - 25) / imgHeight,
                    (double) (tileDimension - 25) / imgWidth);
            int newW = (int) (imgWidth * scale);
            int newH = (int) (imgHeight * scale);
            int x = (tileDimension - newW) / 2;
            int y = (tileDimension - newH) / 2;
            g.drawImage(pieceImage.getImage(), x, y, newW, newH, this);
        }
    }

    public void addImage(String path) {
        this.pieceImage = new ImageIcon(path);
    }

}
