public final class AttackTables {
    private AttackTables() {
    }

    public static final long[] KNIGHT_ATTACKS = new long[64];
    public static final long[] KING_ATTACKS = new long[64];
    public static final long[] WHITE_PAWN_ATTACKS = new long[64];
    public static final long[] BLACK_PAWN_ATTACKS = new long[64];

    static {
        initKnightAttacks();
        initKingAttacks();
        initPawnAttacks();
    }

    private static void initKnightAttacks() {
        int[] offsets = { -17, -15, -10, -6, 6, 10, 15, 17 };
        for (int sq = 0; sq < 64; sq++) {
            long attacks = 0L;
            int srcFile = sq % 8;
            for (int offset : offsets) {
                int target = sq + offset;
                if (target >= 0 && target < 64) {
                    int targetFile = target % 8;
                    if (Math.abs(srcFile - targetFile) <= 2) {
                        attacks |= (1L << target);
                    }
                }
            }
            KNIGHT_ATTACKS[sq] = attacks;
        }
    }

    private static void initKingAttacks() {
        int[] offsets = { -9, -8, -7, -1, 1, 7, 8, 9 };
        for (int sq = 0; sq < 64; sq++) {
            long attacks = 0L;
            int srcFile = sq % 8;
            for (int offset : offsets) {
                int target = sq + offset;
                if (target >= 0 && target < 64) {
                    int targetFile = target % 8;
                    if (Math.abs(srcFile - targetFile) <= 1) {
                        attacks |= (1L << target);
                    }
                }
            }
            KING_ATTACKS[sq] = attacks;
        }
    }

    private static void initPawnAttacks() {
        int[] offsets = { 7, 9 };
        for (int sq = 0; sq < 64; sq++) {
            long white_attacks = 0L;
            long black_attacks = 0L;
            int srcFile = sq % 8;
            for (int offset : offsets) {
                int white_target = sq + offset;
                int black_target = sq - offset;
                if (white_target >= 0 && white_target < 64) {
                    int targetFile = white_target % 8;
                    if (Math.abs(srcFile - targetFile) <= 1) {
                        white_attacks |= (1L << white_target);
                    }
                }
                if (black_target >= 0 && black_target < 64) {
                    int targetFile = black_target % 8;
                    if (Math.abs(srcFile - targetFile) <= 1) {
                        black_attacks |= (1L << white_target);
                    }
                }
            }
            WHITE_PAWN_ATTACKS[sq] = white_attacks;
            BLACK_PAWN_ATTACKS[sq] = black_attacks;
        }
    }

    public static long rookAttacks(int sq, long occupied, long friendly) {
        long attacks = 0L;

        int[] directions = { 8, -8, 1, -1 };
        for (int dir : directions) {
            int target = sq + dir;
            while (target >= 0 && target < 64) {
                // check file wrap for east/west
                if (dir == 1 && target % 8 == 0)
                    break;
                if (dir == -1 && target % 8 == 7)
                    break;

                long targetBit = 1L << target;
                if ((friendly & targetBit) != 0)
                    break; // friendly piece, stop
                attacks |= targetBit;
                if ((occupied & targetBit) != 0)
                    break; // enemy piece, add then stop
                target += dir;
            }
        }
        return attacks;
    }

    public static long bishopAttacks(int sq, long occupied, long friendly) {
        long attacks = 0L;

        int[] directions = { 9, -9, 7, -7 };
        for (int dir : directions) {
            int target = sq + dir;
            while (target >= 0 && target < 64) {
                if (dir == 9 && target % 8 == 0)
                    break;
                if (dir == -7 && target % 8 == 0)
                    break;
                if (dir == 7 && target % 8 == 7)
                    break;
                if (dir == -9 && target % 8 == 7)
                    break;

                long targetBit = 1L << target;
                if ((friendly & targetBit) != 0)
                    break;
                attacks |= targetBit;
                if ((occupied & targetBit) != 0)
                    break;
                target += dir;
            }
        }
        return attacks;
    }

    public static long queenAttacks(int sq, long occupied, long friendly) {
        return rookAttacks(sq, occupied, friendly) | bishopAttacks(sq, occupied, friendly);
    }
}
