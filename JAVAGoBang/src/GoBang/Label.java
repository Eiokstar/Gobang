package GoBang;

import javax.swing.*;
import java.awt.*;

public class Label {

    public JLabel showPlayer = new JLabel();
    public JLabel textRound = new JLabel("  ");
    public JLabel showRoundPlayer = new JLabel("");
    public JLabel textmode = new JLabel("模式:");
    public JLabel difficultyTitle = new JLabel("難度:");
    public JLabel difficultyValue = new JLabel("");
    public JLabel textLoss = new JLabel("");
    public JLabel textWin = new JLabel("");
    public JLabel why = new JLabel(" ");  // 修正「認輸」視窗的 bug 用，若無此 label 其他會出問題
    public JLabel piecesDisappear = new JLabel("棋子消失時請按:");

    public String[] RoundPlayerName = {"玩家一", "玩家二", "玩家", "AI電腦"};

    public Label() {
        Font titleFont = new Font("微軟正黑體", Font.BOLD, 35);

        // === 玩家顯示 ===
        showPlayer.setFont(titleFont);
        showPlayer.setBackground(UI.BACK_COLOR);
        showPlayer.setOpaque(true);

        // === 回合顯示 ===
        textRound.setFont(titleFont);
        textRound.setBackground(UI.BACK_COLOR);
        textRound.setOpaque(true);

        // === 當前回合玩家 ===
        showRoundPlayer.setFont(titleFont);
        showRoundPlayer.setBackground(UI.BACK_COLOR);
        showRoundPlayer.setOpaque(true);

        // === 模式標籤 ===
        textmode.setFont(titleFont);
        textmode.setOpaque(false);

//        // === 難度標題與內容 ===
//        difficultyTitle.setFont(titleFont);
//        difficultyTitle.setOpaque(false);
//
//        difficultyValue.setFont(titleFont);
//        difficultyValue.setBackground(UI.BACK_COLOR);
//        difficultyValue.setOpaque(true);
//        difficultyValue.setHorizontalAlignment(SwingConstants.LEFT);

        // === 勝負顯示 ===
        textWin.setFont(new Font("微軟正黑體", Font.BOLD, 40));
        textLoss.setFont(new Font("微軟正黑體", Font.BOLD, 40));

        // === 提示文字 ===
        piecesDisappear.setFont(titleFont);
    }
}
