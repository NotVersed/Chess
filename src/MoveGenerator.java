import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MoveGenerator {

    private final BitBoard board;

    public MoveGenerator(BitBoard board) {
        this.board = board;
    }

    public List<Integer> generateLegalMoves(boolean white) {
        List<Integer> moves = new ArrayList<>();

        // generate pseudo legal moves
        generateKnightMoves(white, moves);
        generateKingMoves(white, moves);
        generateRookMoves(white, moves);
        generateBishopMoves(white, moves);
        generateQueenMoves(white, moves);
        generatePawnMoves(white, moves);

        // apply and see if checks the king and undo
        // iterating backwards also works and is slightly faster but I wanted to use a feature I haven't used before
        // for (int i = moves.size() - 1; i >= 0; i--) { ... }
        Iterator<Integer> it = moves.iterator();
        while(it.hasNext()){
            int move = it.next();
            UndoInfo undo = board.applyMove(move);
            if(isInCheck(board, white)) it.remove();
            board.undoMove(undo);
        }

        return moves;
    }

    private void generateKnightMoves(boolean white, List<Integer> moves) {
        long knights = white ? board.getWhiteKnights() : board.getBlackKnights();
        long friendly = white ? board.whitePieces() : board.blackPieces();

        while (knights != 0) {
            int sq = Long.numberOfTrailingZeros(knights);
            knights &= knights - 1;

            long attacks = AttackTables.KNIGHT_ATTACKS[sq] & ~friendly;

            while (attacks != 0) {
                int target = Long.numberOfTrailingZeros(attacks);
                attacks &= attacks - 1;
                moves.add(Move.encode(sq, target, Move.NORMAL));
            }
        }
    }

    private void generateKingMoves(boolean white, List<Integer> moves) {
        long king = white ? board.getWhiteKing() : board.getBlackKing();
        long friendly = white ? board.whitePieces() : board.blackPieces();

        int sq = Long.numberOfTrailingZeros(king);

        long attacks = AttackTables.KING_ATTACKS[sq] & ~friendly;

        while (attacks != 0) {
            int target = Long.numberOfTrailingZeros(attacks);
            attacks &= attacks - 1;
            moves.add(Move.encode(sq, target, Move.NORMAL));
        }
    }

    private void generateRookMoves(boolean white, List<Integer> moves) {
        long rooks = white ? board.getWhiteRooks() : board.getBlackRooks();
        long friendly = white ? board.whitePieces() : board.blackPieces();
        long occupied = board.allPieces();

        while (rooks != 0) {
            int sq = Long.numberOfTrailingZeros(rooks);
            rooks &= rooks - 1;

            long attacks = AttackTables.rookAttacks(sq, occupied, friendly);

            while (attacks != 0) {
                int target = Long.numberOfTrailingZeros(attacks);
                attacks &= attacks - 1;
                moves.add(Move.encode(sq, target, Move.NORMAL));
            }
        }
    }

    private void generateBishopMoves(boolean white, List<Integer> moves) {
        long bishops = white ? board.getWhiteBishops() : board.getBlackBishops();
        long friendly = white ? board.whitePieces() : board.blackPieces();
        long occupied = board.allPieces();

        while (bishops != 0) {
            int sq = Long.numberOfTrailingZeros(bishops);
            bishops &= bishops - 1;

            long attacks = AttackTables.bishopAttacks(sq, occupied, friendly);

            while (attacks != 0) {
                int target = Long.numberOfTrailingZeros(attacks);
                attacks &= attacks - 1;
                moves.add(Move.encode(sq, target, Move.NORMAL));
            }
        }
    }

    private void generateQueenMoves(boolean white, List<Integer> moves) {
        long queens = white ? board.getWhiteQueens() : board.getBlackQueens();
        long friendly = white ? board.whitePieces() : board.blackPieces();
        long occupied = board.allPieces();

        while (queens != 0) {
            int sq = Long.numberOfTrailingZeros(queens);
            queens &= queens - 1;

            long attacks = AttackTables.queenAttacks(sq, occupied, friendly);

            while (attacks != 0) {
                int target = Long.numberOfTrailingZeros(attacks);
                attacks &= attacks - 1;
                moves.add(Move.encode(sq, target, Move.NORMAL));
            }
        }
    }

    private void generatePawnMoves(boolean white, List<Integer> moves) {
        long pawns = white ? board.getWhitePawns() : board.getBlackPawns();
        long friendly = white ? board.whitePieces() : board.blackPieces();
        long occupied = board.allPieces();
        while (pawns != 0) {
            int sq = Long.numberOfTrailingZeros(pawns);
            pawns &= pawns - 1;
            long attacks = (white) ? AttackTables.WHITE_PAWN_ATTACKS[sq] : AttackTables.BLACK_PAWN_ATTACKS[sq];
            while (attacks != 0) {
                int target = Long.numberOfTrailingZeros(attacks);
                attacks &= attacks - 1;
                if (((1L << target) & (~friendly & occupied)) != 0) {
                    addPawnMove(sq, target, Move.NORMAL, white, moves);
                }
                // en passant (won't ever bit shift negative with condition short circuiting)
                else if ((board.getEnPassantSquare() != -1) && (target == board.getEnPassantSquare())) {
                    addPawnMove(sq, target, Move.EN_PASSANT, white, moves);
                }

            }
            int squareInFrontOffset = (white) ? 8 : -8;
            int target = sq + squareInFrontOffset;
            if (((1L << target) & occupied) == 0) {
                addPawnMove(sq, target, Move.NORMAL, white, moves);
                int twoSquarePushTarget = sq + 2 * squareInFrontOffset;
                int requiredRank = (white) ? 1 : 6;
                int rank = sq / 8;
                if ((((1L << twoSquarePushTarget) & occupied) == 0) && (requiredRank == rank)) {
                    addPawnMove(sq, twoSquarePushTarget, Move.NORMAL, white, moves);
                }
            }

        }
    }
    private void addPawnMove(int from, int target, int type, boolean white, List<Integer> moves) {
        int backRank = white ? 7 : 0;
        if (type == Move.NORMAL && target / 8 == backRank) {
            moves.add(Move.encode(from, target, Move.PROMOTION, Move.PROMOTE_QUEEN));
            moves.add(Move.encode(from, target, Move.PROMOTION, Move.PROMOTE_ROOK));
            moves.add(Move.encode(from, target, Move.PROMOTION, Move.PROMOTE_BISHOP));
            moves.add(Move.encode(from, target, Move.PROMOTION, Move.PROMOTE_KNIGHT));
        } else {
            moves.add(Move.encode(from, target, type));
        }
    }

    public boolean isInCheck(BitBoard board, boolean white){
        // board info
        int kingSq = white ? Long.numberOfTrailingZeros(board.getWhiteKing()) : Long.numberOfTrailingZeros(board.getBlackKing());
        long occupied = board.allPieces();
        long friendly = white ? board.whitePieces() : board.blackPieces();


        // place imaginary piece on king square and see if it can see another piece of its kind
        long enemyRooks = white ? board.getBlackRooks() : board.getWhiteRooks();
        long enemyBishops = white ? board.getBlackBishops() : board.getWhiteBishops();
        long enemyQueens = white ? board.getBlackQueens() : board.getWhiteQueens();
        long enemyKnights = white ? board.getBlackKnights() : board.getWhiteKnights();
        long enemyPawns = white ? board.getBlackPawns() : board.getWhitePawns();
        long enemyKing = white ? board.getBlackKing() : board.getWhiteKing();
        long pawnAttacks = white ? AttackTables.WHITE_PAWN_ATTACKS[kingSq] : AttackTables.BLACK_PAWN_ATTACKS[kingSq];
        if((AttackTables.rookAttacks(kingSq, occupied, friendly) & (enemyRooks | enemyQueens)) != 0) return true;
        if((AttackTables.bishopAttacks(kingSq, occupied, friendly) & (enemyBishops | enemyQueens)) != 0) return true;
        if((AttackTables.KNIGHT_ATTACKS[kingSq] & enemyKnights) != 0) return true;
        if((pawnAttacks & enemyPawns) != 0) return true;
        if ((AttackTables.KING_ATTACKS[kingSq] & enemyKing) != 0) return true;

        return false;
    }
}