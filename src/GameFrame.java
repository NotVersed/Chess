import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.*;

public class GameFrame extends JFrame{
    private GridLayout layout;
    private JPanel panel;

    public GameFrame(){
        Board board = new Board();
        panel = new JPanel();
        layout = new GridLayout(8,8);
        panel.setLayout(layout);
        addTiles(board, panel);
        this.add(panel);
        this.pack();
		this.setTitle("Chess");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
    }
    public void addTiles(Board board, JPanel panel){
        Tile[][] tiles = board.getTiles();
        int boardLength = tiles.length;
        for(int i = 0; i < boardLength; i++){
            for(int j = 0; j < boardLength; j++){
                panel.add(new TilePanel(tiles[i][j]));
            }
        }
    }

}
