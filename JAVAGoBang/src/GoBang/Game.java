package GoBang;


import java.util.ArrayList;

import GoBang.Listener.MouseListener;
import GoBang.main;

public class Game {
    private int currentPlayer=1;

    public boolean Started;
    public boolean vsHumanMode;
    public boolean vsComputerMode;
    public boolean firstHand;
    public boolean backHand;
    public static boolean playerNotAIRound;
    public static int chessMove=0;
    public static int[][] allScoreChessed = {{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
    public static int[][] allPlayChessed = {{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
    private ArrayList<ArrayList<int[]>> PlacedPieces;
    private GoBang.UI UI = main.getUI();
    private static GoBang.Listener.MouseListener MosList = main.getMouseListener();
    Game(){
        initPlacePieces();
        initStatus();
    }
    public void initStatus(){
        Started = false;
        vsHumanMode = false;
        vsComputerMode = false;
        firstHand = false;
        backHand =false;
    }

    public void reGamePlayChess(){
        for(int i=0;i<=14;i++){
            for(int j=0;j<=14;j++){
                allPlayChessed[i][j]=0;
            }
        }
    }

    private void initPlacePieces(){
        PlacedPieces = new ArrayList<>();
        //Black
        PlacedPieces.add(new ArrayList<>());
        //White
        PlacedPieces.add(new ArrayList<>());
        //Black small
        PlacedPieces.add(new ArrayList<>());
        //White small
        PlacedPieces.add(new ArrayList<>());
    }

    /**
     *
     * 儲存座標到 PlacedPieces
     */
    public void addPointToPlacedPieces(int player ,int x , int y){
        PlacedPieces.get(player-1).add(new int[]{x,y});
    }

    /**
     *
     * @return 已放置的所有白棋座標
     */
    public ArrayList<int[]> getWhitePlacedPieces(){
        return PlacedPieces.get(0);
    }

    /**
     *
     * @return 已放置的所有黑棋座標
     */
    public ArrayList<int[]> getBlackPlacedPieces(){  return PlacedPieces.get(1);  }



    public void removePiecesFromBlackList(int index){
        PlacedPieces.get(1).remove(index);
    }

    public void removePiecesFromWhiteList(int index){  PlacedPieces.get(0).remove(index);}


    /**
     *
     * 檢查位置是否已經被放過
     */
    public boolean LocIsPlaced(int x, int y){

        for (int i = 0;i<getBlackPlacedPieces().size();i++){
            int loc[] = getBlackPlacedPieces().get(i);
            if (loc[0] == x & loc[1] == y){
                return true;

            }
        }
        for (int i = 0;i<getWhitePlacedPieces().size();i++){
            int loc[] = getWhitePlacedPieces().get(i);
            if (loc[0] == x & loc[1] == y){
                return true;
            }
        }
        return false;
    }

    /**
     * 獲取目前玩家
     */
    public int getCurrentPlayer(){
        return currentPlayer;
    }

    /**
     * 將目前玩家調換
     */
    public void changeCurrentPlayer(){
        currentPlayer = currentPlayer == 1 ?  2 : 1;
    }


    public static class Judging{

        private int leftWinLine = 0, rightWinLine = 0,onWinLine = 0,downWinLine=0,leftSlashON=0,leftSlashDown=0,rightSlashON=0,rightSlashDown=0;
        private GoBang.UI UI = main.getUI();
        public int[] judgingHorizontalineLeft(int[] point, int size, ArrayList blockOrWhite) {

            int Loc01[] = point;

            for (int i = 0; i < size; i++) {
                int Loc02[] = (int[]) blockOrWhite.get(i);
                if (Loc01[1] == Loc02[1]) {

                    if (Loc01[0] - 1 == Loc02[0]) {

                        Loc01 = Loc02;
                        leftWinLine++;
                        return judgingHorizontalineLeft(Loc01, size, blockOrWhite);
                    }

                }

            }
            return null;
        }

        public int[] judgingHorizontalineRight(int[] point , int size, ArrayList blockOrWhite) {

            int Loc01[] = point;

            for (int i = 0; i < size; i++) {
                int Loc02[] = (int[]) blockOrWhite.get(i);
                if (Loc01[1] == Loc02[1]) {

                    if (Loc01[0] + 1 == Loc02[0]) {
                        Loc01 = Loc02;
                        rightWinLine++;
                        return judgingHorizontalineRight(Loc01, size, blockOrWhite);
                    }
                }
            }
            return null;
        }

        public int[] judgingStraightlineDown(int[] point, int size, ArrayList blockOrWhite) {

            int Loc01[] = point;

            for (int i = 0; i < size; i++) {
                int Loc02[] = (int[]) blockOrWhite.get(i);

                if (Loc01[0] == Loc02[0]) {

                    if (Loc01[1] - 1  == Loc02[1]) {
                        Loc01 = Loc02;
                        downWinLine++;
                        return judgingStraightlineDown(Loc01,size,blockOrWhite);
                    }

                }


            }
            return null;
        }

        public int[] judgingStraightlineOn(int[] point , int size, ArrayList blockOrWhite) {

            int Loc01[] = point;

            for (int i = 0; i < size; i++) {
                int Loc02[] = (int[]) blockOrWhite.get(i);
                if(Loc01[0] == Loc02[0]){
                    if (Loc01[1] + 1  == Loc02[1]) {
                        Loc01 = Loc02;
                        onWinLine++;
                        return judgingStraightlineOn(Loc01,size,blockOrWhite);
                    }
                }

            }
            return null;
        }
        public int[] judgingRightslashDown(int[] point, int size, ArrayList blockOrWhite) {

            int Loc01[] = point;   //取得棋子放置座標

            for (int i = 0; i < size; i++) {
                int Loc02[] = (int[]) blockOrWhite.get(i);  //取得所有黑棋或白棋座標 取決於當下回合使用的棋子

                if (Loc01[0]+1 == Loc02[0]) {               //假設白旗回合 只要放置棋子 原本棋子在X的位子X+1 = 所有白棋中的一個X(表示X+1上也是有白棋)
                    if(Loc01[1]+1 == Loc02[1]){             //因為是判斷斜的所以 Y也要+1(因為上面的X已經過濾掉其他X，所以現在要判斷Y的座標是否一樣)
                        Loc01 = Loc02;                      //如果一樣的話 就等於到Loc01 要進到遞迴 在判斷下一個x+1 y+1
                        leftSlashDown++;                    //計算左斜下的相連棋子數量 最後要跟右斜上的相連棋子數量合併 判斷有沒有>=5
                        return judgingRightslashDown(Loc01,size,blockOrWhite);      //用找到的棋子繼續找下一個棋子 x+1 y+1
                    }
                }
            }
            return null;
        }

        public int[] judgingRightslashOn(int[] point , int size, ArrayList blockOrWhite) {

            int Loc01[] = point;

            for (int i = 0; i < size; i++) {
                int Loc02[] = (int[]) blockOrWhite.get(i);
                if (Loc01[0]-1 == Loc02[0]){
                    if(Loc01[1]-1 == Loc02[1]){
                        Loc01 = Loc02;
                        leftSlashON++;
                        return judgingRightslashOn(Loc01,size,blockOrWhite);
                    }
                }

            }
            return null;
        }
        public int[] judgingLeftslashDown(int[] point, int size, ArrayList blockOrWhite) {

            int Loc01[] = point;

            for (int i = 0; i < size; i++) {
                int Loc02[] = (int[]) blockOrWhite.get(i);

                if (Loc01[0]-1 == Loc02[0]) {
                    if (Loc01[1]+1 == Loc02[1]){
                        Loc01 = Loc02;
                        rightSlashDown++;
                        return judgingLeftslashDown(Loc01,size,blockOrWhite);
                    }
                }

            }
            return null;
        }

        public int[] judgingLeftslashOn(int[] point , int size, ArrayList blockOrWhite) {

            int Loc01[] = point;

            for (int i = 0; i < size; i++) {
                int Loc02[] = (int[]) blockOrWhite.get(i);
                if (Loc01[0]+1 == Loc02[0]) {
                    if (Loc01[1]-1 == Loc02[1]){
                        Loc01 = Loc02;
                        rightSlashON++;
                        return judgingLeftslashOn(Loc01,size,blockOrWhite);
                    }
                }
            }
            return null;
        }
        public void setNumberWinLine(){
            rightWinLine=0;
            leftWinLine=0;
            downWinLine=0;
            onWinLine=0;
            leftSlashDown=0;
            leftSlashON=0;
            rightSlashDown=0;
            rightSlashON=0;
        }

        public void lineWin(int player){
            if(player==1){
                if ((rightWinLine+leftWinLine>=4)||(onWinLine+downWinLine>=4)||(leftSlashDown+leftSlashON>=4)||(rightSlashON+rightSlashDown>=4)){
                    UI.winFram();
                }

            }
            if(player==2){
                if((rightWinLine+leftWinLine>=4)||(onWinLine+downWinLine>=4)||(leftSlashDown+leftSlashON>=4)||(rightSlashON+rightSlashDown>=4)){
                    UI.winFram();
                }
            }
        }
        public void judgingAndBureau(){
            if(chessMove==225){
                UI.andBureauFram();
                chessMove=0;
            }
        }
    }

    public static class Score {

        public void allChess(){
            int bigScore=0;
            int bigX=0;
            int bigY=0;
            for(int i =0 ; i<=14; i++){
                for(int j=0 ; j<=14 ;j++){
                    if(allPlayChessed[i][j]==0){
                        allScoreChessed[i][j]=juddingScore(i,j);
                        System.out.println("i:"+i+" j:"+j+" " + allScoreChessed[i][j]);
                    }

                }

            }

            for(int i =0 ; i<=14; i++){
                for(int j=0 ; j<=14 ;j++){
                    if(allScoreChessed[i][j]>=bigScore){
                        bigScore=allScoreChessed[i][j];
                        bigX=i;
                        bigY=j;
                    }
                }
            }
            System.out.println("x:"+bigX+" y:"+bigY);

            main.mouseListener.aiPlayChess(bigX,bigY);
            for(int i =0 ; i<=14; i++){
                for(int j=0 ; j<=14 ;j++){
                    allScoreChessed[i][j]=0;
                }
            }
        }

        public int juddingScore(int i,int j){
            int score=0;
            int count=0;
            if(i+1<=14){
                if((allPlayChessed[i+1][j]==1)){
                    count++;
                    if(i+2<=14){
                        if((allPlayChessed[i+2][j]==1)){
                            count++;
                            if(i+3<=14){
                                if((allPlayChessed[i+3][j]==1)||(allPlayChessed[i+3][j]==0)){
                                    count++;
                                    if(i+4<=14){
                                        if((allPlayChessed[i+4][j]==1)||(allPlayChessed[i+4][j]==0)){
                                            count++;
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
            if(count>=1){
                score+=10;
                if(count>=2){
                    score+=50;
                    if(count>=3){
                        score+=100;
                        if(count>=4){
                            score+=3000;
                        }
                    }
                }
            }
            count=0;
            if(i-1>=0){
                if((allPlayChessed[i-1][j]==1)){
                    count++;
                    if(i-2>=0){
                        if((allPlayChessed[i-2][j]==1)){
                            count++;
                            if(i-3>=0){
                                if((allPlayChessed[i-3][j]==1)||(allPlayChessed[i-3][j]==0)){
                                    count++;
                                    if(i-4>=0){
                                        if((allPlayChessed[i-4][j]==1)||(allPlayChessed[i-4][j]==0)){
                                            count++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if(count>=1){
                score+=10;
                if(count>=2){
                    score+=50;
                    if(count>=3){
                        score+=100;
                        if(count>=4){
                            score+=3000;
                        }
                    }
                }
            }
            count=0;
            if(j+1<=14){
                if((allPlayChessed[i][j+1]==1)){
                    count++;
                    if(j+2<=14){
                        if((allPlayChessed[i][j+2]==1)){
                            count++;
                            if(j+3<=14){
                                if((allPlayChessed[i][j+3]==1)||(allPlayChessed[i][j+3]==0)){
                                    count++;
                                    if(j+4<=14){
                                        if((allPlayChessed[i][j+4]==1)||(allPlayChessed[i][j+4]==0)){
                                            count++;
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
            if(count>=1){
                score+=10;
                if(count>=2){
                    score+=50;
                    if(count>=3){
                        score+=100;
                        if(count>=4){
                            score+=3000;
                        }
                    }
                }
            }
            count=0;
            if(j-1>=0){
                if((allPlayChessed[i][j-1]==1)){
                    count++;
                    if(j-2>=0){
                        if((allPlayChessed[i][j-2]==1)){
                            count++;
                            if(j-3>=0){
                                if((allPlayChessed[i][j-3]==1)||(allPlayChessed[i][j-3]==0)){
                                    count++;
                                    if(j-4>=0){
                                        if((allPlayChessed[i][j-4]==1)||(allPlayChessed[i][j-4]==0)){
                                            count++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if(count>=1){
                score+=10;
                if(count>=2){
                    score+=50;
                    if(count>=3){
                        score+=100;
                        if(count>=4){
                            score+=3000;
                        }
                    }
                }
            }
            count=0;
            if((j+1<=14)&&(i+1<=14)){
                if((allPlayChessed[i+1][j+1]==1)){
                    count++;
                    if((j+2<=14)&&(i+2<=14)){
                        if((allPlayChessed[i+2][j+2]==1)){
                            count++;
                            if((j+3<=14)&&(i+3<=14)){
                                if((allPlayChessed[i+3][j+3]==1)||(allPlayChessed[i+3][j+3]==0)){
                                    count++;
                                    if((j+4<=14)&&(i+4<=14)){
                                        if((allPlayChessed[i+4][j+4]==1)||(allPlayChessed[i+4][j+4]==0)){
                                            count++;
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
            if(count>=1){
                score+=10;
                if(count>=2){
                    score+=50;
                    if(count>=3){
                        score+=100;
                        if(count>=4){
                            score+=3000;
                        }
                    }
                }
            }
            count=0;
            if((j-1>=0)&&(i-1>=0)){
                if((allPlayChessed[i-1][j-1]==1)){
                    count++;
                    if((j-2>=0)&&(i-2>=0)){
                        if((allPlayChessed[i-2][j-2]==1)){
                            count++;
                            if((j-3>=0)&&(i-3>=0)){
                                if((allPlayChessed[i-3][j-3]==1)||(allPlayChessed[i-3][j-3]==0)){
                                    count++;
                                    if((j-4>=0)&&(i-4>=0)){
                                        if((allPlayChessed[i-4][j-4]==1)||(allPlayChessed[i-4][j-4]==0)){
                                            count++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if(count>=1){
                score+=10;
                if(count>=2){
                    score+=50;
                    if(count>=3){
                        score+=100;
                        if(count>=4){
                            score+=3000;
                        }
                    }
                }
            }
            count=0;
            if((j-1>=0)&&(i+1<=14)){
                if((allPlayChessed[i+1][j-1]==1)){
                    count++;
                    if((j-2>=0)&&(i+2<=14)){
                        if((allPlayChessed[i+2][j-2]==1)){
                            count++;
                            if((j-3>=0)&&(i+3<=14)){
                                if((allPlayChessed[i+3][j-3]==1)||(allPlayChessed[i+3][j-3]==0)){
                                    count++;
                                    if((j-4>=0)&&(i+4<=14)){
                                        if((allPlayChessed[i+4][j-4]==1)||(allPlayChessed[i+4][j-4]==0)){
                                            count++;
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
            if(count>=1){
                score+=10;
                if(count>=2){
                    score+=50;
                    if(count>=3){
                        score+=100;
                        if(count>=4){
                            score+=3000;
                        }
                    }
                }
            }
            count=0;
            if((j+1<=14)&&(i-1>=0)){
                if((allPlayChessed[i-1][j+1]==1)){
                    count++;
                    if((j+2<=14)&&(i-2>=0)){
                        if((allPlayChessed[i-2][j+2]==1)){
                            count++;
                            if((j+3<=14)&&(i-3>=0)){
                                if((allPlayChessed[i-3][j+3]==1)||(allPlayChessed[i-3][j+3]==0)){
                                    count++;
                                    if((j+4<=14)&&(i-4>=0)){
                                        if((allPlayChessed[i-4][j+4]==1)||(allPlayChessed[i-4][j+4]==0)){
                                            count++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if(count>=1){
                score+=10;
                if(count>=2){
                    score+=50;
                    if(count>=3){
                        score+=100;
                        if(count>=4){
                            score+=3000;
                        }
                    }
                }
            }

            return score;
        }

    }

}
