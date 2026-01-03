
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class MoveValidator {

    private final Board BOARD;
    private Coordinate enPassantTarget;
    private Map<Coordinate, Piece> boardState;
    Set<Coordinate> whiteControlled;
    Set<Coordinate> blackControlled;

    public MoveValidator(Board BOARD, Coordinate enPassantTarget) {
        this.BOARD = BOARD;
        this.enPassantTarget = enPassantTarget;
        this.boardState = BOARD.getBoardState();
        whiteControlled = new HashSet<>();
        blackControlled = new HashSet<>();
    };

    private void updateBoardState() {
        this.boardState = BOARD.getBoardState();
    }

    // only derive state based on the boardState do not use Piece.getCoordinate() it
    // is
    // only for the piece to determine its own legal moves
    public Map<Piece, List<Move>> generateLegalMoves() {
        Map<Piece, List<Move>> legalMoves = new HashMap<>();

        for (Piece piece : boardState.values()) {
            List<Move> pseudoMoves = piece.getPossibleMoves();
            List<Move> validatedMoves;

            if (piece instanceof Pawn) {
                validatedMoves = validatePawnMoves(pseudoMoves);
            } else if (piece instanceof Knight) {
                validatedMoves = validateKnightMoves(pseudoMoves);
            } else if (piece instanceof Bishop || piece instanceof Rook || piece instanceof Queen) {
                validatedMoves = validateRays(pseudoMoves);
            } else if (piece instanceof King) {
                validatedMoves = validateKingMoves(pseudoMoves);
                King king = (King) piece;
                validatedMoves.addAll(castlingMoves(king));
            } else {
                continue;
            }
            if (!validatedMoves.isEmpty()) {
                legalMoves.put(piece, validatedMoves);
            }
        }

        return legalMoves;
    }

    private List<Move> validateRays(List<Move> pieceMoves) {
        List<Move> legalMoves = new ArrayList<>();

        if (pieceMoves.isEmpty()) {
            return legalMoves;
        }

        Coordinate source = pieceMoves.get(0).getSource();
        Piece movingPiece = boardState.get(source);

        Map<String, List<Move>> rays = new HashMap<>();

        for (Move move : pieceMoves) {
            Coordinate destination = move.getDestination();
            if (!BOARD.inBounds(destination))
                continue;

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
                Piece occupying = boardState.get(destination);

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

    private List<Move> validateKnightMoves(List<Move> pseudoLegalMoves) {
        List<Move> legalMoves = new ArrayList<>();
        if (pseudoLegalMoves.isEmpty()) {
            return legalMoves;
        }
        Piece knight = boardState.get(pseudoLegalMoves.get(0).getSource());

        for (Move move : pseudoLegalMoves) {
            Coordinate destination = move.getDestination();
            if (!BOARD.inBounds(destination))
                continue;
            Piece occupyingPiece = boardState.get(destination);
            if (occupyingPiece == null || !isAlly(knight, occupyingPiece)) {
                legalMoves.add(move);
            }
        }

        return legalMoves;
    }

    private List<Move> validateKingMoves(List<Move> pseudoLegalMoves) {
        List<Move> legalMoves = new ArrayList<>();

        if (pseudoLegalMoves.isEmpty()) {
            return legalMoves;
        }

        Piece king = boardState.get(pseudoLegalMoves.get(0).getSource());

        for (Move move : pseudoLegalMoves) {
            Coordinate destination = move.getDestination();
            if (!BOARD.inBounds(destination))
                continue;
            Piece occupyingPiece = boardState.get(destination);

            if (occupyingPiece == null || !isAlly(king, occupyingPiece)) {
                legalMoves.add(move);
            }
        }

        return legalMoves;
    }

    private List<Move> validatePawnMoves(List<Move> pseudoLegalMoves) {
        List<Move> legalMoves = new ArrayList<>();

        if (pseudoLegalMoves.isEmpty()) {
            return legalMoves;
        }

        Piece piece = boardState.get(pseudoLegalMoves.get(0).getSource());
        Pawn pawn = (Pawn) piece;
        Coordinate source = pseudoLegalMoves.get(0).getSource();
        int direction = pawn.getDirection();

        for (Move move : pseudoLegalMoves) {
            Coordinate destination = move.getDestination();
            if (!BOARD.inBounds(destination))
                continue;
            Piece occupyingPiece = boardState.get(destination);

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
                    if (boardState.get(intermediate) == null) {
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
                    Piece capturedPiece = boardState.get(capturedSquare);
                    if (capturedPiece instanceof Pawn && !isAlly(pawn, capturedPiece)) {
                        legalMoves.add(move);
                    }
                }
            }
        }
        return legalMoves;
    }

    private List<Move> castlingMoves(King king) {
        List<Move> castlingMoves = new ArrayList<>();
        Coordinate coordinate = null;
        if (boardState.get(king.getCoordinate()) == king) {
            coordinate = king.getCoordinate();
        } else {
            for (Coordinate c : boardState.keySet()) {
                Piece piece = boardState.get(c);
                if (piece != king)
                    continue;
                coordinate = c;
            }
        }
        if (king.hasMoved()) {
            return castlingMoves;
        }

        int y = coordinate.getY();

        // ---------- King-side castling ----------
        Coordinate kingSideRookSquare = new Coordinate(coordinate.getX() + 3, y);
        Piece rook = boardState.get(kingSideRookSquare);

        if (rook instanceof Rook && !rook.hasMoved()) {
            boolean pathClear = true;

            for (int x = coordinate.getX() + 1; x < kingSideRookSquare.getX(); x++) {
                if (boardState.containsKey(new Coordinate(x, y))) {
                    pathClear = false;
                    break;
                }
            }

            if (pathClear) {
                castlingMoves.add(new Move(coordinate, new Coordinate(coordinate.getX() + 2, y)));
            }
        }

        // ---------- Queen-side castling ----------
        Coordinate queenSideRookSquare = new Coordinate(coordinate.getX() - 4, y);
        rook = boardState.get(queenSideRookSquare);

        if (rook instanceof Rook && !rook.hasMoved()) {
            boolean pathClear = true;

            for (int x = coordinate.getX() - 1; x > queenSideRookSquare.getX(); x--) {
                if (boardState.containsKey(new Coordinate(x, y))) {
                    pathClear = false;
                    break;
                }
            }

            if (pathClear) {
                castlingMoves.add(new Move(coordinate, new Coordinate(coordinate.getX() - 2, y)));
            }
        }
        return castlingMoves;
    }

    private boolean isAlly(Piece piece1, Piece piece2) {
        return piece1.isWhite() == piece2.isWhite();
    }

    private King[] getKings() {
        King[] kings = new King[2];
        for (Coordinate c : boardState.keySet()) {
            if (boardState.get(c) instanceof King) {
                King king = (King) boardState.get(c);
                if (king.isWhite())
                    kings[0] = king;
                else {
                    kings[1] = king;
                }
            }
            if (kings[0] != null && kings[1] != null)
                return kings;
        }
        return kings;
    }

    private boolean wouldLeaveKingInCheck() {
        return false;
    }

    private void generateControlledSquares() {
        whiteControlled.clear();
        blackControlled.clear();
        for (Map.Entry<Coordinate, Piece> entry : boardState.entrySet()) {
            Coordinate source = entry.getKey();
            Piece piece = entry.getValue();
            addControlledSquares(piece, source);
        }
    }

    private void addControlledSquares(Piece piece, Coordinate source) {
        if (piece instanceof Pawn) {
            Pawn pawn = (Pawn) piece;
            addPawnControlledSquares(pawn, source);
        } else if (piece instanceof Queen || piece instanceof Rook || piece instanceof Bishop) {
            addSlidingPieceControlledSquare(piece, source);
        } else if (piece instanceof Knight) {
            Knight knight = (Knight)piece;
            addKnightControlledSquares(knight, source);
        } else if (piece instanceof King) {
            King king = (King)piece;
            addKingControlledSquare(king, source);
        } else {
            throw new IllegalStateException("Piece type unknown: " + piece.getClass());
        }
    }

    private void addPawnControlledSquares(Pawn pawn, Coordinate source) {
        int direction = pawn.getDirection();
        Set<Coordinate> target = pawn.isWhite() ? whiteControlled : blackControlled;
        Coordinate controlledLeft = new Coordinate(source.getX() - 1, source.getY() + direction);
        Coordinate controlledRight = new Coordinate(source.getX() + 1, source.getY() + direction);
        if(BOARD.inBounds(controlledLeft))target.add(controlledLeft);
        if(BOARD.inBounds(controlledRight))target.add(controlledRight);
    }
    private void addKnightControlledSquares(Knight knight, Coordinate source){
        Set<Coordinate> target = knight.isWhite() ? whiteControlled : blackControlled;
        List<Move> possibleKnightMoves = knight.getPossibleMovesFrom(source);
        for(Move move : possibleKnightMoves){
            Coordinate coordinate = move.getDestination();
            if(BOARD.inBounds(coordinate))target.add(coordinate);
        }
    }
    private void addKingControlledSquare(King king, Coordinate source){
        Set<Coordinate> target = king.isWhite() ? whiteControlled : blackControlled;
        List<Move> possibleKingMoves = king.getPossibleMovesFrom(source);
        for(Move move : possibleKingMoves){
            int dx = Math.abs(move.getSource().getX() - move.getDestination().getX());
            if(dx == 2) continue;
            Coordinate coordinate = move.getDestination();
            if(BOARD.inBounds(coordinate))target.add(coordinate);
        }
    }
    private void addSlidingPieceControlledSquare(Piece piece, Coordinate source){
        Set<Coordinate> target = piece.isWhite() ? whiteControlled : blackControlled;
        List<Move> possibleMoves = validateRays(piece.getPossibleMovesFrom(source));
        for(Move move: possibleMoves){
            target.add(move.getDestination());
        }
    }

}
