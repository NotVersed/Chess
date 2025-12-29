import java.awt.Point;
import java.awt.event.*;
import javax.swing.*;

public class MouseListener extends MouseAdapter {
    private final PiecePanel PIECE;
    private int offsetX, offsetY;

    public MouseListener(PiecePanel piece) {
        this.PIECE = piece;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        offsetX = e.getX();
        offsetY = e.getY();

        JLayeredPane parent = (JLayeredPane) PIECE.getParent();
        parent.setLayer(this.PIECE, JLayeredPane.DRAG_LAYER);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point mousePoint = SwingUtilities.convertPoint(this.PIECE, e.getPoint(), PIECE.getParent());
        PIECE.setLocation(mousePoint.x - offsetX, mousePoint.y - offsetY);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        BoardPanel parent = (BoardPanel) PIECE.getParent();
        parent.setLayer(this.PIECE, JLayeredPane.PALETTE_LAYER);
        Board board = parent.getBoard();
        PiecePanel[][] piecePanels = parent.getPiecePanels();

        Point boardPoint = SwingUtilities.convertPoint(this.PIECE, e.getPoint(), parent);
        int tileSize = parent.getWidth() / board.getColumns();
        int col = Math.min(board.getColumns() - 1, boardPoint.x / tileSize);
        int row = Math.min(board.getRows() - 1, boardPoint.y / tileSize);
        PiecePanel targetPiece = piecePanels[row][col];
        if (targetPiece != null) {
            parent.remove(targetPiece);
        }
        parent.getPiecePanels()[row][col] = PIECE;
        PIECE.setBounds(col * tileSize, row * tileSize, tileSize, tileSize);
        parent.repaint();
    }
}
