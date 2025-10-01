import javax.swing.ImageIcon;

public class PieceImages {

    // Pawns
    private static final ImageIcon WHITE_PAWN  = new ImageIcon("images/whitepawn.png");
    private static final ImageIcon BLACK_PAWN  = new ImageIcon("images/blackpawn.png");

    // Rooks
    private static final ImageIcon WHITE_ROOK  = new ImageIcon("images/whiterook.png");
    private static final ImageIcon BLACK_ROOK  = new ImageIcon("images/blackrook.png");

    // Knights
    private static final ImageIcon WHITE_KNIGHT = new ImageIcon("images/whiteknight.png");
    private static final ImageIcon BLACK_KNIGHT = new ImageIcon("images/blackknight.png");

    // Bishops
    private static final ImageIcon WHITE_BISHOP = new ImageIcon("images/whitebishop.png");
    private static final ImageIcon BLACK_BISHOP = new ImageIcon("images/blackbishop.png");

    // Queens
    private static final ImageIcon WHITE_QUEEN = new ImageIcon("images/whitequeen.png");
    private static final ImageIcon BLACK_QUEEN = new ImageIcon("images/blackqueen.png");

    // Kings
    private static final ImageIcon WHITE_KING = new ImageIcon("images/whiteking.png");
    private static final ImageIcon BLACK_KING = new ImageIcon("images/blackking.png");

    // Expose getters (so the fields stay private)
    public static ImageIcon getWhitePawn()   { return WHITE_PAWN; }
    public static ImageIcon getBlackPawn()   { return BLACK_PAWN; }
    public static ImageIcon getWhiteRook()   { return WHITE_ROOK; }
    public static ImageIcon getBlackRook()   { return BLACK_ROOK; }
    public static ImageIcon getWhiteKnight() { return WHITE_KNIGHT; }
    public static ImageIcon getBlackKnight() { return BLACK_KNIGHT; }
    public static ImageIcon getWhiteBishop() { return WHITE_BISHOP; }
    public static ImageIcon getBlackBishop() { return BLACK_BISHOP; }
    public static ImageIcon getWhiteQueen()  { return WHITE_QUEEN; }
    public static ImageIcon getBlackQueen()  { return BLACK_QUEEN; }
    public static ImageIcon getWhiteKing()   { return WHITE_KING; }
    public static ImageIcon getBlackKing()   { return BLACK_KING; }

    // prevent instantiation
    private PieceImages() {}
}

