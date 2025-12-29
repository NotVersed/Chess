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
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Tile tile = BOARD.getTiles()[i][j];
                TilePanel tilePanel = new TilePanel(tile.getColor());
                tilePanel.setBounds(j * tileSize, i * tileSize, tileSize, tileSize);
                TILEPANELS[i][j] = tilePanel;
                add(tilePanel, JLayeredPane.DEFAULT_LAYER);
            }
        }
    }

    private void refreshPieces() {
        int rows = BOARD.getRows();
        int cols = BOARD.getColumns();
        int tileSize = getPreferredSize().width / cols;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Piece piece = BOARD.getPieceAt(i, j);
                if (piece != null) {
                    PiecePanel piecePanel = new PiecePanel(piece);
                    piecePanel.setBounds(j * tileSize, i * tileSize, tileSize, tileSize);
                    PIECEPANELS[i][j] = piecePanel;
                    MouseListener listener = new MouseListener(piecePanel);
                    piecePanel.addMouseListener(listener);
                    piecePanel.addMouseMotionListener(listener);
                    add(piecePanel, JLayeredPane.PALETTE_LAYER);
                }
            }
        }
    }

    public Board getBoard() {
        return this.BOARD;
    }
    public PiecePanel[][] getPiecePanels(){
        return this.PIECEPANELS;
    }
}
