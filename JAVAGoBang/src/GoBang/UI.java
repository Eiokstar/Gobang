package GoBang;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseListener;

public class UI extends JPanel {
    static final Color BACK_COLOR = new Color(200, 195, 214);

    public Label labels = new Label();
    public JFrame windows = new JFrame("五子棋");
    public JFrame lossFram = new JFrame("認輸");
    public JFrame winFram = new JFrame("五子連珠");
    public JFrame andBureauFram = new JFrame("和局");

    private BoardPanel boardPanel;
    private JPanel modeSectionPanel;
    private Button buttonSet;
    private boolean initialized;

    public UI() {
        setLayout(new BorderLayout());
        setBackground(BACK_COLOR);
    }

    // === 建立整體介面 ===
    private void buildInterface() {
        removeAll();
        buttonSet = new Button();

        add(createTopButtonPanel(), BorderLayout.NORTH);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createSidePanel(), BorderLayout.EAST);

        revalidate();
        repaint();
    }

    private JComponent createTopButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        panel.setBackground(BACK_COLOR);
        for (JButton button : buttonSet.topButton) {
            panel.add(button);
        }
        return panel;
    }

    private JComponent createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACK_COLOR);
        panel.setBorder(new EmptyBorder(16, 16, 16, 16));

        boardPanel = new BoardPanel();
        boardPanel.setPreferredSize(new Dimension(720, 720));
        panel.add(boardPanel, BorderLayout.CENTER);
        return panel;
    }

    private JComponent createSidePanel() {
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBackground(BACK_COLOR);
        sidePanel.setBorder(new EmptyBorder(16, 16, 16, 16));

        // 玩家顯示區
        labels.showPlayer.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidePanel.add(labels.showPlayer);
        sidePanel.add(Box.createVerticalStrut(16));

        labels.textRound.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidePanel.add(labels.textRound);
        sidePanel.add(Box.createVerticalStrut(8));

        labels.showRoundPlayer.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidePanel.add(labels.showRoundPlayer);
        sidePanel.add(Box.createVerticalStrut(24));

        // 模式區
        modeSectionPanel = new JPanel();
        modeSectionPanel.setLayout(new BoxLayout(modeSectionPanel, BoxLayout.Y_AXIS));
        modeSectionPanel.setBackground(BACK_COLOR);
        modeSectionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        labels.textmode.setAlignmentX(Component.LEFT_ALIGNMENT);
        modeSectionPanel.add(labels.textmode);
        modeSectionPanel.add(Box.createVerticalStrut(12));

        for (JButton button : buttonSet.chooseButton) {
            button.setAlignmentX(Component.LEFT_ALIGNMENT);
            modeSectionPanel.add(button);
            modeSectionPanel.add(Box.createVerticalStrut(10));
        }


        modeSectionPanel.add(Box.createVerticalStrut(10));
        modeSectionPanel.add(Box.createVerticalStrut(10));
        modeSectionPanel.add(Box.createVerticalStrut(16));

        JPanel handPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        handPanel.setOpaque(false);
        for (JButton button : buttonSet.firstbackButton) {
            handPanel.add(button);
        }
        handPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        modeSectionPanel.add(handPanel);

        modeSectionPanel.add(Box.createVerticalStrut(16));
        sidePanel.add(modeSectionPanel);

        return sidePanel;
    }

    // === 顯示主視窗 ===
    public void showUI() {
        if (!initialized) {
            buildInterface();
            initialized = true;
        }

        ensureBoardMouseListener();

        windows.setContentPane(this);
        windows.setBackground(BACK_COLOR);
        windows.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windows.pack();

        if (windows.getWidth() < 1100 || windows.getHeight() < 820) {
            windows.setSize(Math.max(1100, windows.getWidth()), Math.max(820, windows.getHeight()));
        }

        windows.setMinimumSize(new Dimension(960, 720));
        windows.setLocationRelativeTo(null);
        windows.setVisible(true);
        windows.setResizable(true);
        setDifficultyLabel(main.game.getAiDifficulty());
        refreshBoard();
    }

    private void ensureBoardMouseListener() {
        MouseListener listener = main.mouseListener;
        boolean attached = false;
        for (MouseListener existing : boardPanel.getMouseListeners()) {
            if (existing == listener) {
                attached = true;
                break;
            }
        }
        if (!attached) {
            boardPanel.addMouseListener(listener);
        }
    }

    // === UI 更新與重置 ===
    public void setPlayersLabel(String str) {
        labels.showPlayer.setText(str);
    }

    public void reDisplayLabelAndPainting() {
        refreshBoard();
    }

    public void reSetUIAndGame() {
        main.initGame();
        main.moveCoordinator.setUi(this);
        initRoundLabel();
        main.judging.setNumberWinLine();
        labels.textRound.setText(" ");
        labels.showRoundPlayer.setText(" ");
        setPlayersLabel("");
        main.networkManager.close();
        setDifficultyLabel(main.game.getAiDifficulty());
        setModeControlsVisible(true);
        refreshBoard();
    }

    private void setRound(String str) {
        labels.textRound.setText("目前回合: " + str);
    }

    public void initRoundLabel() {
        setRound(" ");
    }

    public void setRoundLabel() {
        Game game = main.game;
        if (!game.Started) {
            setRound(" ");
            return;
        }

        if (game.onlineMode) {
            String roundPlayer = game.onlineMyTurn ? "本地玩家" : "遠端玩家";
            setRound(roundPlayer);
            return;
        }

        if (game.vsHumanMode) {
            setRound(labels.RoundPlayerName[game.getCurrentPlayer() - 1]);
            return;
        }
        if (game.vsComputerMode) {
            String[] names = labels.RoundPlayerName;
            String roundPlayer = game.getCurrentPlayer() == 1 ? names[2] : names[3];
            setRound(roundPlayer);
            return;
        }
        setRound(" ");
    }

    public void setDifficultyLabel(Game.Difficulty difficulty) {
        if (difficulty == null) {
            labels.difficultyValue.setText("—");
        } else {
            labels.difficultyValue.setText(difficulty.getDisplayName());
        }
    }

    // === 勝負顯示 ===
    public void lossFarm() {
        main.game.Started = false;
        setModeControlsVisible(true);
        Button button = new Button();
        setWinLossLabel(0);
        prepareResultFrame(lossFram, button);
        showResultFrame(lossFram);
    }

    public void winFram() {
        main.game.Started = false;
        setModeControlsVisible(true);
        Button button = new Button();
        setWinLossLabel(1);
        prepareResultFrame(winFram, button);
        showResultFrame(winFram);
    }

    public void andBureauFram() {
        main.game.Started = false;
        setModeControlsVisible(true);
        Button button = new Button();
        setWinLossLabel(2);
        prepareResultFrame(andBureauFram, button);
        showResultFrame(andBureauFram);
    }

    private void showResultFrame(JFrame frame) {
        frame.pack();
        frame.setSize(400, 400);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(windows);
    }

    public void setWinLossLabel(int index) {
        Game game = main.game;
        if (game.vsHumanMode) {
            if (labels.RoundPlayerName[game.getCurrentPlayer()].equals("玩家")) {
                setWinLossPlayer(labels.RoundPlayerName[game.getCurrentPlayer() - 2],
                        labels.RoundPlayerName[game.getCurrentPlayer() - 1], index);
            }
            if (labels.RoundPlayerName[game.getCurrentPlayer()].equals("玩家二")) {
                setWinLossPlayer(labels.RoundPlayerName[game.getCurrentPlayer()],
                        labels.RoundPlayerName[game.getCurrentPlayer() - 1], index);
            }
        }
        if (game.vsComputerMode) {
            if (labels.RoundPlayerName[game.getCurrentPlayer()].equals("玩家")) {
                setWinLossPlayer(labels.RoundPlayerName[game.getCurrentPlayer()],
                        labels.RoundPlayerName[game.getCurrentPlayer() + 1], index);
            }
            if (labels.RoundPlayerName[game.getCurrentPlayer()].equals("玩家二")) {
                setWinLossPlayer(labels.RoundPlayerName[game.getCurrentPlayer() + 2],
                        labels.RoundPlayerName[game.getCurrentPlayer() + 1], index);
            }
        }
    }

    public void setWinLossPlayer(String str01, String str02, int index) {
        if (index == 0) {
            labels.textWin.setText("獲勝方: " + str01);
            labels.textLoss.setText("認輸方: " + str02);
        }
        if (index == 1) {
            labels.textWin.setText("勝利方: " + str02);
            labels.textLoss.setText("敗北方: " + str01);
        }
        if (index == 2) {
            labels.textWin.setText("無勝方: " + str02);
            labels.textLoss.setText("無敗方: " + str01);
        }
    }

    private void prepareResultFrame(JFrame frame, Button button) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BACK_COLOR);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        labels.textWin.setAlignmentX(Component.CENTER_ALIGNMENT);
        labels.textLoss.setAlignmentX(Component.CENTER_ALIGNMENT);
        labels.why.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.sureButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalStrut(10));
        panel.add(labels.textWin);
        panel.add(Box.createVerticalStrut(12));
        panel.add(labels.textLoss);
        panel.add(Box.createVerticalStrut(12));
        panel.add(labels.why);
        panel.add(Box.createVerticalStrut(20));
        panel.add(button.sureButton);
        panel.add(Box.createVerticalGlue());

        frame.setContentPane(panel);
    }

    // === 模式區顯示控制 ===
    public void setModeControlsVisible(boolean visible) {
        if (modeSectionPanel != null) {
            modeSectionPanel.setVisible(visible);
            modeSectionPanel.revalidate();
        }
    }

    // === 棋盤繪製刷新 ===
    public void refreshBoard() {
        if (boardPanel != null) {
            boardPanel.repaint();
        }
    }

    public int[] resolveBoardClick(Point point) {
        if (boardPanel == null) {
            return null;
        }
        return boardPanel.pointToBoard(point);
    }

    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    // === 棋盤繪圖面板 ===
    public static class BoardPanel extends JPanel {
        private int boardStartX;
        private int boardStartY;
        private int boardSize;
        private double cellSize;

        public BoardPanel() {
            setBackground(BACK_COLOR);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            updateMetrics();

            g2.setColor(new Color(210, 150, 85));
            g2.fillRect(boardStartX, boardStartY, boardSize, boardSize);

            g2.setColor(Color.BLACK);
            for (int i = 0; i < 15; i++) {
                int x = (int) Math.round(boardStartX + i * cellSize);
                int y = (int) Math.round(boardStartY + i * cellSize);
                g2.drawLine(boardStartX, y, boardStartX + boardSize, y);
                g2.drawLine(x, boardStartY, x, boardStartY + boardSize);
            }

            drawStarPoints(g2);
            drawPieces(g2);

            g2.dispose();
        }

        private void updateMetrics() {
            int width = getWidth();
            int height = getHeight();
            int margin = Math.max(20, Math.min(width, height) / 20);
            boardSize = Math.min(width, height) - margin * 2;
            if (boardSize <= 0) {
                boardSize = Math.min(width, height);
            }
            boardStartX = (width - boardSize) / 2;
            boardStartY = (height - boardSize) / 2;
            cellSize = boardSize / 14.0;
        }

        private void drawStarPoints(Graphics2D g2) {
            int[][] stars = {{3, 3}, {3, 11}, {7, 7}, {11, 3}, {11, 11}};
            int size = (int) Math.max(6, Math.round(cellSize * 0.18));
            int radius = size / 2;
            for (int[] star : stars) {
                int centerX = (int) Math.round(boardStartX + star[0] * cellSize);
                int centerY = (int) Math.round(boardStartY + star[1] * cellSize);
                g2.fillOval(centerX - radius, centerY - radius, size, size);
            }
        }

        private void drawPieces(Graphics2D g2) {
            Game game = main.game;
            if (game == null) {
                return;
            }
            double pieceSize = cellSize * 0.8;
            int pieceDiameter = (int) Math.max(12, Math.round(pieceSize));
            int radius = pieceDiameter / 2;

            for (int x = 0; x < 15; x++) {
                for (int y = 0; y < 15; y++) {
                    int player = Game.allPlayChessed[x][y];
                    if (player == 0) {
                        continue;
                    }
                    int centerX = (int) Math.round(boardStartX + x * cellSize);
                    int centerY = (int) Math.round(boardStartY + y * cellSize);
                    int drawX = centerX - radius;
                    int drawY = centerY - radius;

                    if (player == 1) {
                        g2.setColor(Color.BLACK);
                        g2.fillOval(drawX, drawY, pieceDiameter, pieceDiameter);
                    } else {
                        g2.setColor(Color.WHITE);
                        g2.fillOval(drawX, drawY, pieceDiameter, pieceDiameter);
                        g2.setColor(Color.BLACK);
                        g2.drawOval(drawX, drawY, pieceDiameter, pieceDiameter);
                    }
                }
            }
        }

        public int[] pointToBoard(Point point) {
            if (cellSize <= 0) {
                return null;
            }
            double relativeX = (point.x - boardStartX) / cellSize;
            double relativeY = (point.y - boardStartY) / cellSize;
            int boardX = (int) Math.round(relativeX);
            int boardY = (int) Math.round(relativeY);
            double tolerance = 0.35;

            if (boardX < 0 || boardX > 14 || boardY < 0 || boardY > 14) {
                return null;
            }
            if (Math.abs(relativeX - boardX) > tolerance || Math.abs(relativeY - boardY) > tolerance) {
                return null;
            }
            return new int[]{boardX, boardY};
        }
    }
}
