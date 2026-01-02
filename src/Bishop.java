
public class Bishop extends Piece{

    private static final int[][] BISHOP_OFFSETS = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
    public Bishop(Coordinate coordinate, boolean whitePiece){
        super(coordinate, whitePiece);
    }

    @Override
    protected void generatePossibleMoves() {
        Coordinate source = this.getCoordinate();
        int x = source.getX();
        int y = source.getY();
        int boardDimension = 8;
        for(int i = 1; i < boardDimension; i++){
            for(int[] offset : BISHOP_OFFSETS){
                int dx = offset[0] * i;
                int dy = offset[1] * i;
                this.possibleMoves.add(new Move(source, new Coordinate(x + dx, y + dy)));
            }
        }
    }
}
