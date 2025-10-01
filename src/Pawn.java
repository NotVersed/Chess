import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Pawn extends Piece {
    private static final int PIECE_DIMENSIONS = Math.min(GamePanel.getTileHeight(), GamePanel.getTileWidth()) - 30;
    private static final Image WHITE_PAWN = scale(new ImageIcon("images/whitepawn.png"), PIECE_DIMENSIONS).getImage();
    private static final Image BLACK_PAWN = scale(new ImageIcon("images/blackpawn.png"), PIECE_DIMENSIONS).getImage();

    public Pawn(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public void draw(Graphics g, Tile tile) {
        g.drawImage(this.isWhitePiece() ? WHITE_PAWN : BLACK_PAWN, tile.x + ((tile.width - PIECE_DIMENSIONS) / 2), tile.y + ((tile.height - PIECE_DIMENSIONS) / 2), null);
    }

}
