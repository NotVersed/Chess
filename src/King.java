
public class King extends Piece {
    public King(boolean isWhite){
        super(isWhite, isWhite ? PieceImages.getWhiteKing() : PieceImages.getBlackKing());
    }
}
