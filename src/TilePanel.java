import java.awt.*;
import javax.swing.*;

public class TilePanel extends JPanel {

    private static final Color LIGHT_SQUARE = new Color(238, 238, 210);
    private static final Color DARK_SQUARE = new Color(118, 150, 86);
    private final boolean isLight;
    private final int square;
    private final BoardPanel parent;

    public TilePanel(BoardPanel parent, int square, boolean isLight) {
        this.isLight = isLight;
        this.square = square;
        this.parent = parent;
        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(isLight ? LIGHT_SQUARE : DARK_SQUARE);
        g.fillRect(0, 0, getWidth(), getHeight());
        if (parent.isLegalMoveTarget(square)) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(new Color(160, 160, 160, 120));

            int d = Math.min(getWidth(), getHeight()) / 3;
            int x = (getWidth() - d) / 2;
            int y = (getHeight() - d) / 2;

            g2.fillOval(x, y, d, d);
            g2.dispose();
        }
    }
}