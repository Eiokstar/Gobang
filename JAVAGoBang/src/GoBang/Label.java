package GoBang;


import javax.swing.*;

public class Label{
    static int x01=975,x02=960;

    public JLabel[][] boardButtons = new JLabel[15][15];

    public JLabel showPlayer = new JLabel();

    JLabel textRound = new JLabel("  ");
    JLabel textmode = new JLabel("模式:");
    public JLabel difficultyTitle = new JLabel("難度:");
    public JLabel difficultyValue = new JLabel("");
    JLabel textLoss = new JLabel("");
    JLabel textWin = new JLabel("");
    JLabel why = new JLabel(" ");  // de認輸視窗的bug用的 沒有這個label 其他兩個label會有問題
    JLabel piecesDisappear = new JLabel("棋子消失時請按:");

    JLabel[] showRoundPlayer = new JLabel[4];
    public String[] RoundPlayerName = {"玩家一","玩家二","玩家","AI電腦"};

    Label(){
        setShowGamePlayerLabel();
        setRoundLabel();
        setShowRoundPlayer();
        setModeLabel();
        setDifficultyLabels();
        setBoardButtons();
        setTextLoss();
        setTextWin();
        setWhy();
        setPiecesDisappear();
    }

    public void setShowGamePlayerLabel(){
        showPlayer.setBounds(x01,30,290,45);
        showPlayer.setFont(new java.awt.Font("微軟正黑體",1,35));
        showPlayer.setBackground(UI.BACK_COLOR);
        showPlayer.setOpaque(true);
    }


    public void setShowRoundPlayer(){
        for(int i=0;i<showRoundPlayer.length;i++){
            showRoundPlayer[i] = new JLabel("");
            showRoundPlayer[i].setBounds(1130,100,280,45);
            showRoundPlayer[i].setFont(new java.awt.Font("微軟正黑體",1,35));
            showRoundPlayer[i].setOpaque(true);
            showRoundPlayer[i].setBackground(UI.BACK_COLOR);
        }
    }


    public void setRoundLabel(){
        textRound.setOpaque(true);
        textRound.setBounds(980,100,280,45);
        textRound.setFont(new java.awt.Font("微軟正黑體",1,35));
        textRound.setBackground(UI.BACK_COLOR);
    }

    public void setModeLabel(){
        textmode.setOpaque(false);
        textmode.setBounds(980,480,280,45);
        textmode.setFont(new java.awt.Font("微軟正黑體",1,35));
    }

    public void setDifficultyLabels(){
        difficultyTitle.setOpaque(false);
        difficultyTitle.setBounds(980,620,120,45);
        difficultyTitle.setFont(new java.awt.Font("微軟正黑體",1,35));

        difficultyValue.setOpaque(true);
        difficultyValue.setBounds(1100,620,160,45);
        difficultyValue.setFont(new java.awt.Font("微軟正黑體",1,35));
        difficultyValue.setBackground(UI.BACK_COLOR);
        difficultyValue.setHorizontalAlignment(SwingConstants.LEFT);
    }


    public void setBoardButtons(){

        int x=90,y;
        for(int i = 0; i<15;i++){
            x+=50;
            y=80;
            for(int j = 0 ; j<15;j++){
                boardButtons[i][j] = new JLabel(i+" "+j);
                boardButtons[i][j].setBounds(x+5,y+4,30,35);
                boardButtons[i][j].setOpaque(false);
                boardButtons[i][j].setIcon(new ImageIcon(getClass().getResource("image/TEST1.png")));

                y+=50;
            }
        }
    }
    public void setTextWin(){
        textWin.setBounds(30,40, 400,70);
        textWin.setFont(new java.awt.Font("微軟正黑體",1,40));

    }
    public void setTextLoss(){
        textLoss.setBounds(30,120,400,70);
        textLoss.setFont(new java.awt.Font("微軟正黑體",1,40));

    }

    public void setWhy(){
        why.setBounds(150,200,40,40);
    }

    public void setPiecesDisappear(){
        piecesDisappear.setBounds(860,790,300,100);
        piecesDisappear.setFont(new java.awt.Font("微軟正黑體",1,35));
    }




}
