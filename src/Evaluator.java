public final class Evaluator {
    private Evaluator() {
    }

    private static final int PAWN_VALUE = 100;
    private static final int KNIGHT_VALUE = 320;
    private static final int BISHOP_VALUE = 330;
    private static final int ROOK_VALUE = 500;
    private static final int QUEEN_VALUE = 900;
    private static final int KING_VALUE = 20000;
    private static final int[] PAWN_TABLE = {
            0, 0, 0, 0, 0, 0, 0, 0,
            50, 50, 50, 50, 50, 50, 50, 50,
            10, 10, 20, 30, 30, 20, 10, 10,
            5, 5, 10, 25, 25, 10, 5, 5,
            0, 0, 0, 20, 20, 0, 0, 0,
            5, -5, -10, 0, 0, -10, -5, 5,
            5, 10, 10, -20, -20, 10, 10, 5,
            0, 0, 0, 0, 0, 0, 0, 0
    };

    private static final int[] KNIGHT_TABLE = {
            -50, -40, -30, -30, -30, -30, -40, -50,
            -40, -20, 0, 0, 0, 0, -20, -40,
            -30, 0, 10, 15, 15, 10, 0, -30,
            -30, 5, 15, 20, 20, 15, 5, -30,
            -30, 0, 15, 20, 20, 15, 0, -30,
            -30, 5, 10, 15, 15, 10, 5, -30,
            -40, -20, 0, 5, 5, 0, -20, -40,
            -50, -40, -30, -30, -30, -30, -40, -50
    };

    private static final int[] BISHOP_TABLE = {
            -20, -10, -10, -10, -10, -10, -10, -20,
            -10, 0, 0, 0, 0, 0, 0, -10,
            -10, 0, 5, 10, 10, 5, 0, -10,
            -10, 5, 5, 10, 10, 5, 5, -10,
            -10, 0, 10, 10, 10, 10, 0, -10,
            -10, 10, 10, 10, 10, 10, 10, -10,
            -10, 5, 0, 0, 0, 0, 5, -10,
            -20, -10, -10, -10, -10, -10, -10, -20
    };

    private static final int[] ROOK_TABLE = {
            0, 0, 0, 0, 0, 0, 0, 0,
            5, 10, 10, 10, 10, 10, 10, 5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            0, 0, 0, 5, 5, 0, 0, 0
    };

    private static final int[] QUEEN_TABLE = {
            -20, -10, -10, -5, -5, -10, -10, -20,
            -10, 0, 0, 0, 0, 0, 0, -10,
            -10, 0, 5, 5, 5, 5, 0, -10,
            -5, 0, 5, 5, 5, 5, 0, -5,
            0, 0, 5, 5, 5, 5, 0, -5,
            -10, 5, 5, 5, 5, 5, 0, -10,
            -10, 0, 5, 0, 0, 0, 0, -10,
            -20, -10, -10, -5, -5, -10, -10, -20
    };

    private static final int[] KING_MIDDLEGAME_TABLE = {
            -30, -40, -40, -50, -50, -40, -40, -30,
            -30, -40, -40, -50, -50, -40, -40, -30,
            -30, -40, -40, -50, -50, -40, -40, -30,
            -30, -40, -40, -50, -50, -40, -40, -30,
            -20, -30, -30, -40, -40, -30, -30, -20,
            -10, -20, -20, -20, -20, -20, -20, -10,
            20, 20, 0, 0, 0, 0, 20, 20,
            20, 30, 10, 0, 0, 10, 30, 20
    };

    public static int evaluate(BitBoard board) {
        int eval = 0;

        // material bonuses
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

        // piece square table bonuses
        eval += evaluatePieceTable(board.getWhitePawns(), PAWN_TABLE, true);
        eval += evaluatePieceTable(board.getWhiteKnights(), KNIGHT_TABLE, true);
        eval += evaluatePieceTable(board.getWhiteBishops(), BISHOP_TABLE, true);
        eval += evaluatePieceTable(board.getWhiteRooks(), ROOK_TABLE, true);
        eval += evaluatePieceTable(board.getWhiteQueens(), QUEEN_TABLE, true);
        eval += evaluatePieceTable(board.getWhiteKing(), KING_MIDDLEGAME_TABLE, true);

        eval -= evaluatePieceTable(board.getBlackPawns(), PAWN_TABLE, false);
        eval -= evaluatePieceTable(board.getBlackKnights(), KNIGHT_TABLE, false);
        eval -= evaluatePieceTable(board.getBlackBishops(), BISHOP_TABLE, false);
        eval -= evaluatePieceTable(board.getBlackRooks(), ROOK_TABLE, false);
        eval -= evaluatePieceTable(board.getBlackQueens(), QUEEN_TABLE, false);
        eval -= evaluatePieceTable(board.getBlackKing(), KING_MIDDLEGAME_TABLE, false);

        return eval;
    }

    private static int evaluatePieceTable(long pieces, int[] table, boolean white) {
        int reward = 0;
        while (pieces != 0) {
            int square = Long.numberOfTrailingZeros(pieces);
            pieces &= pieces - 1;
            reward += white ? table[63 - square] : table[square];
        }

        return reward;
    }
}