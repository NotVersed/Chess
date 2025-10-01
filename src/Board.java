
public class Board {
    // chess board 8x8
    private Tile[][] tiles = new Tile[8][8];

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
                tiles[i][j] = new Tile(tileCoordinate, null, isLightSquare);
            }
        }
    }

    public void initializePieces() {
        Pawn whitePawn = new Pawn(true);
        Pawn blackPawn = new Pawn(false);
        King whiteKing = new King(true);
        King blackKing = new King(false);
        Queen whiteQueen = new Queen(true);
        Queen blackQueen = new Queen(false);
        Rook whiteRook = new Rook(true);
        Rook blackRook = new Rook(false);
        Knight whiteKnight = new Knight(true);
        Knight blackKnight = new Knight(false);
        Bishop whiteBishop = new Bishop(true);
        Bishop blackBishop = new Bishop(false);
        for (int i = 0; i < tiles.length; i++) {
            tiles[1][i].setPiece(blackPawn);
            tiles[6][i].setPiece(whitePawn);
        }
        
    }

}