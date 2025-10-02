import javax.swing.ImageIcon;

public abstract class Piece {

    private boolean whitePiece;
    private ImageIcon pieceImage;

    public Piece(boolean whitePiece, ImageIcon pieceImage){
        this.whitePiece = whitePiece;
        this.pieceImage = pieceImage;
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

}
