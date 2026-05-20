public class BitBoard {

    // white pieces
    private long whitePawns;
    private long whiteKnights;
    private long whiteBishops;
    private long whiteRooks;
    private long whiteQueens;
    private long whiteKing;

    // black pieces
    private long blackPawns;
    private long blackKnights;
    private long blackBishops;
    private long blackRooks;
    private long blackQueens;
    private long blackKing;

    public BitBoard() {
        initializeStartingPosition();
    }

    public static int toIndex(int x, int y) {
        return y * 8 + x;
    }

    public static int toX(int index) {
        return index % 8;
    }

    public static int toY(int index) {
        return index / 8;
    }

    private static long setBit(long bitboard, int index) {
        return bitboard | (1L << index);
    }

    public void initializeStartingPosition() {
        // pawns (FF00 is bits 8-15 set and FF000000000000 is 48-55 set)
        whitePawns = 0xFF00L;
        blackPawns = 0xFF000000000000L;

        // rooks
        whiteRooks = 1L | (1L << 7);
        blackRooks = (1L << 56) | (1L << 63);

        // knights
        whiteKnights = (1L << 1) | (1L << 6);
        blackKnights = (1L << 57) | (1L << 62);

        // bishops
        whiteBishops = (1L << 2) | (1L << 5);
        blackBishops = (1L << 58) | (1L << 61);

        // queens
        whiteQueens = 1L << 3;
        blackQueens = 1L << 59;
        // kings

        whiteKing = 1L << 4;
        blackKing = 1L << 60;
    }

    public UndoInfo applyMove(int move) {
        int from = Move.from(move);
        int to = Move.to(move);

        PieceInfo moving = getPieceAt(from);
        PieceInfo captured = getPieceAt(to);

        // save captured piece info for undo
        PieceType capturedType = captured != null ? captured.type() : null;
        boolean capturedWasWhite = captured != null && captured.white();

        // clear source and destination
        clearSquare(from);
        clearSquare(to);

        // place piece on destination
        setPiece(to, moving.type(), moving.white());

        return new UndoInfo(move, capturedType, capturedWasWhite, -1);
    }

    private void setPiece(int square, PieceType type, boolean white) {
        long bit = 1L << square;
        switch (type) {
            case PAWN -> {
                if (white)
                    whitePawns |= bit;
                else
                    blackPawns |= bit;
            }
            case KNIGHT -> {
                if (white)
                    whiteKnights |= bit;
                else
                    blackKnights |= bit;
            }
            case BISHOP -> {
                if (white)
                    whiteBishops |= bit;
                else
                    blackBishops |= bit;
            }
            case ROOK -> {
                if (white)
                    whiteRooks |= bit;
                else
                    blackRooks |= bit;
            }
            case QUEEN -> {
                if (white)
                    whiteQueens |= bit;
                else
                    blackQueens |= bit;
            }
            case KING -> {
                if (white)
                    whiteKing |= bit;
                else
                    blackKing |= bit;
            }
        }
    }

    private void clearSquare(int square) {
        long mask = ~(1L << square);
        whitePawns &= mask;
        whiteKnights &= mask;
        whiteBishops &= mask;
        whiteRooks &= mask;
        whiteQueens &= mask;
        whiteKing &= mask;
        blackPawns &= mask;
        blackKnights &= mask;
        blackBishops &= mask;
        blackRooks &= mask;
        blackQueens &= mask;
        blackKing &= mask;
    }

    public long whitePieces() {
        return whitePawns | whiteRooks | whiteKnights | whiteBishops | whiteQueens | whiteKing;
    }

    public long blackPieces() {
        return blackPawns | blackRooks | blackKnights | blackBishops | blackQueens | blackKing;
    }

    public long allPieces() {
        return whitePieces() | blackPieces();
    }

    public long emptySquares() {
        return ~allPieces();
    }

    public long getWhitePawns() {
        return whitePawns;
    }

    public long getWhiteKnights() {
        return whiteKnights;
    }

    public long getWhiteBishops() {
        return whiteBishops;
    }

    public long getWhiteRooks() {
        return whiteRooks;
    }

    public long getWhiteQueens() {
        return whiteQueens;
    }

    public long getWhiteKing() {
        return whiteKing;
    }

    public long getBlackPawns() {
        return blackPawns;
    }

    public long getBlackKnights() {
        return blackKnights;
    }

    public long getBlackBishops() {
        return blackBishops;
    }

    public long getBlackRooks() {
        return blackRooks;
    }

    public long getBlackQueens() {
        return blackQueens;
    }

    public long getBlackKing() {
        return blackKing;
    }

    public PieceInfo getPieceAt(int index) {
        long bit = 1L << index;
        if ((whitePawns & bit) != 0)
            return new PieceInfo(PieceType.PAWN, true);
        if ((whiteKnights & bit) != 0)
            return new PieceInfo(PieceType.KNIGHT, true);
        if ((whiteBishops & bit) != 0)
            return new PieceInfo(PieceType.BISHOP, true);
        if ((whiteRooks & bit) != 0)
            return new PieceInfo(PieceType.ROOK, true);
        if ((whiteQueens & bit) != 0)
            return new PieceInfo(PieceType.QUEEN, true);
        if ((whiteKing & bit) != 0)
            return new PieceInfo(PieceType.KING, true);
        if ((blackPawns & bit) != 0)
            return new PieceInfo(PieceType.PAWN, false);
        if ((blackKnights & bit) != 0)
            return new PieceInfo(PieceType.KNIGHT, false);
        if ((blackBishops & bit) != 0)
            return new PieceInfo(PieceType.BISHOP, false);
        if ((blackRooks & bit) != 0)
            return new PieceInfo(PieceType.ROOK, false);
        if ((blackQueens & bit) != 0)
            return new PieceInfo(PieceType.QUEEN, false);
        if ((blackKing & bit) != 0)
            return new PieceInfo(PieceType.KING, false);
        return null;
    }
}
