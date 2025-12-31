
import javax.swing.ImageIcon;

public class PieceImage {

    private static final ImageIcon WHITEPAWN = new ImageIcon("images/whitepawn.png");
    private static final ImageIcon BLACKPAWN = new ImageIcon("images/blackpawn.png");

    private static final ImageIcon WHITEBISHOP = new ImageIcon("images/whitebishop.png");
    private static final ImageIcon BLACKBISHOP = new ImageIcon("images/blackbishop.png");

    private static final ImageIcon WHITEKNIGHT = new ImageIcon("images/whiteknight.png");
    private static final ImageIcon BLACKKNIGHT = new ImageIcon("images/blackknight.png");

    private static final ImageIcon WHITEROOK = new ImageIcon("images/whiterook.png");
    private static final ImageIcon BLACKROOK = new ImageIcon("images/blackrook.png");

    private static final ImageIcon WHITEQUEEN = new ImageIcon("images/whitequeen.png");
    private static final ImageIcon BLACKQUEEN = new ImageIcon("images/blackqueen.png");

    private static final ImageIcon WHITEKING = new ImageIcon("images/whiteking.png");
    private static final ImageIcon BLACKKING = new ImageIcon("images/blackking.png");

    private PieceImage() {
    }

    ;

    public static ImageIcon getPieceImage(Piece piece) {

        if (piece instanceof Pawn) {
            return piece.isWhite() ? WHITEPAWN : BLACKPAWN;
        }

        if (piece instanceof Bishop) {
            return piece.isWhite() ? WHITEBISHOP : BLACKBISHOP;
        }

        if (piece instanceof Knight) {
            return piece.isWhite() ? WHITEKNIGHT : BLACKKNIGHT;
        }

        if (piece instanceof Rook) {
            return piece.isWhite() ? WHITEROOK : BLACKROOK;
        }

        if (piece instanceof Queen) {
            return piece.isWhite() ? WHITEQUEEN : BLACKQUEEN;
        }

        if (piece instanceof King) {
            return piece.isWhite() ? WHITEKING : BLACKKING;
        }

        if (piece == null) {
            throw new IllegalArgumentException("Piece is null");
        }

        throw new IllegalArgumentException(
                "Unknown piece type: " + piece.getClass()
        );
    }

}
