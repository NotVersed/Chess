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
}