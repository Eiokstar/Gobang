package GoBang;

import java.awt.*;
import javax.swing.*;
import java.awt.geom.Rectangle2D;

import static GoBang.main.game;
import static GoBang.main.judging;


public class UI extends JPanel{
    static final Color BACK_COLOR = new Color(200, 195, 214);

    public Label labels = new Label();
    public JFrame windows = new JFrame("五子棋");
    public JFrame lossFram = new JFrame("認輸");
    public JFrame winFram = new JFrame("五子連珠");
    public JFrame andBureauFram = new JFrame("和局");

    public void paint(Graphics g){

        g.setColor(Color.BLACK);
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(new Color(210,150,85));
        g2.fill(new Rectangle2D.Float(160,100,700,700));
        g2.setPaint(Color.BLACK);
        g2.fillOval(505,445 , 10, 10);
        g2.fillOval(305,245 , 10, 10);
        g2.fillOval(705,245 , 10, 10);
        g2.fillOval(305,645 , 10, 10);
        g2.fillOval(705,645 , 10, 10);
        for(int i=1;i<=15;i++){
            g2.setPaint(Color.BLACK);
            g2.drawLine(160,50+i*50,860,50+i*50);
            g2.setPaint(Color.BLACK);
            g2.drawLine(110+i*50,100,110+i*50,800);
        }

        g.setColor(Color.BLACK);
        Graphics2D g3 = (Graphics2D) g;

        g3.setPaint(new Color(210,150,85));
        g3.fill(new Rectangle2D.Float(980,180,280,280));
        g3.setPaint(Color.BLACK);
        g3.fillOval(1037,237 , 6, 6);
        g3.fillOval(1197,237 , 6, 6);
        g3.fillOval(1037,397 , 6, 6);
        g3.fillOval(1197,397 , 6, 6);
        g3.fillOval(1117,317 , 6, 6);
        for(int i=1;i<=15;i++){
            g3.setPaint(Color.BLACK);
            g3.drawLine(980,160+i*20,1260,160+i*20);
            g3.setPaint(Color.BLACK);
            g3.drawLine(960+i*20,180,960+i*20,460);
        }

    }

    /**
     *
     * 繪製旗子到棋盤上
     */
    public void DrawPiecesInBoard(int x, int y, int color){
        new pointCheckerboard(windows.getGraphics(), x,y,color).paintComponent();
    }
    public void DrawSamllPiecesBoard(int loc[]){
        new pointCheckerboard(windows.getGraphics(),loc).paintComponent();
    }

    private void reDrawAllPiecesInBoard(){
        for (int i = 0 ;i<game.getBlackPlacedPieces().size();i++){
            int loc[] = game.getBlackPlacedPieces().get(i);
            JLabel PointLoc = labels.boardButtons[loc[0]][loc[1]];
            DrawPiecesInBoard(PointLoc.getX(),PointLoc.getY()+28,2);
            DrawSamllPiecesBoard(loc);
        }

        for (int i = 0 ;i<game.getWhitePlacedPieces().size();i++){
            int loc[] = game.getWhitePlacedPieces().get(i);
            JLabel PointLoc = labels.boardButtons[loc[0]][loc[1]];
            DrawPiecesInBoard(PointLoc.getX(),PointLoc.getY()+28,1);
            DrawSamllPiecesBoard(loc);
        }

    }




    public void showUI(){

        windows.setSize(1400,1000);
        windows.setBackground(BACK_COLOR);
        createButton();
        createLable();
        windows.getContentPane().add(main.UI);
        windows.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windows.setVisible(true);
        windows.setResizable((false));



    }
    public void createLable() {
        windows.add(labels.textmode);
        windows.add(labels.textRound);
        windows.add(labels.showRoundPlayer[0]);
        windows.add(labels.showPlayer);
        windows.add(labels.piecesDisappear);

        for(int i = 0 ;i<15;i++){
            for (int j = 0;j<15;j++){
                windows.add(labels.boardButtons[i][j]);
                labels.boardButtons[i][j].addMouseListener(main.mouseListener);
            }
        }

    }
    public void createButton(){
        Button button = new Button();
        for(int i=0;i<button.topButton.length;i++){
            windows.getContentPane().add(button.topButton[i]);
        }

        for(int i=0;i< button.chooseButton.length;i++){
            windows.getContentPane().add(button.chooseButton[i]);
        }
        for(int i=0;i<button.firstbackButton.length;i++){
            windows.getContentPane().add(button.firstbackButton[i]);
        }
        windows.getContentPane().add(button.piecesDisappear);
    }

    /**
     *
     * 設定右上角 vs 的 label
     */
    public void setPlayersLabel(String str){
        labels.showPlayer.setText(str);
    }

