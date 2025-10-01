
public class Board {
    // chess board 8x8
    private Tile[][] tiles = new Tile[8][8];
    private static final int TILE_WIDTH = GamePanel.getTileWidth();
    private static final int TILE_HEIGHT = GamePanel.getTileHeight();


    public Board() {
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
                tiles[i][j] = new Tile(j*TILE_HEIGHT, i*TILE_WIDTH, TILE_HEIGHT,TILE_WIDTH, tileCoordinate, null, isLightSquare);
            }
        }
    }

    public void initializePieces() {
        for (int i = 0; i < tiles.length; i++) {
            tiles[1][i].setPiece(new Pawn(false));
            tiles[6][i].setPiece(new Pawn(true));
        }
        
    }

}