
public class Queen extends Piece{
    public Queen(boolean isWhite){
        super(isWhite, isWhite ? PieceImages.getWhiteQueen() : PieceImages.getBlackQueen());
    }
}
