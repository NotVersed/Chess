
public class Pawn extends Piece {
    public Pawn(boolean isWhite){
        super(isWhite, isWhite ? PieceImages.getWhitePawn() : PieceImages.getBlackPawn());
    }

}
