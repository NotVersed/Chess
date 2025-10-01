
public class Tile {
    private Coordinate coordinate;
    private Piece piece;
    private boolean lightSquare;
    public Tile(Coordinate coordinate, Piece piece, boolean lightSquare){
        this.coordinate = coordinate;
        this.piece = piece;
        this.lightSquare = lightSquare;
    }
    public int getXCoordinate(){
        return this.coordinate.getX();
    }
    public int getYCoordinate(){
        return this.coordinate.getY();
    }
    public boolean isLightSquare(){
        return lightSquare;
    }
    public Piece getPiece(){
        return piece;
    }
    public void setPiece(Piece piece){
        this.piece = piece;
    }

}
