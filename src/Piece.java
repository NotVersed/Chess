import java.util.ArrayList;
import java.util.List;

public abstract class Piece {

    private boolean whitePiece;
    private boolean hasMoved;
    private Coordinate coordinate;
    protected List<Move> possibleMoves;

    public Piece(Coordinate coordinate, boolean whitePiece){
        this.whitePiece = whitePiece;
        this.coordinate = coordinate;
        this.possibleMoves = new ArrayList<>();
    }
    public boolean isWhite(){
        return this.whitePiece;
    }
    public boolean hasMoved(){
        return this.hasMoved;
    }
    public Coordinate getCoordinate(){
        return this.coordinate;
    }
    protected void setCoordinate(Coordinate coordinate){
        this.coordinate = coordinate;
        this.hasMoved = true;
    }
    protected abstract void generatePossibleMoves();

    public List<Move> getPossibleMoves(){
        this.possibleMoves.clear();
        generatePossibleMoves();
        return List.copyOf(this.possibleMoves);
    }

}
