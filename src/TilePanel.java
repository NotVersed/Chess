import java.awt.*;
import javax.swing.*;

public class TilePanel extends JPanel {
    private Color color;

    public TilePanel(Color color) {
        this.color = color;
        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(color);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
