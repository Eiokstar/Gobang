package GoBang;


import java.util.ArrayList;
import java.util.Random;

import GoBang.Listener.MouseListener;
import GoBang.Main;

public class Game {
    private int currentPlayer=1;

    public enum Difficulty {
        EASY("簡單AI"),
        MEDIUM("中等AI"),
        HARD("困難AI");

        private final String displayName;

        Difficulty(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    private Difficulty aiDifficulty = Difficulty.MEDIUM;

    public boolean Started;
    public boolean vsHumanMode;
    public boolean vsComputerMode;
    public boolean firstHand;
    public boolean backHand;
    public boolean onlineMode;
    public boolean onlineHost;
    public boolean onlineMyTurn;
    public static int chessMove=0;
    public static int[][] allScoreChessed = {{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
    public static int[][] allPlayChessed = {{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
    private ArrayList<ArrayList<int[]>> PlacedPieces;
    private GoBang.UI UI = Main.getUI();
    private static GoBang.Listener.MouseListener MosList = Main.getMouseListener();
    Game(){
        initPlacePieces();
        initStatus();
        reGamePlayChess();
        resetScoreBoard();
    }
    public void initStatus(){
        Started = false;
        vsHumanMode = false;
        vsComputerMode = false;
        firstHand = false;
        backHand =false;
        onlineMode = false;
        onlineHost = false;
        onlineMyTurn = false;
    }

    public void reGamePlayChess(){
        for(int i=0;i<=14;i++){
            for(int j=0;j<=14;j++){
                allPlayChessed[i][j]=0;
                allScoreChessed[i][j]=0;
            }
        }
    }

    private void resetScoreBoard(){
        for(int i=0;i<=14;i++){
            for(int j=0;j<=14;j++){
                allScoreChessed[i][j]=0;
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
        return allPlayChessed[x][y] != 0;
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

    public Difficulty getAiDifficulty() {
        return aiDifficulty;
    }

    public void setAiDifficulty(Difficulty aiDifficulty) {
        if (aiDifficulty != null) {
            this.aiDifficulty = aiDifficulty;
        }
    }


    public static class Judging{

        private int leftWinLine = 0, rightWinLine = 0,onWinLine = 0,downWinLine=0,leftSlashON=0,leftSlashDown=0,rightSlashON=0,rightSlashDown=0;
        private GoBang.UI UI = Main.getUI();
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

        private static final int[][] DIRECTIONS = {{1,0},{0,1},{1,1},{1,-1}};
        private final Random random = new Random();

        public void allChess(){
            Game.Difficulty difficulty = Main.game.getAiDifficulty();
            if(difficulty == Game.Difficulty.EASY){
                makeRandomMove();
            }else if(difficulty == Game.Difficulty.HARD){
                makeBestMove(true);
            }else{
                makeBestMove(false);
            }
        }

        public int juddingScore(int i,int j){
            int aiPlayer = Main.game.getCurrentPlayer();
            return juddingScore(i,j,aiPlayer);
        }

        public int juddingScore(int i,int j,int player){
            int aiPlayer = player;
            int opponent = aiPlayer == 1 ? 2 : 1;
            int attackScore = evaluateForPlayer(i,j,aiPlayer);
            int defendScore = evaluateForPlayer(i,j,opponent);
            return attackScore * 2 + defendScore;
        }

        private void makeRandomMove(){
            ArrayList<int[]> available = new ArrayList<>();
            for(int i =0 ; i<=14; i++){
                for(int j=0 ; j<=14 ;j++){
                    if(allPlayChessed[i][j]==0){
                        available.add(new int[]{i,j});
                    }
                    allScoreChessed[i][j]=0;
                }
            }

            if(available.isEmpty()){
                return;
            }

            int[] move;
            if(Main.game.chessMove==0){
                move = new int[]{7,7};
            }else{
                move = available.get(random.nextInt(available.size()));
            }
            Main.mouseListener.aiPlayChess(move[0],move[1]);
        }

        private void makeBestMove(boolean hardMode){
            int bestScore = Integer.MIN_VALUE;
            int bestX = -1;
            int bestY = -1;
            int aiPlayer = Main.game.getCurrentPlayer();

            for(int i =0 ; i<=14; i++){
                for(int j=0 ; j<=14 ;j++){
                    if(allPlayChessed[i][j]==0){
                        int score = hardMode ? calculateHardScore(i,j,aiPlayer) : juddingScore(i,j,aiPlayer);
                        allScoreChessed[i][j]=score;
                        if(score>bestScore || (score==bestScore && isCloserToCenter(i,j,bestX,bestY))){
                            bestScore=score;
                            bestX=i;
                            bestY=j;
                        }
                    }else{
                        allScoreChessed[i][j]=0;
                    }
                }
            }

            if(bestX>=0 && bestY>=0){
                Main.mouseListener.aiPlayChess(bestX,bestY);
            }
        }

        private int calculateHardScore(int x,int y,int aiPlayer){
            int baseScore = juddingScore(x,y,aiPlayer);
            int opponent = aiPlayer == 1 ? 2 : 1;
            allPlayChessed[x][y] = aiPlayer;
            int opponentBest = Integer.MIN_VALUE;

            for(int i =0 ; i<=14; i++){
                for(int j=0 ; j<=14 ;j++){
                    if(allPlayChessed[i][j]==0){
                        int score = juddingScore(i,j,opponent);
                        if(score>opponentBest){
                            opponentBest = score;
                        }
                    }
                }
            }

            allPlayChessed[x][y] = 0;
            if(opponentBest == Integer.MIN_VALUE){
                opponentBest = 0;
            }
            return baseScore * 2 - opponentBest;
        }

        private boolean isCloserToCenter(int x,int y,int currentBestX,int currentBestY){
            if(currentBestX<0 || currentBestY<0){
                return true;
            }
            int center = 7;
            int currentDist = Math.abs(currentBestX-center) + Math.abs(currentBestY-center);
            int newDist = Math.abs(x-center) + Math.abs(y-center);
            return newDist < currentDist;
        }

        private int evaluateForPlayer(int x,int y,int player){
            int total = 0;
            for(int[] dir : DIRECTIONS){
                total += evaluateDirection(x,y,dir[0],dir[1],player);
            }
            return total;
        }

        private int evaluateDirection(int x,int y,int dx,int dy,int player){
            int count = 1;
            int openEnds = 0;

            int i = x + dx;
            int j = y + dy;
            while(inBounds(i,j) && allPlayChessed[i][j] == player){
                count++;
                i += dx;
                j += dy;
            }
            if(inBounds(i,j) && allPlayChessed[i][j] == 0){
                openEnds++;
            }

            i = x - dx;
            j = y - dy;
            while(inBounds(i,j) && allPlayChessed[i][j] == player){
                count++;
                i -= dx;
                j -= dy;
            }
            if(inBounds(i,j) && allPlayChessed[i][j] == 0){
                openEnds++;
            }

            if(count >= 5){
                return 100000;
            }
            if(count == 4 && openEnds == 2){
                return 10000;
            }
            if(count == 4 && openEnds == 1){
                return 4000;
            }
            if(count == 3 && openEnds == 2){
                return 2000;
            }
            if(count == 3 && openEnds == 1){
                return 400;
            }
            if(count == 2 && openEnds == 2){
                return 200;
            }
            if(count == 2 && openEnds == 1){
                return 40;
            }
            if(count == 1 && openEnds == 2){
                return 10;
            }
            return 2;
        }

        private boolean inBounds(int x,int y){
            return x>=0 && x<=14 && y>=0 && y<=14;
        }

    }

}
