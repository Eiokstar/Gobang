package GoBang.Listener;

import GoBang.Game;
import GoBang.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static GoBang.main.game;
import static GoBang.main.judging;
import static GoBang.main.score;



public class MouseListener implements java.awt.event.MouseListener {
    private GoBang.UI UI = main.getUI();


    // 獲得 for 循環所有pointbutton 比對 x 和 y 座標 如果 = 點擊 獲得 index


    @Override
    public void mouseClicked(MouseEvent e) {
        if (game.Started){
            if(game.vsHumanMode){
                addPointToBoard(e.getComponent().getX(),e.getComponent().getY());
            }
            if(game.vsComputerMode){
                notAiRound();
                if(game.playerNotAIRound){
                    addPointToBoard(e.getComponent().getX(),e.getComponent().getY());
                }

            }

        }

    }

    private void addPointToBoard(int x , int y){
        int[] loc = getClickLoc(x,y);

        if (!game.LocIsPlaced(loc[0],loc[1])) {
            UI.DrawPiecesInBoard(x,y+28, game.getCurrentPlayer());
            game.addPointToPlacedPieces(game.getCurrentPlayer(),loc[0],loc[1]);
            System.out.println("玩家放置座標 : " + loc[0] + " " + loc[1]);
            int pointX =loc[0];
            int pointY =loc[1];

            game.allPlayChessed[pointX][pointY]=2;
            game.chessMove++;

            judging.judgingAndBureau();
            UI.DrawSamllPiecesBoard(loc);
            judgingBlockLeftLine();
            game.changeCurrentPlayer();
            UI.setRoundLabel();
            int[] whitePlaychess= loc;
            game.playerNotAIRound=false;
            if(game.vsComputerMode) {
                if(game.playerNotAIRound==false){
                    score.allChess();
                }
            }
        }

    }
    public void addAIPointToBoard(int x , int y){
        int[] loc = getClickLoc(x,y);
        if (!game.LocIsPlaced(loc[0],loc[1])) {
            UI.DrawPiecesInBoard(x,y+28, game.getCurrentPlayer());
            game.addPointToPlacedPieces(game.getCurrentPlayer(),loc[0],loc[1]);
            System.out.println("玩家放置座標 : " + loc[0] + " " + loc[1]);
            int pointX=loc[0];
            int pointY=loc[1];
            game.chessMove++;
            game.allPlayChessed[pointX][pointY]=1;
            judging.judgingAndBureau();
            UI.DrawSamllPiecesBoard(loc);
            judgingBlockLeftLine();
            game.changeCurrentPlayer();
            UI.setRoundLabel();

        }

    }
    private void notAiRound(){
        if(game.vsComputerMode){
            if(game.getCurrentPlayer()==1){
                game.playerNotAIRound=true;
            }
        }
    }
    public void aiPlayChess(int x, int y){
        if(game.getCurrentPlayer()==2){
            JLabel checkPoint = UI.labels.boardButtons[x][y];
            addAIPointToBoard(checkPoint.getX(),checkPoint.getY());
        }
    }
    private int[] getClickLoc(int x , int y){
        for (int i = 0;i<15;i++){
            for (int j = 0 ; j<15 ; j++){
                JLabel checkPoint = UI.labels.boardButtons[i][j];
                int checkX = checkPoint.getX();
                int checkY = checkPoint.getY();
                if (checkX == x & checkY == y){
                    return new int[]{i,j};
                }
            }
        }
        return null;
    }

    private void judgingBlockLeftLine(){
        if(game.getCurrentPlayer()==1){
            int blackLoc01[] = game.getWhitePlacedPieces().get(game.getWhitePlacedPieces().size() - 1);
            int blackSize =game.getWhitePlacedPieces().size();
            ArrayList<int[]> blackLoc =(game.getWhitePlacedPieces());
            judging.setNumberWinLine();

            judging.judgingHorizontalineLeft(blackLoc01,blackSize,blackLoc);
            judging.judgingHorizontalineRight(blackLoc01,blackSize, blackLoc);

            judging.judgingStraightlineDown(blackLoc01,blackSize, blackLoc);
            judging.judgingStraightlineOn(blackLoc01,blackSize, blackLoc);

            judging.judgingRightslashDown(blackLoc01,blackSize, blackLoc);
            judging.judgingRightslashOn(blackLoc01,blackSize, blackLoc);

            judging.judgingLeftslashDown(blackLoc01,blackSize, blackLoc);
            judging.judgingLeftslashOn(blackLoc01,blackSize, blackLoc);

            judging.lineWin(game.getCurrentPlayer());
        }
        if(game.getCurrentPlayer()==2){
            int whiteMaxLoc01[] = game.getBlackPlacedPieces().get(game.getBlackPlacedPieces().size() - 1);
            int whiteSize =game.getBlackPlacedPieces().size();
            ArrayList<int[]> whiteLoc =(game.getBlackPlacedPieces());
            judging.setNumberWinLine();

            judging.judgingHorizontalineLeft(whiteMaxLoc01,whiteSize,whiteLoc);
            judging.judgingHorizontalineRight(whiteMaxLoc01,whiteSize,whiteLoc);

            judging.judgingStraightlineDown(whiteMaxLoc01,whiteSize,whiteLoc);
            judging.judgingStraightlineOn(whiteMaxLoc01,whiteSize,whiteLoc);

            judging.judgingRightslashDown(whiteMaxLoc01,whiteSize,whiteLoc);
            judging.judgingRightslashOn(whiteMaxLoc01,whiteSize,whiteLoc);

            judging.judgingLeftslashDown(whiteMaxLoc01,whiteSize,whiteLoc);
            judging.judgingLeftslashOn(whiteMaxLoc01,whiteSize,whiteLoc);

            judging.lineWin(game.getCurrentPlayer());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {UI.windows.setCursor(Cursor.HAND_CURSOR);}

    @Override
    public void mouseExited(MouseEvent e) {
        UI.windows.setCursor(0);
    }
}
