public class Move {
    private final MoveType MOVETYPE;
    private final Tile SOURCE;
    private final Tile DESTINATION;
    public Move(final MoveType MOVETYPE, final Tile SOURCE, final Tile DESTINATION){
        this.MOVETYPE = MOVETYPE;
        this.SOURCE = SOURCE;
        this.DESTINATION = DESTINATION;
    }
}
