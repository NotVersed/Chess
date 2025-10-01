
public class Knight extends Piece{
    public Knight(boolean isWhite){
        super(isWhite, isWhite ? PieceImages.getWhiteKnight() : PieceImages.getBlackKnight());
    }
}
