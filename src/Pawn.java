import javax.swing.ImageIcon;

public class Pawn extends Piece{

    private static final ImageIcon WHITE = new ImageIcon("images/whitepawn.png");
    private static final ImageIcon BLACK = new ImageIcon("images/blackpawn.png");
    
    public Pawn(boolean whitePiece){
        super(whitePiece, whitePiece ? WHITE : BLACK);
    }

}
