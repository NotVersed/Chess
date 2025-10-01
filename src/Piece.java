
public abstract class Piece {
    private boolean isWhite;

    public Piece(boolean isWhite){
        this.isWhite = isWhite;
    }
    public boolean isWhitePiece(){
        return this.isWhite;
    }
}
