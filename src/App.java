import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame dummy = new JFrame();
            GameSetupDialog dialog = new GameSetupDialog(dummy);
            if (dialog.isConfirmed()) {
                GameFrame frame = new GameFrame(dialog.humanIsWhite(), dialog.getDepth());
            } else {
                System.exit(0);
            }
        });
    }
}