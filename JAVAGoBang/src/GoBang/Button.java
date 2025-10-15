package GoBang;

import javax.swing.*;
import java.awt.*;

public class Button {
    private final Main instance = Main.getInstance();

    JButton[] topButton = new JButton[4];
    JButton[] chooseButton = new JButton[3];
    JButton[] difficultyButtons = new JButton[3];
    JButton[] firstbackButton = new JButton[2];
    JButton sureButton = new JButton("確定");
    JButton piecesDisappear = new JButton("回復棋子");

    String[] topButtonImageLocation = {
            "image/fast01.png", "image/magic-wand01.png",
            "image/whiteflag01.png", "image/return01.png"
    };
    String[] topButtonName = {"開始", "悔棋", "認輸", "重新"};
    String[] firstbackName = {"先手", "後手"};
    String[] difficultyButtonName = {"簡單AI", "中等AI", "困難AI"};
    String[] chooseButtonName = {"玩家VS玩家", "玩家VS電腦", "聯機對戰"};

    public Button() {
        TopButton();
        ChooseButton();
        DifficultyButtons();
        SureButton();
        PiecesDisappear();
        firstBack();
    }

    // 上方四個按鈕（開始、悔棋、認輸、重新）
    public JButton[] TopButton() {
        for (int i = 0; i < topButton.length; i++) {
            topButton[i] = new JButton(topButtonName[i]);
            topButton[i].addActionListener(instance.actionListener);
            topButton[i].setFont(new Font("微軟正黑體", Font.BOLD, 20));
            topButton[i].setPreferredSize(new Dimension(150, 60));
            topButton[i].setFocusPainted(false);
            topButton[i].setBorder(BorderFactory.createRaisedBevelBorder());
            topButton[i].setIcon(new ImageIcon(getClass().getResource(topButtonImageLocation[i])));
        }
        return topButton;
    }

    // 模式選擇按鈕（玩家VS玩家、玩家VS電腦、聯機對戰）
    public JButton[] ChooseButton() {
        for (int i = 0; i < chooseButtonName.length; i++) {
            chooseButton[i] = new JButton(chooseButtonName[i]);
            chooseButton[i].addActionListener(instance.actionListener);
            chooseButton[i].setFont(new Font("微軟正黑體", Font.BOLD, 28));
            chooseButton[i].setFocusPainted(false);
            chooseButton[i].setBorder(BorderFactory.createRaisedBevelBorder());
            chooseButton[i].setAlignmentX(Component.LEFT_ALIGNMENT);
            chooseButton[i].setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        }
        return chooseButton;
    }

    // AI 難度按鈕（簡單AI、中等AI、困難AI）
    public JButton[] DifficultyButtons() {
        for (int i = 0; i < difficultyButtonName.length; i++) {
            difficultyButtons[i] = new JButton(difficultyButtonName[i]);
            difficultyButtons[i].addActionListener(instance.actionListener);
            difficultyButtons[i].setFont(new Font("微軟正黑體", Font.BOLD, 24));
            difficultyButtons[i].setFocusPainted(false);
            difficultyButtons[i].setBorder(BorderFactory.createRaisedBevelBorder());
            difficultyButtons[i].setAlignmentX(Component.LEFT_ALIGNMENT);
            difficultyButtons[i].setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        }
        return difficultyButtons;
    }

    // 先手 / 後手 按鈕
    public JButton[] firstBack() {
        for (int i = 0; i < firstbackButton.length; i++) {
            firstbackButton[i] = new JButton(firstbackName[i]);
            firstbackButton[i].addActionListener(instance.actionListener);
            firstbackButton[i].setFont(new Font("微軟正黑體", Font.BOLD, 26));
            firstbackButton[i].setFocusPainted(false);
            firstbackButton[i].setBorder(BorderFactory.createRaisedBevelBorder());
            firstbackButton[i].setPreferredSize(new Dimension(100, 50));
        }
        return firstbackButton;
    }

    // 確定按鈕
    public JButton SureButton() {
        sureButton.addActionListener(instance.actionListener);
        sureButton.setFont(new Font("微軟正黑體", Font.BOLD, 28));
        sureButton.setFocusPainted(false);
        return sureButton;
    }

    // 回復棋子按鈕
    public JButton PiecesDisappear() {
        piecesDisappear.addActionListener(instance.actionListener);
        piecesDisappear.setFont(new Font("微軟正黑體", Font.BOLD, 26));
        piecesDisappear.setFocusPainted(false);
        return piecesDisappear;
    }
}
