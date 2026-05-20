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

    private PieceImage() {}

    public static ImageIcon getPieceImage(PieceInfo piece) {
        if (piece == null) throw new IllegalArgumentException("Piece is null");
        return switch (piece.type()) {
            case PAWN -> piece.white() ? WHITEPAWN : BLACKPAWN;
            case BISHOP -> piece.white() ? WHITEBISHOP : BLACKBISHOP;
            case KNIGHT -> piece.white() ? WHITEKNIGHT : BLACKKNIGHT;
            case ROOK -> piece.white() ? WHITEROOK : BLACKROOK;
            case QUEEN -> piece.white() ? WHITEQUEEN : BLACKQUEEN;
            case KING -> piece.white() ? WHITEKING : BLACKKING;
        };
    }
}