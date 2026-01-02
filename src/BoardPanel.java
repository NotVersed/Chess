
import java.awt.*;
import javax.swing.*;

public class BoardPanel extends JLayeredPane {

    private final TilePanel[][] TILEPANELS;
    private final PiecePanel[][] PIECEPANELS;
    private final Board BOARD;

    public BoardPanel() {
        this.BOARD = new Board();
        TILEPANELS = new TilePanel[BOARD.getRows()][BOARD.getColumns()];
        PIECEPANELS = new PiecePanel[BOARD.getRows()][BOARD.getColumns()];
        setLayout(null);
        setPreferredSize(new Dimension(800, 800));
        addTilePanels();
        refreshPieces();
    }

    private void addTilePanels() {
        int rows = BOARD.getRows();
        int cols = BOARD.getColumns();
        int tileSize = getPreferredSize().width / cols;

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {

                boolean isLight = (x + y) % 2 == 0;
                TilePanel tilePanel = new TilePanel(isLight);
                TILEPANELS[x][y] = tilePanel;

                int px = x * tileSize;
                int py = (rows - 1 - y) * tileSize;

                tilePanel.setBounds(px, py, tileSize, tileSize);
                add(tilePanel, JLayeredPane.DEFAULT_LAYER);
            }
        }
    }

    private void refreshPieces() {
        int rows = BOARD.getRows();
        int cols = BOARD.getColumns();
        int tileSize = getPreferredSize().width / cols;

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {

                Piece piece = BOARD.getPieceAt(new Coordinate(x, y));
                if (piece != null) {
                    PiecePanel piecePanel = new PiecePanel(piece);

                    int px = x * tileSize;
                    int py = (rows - 1 - y) * tileSize;

                    piecePanel.setBounds(px, py, tileSize, tileSize);

                    MouseListener listener = new MouseListener(piecePanel);
                    piecePanel.addMouseListener(listener);
                    piecePanel.addMouseMotionListener(listener);

                    add(piecePanel, JLayeredPane.PALETTE_LAYER);
                }
            }
        }
    }

    public Coordinate screenToBoard(Point p) {
        int tileSize = getWidth() / BOARD.getColumns();
        int x = p.x / tileSize;
        int y = BOARD.getRows() - 1 - (p.y / tileSize);
        return new Coordinate(x, y);
    }

    public Point boardToScreen(int x, int y) {
        int tileSize = getWidth() / BOARD.getColumns();
        return new Point(
                x * tileSize,
                (BOARD.getRows() - 1 - y) * tileSize);
    }

    public Board getBoard() {
        return this.BOARD;
    }

    public PiecePanel[][] getPiecePanels() {
        return this.PIECEPANELS;
    }
}
