/*
 * The purpose of this class it to create a tile object that can hold a piece.
 */
import java.awt.*;
public class Tile {
    private Piece piece;
    private final boolean LIGHTSQUARE;
    private Color color;
    private final Coordinate COORDINATE;
    private static final Color DARK_SQUARE = new Color(118,150,86);
    private static final Color LIGHT_SQUARE = new Color(238,238,210);

    public Tile(Piece piece, boolean LIGHTSQUARE, final Coordinate COORDINATE){
        this.piece = piece;
        this.LIGHTSQUARE = LIGHTSQUARE;
        this.COORDINATE = COORDINATE;
        this.color = (LIGHTSQUARE) ? LIGHT_SQUARE : DARK_SQUARE;
    }
    public Piece getPiece(){
        return this.piece;
    }
    public boolean isLight(){
        return this.LIGHTSQUARE;
    }
    public Color getColor(){
        return this.color;
    }
    public void setPiece(Piece piece){
        this.piece = piece;
    }
    public void setColor(Color color){
        this.color = color;
    }
    public Coordinate getTileCoordinate(){
        return this.COORDINATE;
    }
}
