import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Controller {
    private final Board BOARD;
    private Map<Piece, List<Move>> legalMoves;
    private Coordinate enPassantTarget;
    private MoveValidator validator;
    private Set<Coordinate> whiteControlled;
    private Set<Coordinate> blackControlled;
    private boolean whiteToMove;

    public Controller(final Board BOARD) {
        this.whiteToMove = true;
        this.BOARD = BOARD;
        this.legalMoves = new HashMap<>();
        this.enPassantTarget = null;
        this.validator = new MoveValidator(BOARD, enPassantTarget);
        generateLegalMoves();
        generateControlledSquares();
    }

    private void generateLegalMoves() {
        legalMoves = validator.generateLegalMoves();
    }

    private void generateControlledSquares() {
        whiteControlled = validator.getControlledSquares(true);
        blackControlled = validator.getControlledSquares(false);
    }

    public boolean tryMove(Move move) {
        if (move == null)
            return false;

        Piece movingPiece = BOARD.getBoardState().get(move.getSource());
        if (movingPiece == null)
            return false;

        // turn enforcement
        if (movingPiece.isWhite() != whiteToMove)
            return false;

        // legality check
        List<Move> moves = legalMoves.get(movingPiece);
        if (moves == null || !moves.contains(move))
            return false;

        // apply move
        BOARD.applyMove(move);

        // flip turn
        whiteToMove = !whiteToMove;

        // recompute validator + caches
        validator = new MoveValidator(BOARD, enPassantTarget);
        generateLegalMoves();
        generateControlledSquares();

        return true;
    }

}
