import java.awt.*;
import javax.swing.*;

public class TilePanel extends JPanel {
    private final Color COLOR;

    public TilePanel(final Color COLOR) {
        this.COLOR = COLOR;
        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(COLOR);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
