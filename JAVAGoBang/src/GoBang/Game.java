package GoBang;


import java.util.ArrayList;
import java.util.Random;

import GoBang.Listener.MouseListener;
import GoBang.main;

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
    private GoBang.UI UI = main.getUI();
    private static GoBang.Listener.MouseListener MosList = main.getMouseListener();
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

        private static final int[][] DIRECTIONS = {{1,0},{0,1},{1,1},{1,-1}};
        private final Random random = new Random();

        public void allChess(){
            Game.Difficulty difficulty = main.game.getAiDifficulty();
            if(difficulty == Game.Difficulty.EASY){
                // 原本的中等難度評估邏輯移至簡單難度
                makeBestMove(false);
            }else if(difficulty == Game.Difficulty.MEDIUM){
                // 新的中等難度：使用淺層極大極小策略，兼顧攻守
                makeStrategicMove(2, 12, false);
            }else{
                // 新的困難難度：更深層的搜尋並偏向進攻
                makeStrategicMove(3, 16, true);
            }
        }

        public int juddingScore(int i,int j){
            int aiPlayer = main.game.getCurrentPlayer();
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
            if(main.game.chessMove==0){
                move = new int[]{7,7};
            }else{
                move = available.get(random.nextInt(available.size()));
            }
            main.mouseListener.aiPlayChess(move[0],move[1]);
        }

        private void makeBestMove(boolean hardMode){
            int bestScore = Integer.MIN_VALUE;
            int bestX = -1;
            int bestY = -1;
            int aiPlayer = main.game.getCurrentPlayer();

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
                main.mouseListener.aiPlayChess(bestX,bestY);
            }
        }

        private void makeStrategicMove(int depth,int candidateWidth,boolean aggressive){
            int aiPlayer = main.game.getCurrentPlayer();
            int opponent = aiPlayer == 1 ? 2 : 1;

            int[] immediateWin = findImmediateLineCompletion(aiPlayer);
            if(immediateWin != null){
                main.mouseListener.aiPlayChess(immediateWin[0],immediateWin[1]);
                return;
            }

            int[] urgentBlock = findImmediateLineCompletion(opponent);
            if(urgentBlock != null){
                main.mouseListener.aiPlayChess(urgentBlock[0],urgentBlock[1]);
                return;
            }

            ArrayList<int[]> candidates = generateCandidateMoves(aiPlayer,candidateWidth);
            if(candidates.isEmpty()){
                makeBestMove(false);
                return;
            }

            int bestScore = Integer.MIN_VALUE;
            int bestX = -1;
            int bestY = -1;

            for(int[] move : candidates){
                int x = move[0];
                int y = move[1];
                allPlayChessed[x][y] = aiPlayer;
                int score;
                if(completesFive(x,y,aiPlayer)){
                    score = Integer.MAX_VALUE/4;
                }else{
                    score = minimax(depth - 1,false,aiPlayer,opponent,candidateWidth,aggressive,Integer.MIN_VALUE/2,Integer.MAX_VALUE/2);
                }
                allPlayChessed[x][y] = 0;

                if(score>bestScore || (score==bestScore && isCloserToCenter(x,y,bestX,bestY))){
                    bestScore = score;
                    bestX = x;
                    bestY = y;
                }
            }

            if(bestX>=0 && bestY>=0){
                main.mouseListener.aiPlayChess(bestX,bestY);
            }else{
                makeBestMove(false);
            }
        }

        private int[] findImmediateLineCompletion(int player){
            for(int i=0;i<=14;i++){
                for(int j=0;j<=14;j++){
                    if(allPlayChessed[i][j]!=0){
                        continue;
                    }
                    allPlayChessed[i][j] = player;
                    boolean completes = completesFive(i,j,player);
                    allPlayChessed[i][j] = 0;
                    if(completes){
                        return new int[]{i,j};
                    }
                }
            }
            return null;
        }

        private int minimax(int depth,boolean maximizing,int aiPlayer,int currentPlayer,int candidateWidth,boolean aggressive,int alpha,int beta){
            int opponent = currentPlayer == 1 ? 2 : 1;

            if(depth<=0 || isBoardFull()){
                return evaluateBoardState(aiPlayer,aggressive);
            }

            ArrayList<int[]> moves = generateCandidateMoves(currentPlayer,candidateWidth);
            if(moves.isEmpty()){
                return evaluateBoardState(aiPlayer,aggressive);
            }

            if(maximizing){
                int maxEval = Integer.MIN_VALUE;
                for(int[] move : moves){
                    int x = move[0];
                    int y = move[1];
                    allPlayChessed[x][y] = currentPlayer;
                    int eval;
                    if(completesFive(x,y,currentPlayer)){
                        eval = currentPlayer == aiPlayer ? Integer.MAX_VALUE/4 : Integer.MIN_VALUE/4;
                    }else{
                        eval = minimax(depth-1,false,aiPlayer,opponent,candidateWidth,aggressive,alpha,beta);
                    }
                    allPlayChessed[x][y] = 0;
                    if(eval>maxEval){
                        maxEval = eval;
                    }
                    if(eval>alpha){
                        alpha = eval;
                    }
                    if(beta<=alpha){
                        break;
                    }
                }
                return maxEval;
            }else{
                int minEval = Integer.MAX_VALUE;
                for(int[] move : moves){
                    int x = move[0];
                    int y = move[1];
                    allPlayChessed[x][y] = currentPlayer;
                    int eval;
                    if(completesFive(x,y,currentPlayer)){
                        eval = currentPlayer == aiPlayer ? Integer.MAX_VALUE/4 : Integer.MIN_VALUE/4;
                    }else{
                        eval = minimax(depth-1,true,aiPlayer,opponent,candidateWidth,aggressive,alpha,beta);
                    }
                    allPlayChessed[x][y] = 0;
                    if(eval<minEval){
                        minEval = eval;
                    }
                    if(eval<beta){
                        beta = eval;
                    }
                    if(beta<=alpha){
                        break;
                    }
                }
                return minEval;
            }
        }

        private ArrayList<int[]> generateCandidateMoves(int perspectivePlayer,int candidateWidth){
            ArrayList<int[]> result = new ArrayList<>();
            ArrayList<int[]> scoredMoves = new ArrayList<>();
            ArrayList<int[]> defensiveMoves = new ArrayList<>();
            boolean hasPieces = false;

            for(int i=0;i<=14;i++){
                for(int j=0;j<=14;j++){
                    if(allPlayChessed[i][j]!=0){
                        hasPieces = true;
                        continue;
                    }
                    if(!hasNearbyPieces(i,j)){
                        continue;
                    }
                    int opponent = perspectivePlayer == 1 ? 2 : 1;
                    int attackScore = evaluateForPlayer(i,j,perspectivePlayer);
                    int defendScore = evaluateForPlayer(i,j,opponent);
                    int combinedScore = attackScore * 2 + defendScore;
                    scoredMoves.add(new int[]{i,j,combinedScore,defendScore});
                    defensiveMoves.add(new int[]{i,j,defendScore});
                }
            }

            if(!hasPieces){
                result.add(new int[]{7,7});
                return result;
            }

            scoredMoves.sort((a,b) -> Integer.compare(b[2],a[2]));
            defensiveMoves.sort((a,b) -> Integer.compare(b[2],a[2]));

            int limit = candidateWidth>0 ? Math.min(candidateWidth, scoredMoves.size()) : scoredMoves.size();
            for(int idx=0; idx<limit; idx++){
                int[] move = scoredMoves.get(idx);
                result.add(new int[]{move[0],move[1]});
            }

            int extraDefensive = Math.min(3, defensiveMoves.size());
            for(int idx=0; idx<extraDefensive; idx++){
                int[] move = defensiveMoves.get(idx);
                if(!containsMove(result,move[0],move[1])){
                    result.add(new int[]{move[0],move[1]});
                }
            }
            return result;
        }

        private boolean hasNearbyPieces(int x,int y){
            for(int dx=-2; dx<=2; dx++){
                for(int dy=-2; dy<=2; dy++){
                    if(dx==0 && dy==0){
                        continue;
                    }
                    int nx = x + dx;
                    int ny = y + dy;
                    if(inBounds(nx,ny) && allPlayChessed[nx][ny]!=0){
                        return true;
                    }
                }
            }
            return false;
        }

        private boolean isBoardFull(){
            for(int i=0;i<=14;i++){
                for(int j=0;j<=14;j++){
                    if(allPlayChessed[i][j]==0){
                        return false;
                    }
                }
            }
            return true;
        }

        private int evaluateBoardState(int aiPlayer,boolean aggressive){
            int opponent = aiPlayer == 1 ? 2 : 1;
            if(hasWinningLine(aiPlayer)){
                return Integer.MAX_VALUE/4;
            }
            if(hasWinningLine(opponent)){
                return Integer.MIN_VALUE/4;
            }

            int bestAi = 0;
            int secondAi = 0;
            int bestOpponent = 0;
            int secondOpponent = 0;

            for(int i=0;i<=14;i++){
                for(int j=0;j<=14;j++){
                    if(allPlayChessed[i][j]!=0){
                        continue;
                    }
                    int aiScore = juddingScore(i,j,aiPlayer);
                    int opponentScore = juddingScore(i,j,opponent);
                    if(aiScore>bestAi){
                        secondAi = bestAi;
                        bestAi = aiScore;
                    }else if(aiScore>secondAi){
                        secondAi = aiScore;
                    }
                    if(opponentScore>bestOpponent){
                        secondOpponent = bestOpponent;
                        bestOpponent = opponentScore;
                    }else if(opponentScore>secondOpponent){
                        secondOpponent = opponentScore;
                    }
                }
            }

            if(bestAi>=100000){
                return Integer.MAX_VALUE/4;
            }
            if(bestOpponent>=100000){
                return Integer.MIN_VALUE/4;
            }

            int aiValue = bestAi + secondAi/2;
            int opponentValue = bestOpponent + secondOpponent/2;

            if(aggressive){
                aiValue = aiValue * 2;
            }
            return aiValue - opponentValue;
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

        private boolean containsMove(ArrayList<int[]> moves,int x,int y){
            for(int[] move : moves){
                if(move[0]==x && move[1]==y){
                    return true;
                }
            }
            return false;
        }

        private boolean completesFive(int x,int y,int player){
            for(int[] dir : DIRECTIONS){
                int count = 1;
                count += countStones(x,y,dir[0],dir[1],player);
                count += countStones(x,y,-dir[0],-dir[1],player);
                if(count>=5){
                    return true;
                }
            }
            return false;
        }

        private int countStones(int x,int y,int dx,int dy,int player){
            int count = 0;
            int nx = x + dx;
            int ny = y + dy;
            while(inBounds(nx,ny) && allPlayChessed[nx][ny]==player){
                count++;
                nx += dx;
                ny += dy;
            }
            return count;
        }

        private boolean hasWinningLine(int player){
            for(int i=0;i<=14;i++){
                for(int j=0;j<=14;j++){
                    if(allPlayChessed[i][j]!=player){
                        continue;
                    }
                    if(completesFive(i,j,player)){
                        return true;
                    }
                }
            }
            return false;
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
