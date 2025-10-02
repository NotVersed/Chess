import javax.swing.ImageIcon;

public class Queen extends Piece{
    private static final ImageIcon WHITE = new ImageIcon("images/whitequeen.png");
    private static final ImageIcon BLACK = new ImageIcon("images/blackqueen.png");
    
    public Queen(boolean whitePiece){
        super(whitePiece, whitePiece ? WHITE : BLACK);
    }
}
