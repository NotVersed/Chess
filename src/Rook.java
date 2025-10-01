
public class Rook extends Piece {
    public Rook(boolean isWhite){
        super(isWhite, isWhite ? PieceImages.getWhiteRook() : PieceImages.getBlackRook());
    }
}
