
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MoveValidator {

    @SuppressWarnings("unused")
    private MoveValidator() {
    }

    ;

    public static Map<Piece, List<Move>> generateLegalMoves(Map<Coordinate, Piece> BOARDSTATE, Coordinate enPassantTarget) {
        Map<Piece, List<Move>> legalMoves = new HashMap<>();

        for (Piece piece : BOARDSTATE.values()) {
            List<Move> pseudoMoves = piece.getPossibleMoves();
            List<Move> validatedMoves;

            if (piece instanceof Pawn) {
                validatedMoves = validatePawnMoves(pseudoMoves, BOARDSTATE, enPassantTarget);
            } else if (piece instanceof Knight) {
                validatedMoves = validateKnightMoves(pseudoMoves, BOARDSTATE);
            } else if (piece instanceof Bishop || piece instanceof Rook || piece instanceof Queen) {
                validatedMoves = validateRays(pseudoMoves, BOARDSTATE);
            } else if (piece instanceof King) {
                validatedMoves = validateKingMoves(pseudoMoves, BOARDSTATE);
            } else {
                continue;
            }
            validatedMoves.addAll(castlingMoves(BOARDSTATE));
            if (!validatedMoves.isEmpty()) {
                legalMoves.put(piece, validatedMoves);
            }
        }

        return legalMoves;
    }

    private static List<Move> validateRays(List<Move> pieceMoves, Map<Coordinate, Piece> BOARDSTATE) {
        List<Move> legalMoves = new ArrayList<>();

        if (pieceMoves.isEmpty()) {
            return legalMoves;
        }

        Coordinate source = pieceMoves.get(0).getSource();
        Piece movingPiece = BOARDSTATE.get(source);

        Map<String, List<Move>> rays = new HashMap<>();

        for (Move move : pieceMoves) {
            Coordinate destination = move.getDestination();

            int dx = destination.getX() - source.getX();
            int dy = destination.getY() - source.getY();

            int dirX = Integer.signum(dx);
            int dirY = Integer.signum(dy);

            String key = dirX + "," + dirY;

            rays.computeIfAbsent(key, k -> new ArrayList<>()).add(move);
        }

        for (List<Move> ray : rays.values()) {

            ray.sort((a, b) -> {
                Coordinate da = a.getDestination();
                Coordinate db = b.getDestination();

                int distA = Math.abs(da.getX() - source.getX()) + Math.abs(da.getY() - source.getY());
                int distB = Math.abs(db.getX() - source.getX()) + Math.abs(db.getY() - source.getY());

                return Integer.compare(distA, distB);
            });

            for (Move move : ray) {
                Coordinate destination = move.getDestination();
                Piece occupying = BOARDSTATE.get(destination);

                if (occupying == null) {
                    legalMoves.add(move);
                    continue;
                }

                if (!isAlly(movingPiece, occupying)) {
                    legalMoves.add(move);
                }

                break;
            }
        }

        return legalMoves;
    }

    public static List<Move> validateKnightMoves(List<Move> pseudoLegalMoves, Map<Coordinate, Piece> BOARDSTATE) {
        List<Move> legalMoves = new ArrayList<>();
        if (pseudoLegalMoves.isEmpty()) {
            return legalMoves;
        }
        Piece knight = BOARDSTATE.get(pseudoLegalMoves.get(0).getSource());

        for (Move move : pseudoLegalMoves) {
            Coordinate destination = move.getDestination();
            Piece occupyingPiece = BOARDSTATE.get(destination);
            if (occupyingPiece == null || !isAlly(knight, occupyingPiece)) {
                legalMoves.add(move);
            }
        }

        return legalMoves;
    }

    public static List<Move> validateKingMoves(List<Move> pseudoLegalMoves, Map<Coordinate, Piece> BOARDSTATE) {
        List<Move> legalMoves = new ArrayList<>();

        if (pseudoLegalMoves.isEmpty()) {
            return legalMoves;
        }

        Piece king = BOARDSTATE.get(pseudoLegalMoves.get(0).getSource());

        for (Move move : pseudoLegalMoves) {
            Coordinate destination = move.getDestination();
            Piece occupyingPiece = BOARDSTATE.get(destination);

            if (occupyingPiece == null || !isAlly(king, occupyingPiece)) {
                legalMoves.add(move);
            }
        }

        return legalMoves;
    }

    public static List<Move> validatePawnMoves(List<Move> pseudoLegalMoves, Map<Coordinate, Piece> BOARDSTATE, Coordinate enPassantTarget) {
        List<Move> legalMoves = new ArrayList<>();

        if (pseudoLegalMoves.isEmpty()) {
            return legalMoves;
        }

        Piece piece = BOARDSTATE.get(pseudoLegalMoves.get(0).getSource());
        Pawn pawn = (Pawn) piece;
        Coordinate source = pawn.getCoordinate();
        int direction = pawn.getDirection();

        for (Move move : pseudoLegalMoves) {
            Coordinate destination = move.getDestination();
            Piece occupyingPiece = BOARDSTATE.get(destination);

            int dx = destination.getX() - source.getX();
            int dy = destination.getY() - source.getY();

            // Forward move
            if (dx == 0) {
                // One square forward
                if (dy == direction && occupyingPiece == null) {
                    legalMoves.add(move);
                } // Two squares forward
                else if (dy == 2 * direction && !pawn.hasMoved() && occupyingPiece == null) {
                    Coordinate intermediate = new Coordinate(source.getX(), source.getY() + direction);
                    if (BOARDSTATE.get(intermediate) == null) {
                        legalMoves.add(move);
                    }
                }
            } // Diagonal capture
            else if (Math.abs(dx) == 1 && dy == direction) {
                if (occupyingPiece != null && !isAlly(pawn, occupyingPiece)) {
                    legalMoves.add(move);
                } // en passant
                else if (occupyingPiece == null && enPassantTarget != null && destination.equals(enPassantTarget)) {
                    Coordinate capturedSquare = new Coordinate(destination.getX(), destination.getY() - direction);
                    Piece capturedPiece = BOARDSTATE.get(capturedSquare);
                    if (capturedPiece instanceof Pawn && !isAlly(pawn, capturedPiece)) {
                        legalMoves.add(move);
                    }
                }
            }
        }
        return legalMoves;
    }

    public static List<Move> castlingMoves(Map<Coordinate, Piece> BOARDSTATE) {
        List<Move> castlingMoves = new ArrayList<>();

        for (Piece piece : BOARDSTATE.values()) {
            if (!(piece instanceof King)) {
                continue;
            }

            King king = (King) piece;
            if (king.hasMoved()) {
                continue;
            }

            Coordinate kingPos = king.getCoordinate();
            int y = kingPos.getY();

            // ---------- King-side castling ----------
            Coordinate kingSideRookSquare = new Coordinate(kingPos.getX() + 3, y);
            Piece rook = BOARDSTATE.get(kingSideRookSquare);

            if (rook instanceof Rook && !rook.hasMoved()) {
                boolean pathClear = true;

                for (int x = kingPos.getX() + 1; x < kingSideRookSquare.getX(); x++) {
                    if (BOARDSTATE.containsKey(new Coordinate(x, y))) {
                        pathClear = false;
                        break;
                    }
                }

                if (pathClear) {
                    castlingMoves.add(
                            new Move(kingPos, new Coordinate(kingPos.getX() + 2, y))
                    );
                }
            }

            // ---------- Queen-side castling ----------
            Coordinate queenSideRookSquare = new Coordinate(kingPos.getX() - 4, y);
            rook = BOARDSTATE.get(queenSideRookSquare);

            if (rook instanceof Rook && !rook.hasMoved()) {
                boolean pathClear = true;

                for (int x = kingPos.getX() - 1; x > queenSideRookSquare.getX(); x--) {
                    if (BOARDSTATE.containsKey(new Coordinate(x, y))) {
                        pathClear = false;
                        break;
                    }
                }

                if (pathClear) {
                    castlingMoves.add(new Move(kingPos, new Coordinate(kingPos.getX() - 2, y)));
                }
            }
        }

        return castlingMoves;
    }

    private static boolean isAlly(Piece piece1, Piece piece2) {
        return piece1.isWhite() == piece2.isWhite();
    }

}
