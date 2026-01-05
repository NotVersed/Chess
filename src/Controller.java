
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.SwingUtilities;

public class Controller {

    private final Board BOARD;
    private Map<Piece, List<Move>> legalMoves;
    private Coordinate enPassantTarget;
    private MoveValidator validator;
    private Set<Coordinate> whiteControlled;
    private Set<Coordinate> blackControlled;
    private boolean whiteToMove;
    private boolean gameOver;

    public Controller(final Board BOARD) {
        this.whiteToMove = true;
        this.BOARD = BOARD;
        this.legalMoves = new HashMap<>();
        this.enPassantTarget = null;
        this.validator = new MoveValidator(BOARD, enPassantTarget);
        gameOver = false;
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
        if (move == null) {
            return false;
        }

        Piece movingPiece = BOARD.getBoardState().get(move.getSource());
        if (movingPiece == null) {
            return false;
        }

        // turn enforcement
        if (movingPiece.isWhite() != whiteToMove) {
            return false;
        }

        // legality check
        List<Move> moves = legalMoves.get(movingPiece);
        if (moves == null || !moves.contains(move)) {
            return false;
        }

        BOARD.applyMove(move);

        whiteToMove = !whiteToMove;

        // recompute validator methods
        validator = new MoveValidator(BOARD, enPassantTarget);
        generateLegalMoves();
        generateControlledSquares();

        GameResult result = evaluateGameState();
        if (result != GameResult.ONGOING) {
            gameOver = true;

            // repaint board first
            SwingUtilities.invokeLater(() -> {
                GameOverDialog.show(result);
            });
        }

        return true;
    }

    private boolean isKingInCheck(boolean white) {
        Coordinate kingSquare = getKingSquare(white);

        Set<Coordinate> enemyControlled
                = white ? validator.getControlledSquares(false)
                        : validator.getControlledSquares(true);

        return enemyControlled.contains(kingSquare);
    }

    private Coordinate getKingSquare(boolean white) {
        for (Map.Entry<Coordinate, Piece> entry : BOARD.getBoardState().entrySet()) {
            if (entry.getValue() instanceof King
                    && entry.getValue().isWhite() == white) {
                return entry.getKey();
            }
        }
        throw new IllegalStateException("King not found");
    }

    private GameResult evaluateGameState() {
        // generate legal moves already happened
        boolean hasAnyMoves = false;

        for (Map.Entry<Piece, List<Move>> entry : legalMoves.entrySet()) {
            Piece piece = entry.getKey();

            // only consider side to move
            if (piece.isWhite() != whiteToMove) {
                continue;
            }

            if (!entry.getValue().isEmpty()) {
                hasAnyMoves = true;
                break;
            }
        }

        if (hasAnyMoves) {
            return GameResult.ONGOING;
        }

        // no legal moves exist
        boolean inCheck = isKingInCheck(whiteToMove);

        if (inCheck) {
            return whiteToMove
                    ? GameResult.BLACK_WINS_CHECKMATE
                    : GameResult.WHITE_WINS_CHECKMATE;
        } else {
            return GameResult.STALEMATE;
        }
    }

}
