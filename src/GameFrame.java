import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameFrame extends JFrame{
    private JPanel panel;

    public GameFrame(){
        this.panel = new GamePanel();
        this.add(this.panel);
        this.setTitle("Chess Game");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

}
