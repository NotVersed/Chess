
import java.awt.Point;
import java.awt.event.*;
import javax.swing.*;

public class MouseListener extends MouseAdapter {

    private final PiecePanel PIECE;
    private int offsetX, offsetY;
    private Coordinate origin;

    public MouseListener(PiecePanel piece) {
        this.PIECE = piece;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        BoardPanel parent = (BoardPanel) PIECE.getParent();
        Point p = SwingUtilities.convertPoint(this.PIECE, e.getPoint(), parent);
        origin = parent.screenToBoard(p);
        offsetX = e.getX();
        offsetY = e.getY();
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
        parent.setLayer(PIECE, JLayeredPane.PALETTE_LAYER);

        Point p = SwingUtilities.convertPoint(PIECE, e.getPoint(), parent);
        Coordinate target = parent.screenToBoard(p);

        //parent.getController().onMoveAttempt(origin, target);
    }

}
