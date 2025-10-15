package GoBang;

import javax.swing.*;

public  class Button{
    private main instance = main.getInstance();
    static int x01 = 140,  y02=530 ,x03=1040;
    JButton[] topButton = new JButton[4];
    JButton[] chooseButton = new JButton[2];
    JButton[] firstbackButton = new JButton[2];
    JButton sureButton = new JButton("確定");
    JButton piecesDisappear = new JButton("回復棋子");
    String[] topButtonImageLocation = {"image/fast01.png","image/magic-wand01.png","image/whiteflag01.png","image/return01.png"};
    String[] topButtonName = {"開始","悔棋","認輸","重新"};
    String[] firstbackName ={"先手","後手"};


    Button(){
        TopButton();
        ChooseButton();
        SureButton();
        PiecesDisappear();
        firstBack();
    }


    public JButton[] TopButton(){

        for(int i =0,j=0; i<4;i++,j=200){
            topButton[i]=new JButton(topButtonName[i]);
            topButton[i].addActionListener(instance.actionListener);
            topButton[i].setBounds(x01=x01+j,10,135,65);
            topButton[i].setBorder(BorderFactory.createRaisedBevelBorder());
            topButton[i].setFont(new  java.awt.Font("微軟正黑體",  1,  20));
            topButton[i].setIcon(new ImageIcon(getClass().getResource(topButtonImageLocation[i])));
            topButton[i].setBorderPainted(false);
        }
        return topButton;
    }
    String[] chooseButtonName = {"玩家VS玩家","玩家VS高級AI"};

    public JButton[] ChooseButton(){
        for (int i = 0, j = 0; i < chooseButtonName.length; i++, j += 70) {
            chooseButton[i] = new JButton(chooseButtonName[i]);
            chooseButton[i].addActionListener(instance.actionListener);
            chooseButton[i].setBounds(1020, y02 + j, 220, 45);
            chooseButton[i].setFont(new java.awt.Font("微軟正黑體", 1, 30));
            chooseButton[i].setBorder(BorderFactory.createRaisedBevelBorder());
            chooseButton[i].setBorderPainted(false);
        }
        return chooseButton;
    }
    public JButton[] firstBack(){
        for(int i=0,j=0;i<2;i++,j=100){
            firstbackButton[i]=new JButton(firstbackName[i]);
            firstbackButton[i].addActionListener(instance.actionListener);
            firstbackButton[i].setBounds(x03+j,670,80,45);
            firstbackButton[i].setFont(new  java.awt.Font("微軟正黑體",  1,  30));
            firstbackButton[i].setBorder(BorderFactory.createRaisedBevelBorder());
            firstbackButton[i].setBorderPainted(false);
        }
        return firstbackButton;
    }
    public JButton SureButton(){
        sureButton.addActionListener(instance.actionListener);
        sureButton.setBounds(140,230,100,45);
        sureButton.setFont(new  java.awt.Font("微軟正黑體",  1,  30));
        return sureButton;
    }
    public JButton PiecesDisappear(){
        piecesDisappear.addActionListener(instance.actionListener);
        piecesDisappear.setBounds(1120,820,200,45);
        piecesDisappear.setFont(new  java.awt.Font("微軟正黑體",  1,  30));
        piecesDisappear.setBorderPainted(false);
        return piecesDisappear;
    }

}
