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

    public Controller(final Board BOARD) {
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
    private void generateControlledSquares(){
        whiteControlled = validator.getControlledSquares(true);
        blackControlled = validator.getControlledSquares(false);
    }

}
