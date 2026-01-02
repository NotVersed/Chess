
public class Pawn extends Piece {
    private final int direction;
    private static final int[][] PAWN_OFFSETS = {{0, 1}, {0, 2}, {1, 1}, {-1, 1}};
    public Pawn(Coordinate coordinate, boolean whitePiece) {
        super(coordinate, whitePiece);
        direction = (this.isWhite()) ? 1 : -1;
    }

    public int getDirection(){
        return this.direction;
    }
    @Override
    protected void generatePossibleMoves() {
        // get source coordinate
        Coordinate source = this.getCoordinate();
        int x = source.getX();
        int y = source.getY();
        for(int[] offset : PAWN_OFFSETS){
            int dx = offset[0];
            int dy = offset[1] * this.direction;
            this.possibleMoves.add(new Move(source, new Coordinate(x + dx, y + dy)));
        }
        
    }

}
