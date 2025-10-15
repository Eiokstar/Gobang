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

    private enum MoveSource {
        LOCAL,
        AI,
        REMOTE
    }


    // 獲得 for 循環所有pointbutton 比對 x 和 y 座標 如果 = 點擊 獲得 index


    @Override
    public void mouseClicked(MouseEvent e) {
        if (!game.Started) {
            return;
        }

        int[] loc = getClickLoc(e.getComponent().getX(), e.getComponent().getY());
        if (loc == null) {
            return;
        }

        if (game.vsComputerMode && game.getCurrentPlayer() == 2) {
            return;
        }

        if (game.onlineMode && !game.onlineMyTurn) {
            return;
        }

        placePiece(loc[0], loc[1], MoveSource.LOCAL);
    }

    public void aiPlayChess(int x, int y){
        if(game.getCurrentPlayer()==2){
            placePiece(x, y, MoveSource.AI);
        }
    }

    public void handleRemoteMove(int x,int y){
        if(game.onlineMode){
            placePiece(x, y, MoveSource.REMOTE);
        }
    }

    private void placePiece(int boardX, int boardY, MoveSource source){
        if (!game.Started) {
            return;
        }

        if (game.LocIsPlaced(boardX,boardY)) {
            return;
        }

        int placedPlayer = game.getCurrentPlayer();
        JLabel checkPoint = UI.labels.boardButtons[boardX][boardY];
        UI.DrawPiecesInBoard(checkPoint.getX(),checkPoint.getY()+28, placedPlayer);
        game.addPointToPlacedPieces(placedPlayer,boardX,boardY);
        game.allPlayChessed[boardX][boardY]=placedPlayer;
        game.chessMove++;

        judging.judgingAndBureau();
        UI.DrawSamllPiecesBoard(new int[]{boardX, boardY});
        judgingBlockLeftLine(placedPlayer);

        game.changeCurrentPlayer();
        handlePostMove(source, boardX, boardY);
    }

    private void handlePostMove(MoveSource source, int boardX, int boardY){
        if(game.onlineMode){
            if(source == MoveSource.LOCAL){
                main.networkManager.sendMove(boardX, boardY);
                game.onlineMyTurn = false;
            }else if(source == MoveSource.REMOTE){
                game.onlineMyTurn = true;
            }
            UI.setRoundLabel();
            return;
        }

        if(game.vsComputerMode && source == MoveSource.LOCAL){
            SwingUtilities.invokeLater(() -> score.allChess());
        }

        UI.setRoundLabel();
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

    private void judgingBlockLeftLine(int placedPlayer){
        if(placedPlayer==1){
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

            judging.lineWin(placedPlayer);
        }
        if(placedPlayer==2){
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

            judging.lineWin(placedPlayer);
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
