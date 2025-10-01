import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameFrame extends JFrame{
    private JPanel panel;

    public GameFrame(){
        this.panel = new GamePanel(1000, 1000);
        this.add(this.panel);
        this.setTitle("Chess Game");
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addComponentListener(new GameFrameResizer());
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
    public GamePanel getGamePanel(){
        return (GamePanel)this.panel;
    }


}
