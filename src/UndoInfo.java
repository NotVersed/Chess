public record UndoInfo(
    int move,
    PieceType capturedPiece,
    boolean capturedWasWhite,
    int prevEnPassantSquare,
    CastlingRights prevCastlingRights
) {}