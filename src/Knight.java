
public class Knight extends Piece {

    private static final int[][] KNIGHT_OFFSETS = { { 1, 2 }, { 2, 1 },
            { 2, -1 }, { 1, -2 }, { -1, -2 }, { -2, -1 }, { -2, 1 }, { -1, 2 }
    };

    public Knight(Coordinate coordinate, boolean whitePiece) {
        super(coordinate, whitePiece);
    }

    @Override
    protected void generatePossibleMoves() {
        Coordinate source = this.getCoordinate();
        int x = source.getX();
        int y = source.getY();
        for (int[] offset : KNIGHT_OFFSETS) {
            int dx = offset[0];
            int dy = offset[1];
            possibleMoves.add(new Move(source, new Coordinate(x + dx, y + dy)));
        }
    }
    @Override
    protected void generatePossibleMovesFrom(Coordinate source) {
        int x = source.getX();
        int y = source.getY();
        for (int[] offset : KNIGHT_OFFSETS) {
            int dx = offset[0];
            int dy = offset[1];
            possibleMoves.add(new Move(source, new Coordinate(x + dx, y + dy)));
        }
    }
}
