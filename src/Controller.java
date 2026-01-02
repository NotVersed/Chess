import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller {
    private final Board BOARD;
    private final Map<Piece, List<Move>> legalMoves;
    public Controller(final Board BOARD){
        this.BOARD = BOARD;
        this.legalMoves = new HashMap<>();
    }
}
