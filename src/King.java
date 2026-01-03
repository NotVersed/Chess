
public class King extends Piece{
    
    private static final int[][] KING_OFFSETS = {{1,1}, {1, -1}, {-1, 1}, {-1, -1}, {1, 0}, {-1, 0}, {0, -1}, {0, 1}, {2, 0}, {-2, 0}};
    public King(Coordinate coordinate, boolean whitePiece){
        super(coordinate, whitePiece);
    }

    @Override
    protected void generatePossibleMoves() {
        Coordinate source = this.getCoordinate();
        int x = source.getX();
        int y = source.getY();
        for(int[] offset : KING_OFFSETS){
            int dx = offset[0];
            int dy = offset[1];
            this.possibleMoves.add(new Move(source, new Coordinate(x + dx, y + dy)));
        }
    }
    @Override
    protected void generatePossibleMovesFrom(Coordinate source) {
        int x = source.getX();
        int y = source.getY();
        for(int[] offset : KING_OFFSETS){
            int dx = offset[0];
            int dy = offset[1];
            this.possibleMoves.add(new Move(source, new Coordinate(x + dx, y + dy)));
        }
    }
}
