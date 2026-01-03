public final class UndoMove {
    public final Move move;

    // moved piece (always exists)
    public final Piece movedPiece;
    public final boolean movedPieceHadMoved;

    // normal capture (may be null)
    public final Piece capturedPiece;

    // en passant
    public final Coordinate enPassantCaptureSquare;

    // castling
    public final Piece castlingRook;
    public final Coordinate castlingRookFrom;
    public final Coordinate castlingRookTo;
    public final boolean castlingRookHadMoved;

    // promotion
    public final Piece promotedFrom; 
    public final Piece promotedTo;   

    public UndoMove(
            Move move,
            Piece movedPiece,
            boolean movedPieceHadMoved,
            Piece capturedPiece,
            Coordinate enPassantCaptureSquare,
            Piece castlingRook,
            Coordinate castlingRookFrom,
            Coordinate castlingRookTo,
            boolean castlingRookHadMoved,
            Piece promotedFrom,
            Piece promotedTo
    ) {
        this.move = move;
        this.movedPiece = movedPiece;
        this.movedPieceHadMoved = movedPieceHadMoved;
        this.capturedPiece = capturedPiece;
        this.enPassantCaptureSquare = enPassantCaptureSquare;
        this.castlingRook = castlingRook;
        this.castlingRookFrom = castlingRookFrom;
        this.castlingRookTo = castlingRookTo;
        this.castlingRookHadMoved = castlingRookHadMoved;
        this.promotedFrom = promotedFrom;
        this.promotedTo = promotedTo;
    }
}
