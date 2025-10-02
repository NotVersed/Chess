import javax.swing.ImageIcon;

public class Bishop extends Piece{
    private static final ImageIcon WHITE = new ImageIcon("images/whitebishop.png");
    private static final ImageIcon BLACK = new ImageIcon("images/blackbishop.png");
    
    public Bishop(boolean whitePiece){
        super(whitePiece, whitePiece ? WHITE : BLACK);
    }
}
