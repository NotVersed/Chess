import java.util.List;

public class Engine {

    private final BitBoard board;
    private final Controller controller;
    private final boolean playingAsWhite;
    private final int depth;

    public Engine(BitBoard board, Controller controller, boolean playingAsWhite, int depth) {
        this.board = board;
        this.controller = controller;
        this.playingAsWhite = playingAsWhite;
        this.depth = depth;
    }

    public int findBestMove() {
        int bestScore = playingAsWhite ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int bestMove = -1;

        List<Integer> legalMoves = new MoveGenerator(board).generateLegalMoves(playingAsWhite);

        for (int move : legalMoves) {
            UndoInfo undo = board.applyMove(move);
            int score = minimax(depth - 1, !playingAsWhite, Integer.MIN_VALUE, Integer.MAX_VALUE);
            board.undoMove(undo);

            boolean isBetter = playingAsWhite ? score > bestScore : score < bestScore;
            if (isBetter) {
                bestScore = score;
                bestMove = move;
            }
        }

        return bestMove;
    }

    private int minimax(int depth, boolean isMaximizing, int alpha, int beta) {
        if (depth == 0) {
            return Evaluator.evaluate(board);
        }

        MoveGenerator gen = new MoveGenerator(board);
        List<Integer> legalMoves = gen.generateLegalMoves(isMaximizing);

        if (legalMoves.isEmpty()) {
            if (gen.isInCheck(board, isMaximizing)) {
                return isMaximizing ? -100000 : 100000;
            }
            return 0;
        }

        int eval = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        legalMoves.sort((a, b) -> scoreMove(b) - scoreMove(a));
        for (int move : legalMoves) {
            UndoInfo undo = board.applyMove(move);
            int score = minimax(depth - 1, !isMaximizing, alpha, beta);
            board.undoMove(undo);

            if (isMaximizing) {
                eval = Math.max(eval, score);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha)
                    break;
            } else {
                eval = Math.min(eval, score);
                beta = Math.min(beta, eval);
                if (beta <= alpha)
                    break;
            }
        }

        return eval;
    }

    private int scoreMove(int move) {
        PieceInfo captured = board.getPieceAt(Move.to(move));
        if (captured == null)
            return 0;

        PieceInfo attacker = board.getPieceAt(Move.from(move));
        int victimValue = pieceValue(captured.type());
        int attackerValue = pieceValue(attacker.type());
        return victimValue - attackerValue;
    }

    private int pieceValue(PieceType type) {
        return switch (type) {
            case PAWN -> 100;
            case KNIGHT -> 320;
            case BISHOP -> 330;
            case ROOK -> 500;
            case QUEEN -> 900;
            case KING -> 20000;
        };
    }

    public boolean isEngineTurn() {
        return controller.whiteTurn() == playingAsWhite;
    }

    public boolean isPlayingAsWhite() {
        return playingAsWhite;
    }
}