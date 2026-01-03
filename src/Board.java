import java.util.HashMap;
import java.util.Map;

public class Board {

    private final Map<Coordinate, Piece> BOARDSTATE;
    private final int ROWS;
    private final int COLUMNS;

    public Board() {
        this(8, 8);
    }

    public Board(int rows, int columns) {
        this.ROWS = rows;
        this.COLUMNS = columns;
        this.BOARDSTATE = new HashMap<>();
        initializeBoard();
    }

    private void initializeBoard() {
        // Pawns
        for (int col = 0; col < COLUMNS; col++) {
            BOARDSTATE.put(new Coordinate(col, 1), new Pawn(new Coordinate(col, 1), true));
            BOARDSTATE.put(new Coordinate(col, 6), new Pawn(new Coordinate(col, 6), false));
        }

        // Rooks
        BOARDSTATE.put(new Coordinate(0, 0), new Rook(new Coordinate(0, 0), true));
        BOARDSTATE.put(new Coordinate(7, 0), new Rook(new Coordinate(7, 0), true));
        BOARDSTATE.put(new Coordinate(0, 7), new Rook(new Coordinate(0, 7), false));
        BOARDSTATE.put(new Coordinate(7, 7), new Rook(new Coordinate(7, 7), false));

        // Knights
        BOARDSTATE.put(new Coordinate(1, 0), new Knight(new Coordinate(1, 0), true));
        BOARDSTATE.put(new Coordinate(6, 0), new Knight(new Coordinate(6, 0), true));
        BOARDSTATE.put(new Coordinate(1, 7), new Knight(new Coordinate(1, 7), false));
        BOARDSTATE.put(new Coordinate(6, 7), new Knight(new Coordinate(6, 7), false));

        // Bishops
        BOARDSTATE.put(new Coordinate(2, 0), new Bishop(new Coordinate(2, 0), true));
        BOARDSTATE.put(new Coordinate(5, 0), new Bishop(new Coordinate(5, 0), true));
        BOARDSTATE.put(new Coordinate(2, 7), new Bishop(new Coordinate(2, 7), false));
        BOARDSTATE.put(new Coordinate(5, 7), new Bishop(new Coordinate(5, 7), false));

        // Queens
        BOARDSTATE.put(new Coordinate(3, 0), new Queen(new Coordinate(3, 0), true));
        BOARDSTATE.put(new Coordinate(3, 7), new Queen(new Coordinate(3, 7), false));

        // Kings
        BOARDSTATE.put(new Coordinate(4, 0), new King(new Coordinate(4, 0), true));
        BOARDSTATE.put(new Coordinate(4, 7), new King(new Coordinate(4, 7), false));
    }

    public UndoMove applyMove(Move move) {
        Coordinate source = move.getSource();
        Coordinate destination = move.getDestination();

        Piece movedPiece = BOARDSTATE.get(source);
        Piece capturedPiece = BOARDSTATE.get(destination);

        boolean movedPieceHadMoved = movedPiece.hasMoved();
        Coordinate enPassantCaptureSquare = null;

        // --- en passant ---
        if (movedPiece instanceof Pawn && capturedPiece == null && source.getX() != destination.getX()) {
            int direction = ((Pawn) movedPiece).getDirection();
            enPassantCaptureSquare = new Coordinate(destination.getX(), destination.getY() - direction);
            capturedPiece = BOARDSTATE.get(enPassantCaptureSquare);
            BOARDSTATE.remove(enPassantCaptureSquare);
        }

        // --- castling ---
        Piece castlingRook = null;
        Coordinate rookFrom = null;
        Coordinate rookTo = null;
        boolean rookHadMoved = false;

        if (movedPiece instanceof King && Math.abs(destination.getX() - source.getX()) == 2) {
            int y = source.getY();
            boolean kingSide = destination.getX() > source.getX();

            rookFrom = kingSide ? new Coordinate(source.getX() + 3, y) : new Coordinate(source.getX() - 4, y);
            rookTo = kingSide ? new Coordinate(source.getX() + 1, y) : new Coordinate(source.getX() - 1, y);

            castlingRook = BOARDSTATE.get(rookFrom);
            rookHadMoved = castlingRook.hasMoved();

            BOARDSTATE.remove(rookFrom);
            castlingRook.setCoordinate(rookTo);
            castlingRook.setPieceMoved(true);
            BOARDSTATE.put(rookTo, castlingRook);
        }

        // --- remove source ---
        BOARDSTATE.remove(source);

        // --- remove normal capture ---
        if (capturedPiece != null && enPassantCaptureSquare == null) {
            BOARDSTATE.remove(destination);
        }

        // --- promotion ---
        Piece promotedFrom = null;
        Piece promotedTo = null;

        if (movedPiece instanceof Pawn) {
            int lastRank = movedPiece.isWhite() ? 7 : 0;
            if (destination.getY() == lastRank) {
                promotedFrom = movedPiece;
                promotedTo = new Queen(destination, movedPiece.isWhite());
                movedPiece = promotedTo;
            }
        }

        // --- place moved (or promoted) piece ---
        movedPiece.setCoordinate(destination);
        movedPiece.setPieceMoved(true);
        BOARDSTATE.put(destination, movedPiece);

        return new UndoMove(
                move,
                movedPiece,
                movedPieceHadMoved,
                capturedPiece,
                enPassantCaptureSquare,
                castlingRook,
                rookFrom,
                rookTo,
                rookHadMoved,
                promotedFrom,
                promotedTo);
    }

    public void undoMove(UndoMove undo) {

        // remove promoted piece
        if (undo.promotedTo != null) {
            BOARDSTATE.remove(undo.move.getDestination());
            BOARDSTATE.put(undo.move.getSource(), undo.promotedFrom);
        }

        // undo castling rook
        if (undo.castlingRook != null) {
            BOARDSTATE.remove(undo.castlingRookTo);
            BOARDSTATE.put(undo.castlingRookFrom, undo.castlingRook);
            undo.castlingRook.setPieceMoved(undo.castlingRookHadMoved);
        }

        // undo moved piece
        BOARDSTATE.remove(undo.move.getDestination());
        BOARDSTATE.put(undo.move.getSource(), undo.movedPiece);
        undo.movedPiece.setPieceMoved(undo.movedPieceHadMoved);

        // restore captures
        if (undo.enPassantCaptureSquare != null) {
            BOARDSTATE.put(undo.enPassantCaptureSquare, undo.capturedPiece);
        } else if (undo.capturedPiece != null) {
            BOARDSTATE.put(undo.move.getDestination(), undo.capturedPiece);
        }
    }

    public boolean inBounds(Coordinate c) {
        return c.getX() >= 0 && c.getX() < ROWS
                && c.getY() >= 0 && c.getY() < COLUMNS;
    }

    public boolean isOccupied(Coordinate c) {
        return BOARDSTATE.containsKey(c);
    }

    public Piece getPieceAt(Coordinate c) {
        return BOARDSTATE.get(c);
    }

    public int getRows() {
        return ROWS;
    }

    public int getColumns() {
        return COLUMNS;
    }

    public Map<Coordinate, Piece> getBoardState() {
        return Map.copyOf(this.BOARDSTATE);
    }
}
