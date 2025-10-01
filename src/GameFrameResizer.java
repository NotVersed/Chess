import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;


public class GameFrameResizer extends ComponentAdapter{
    @Override
    public void componentResized(ComponentEvent e){
        GameFrame frame = (GameFrame)e.getSource();
        Dimension newDimension = frame.getSize();
        GamePanel panel = frame.getGamePanel();
        panel.resizePanel(newDimension);
        panel.repaint();
    }

}
