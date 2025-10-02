import javax.swing.ImageIcon;

public class Rook extends Piece{
    private static final ImageIcon WHITE = new ImageIcon("images/whiterook.png");
    private static final ImageIcon BLACK = new ImageIcon("images/blackrook.png");

    public Rook(boolean whitePiece){
        super(whitePiece, whitePiece ? WHITE : BLACK);
    }

}
