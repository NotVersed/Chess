import java.awt.*;
import javax.swing.*;

public class BoardPanel extends JLayeredPane {
    private TilePanel[][] tilePanels;
    private PiecePanel[][] piecePanels;
    private Board board;

    public BoardPanel() {
        this.board = new Board();
        tilePanels = new TilePanel[board.getRows()][board.getColumns()];
        piecePanels = new PiecePanel[board.getRows()][board.getColumns()];
        setLayout(null);
        setPreferredSize(new Dimension(800, 800));
        addTilePanels();
        refreshPieces();
    }

    public void addTilePanels() {
        int rows = board.getRows();
        int cols = board.getColumns();
        int tileSize = getPreferredSize().width / cols;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Tile tile = board.getTiles()[i][j];
                TilePanel tilePanel = new TilePanel(tile.getColor());
                tilePanel.setBounds(j * tileSize, i * tileSize, tileSize, tileSize);
                tilePanels[i][j] = tilePanel;
                add(tilePanel, JLayeredPane.DEFAULT_LAYER);
            }
        }
    }

    public void refreshPieces() {
        int rows = board.getRows();
        int cols = board.getColumns();
        int tileSize = getPreferredSize().width / cols;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Piece piece = board.getPieceAt(i, j);
                if (piece != null) {
                    PiecePanel piecePanel = new PiecePanel(piece);
                    piecePanel.setBounds(j * tileSize, i * tileSize, tileSize, tileSize);
                    piecePanels[i][j] = piecePanel;
                    MouseListener listener = new MouseListener(piecePanel);
                    piecePanel.addMouseListener(listener);
                    piecePanel.addMouseMotionListener(listener);
                    add(piecePanel, JLayeredPane.PALETTE_LAYER);
                }
            }
        }
    }

    public Board getBoard() {
        return this.board;
    }
}
