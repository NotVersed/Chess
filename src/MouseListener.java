import java.awt.Point;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;

public class MouseListener extends MouseAdapter {

    private final PiecePanel PIECE;
    private int offsetX, offsetY;
    private int originSquare = -1;

    public MouseListener(PiecePanel piece) {
        this.PIECE = piece;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        BoardPanel parent = (BoardPanel) PIECE.getParent();
        Controller controller = parent.getController();
        Point p = SwingUtilities.convertPoint(this.PIECE, e.getPoint(), parent);
        originSquare = parent.screenToSquare(p);
        offsetX = e.getX();
        offsetY = e.getY();
        parent.setLegalMoves(controller.getLegalMoves(), originSquare);
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
        int targetSquare = parent.screenToSquare(p);

        if (originSquare != -1 && targetSquare != -1) {
            List<Integer> legalMoves = parent.getController().getLegalMoves();
            int matchedMove = -1;
            for (int legalMove : legalMoves) {
                if (Move.from(legalMove) == originSquare && Move.to(legalMove) == targetSquare) {
                    matchedMove = legalMove;
                    break;
                }
            }
            if (matchedMove != -1) {
                parent.getController().tryMove(matchedMove);
            }
        }
        parent.clearLegalMoves();
        parent.refreshPieces();
        parent.onMoveMade();

        originSquare = -1;
        offsetX = 0;
        offsetY = 0;
    }
}