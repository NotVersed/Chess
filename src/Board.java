
public class Board {
    // chess board 8x8
    private Tile[][] tiles = new Tile[8][8];
    private int tileWidth;
    private int tileHeight;

    public Board(int tileWidth, int tileHeight) {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        initializeBoard();
        initializePieces();
    }

    public Board() {
        this.tileWidth = 100;
        this.tileHeight = 100;
        initializeBoard();
        initializePieces();
    }

    public Tile[][] getTiles() {
        return this.tiles;
    }

    public void initializeBoard() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                Coordinate tileCoordinate = new Coordinate(8 - i, 8 - j);
                boolean isLightSquare = (i + j) % 2 == 0;
                tiles[i][j] = new Tile(j * tileHeight, i * tileWidth, tileHeight, tileWidth, tileCoordinate, null,
                        isLightSquare);
            }
        }
    }

    public void updateTileSizes(int tileWidth, int tileHeight) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                Tile tile = tiles[i][j];
                tile.setWidth(tileWidth);
                tile.setHeight(tileHeight);
                tile.setX(j * tileWidth);
                tile.setY(i * tileHeight);
            }
        }
    }

    public void initializePieces() {
        boolean white = true;
        boolean black = false;
        int blackPawnRow = 1;
        int whitePawnRow = 6;
        int blackStartingRow = 0;
        int whiteStartingRow = 7;
        for (int i = 0; i < tiles.length; i++) {
            tiles[blackPawnRow][i].setPiece(new Pawn(black));
            tiles[whitePawnRow][i].setPiece(new Pawn(white));
            if (i == 0) {
                tiles[blackStartingRow][i].setPiece(new Rook(black));
                tiles[blackStartingRow][tiles.length - 1 - i].setPiece(new Rook(black));
                tiles[whiteStartingRow][i].setPiece(new Rook(white));
                tiles[whiteStartingRow][tiles.length - 1 - i].setPiece(new Rook(white));
            }
            if (i == 1) {
                tiles[blackStartingRow][i].setPiece(new Knight(black));
                tiles[blackStartingRow][tiles.length - 1 - i].setPiece(new Knight(black));
                tiles[whiteStartingRow][i].setPiece(new Knight(white));
                tiles[whiteStartingRow][tiles.length - 1 - i].setPiece(new Knight(white));
            }
            if (i == 2) {
                tiles[blackStartingRow][i].setPiece(new Bishop(black));
                tiles[blackStartingRow][tiles.length - 1 - i].setPiece(new Bishop(black));
                tiles[whiteStartingRow][i].setPiece(new Bishop(white));
                tiles[whiteStartingRow][tiles.length - 1 - i].setPiece(new Bishop(white));
            }
            if (i == 3) {
                tiles[blackStartingRow][i].setPiece(new Queen(black));
                tiles[blackStartingRow][tiles.length - 1 - i].setPiece(new King(black));
                tiles[whiteStartingRow][i].setPiece(new Queen(white));
                tiles[whiteStartingRow][tiles.length - 1 - i].setPiece(new King(white));
            }
        }

    }

}