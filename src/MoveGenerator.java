import java.util.ArrayList;
import java.util.List;

public class MoveGenerator {

    private final BitBoard board;

    public MoveGenerator(BitBoard board) {
        this.board = board;
    }

    public List<Integer> generateLegalMoves(boolean white) {
        List<Integer> moves = new ArrayList<>();
        // TODO
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
}