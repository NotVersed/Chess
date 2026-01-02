import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller {
    private final Board BOARD;
    private Map<Piece, List<Move>> legalMoves;
    Coordinate enPassantTarget;
    public Controller(final Board BOARD){
        this.BOARD = BOARD;
        this.legalMoves = new HashMap<>();
        this.enPassantTarget = null;
    }

    private void generateLegalMoves(){
        legalMoves = MoveValidator.generateLegalMoves(BOARD.getBoardState(), this.enPassantTarget);
    }

}
