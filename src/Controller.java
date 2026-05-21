import java.util.List;
import javax.swing.SwingUtilities;

public class Controller {

    private final BitBoard board;
    private MoveGenerator generator;
    private boolean whiteToMove;
    private boolean gameOver;
    private List<Integer> legalMoves;

    public Controller(BitBoard board) {
        this.board = board;
        this.whiteToMove = true;
        this.gameOver = false;
        this.generator = new MoveGenerator(board);
        this.legalMoves = generator.generateLegalMoves(whiteToMove);
    }

    public boolean tryMove(int move) {
        if (gameOver)
            return false;
        if (!legalMoves.contains(move))
            return false;

        board.applyMove(move);

        whiteToMove = !whiteToMove;
        generator = new MoveGenerator(board);
        legalMoves = generator.generateLegalMoves(whiteToMove);

        GameResult result = evaluateGameState();
        if (result != GameResult.ONGOING) {
            gameOver = true;
            SwingUtilities.invokeLater(() -> GameOverDialog.show(result));
        }

        return true;
    }

    private GameResult evaluateGameState() {
        if (legalMoves.isEmpty()) {
            if (generator.isInCheck(board, whiteToMove)) {
                return whiteToMove ? GameResult.BLACK_WINS_CHECKMATE : GameResult.WHITE_WINS_CHECKMATE;
            }
            return GameResult.STALEMATE;
        }
        return GameResult.ONGOING;
    }

    public List<Integer> getLegalMoves() {
        return legalMoves;
    }

    public boolean whiteTurn() {
        return whiteToMove;
    }

    public BitBoard getBoard() {
        return board;
    }

    public GameResult getGameState() {
        return evaluateGameState();
    }

    public boolean isGameOver() {
        return gameOver;
    }
}