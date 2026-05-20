import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import javax.swing.*;

public class BoardPanel extends JLayeredPane {

    private final TilePanel[][] TILEPANELS;
    private final BitBoard BOARD;
    private final Controller CONTROLLER;
    private final Set<Integer> legalMoveTargets = new HashSet<>();
    private final Engine ENGINE;

    public BoardPanel(BitBoard board, Controller controller, Engine engine) {
        this.ENGINE = engine;
        this.CONTROLLER = controller;
        this.BOARD = board;
        TILEPANELS = new TilePanel[8][8];
        setLayout(null);
        setPreferredSize(new Dimension(800, 800));
        addTilePanels();
        refreshPieces();
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                refreshTiles();
                refreshPieces();
            }
        });
    }

    private void addTilePanels() {
        int tileSize = Math.min(getWidth(), getHeight()) / 8;
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                boolean isLight = (x + y) % 2 == 0;
                int square = BitBoard.toIndex(x, y);
                TilePanel tilePanel = new TilePanel(this, square, isLight);
                TILEPANELS[x][y] = tilePanel;
                int px = x * tileSize;
                int py = (7 - y) * tileSize;
                tilePanel.setBounds(px, py, tileSize, tileSize);
                add(tilePanel, JLayeredPane.DEFAULT_LAYER);
            }
        }
    }

    public void refreshTiles() {
        int tileSize = Math.min(getWidth(), getHeight()) / 8;
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                int px = x * tileSize;
                int py = (7 - y) * tileSize;
                TILEPANELS[x][y].setBounds(px, py, tileSize, tileSize);
            }
        }
        repaint();
    }

    public final void refreshPieces() {
        for (Component c : getComponents()) {
            if (c instanceof PiecePanel)
                remove(c);
        }

        int tileSize = Math.min(getWidth(), getHeight()) / 8;

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                int square = BitBoard.toIndex(x, y);
                PieceInfo piece = BOARD.getPieceAt(square);
                if (piece != null) {
                    PiecePanel piecePanel = new PiecePanel(piece);
                    int px = x * tileSize;
                    int py = (7 - y) * tileSize;
                    piecePanel.setBounds(px, py, tileSize, tileSize);
                    MouseListener listener = new MouseListener(piecePanel);
                    piecePanel.addMouseListener(listener);
                    piecePanel.addMouseMotionListener(listener);
                    add(piecePanel, JLayeredPane.PALETTE_LAYER);
                }
            }
        }
        revalidate();
        repaint();
    }

    public void setLegalMoves(List<Integer> moves, int sourceSquare) {
        legalMoveTargets.clear();
        for (int move : moves) {
            if (Move.from(move) == sourceSquare) {
                legalMoveTargets.add(Move.to(move));
            }
        }
        repaint();
    }

    public void onMoveMade() {
        if (ENGINE.isEngineTurn()) {
            SwingWorker<Integer, Void> worker = new SwingWorker<>() {
                @Override
                protected Integer doInBackground() {
                    return ENGINE.findBestMove();
                }

                @Override
                @SuppressWarnings("CallToPrintStackTrace")
                protected void done() {
                    try {
                        int move = get();
                        CONTROLLER.tryMove(move);
                        refreshTiles();
                        refreshPieces();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            };
            worker.execute();
        }
    }

    public void clearLegalMoves() {
        legalMoveTargets.clear();
        repaint();
    }

    public boolean isLegalMoveTarget(int square) {
        return legalMoveTargets.contains(square);
    }

    public int screenToSquare(Point p) {
        int tileSize = Math.min(getWidth(), getHeight()) / 8;
        int x = p.x / tileSize;
        int y = 7 - (p.y / tileSize);
        if (x < 0 || x >= 8 || y < 0 || y >= 8)
            return -1;
        return BitBoard.toIndex(x, y);
    }

    public BitBoard getBoard() {
        return this.BOARD;
    }

    public Controller getController() {
        return this.CONTROLLER;
    }
}