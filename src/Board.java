import java.util.HashMap;
import java.util.Map;

public class Board {

    private final Map<Coordinate, Piece> BOARDSTATE;
    private final int ROWS;
    private final int COLUMNS;

    public Board() {
        this(8, 8);
    }

    public Board(int rows, int columns) {
        this.ROWS = rows;
        this.COLUMNS = columns;
        this.BOARDSTATE = new HashMap<>();
        initializeBoard();
    }

    private void initializeBoard() {
        // Pawns
        for (int col = 0; col < COLUMNS; col++) {
            BOARDSTATE.put(new Coordinate(col, 1), new Pawn(new Coordinate(col, 1), true));
            BOARDSTATE.put(new Coordinate(col, 6), new Pawn(new Coordinate(col, 6), false));
        }

        // Rooks
        BOARDSTATE.put(new Coordinate(0, 0), new Rook(new Coordinate(0, 0), true));
        BOARDSTATE.put(new Coordinate(7, 0), new Rook(new Coordinate(7, 0), true));
        BOARDSTATE.put(new Coordinate(0, 7), new Rook(new Coordinate(0, 7), false));
        BOARDSTATE.put(new Coordinate(7, 7), new Rook(new Coordinate(7, 7), false));

        // Knights
        BOARDSTATE.put(new Coordinate(1, 0), new Knight(new Coordinate(1, 0), true));
        BOARDSTATE.put(new Coordinate(6, 0), new Knight(new Coordinate(6, 0), true));
        BOARDSTATE.put(new Coordinate(1, 7), new Knight(new Coordinate(1, 7), false));
        BOARDSTATE.put(new Coordinate(6, 7), new Knight(new Coordinate(6, 7), false));

        // Bishops
        BOARDSTATE.put(new Coordinate(2, 0), new Bishop(new Coordinate(2, 0), true));
        BOARDSTATE.put(new Coordinate(5, 0), new Bishop(new Coordinate(5, 0), true));
        BOARDSTATE.put(new Coordinate(2, 7), new Bishop(new Coordinate(2, 7), false));
        BOARDSTATE.put(new Coordinate(5, 7), new Bishop(new Coordinate(5, 7), false));

        // Queens
        BOARDSTATE.put(new Coordinate(3, 0), new Queen(new Coordinate(3, 0), true));
        BOARDSTATE.put(new Coordinate(3, 7), new Queen(new Coordinate(3, 7), false));

        // Kings
        BOARDSTATE.put(new Coordinate(4, 0), new King(new Coordinate(4, 0), true));
        BOARDSTATE.put(new Coordinate(4, 7), new King(new Coordinate(4, 7), false));
    }

    public boolean inBounds(Coordinate c) {
        return c.getX() >= 0 && c.getX() < ROWS
            && c.getY() >= 0 && c.getY() < COLUMNS;
    }

    public boolean isOccupied(Coordinate c) {
        return BOARDSTATE.containsKey(c);
    }

    public Piece getPieceAt(Coordinate c) {
        return BOARDSTATE.get(c);
    }

    public int getRows() {
        return ROWS;
    }

    public int getColumns() {
        return COLUMNS;
    }
    public Map<Coordinate, Piece> getBoardState(){
        return Map.copyOf(this.BOARDSTATE);
    }
}
