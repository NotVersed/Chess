/**
 * The purpose of this class is to create a board object
 * The board object holds row x col tiles
 */

public class Board {
    private Tile[][] tiles;
    private int rows;
    private int columns;

    public Board() {
        this(8, 8);
    }

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.tiles = new Tile[this.rows][this.columns];
        initializeTiles();
        initializePieces();
    }

    private void initializePieces() {
        // Pawns
        for (int col = 0; col < columns; col++) {
            tiles[1][col].setPiece(new Pawn(false));
            tiles[6][col].setPiece(new Pawn(true));
        }

        // Rooks
        tiles[0][0].setPiece(new Rook(false));
        tiles[0][7].setPiece(new Rook(false));
        tiles[7][0].setPiece(new Rook(true));
        tiles[7][7].setPiece(new Rook(true));

        // Knights
        tiles[0][1].setPiece(new Knight(false));
        tiles[0][6].setPiece(new Knight(false));
        tiles[7][1].setPiece(new Knight(true));
        tiles[7][6].setPiece(new Knight(true));

        // Bishops
        tiles[0][2].setPiece(new Bishop(false));
        tiles[0][5].setPiece(new Bishop(false));
        tiles[7][2].setPiece(new Bishop(true));
        tiles[7][5].setPiece(new Bishop(true));

        // Queens
        tiles[0][3].setPiece(new Queen(false));
        tiles[7][3].setPiece(new Queen(true));

        // Kings
        tiles[0][4].setPiece(new King(false));
        tiles[7][4].setPiece(new King(true));
    }

    private void initializeTiles() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                tiles[i][j] = new Tile(null, (j + i) % 2 == 0);
            }
        }
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

    public Piece getPieceAt(int row, int col){
        return tiles[row][col].getPiece();
    }

   

}
