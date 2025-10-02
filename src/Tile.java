/*
 * The purpose of this class it to create a tile object that can hold a piece.
 */
import java.awt.*;
public class Tile {
    private Piece piece;
    private boolean lightSquare;
    private Color color;
    private static final Color DARK_SQUARE = new Color(118,150,86);
    private static final Color LIGHT_SQUARE = new Color(238,238,210);

    public Tile(Piece piece, boolean lightSquare){
        this.piece = piece;
        this.lightSquare = lightSquare;
        this.color = (lightSquare) ? LIGHT_SQUARE : DARK_SQUARE;
    }
    public Piece getPiece(){
        return this.piece;
    }
    public boolean isLight(){
        return this.lightSquare;
    }
    public Color getColor(){
        return this.color;
    }
    public void setPiece(Piece piece){
        this.piece = piece;
    }
    public void setLightSquare(boolean lightSquare){
        this.lightSquare = lightSquare;
    }
    public void setColor(Color color){
        this.color = color;
    }
}
