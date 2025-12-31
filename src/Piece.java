
public abstract class Piece {

    private boolean whitePiece;
    private Coordinate coordinate;

    public Piece(Coordinate coordinate, boolean whitePiece){
        this.whitePiece = whitePiece;
        this.coordinate = coordinate;
    }
    public boolean isWhite(){
        return this.whitePiece;
    }
    public void setWhite(boolean white){
        this.whitePiece = white;
    }
    public Coordinate getCoordinate(){
        return this.coordinate;
    }
    public void setCoordinate(Coordinate coordinate){
        this.coordinate = coordinate;
    }

}
