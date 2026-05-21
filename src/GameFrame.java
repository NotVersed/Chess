import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class GameFrame extends JFrame {

    private final BoardPanel PANEL;
    private final Engine engine;

    public GameFrame(boolean humanIsWhite, int depth) {
        BitBoard board = new BitBoard();
        Controller controller = new Controller(board);
        boolean engineIsWhite = !humanIsWhite;
        engine = new Engine(board, controller, engineIsWhite, depth);
        PANEL = new BoardPanel(board, controller, engine);
        add(PANEL);
        setTitle("Chess Game");
        setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
        SwingUtilities.invokeLater(() -> {
            PANEL.refreshTiles();
            PANEL.refreshPieces();
        });
        if (engine.isEngineTurn()) {
            SwingUtilities.invokeLater(() -> {
                int move = engine.findBestMove();
                controller.tryMove(move);
                PANEL.refreshTiles();
                PANEL.refreshPieces();
            });
        }
    }

}
