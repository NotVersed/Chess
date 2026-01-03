import java.util.ArrayList;
import java.util.List;

public abstract class Piece {

    private final boolean WHITEPIECE;
    private boolean hasMoved;
    private Coordinate coordinate;
    protected List<Move> possibleMoves;

    public Piece(Coordinate coordinate, boolean whitePiece){
        this.WHITEPIECE = whitePiece;
        this.coordinate = coordinate;
        this.possibleMoves = new ArrayList<>();
    }
    public boolean isWhite(){
        return this.WHITEPIECE;
    }
    public boolean hasMoved(){
        return this.hasMoved;
    }
    public void setPieceMoved(boolean hasMoved){
        this.hasMoved = hasMoved;
    }
    public Coordinate getCoordinate(){
        return this.coordinate;
    }
    protected void setCoordinate(Coordinate coordinate){
        this.coordinate = coordinate;
        this.hasMoved = true;
    }
    protected abstract void generatePossibleMoves();
    protected abstract void generatePossibleMovesFrom(Coordinate source);

    public List<Move> getPossibleMoves(){
        this.possibleMoves.clear();
        generatePossibleMoves();
        return List.copyOf(this.possibleMoves);
    }
    public List<Move> getPossibleMovesFrom(Coordinate source){
        this.possibleMoves.clear();
        generatePossibleMovesFrom(source);
        return List.copyOf(this.possibleMoves);
    }

}
