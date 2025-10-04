import java.awt.Color;
import javax.swing.JFrame;

public class GameFrame extends JFrame{

    private BoardPanel panel;

    public GameFrame(){
        this.panel = new BoardPanel();
        this.add(panel);
        this.setTitle("Chess Game");
		this.setBackground(Color.WHITE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
    }


}
