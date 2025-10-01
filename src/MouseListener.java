import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseListener extends MouseAdapter {

    private Point dragPoint;          // Current mouse position during drag
    private Piece draggedPiece;       // The piece currently being dragged
    private Tile sourceTile;          // The tile the piece came from

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() != MouseEvent.BUTTON1) return;

        GamePanel panel = (GamePanel) e.getComponent();
        int x = e.getX();
        int y = e.getY();

        // Determine which tile was clicked
        int col = Math.min(x / panel.getTileWidth(), 7);
        int row = Math.min(y / panel.getTileHeight(), 7);

        Tile[][] tiles = panel.getBoard().getTiles();
        sourceTile = tiles[row][col];
        draggedPiece = sourceTile.getPiece();

        if (draggedPiece != null) {
            sourceTile.setPiece(null); // Remove piece from the board while dragging
            dragPoint = e.getPoint();
            panel.repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (draggedPiece == null) return;
        dragPoint = e.getPoint();
        GamePanel panel = (GamePanel) e.getComponent();
        panel.repaint(); // The panel will draw the dragged piece at dragPoint
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (draggedPiece == null) return;

        GamePanel panel = (GamePanel) e.getComponent();
        int x = e.getX();
        int y = e.getY();

        // Determine target tile
        int col = Math.min(x / panel.getTileWidth(), 7);
        int row = Math.min(y / panel.getTileHeight(), 7);

        Tile[][] tiles = panel.getBoard().getTiles();
        Tile targetTile = tiles[row][col];

        // Place the piece on the target tile (can later check legal moves)
        targetTile.setPiece(draggedPiece);

        // Clear dragging state
        draggedPiece = null;
        sourceTile = null;
        dragPoint = null;

        panel.repaint();
    }

    // Optional getter for GamePanel.draw to render the piece while dragging
    public Piece getDraggedPiece() {
        return draggedPiece;
    }

    public Point getDragPoint() {
        return dragPoint;
    }
}
