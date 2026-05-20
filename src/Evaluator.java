public final class Evaluator {
    private Evaluator() {}

    private static final int PAWN_VALUE = 100;
    private static final int KNIGHT_VALUE = 320;
    private static final int BISHOP_VALUE = 330;
    private static final int ROOK_VALUE = 500;
    private static final int QUEEN_VALUE = 900;
    private static final int KING_VALUE = 20000;

    public static int evaluate(BitBoard board) {
        int eval = 0;

        eval += Long.bitCount(board.getWhitePawns()) * PAWN_VALUE;
        eval += Long.bitCount(board.getWhiteKnights()) * KNIGHT_VALUE;
        eval += Long.bitCount(board.getWhiteBishops()) * BISHOP_VALUE;
        eval += Long.bitCount(board.getWhiteRooks()) * ROOK_VALUE;
        eval += Long.bitCount(board.getWhiteQueens()) * QUEEN_VALUE;
        eval += Long.bitCount(board.getWhiteKing()) * KING_VALUE;

        eval -= Long.bitCount(board.getBlackPawns()) * PAWN_VALUE;
        eval -= Long.bitCount(board.getBlackKnights()) * KNIGHT_VALUE;
        eval -= Long.bitCount(board.getBlackBishops()) * BISHOP_VALUE;
        eval -= Long.bitCount(board.getBlackRooks()) * ROOK_VALUE;
        eval -= Long.bitCount(board.getBlackQueens()) * QUEEN_VALUE;
        eval -= Long.bitCount(board.getBlackKing()) * KING_VALUE;

        return eval;
    }
}