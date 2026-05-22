import java.awt.*;
import javax.swing.*;

enum Difficulty {
    EASY, MEDIUM, HARD;

    public int getDepth() {
        return switch (this) {
            case EASY -> 3;
            case MEDIUM -> 5;
            case HARD -> 7;
        };
    }
}

public class GameSetupDialog extends JDialog {

    private Difficulty difficulty = Difficulty.MEDIUM;
    private boolean humanIsWhite = true;
    private boolean confirmed = false;

    private static final Color NAVY = new Color(10, 25, 47);
    private static final Color TEAL = new Color(100, 255, 218);
    private static final Color SLATE = new Color(136, 146, 176);
    private static final Color WHITE_TEXT = new Color(230, 241, 255);
    private static final Color NAVY_LIGHT = new Color(17, 34, 64);
    private static final Color NAVY_LIGHTER = new Color(35, 53, 84);

    public GameSetupDialog(JFrame parent) {
        super(parent, "Game Setup", true);
        setSize(400, 350);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel root = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(NAVY);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        root.setBorder(BorderFactory.createLineBorder(NAVY_LIGHTER, 1));
        setContentPane(root);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 30, 0);
        JLabel title = new JLabel("GAME SETUP");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(WHITE_TEXT);
        root.add(title, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 8, 0);
        JLabel diffLabel = new JLabel("DIFFICULTY");
        diffLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        diffLabel.setForeground(TEAL);
        root.add(diffLabel, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 30, 0);
        JPanel diffPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        diffPanel.setOpaque(false);
        JButton easyBtn = new JButton("Easy");
        JButton mediumBtn = new JButton("Medium");
        JButton hardBtn = new JButton("Hard");
        diffPanel.add(easyBtn);
        diffPanel.add(mediumBtn);
        diffPanel.add(hardBtn);
        root.add(diffPanel, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 8, 0);
        JLabel sideLabel = new JLabel("PLAY AS");
        sideLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        sideLabel.setForeground(TEAL);
        root.add(sideLabel, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 30, 0);
        JPanel sidePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        sidePanel.setOpaque(false);
        JButton whiteBtn = new JButton("White");
        JButton blackBtn = new JButton("Black");
        sidePanel.add(whiteBtn);
        sidePanel.add(blackBtn);
        root.add(sidePanel, gbc);

        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 20, 0);
        JButton startBtn = new JButton("Start Game");
        root.add(startBtn, gbc);

        JButton[] buttons = { easyBtn, mediumBtn, hardBtn, whiteBtn, blackBtn, startBtn };
        for (JButton btn : buttons)
            styleButton(btn);

        activateButton(mediumBtn);
        activateButton(whiteBtn);

        easyBtn.addActionListener(e -> {
            difficulty = Difficulty.EASY;
            selectButton(easyBtn, mediumBtn, hardBtn);
        });
        mediumBtn.addActionListener(e -> {
            difficulty = Difficulty.MEDIUM;
            selectButton(mediumBtn, easyBtn, hardBtn);
        });
        hardBtn.addActionListener(e -> {
            difficulty = Difficulty.HARD;
            selectButton(hardBtn, easyBtn, mediumBtn);
        });

        whiteBtn.addActionListener(e -> {
            humanIsWhite = true;
            selectButton(whiteBtn, blackBtn);
        });
        blackBtn.addActionListener(e -> {
            humanIsWhite = false;
            selectButton(blackBtn, whiteBtn);
        });

        startBtn.addActionListener(e -> {
            confirmed = true;
            dispose();
        });

        setVisible(true);
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public boolean humanIsWhite() {
        return humanIsWhite;
    }

    public int getDepth() {
        return difficulty.getDepth();
    }

    private void styleButton(JButton btn) {
        btn.setFont(new Font("SansSerif", Font.PLAIN, 13));
        btn.setForeground(SLATE);
        btn.setBackground(NAVY_LIGHT);
        btn.setBorder(BorderFactory.createLineBorder(NAVY_LIGHTER, 1));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(100, 36));
    }

    private void activateButton(JButton btn) {
        btn.setForeground(TEAL);
        btn.setBackground(NAVY_LIGHTER);
        btn.setBorder(BorderFactory.createLineBorder(TEAL, 1));
    }

    private void selectButton(JButton selected, JButton... others) {
        activateButton(selected);
        for (JButton btn : others)
            styleButton(btn);
    }
}