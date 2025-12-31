import javax.swing.ImageIcon;

public abstract class Piece {

    private boolean whitePiece;
    private ImageIcon pieceImage;
    private Coordinate coordinate;

    public Piece(Coordinate coordinate, boolean whitePiece, ImageIcon pieceImage){
        this.whitePiece = whitePiece;
        this.pieceImage = pieceImage;
        this.coordinate = coordinate;
    }
    public boolean isWhite(){
        return this.whitePiece;
    }
    public ImageIcon getPieceImage(){
        return this.pieceImage;
    }
    public void setWhite(boolean white){
        this.whitePiece = white;
    }
    public void setPieceImage(ImageIcon image){
        this.pieceImage = image;
    }
    public Coordinate getCoordinate(){
        return this.coordinate;
    }
    public void setCoordinate(Coordinate coordinate){
        this.coordinate = coordinate;
    }

}
