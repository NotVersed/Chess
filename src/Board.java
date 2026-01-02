import java.util.Map;
import java.util.HashMap;

public class Board {

    private final Map<Coordinate, Piece> board;
    private final int rows;
    private final int columns;

    public Board() {
        this(8, 8);
    }

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.board = new HashMap<>();
        initializeBoard();
    }

    private void initializeBoard() {
        // Pawns
        for (int col = 0; col < columns; col++) {
            board.put(new Coordinate(col, 1), new Pawn(new Coordinate(col, 1), true));
            board.put(new Coordinate(col, 6), new Pawn(new Coordinate(col, 6), false));
        }

        // Rooks
        board.put(new Coordinate(0, 0), new Rook(new Coordinate(0, 0), true));
        board.put(new Coordinate(7, 0), new Rook(new Coordinate(7, 0), true));
        board.put(new Coordinate(0, 7), new Rook(new Coordinate(0, 7), false));
        board.put(new Coordinate(7, 7), new Rook(new Coordinate(7, 7), false));

        // Knights
        board.put(new Coordinate(1, 0), new Knight(new Coordinate(1, 0), true));
        board.put(new Coordinate(6, 0), new Knight(new Coordinate(6, 0), true));
        board.put(new Coordinate(1, 7), new Knight(new Coordinate(1, 7), false));
        board.put(new Coordinate(6, 7), new Knight(new Coordinate(6, 7), false));

        // Bishops
        board.put(new Coordinate(2, 0), new Bishop(new Coordinate(2, 0), true));
        board.put(new Coordinate(5, 0), new Bishop(new Coordinate(5, 0), true));
        board.put(new Coordinate(2, 7), new Bishop(new Coordinate(2, 7), false));
        board.put(new Coordinate(5, 7), new Bishop(new Coordinate(5, 7), false));

        // Queens
        board.put(new Coordinate(3, 0), new Queen(new Coordinate(3, 0), true));
        board.put(new Coordinate(3, 7), new Queen(new Coordinate(3, 7), false));

        // Kings
        board.put(new Coordinate(4, 0), new King(new Coordinate(4, 0), true));
        board.put(new Coordinate(4, 7), new King(new Coordinate(4, 7), false));
    }

    public boolean inBounds(Coordinate c) {
        return c.getX() >= 0 && c.getX() < rows
            && c.getY() >= 0 && c.getY() < columns;
    }

    public boolean isOccupied(Coordinate c) {
        return board.containsKey(c);
    }

    public Piece getPieceAt(Coordinate c) {
        return board.get(c);
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
}
