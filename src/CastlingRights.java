public record CastlingRights(boolean whiteKingside, boolean whiteQueenside, boolean blackKingside,
        boolean blackQueenside) {
    public CastlingRights withoutWhiteKingside() {
        return new CastlingRights(false, whiteQueenside, blackKingside, blackQueenside);
    }

    public CastlingRights withoutWhiteQueenside() {
        return new CastlingRights(whiteKingside, false, blackKingside, blackQueenside);
    }

    public CastlingRights withoutBlackKingside() {
        return new CastlingRights(whiteKingside, whiteQueenside, false, blackQueenside);
    }

    public CastlingRights withoutBlackQueenside() {
        return new CastlingRights(whiteKingside, whiteQueenside, blackKingside, false);
    }

    public CastlingRights withoutWhite() {
        return new CastlingRights(false, false, blackKingside, blackQueenside);
    }

    public CastlingRights withoutBlack() {
        return new CastlingRights(whiteKingside, whiteQueenside, false, false);
    }
}
