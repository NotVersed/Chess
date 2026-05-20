public final class Move {
    private Move() {
    }

    // encode a move into an int
    public static int encode(int from, int to, int type) {
        return (type << 12) | (to << 6) | from;
    }

    // overloaded for promotion moves
    public static int encode(int from, int to, int type, int promotionPiece) {
        return (promotionPiece << 14) | (type << 12) | (to << 6) | from;
    }

    // decode
    public static int from(int move) {
        return move & 0x3F;
    }

    public static int to(int move) {
        return (move >> 6) & 0x3F;
    }

    public static int type(int move) {
        return (move >> 12) & 0x3;
    }

    public static int promotionPiece(int move) {
        return (move >> 14) & 0x3;
    }

    // move types
    public static final int NORMAL = 0;
    public static final int CASTLING = 1;
    public static final int EN_PASSANT = 2;
    public static final int PROMOTION = 3;

    // promotion pieces
    public static final int PROMOTE_QUEEN = 0;
    public static final int PROMOTE_ROOK = 1;
    public static final int PROMOTE_BISHOP = 2;
    public static final int PROMOTE_KNIGHT = 3;
}