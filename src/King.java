import javax.swing.ImageIcon;

public class King extends Piece{
    private static final ImageIcon WHITE = new ImageIcon("images/whiteking.png");
    private static final ImageIcon BLACK = new ImageIcon("images/blackking.png");
    
    public King(boolean whitePiece){
        super(whitePiece, whitePiece ? WHITE : BLACK);
    }
}