    private void initUI(){
        UI w = new UI();
        windows.setSize(1400,1000);
        windows.setBackground(BACK_COLOR);
        windows.setVisible(true);
        windows.setResizable(false);

    }

    public void reDisplayLabelAndPainting(){
        windows.update(windows.getGraphics());
        reDrawAllPiecesInBoard();
    }

    public void reSetUIAndGame(){
        initUI();
        main.initGame();
        initRoundLabel();
        judging.setNumberWinLine();
        labels.textRound.setText(" ");
        labels.showRoundPlayer[0].setText(" ");
        main.networkManager.close();
    }
    private void setRound(String str){
        labels.textRound.setText("目前回合: "+str);
        labels.showRoundPlayer[0].setText(str);

    }
    public void initRoundLabel(){
        setRound(" ");
    }


    public void setRoundLabel(){
        if(!game.Started){
            setRound(" ");
            return;
        }

        if(game.onlineMode){
            String roundPlayer = game.onlineMyTurn ? "本地玩家" : "遠端玩家";
            setRound(roundPlayer);
            return;
        }

        if(game.vsHumanMode){
            setRound(main.UI.labels.RoundPlayerName[game.getCurrentPlayer()-1]);
            return;
        }
        if(game.vsComputerMode){
            String[] names = main.UI.labels.RoundPlayerName;
            String roundPlayer = game.getCurrentPlayer()==1 ? names[2] : names[3];
            setRound(roundPlayer);
            return;
        }
        setRound(" ");
    }
    public void lossFarm(){
        Button button = new Button();
        lossFram.setSize(400,400);
        lossFram.add(button.sureButton);
        setWinLossLabel(0);
        lossFram.add(labels.textWin);
        lossFram.add(labels.textLoss);
        lossFram.add(labels.why);

        lossFram.setVisible(true);
        lossFram.setResizable(false);
        lossFram.setLocationRelativeTo(null);
    }
    public void winFram(){
        Button button = new Button();
        winFram.setSize(400,400);
        winFram.add(button.sureButton);
        setWinLossLabel(1);
        winFram.add(labels.textWin);
        winFram.add(labels.textLoss);
        winFram.add(labels.why);

        winFram.setVisible(true);
        winFram.setResizable(false);
        winFram.setLocationRelativeTo(null);
    }
    public void andBureauFram(){
        Button button = new Button();
        andBureauFram.setSize(400,400);
        andBureauFram.add(button.sureButton);
        setWinLossLabel(2);
        andBureauFram.add(labels.textWin);
        andBureauFram.add(labels.textLoss);
        andBureauFram.add(labels.why);

        andBureauFram.setVisible(true);
        andBureauFram.setResizable(false);
        andBureauFram.setLocationRelativeTo(null);

    }
    public void setWinLossLabel(int index){
        if(game.vsHumanMode){
            if(main.UI.labels.RoundPlayerName[game.getCurrentPlayer()]=="玩家"){
                setWinLossPlayer(main.UI.labels.RoundPlayerName[game.getCurrentPlayer()-2],main.UI.labels.RoundPlayerName[game.getCurrentPlayer()-1],index);
            }
            if(main.UI.labels.RoundPlayerName[game.getCurrentPlayer()]=="玩家二"){
                setWinLossPlayer(main.UI.labels.RoundPlayerName[game.getCurrentPlayer()],main.UI.labels.RoundPlayerName[game.getCurrentPlayer()-1],index);
            }

        }
        if(game.vsComputerMode){
            System.out.println(main.UI.labels.RoundPlayerName[game.getCurrentPlayer()]);
            if(main.UI.labels.RoundPlayerName[game.getCurrentPlayer()]=="玩家"){
                setWinLossPlayer(main.UI.labels.RoundPlayerName[game.getCurrentPlayer()],main.UI.labels.RoundPlayerName[game.getCurrentPlayer()+1],index);
            }
            if(main.UI.labels.RoundPlayerName[game.getCurrentPlayer()]=="玩家二"){
                setWinLossPlayer(main.UI.labels.RoundPlayerName[game.getCurrentPlayer()+2],main.UI.labels.RoundPlayerName[game.getCurrentPlayer()+1],index);
            }
        }
    }
    public void setWinLossPlayer(String str01,String str02,int index){
        if(index==0){
            labels.textWin.setText("獲勝方: "+str01);
            labels.textLoss.setText("認輸方: "+str02);
        }
        if(index==1){
            labels.textWin.setText("勝利方: "+str02);
            labels.textLoss.setText("敗北方: "+str01);
        }
        if(index==2){
            labels.textWin.setText("無勝方: "+str02);
            labels.textLoss.setText("無敗方: "+str01);
        }

    }
}
