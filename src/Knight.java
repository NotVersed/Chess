import javax.swing.ImageIcon;

public class Knight extends Piece{
    private static final ImageIcon WHITE = new ImageIcon("images/whiteknight.png");
    private static final ImageIcon BLACK = new ImageIcon("images/blackknight.png");
    
    public Knight(boolean whitePiece){
        super(whitePiece, whitePiece ? WHITE : BLACK);
    }
}
