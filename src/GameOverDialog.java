import javax.swing.JOptionPane;

public final class GameOverDialog {

    private GameOverDialog() {}

    public static void show(GameResult result) {
        String message;

        switch (result) {
            case WHITE_WINS_CHECKMATE ->
                    message = "Checkmate!\nWhite wins.";
            case BLACK_WINS_CHECKMATE ->
                    message = "Checkmate!\nBlack wins.";
            case STALEMATE ->
                    message = "Stalemate.\nIt's a draw.";
            default ->
                    throw new IllegalStateException("Unknown game result");
        }

        JOptionPane.showMessageDialog(
                null,
                message,
                "Game Over",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
