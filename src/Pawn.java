
public class Pawn extends Piece {

    public Pawn(Coordinate coordinate, boolean whitePiece) {
        super(coordinate, whitePiece);
    }

    @Override
    protected void generatePossibleMoves() {
        // direction based on color white moves up black moves down
        int direction = (this.isWhite()) ? 1 : -1;
        // get source coordinate
        Coordinate source = this.getCoordinate();
        int x = source.getX();
        int y = source.getY();
        // add move 1 forward
        this.possibleMoves.add(new Move(source, new Coordinate(x, y + direction)));
        // add move 2 forward only if pawn hasn't moved, no need to make controller
        // validate this type of move
        // when piece can easily validate if it has moved or not
        if (!this.hasMoved())
            this.possibleMoves.add(new Move(source, new Coordinate(x, y + (2 * direction))));
        // add both directional captures as possible moves and controller can validate
        // based on occupancy and color
        this.possibleMoves.add(new Move(source, new Coordinate(x + 1, y + direction)));
        this.possibleMoves.add(new Move(source, new Coordinate(x - 1, y + direction)));
    }

}
