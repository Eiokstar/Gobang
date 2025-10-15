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
    public JLabel why = new JLabel(" ");  // de認輸視窗的bug用的 沒有這個label 其他兩個label會有問題
    public JLabel piecesDisappear = new JLabel("棋子消失時請按:");
    public String[] RoundPlayerName = {"玩家一","玩家二","玩家","AI電腦"};

    public Label(){
        Font titleFont = new Font("微軟正黑體", Font.BOLD, 35);
        showPlayer.setFont(titleFont);
        showPlayer.setBackground(UI.BACK_COLOR);
        showPlayer.setOpaque(true);

        textRound.setFont(titleFont);
        textRound.setBackground(UI.BACK_COLOR);
        textRound.setOpaque(true);

        showRoundPlayer.setFont(titleFont);
        showRoundPlayer.setBackground(UI.BACK_COLOR);
        showRoundPlayer.setOpaque(true);

        textmode.setFont(titleFont);
        difficultyTitle.setFont(titleFont);

        difficultyValue.setFont(titleFont);
        difficultyValue.setBackground(UI.BACK_COLOR);
        difficultyValue.setOpaque(true);
        difficultyValue.setHorizontalAlignment(SwingConstants.LEFT);

        textWin.setFont(new Font("微軟正黑體", Font.BOLD, 40));
        textLoss.setFont(new Font("微軟正黑體", Font.BOLD, 40));
        piecesDisappear.setFont(titleFont);
    }
}
