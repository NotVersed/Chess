import java.awt.Color;
import javax.swing.JFrame;

public class GameFrame extends JFrame{

    private final BoardPanel PANEL;

    public GameFrame(){
        Board board = new Board();
        Controller controller = new Controller(board);
        this.PANEL = new BoardPanel(board, controller);
        this.add(PANEL);
        this.setTitle("Chess Game");
		this.setBackground(Color.WHITE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
    }


}
