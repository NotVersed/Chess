import javax.swing.ImageIcon;

public class Bishop extends Piece{
    private static final ImageIcon WHITE = new ImageIcon("images/whitebishop.png");
    private static final ImageIcon BLACK = new ImageIcon("images/blackbishop.png");
    public Bishop(Coordinate coordinate, boolean whitePiece){
        super(coordinate, whitePiece, whitePiece ? WHITE : BLACK);
    }
}
