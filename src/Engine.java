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
            int score = minimax(depth - 1, !playingAsWhite);
            board.undoMove(undo);

            boolean isBetter = playingAsWhite ? score > bestScore : score < bestScore;
            if (isBetter) {
                bestScore = score;
                bestMove = move;
            }
        }

        return bestMove;
    }

    private int minimax(int depth, boolean isMaximizing) {
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

        for (int move : legalMoves) {
            UndoInfo undo = board.applyMove(move);
            int score = minimax(depth - 1, !isMaximizing);
            board.undoMove(undo);

            if (isMaximizing) {
                eval = Math.max(eval, score);
            } else {
                eval = Math.min(eval, score);
            }
        }

        return eval;
    }

    public boolean isEngineTurn() {
        return controller.whiteTurn() == playingAsWhite;
    }
}