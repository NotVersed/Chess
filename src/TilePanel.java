import java.awt.*;
import javax.swing.*;

public class TilePanel extends JPanel {

    private static final Color LIGHT_SQUARE = new Color(238, 238, 210);
    private static final Color DARK_SQUARE  = new Color(118, 150, 86);
    private final boolean isLight;

    public TilePanel(boolean isLight) {
        this.isLight = isLight;
        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(isLight ? LIGHT_SQUARE : DARK_SQUARE);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
