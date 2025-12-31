
import java.util.ArrayList;

/**
 * The purpose of this class is to create a board object The board object holds
 * row x col tiles
 */
public class Board {

    private Tile[][] tiles;
    private ArrayList<Piece> pieces;
    private ArrayList<Move> moves;
    private int rows;
    private int columns;

    public Board() {
        this(8, 8);
    }

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.tiles = new Tile[this.rows][this.columns];
        this.pieces = new ArrayList<>();
        this.moves = new ArrayList<>();
        initializeTiles();
        initializePieces();
    }

    private void initializePieces() {
        // Pawns
        for (int col = 0; col < columns; col++) {
            Pawn whitePawn = new Pawn(new Coordinate(col, 6), true);
            Pawn blackPawn = new Pawn(new Coordinate(col, 1), false);
            pieces.add(whitePawn);
            pieces.add(blackPawn);
            tiles[1][col].setPiece(blackPawn);
            tiles[6][col].setPiece(whitePawn);
        }

        // Rooks
        Rook whiteRookOne = new Rook(new Coordinate(0, 7), true);
        Rook whiteRookTwo = new Rook(new Coordinate(7, 7), true);
        Rook blackRookOne = new Rook(new Coordinate(0, 0), false);
        Rook blackRookTwo = new Rook(new Coordinate(7, 0), false);
        pieces.add(blackRookOne);
        pieces.add(blackRookTwo);
        pieces.add(whiteRookOne);
        pieces.add(whiteRookTwo);
        tiles[0][0].setPiece(blackRookOne);
        tiles[0][7].setPiece(blackRookTwo);
        tiles[7][0].setPiece(whiteRookOne);
        tiles[7][7].setPiece(whiteRookTwo);

        // Knights
        Knight whiteKnightOne = new Knight(new Coordinate(1, 0), true);
        Knight whiteKnightTwo = new Knight(new Coordinate(6, 0), true);
        Knight blackKnightOne = new Knight(new Coordinate(1, 7), false);
        Knight blackKnightTwo = new Knight(new Coordinate(6, 7), false);
        pieces.add(whiteKnightOne);
        pieces.add(whiteKnightTwo);
        pieces.add(blackKnightOne);
        pieces.add(blackKnightTwo);
        tiles[0][1].setPiece(blackKnightOne);
        tiles[0][6].setPiece(blackKnightTwo);
        tiles[7][1].setPiece(whiteKnightOne);
        tiles[7][6].setPiece(whiteKnightTwo);

        // Bishops
        Bishop whiteBishopOne = new Bishop(new Coordinate(2, 0), true);
        Bishop whiteBishopTwo = new Bishop(new Coordinate(5, 0), true);
        Bishop blackBishopOne = new Bishop(new Coordinate(2, 7), false);
        Bishop blackBishopTwo = new Bishop(new Coordinate(5, 7), false);
        pieces.add(whiteBishopOne);
        pieces.add(whiteBishopTwo);
        pieces.add(blackBishopOne);
        pieces.add(blackBishopTwo);
        tiles[0][2].setPiece(blackBishopOne);
        tiles[0][5].setPiece(blackBishopTwo);
        tiles[7][2].setPiece(whiteBishopOne);
        tiles[7][5].setPiece(whiteBishopTwo);

        // Queens
        Queen whiteQueen = new Queen(new Coordinate(3, 0), true);
        Queen blackQueen = new Queen(new Coordinate(3, 7), false);
        pieces.add(whiteQueen);
        pieces.add(blackQueen);
        tiles[0][3].setPiece(blackQueen);
        tiles[7][3].setPiece(whiteQueen);

        // Kings
        King whiteKing = new King(new Coordinate(4, 0), true);
        King blackKing = new King(new Coordinate(4, 7), false);
        pieces.add(whiteKing);
        pieces.add(blackKing);
        tiles[0][4].setPiece(blackKing);
        tiles[7][4].setPiece(whiteKing);
    }

    private void initializeTiles() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                tiles[i][j] = new Tile(null, (j + i) % 2 == 0, new Coordinate(i, j));
            }
        }
    }

    private boolean inBounds(Coordinate coordinate) {
        int x = coordinate.getX();
        int y = coordinate.getY();
        return x >= 0 && x < this.rows && y >= 0 && y < this.columns;
    }

    private boolean tileOccupied(Tile tile) {
        return tile.getPiece() != null;
    }

    public void generateLegalMoves() {

    }

    public int getRows() {
        return this.rows;
    }

    public int getColumns() {
        return this.columns;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public Tile[][] getTiles() {
        return this.tiles;
    }

    public Piece getPieceAt(int row, int col) {
        return tiles[row][col].getPiece();
    }

}
