

public class Bishop extends Piece{

    public Bishop(boolean isWhite){
        super(isWhite, isWhite ? PieceImages.getWhiteBishop() : PieceImages.getBlackBishop());
    }
}
