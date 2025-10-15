package GoBang.Listener;

import GoBang.MoveCoordinator;
import GoBang.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

import static GoBang.main.game;



public class MouseListener implements java.awt.event.MouseListener {
    private GoBang.UI UI = main.getUI();
    private MoveCoordinator moveCoordinator = main.getMoveCoordinator();


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

        moveCoordinator.handleLocalMove(loc[0], loc[1]);
    }

    public void aiPlayChess(int x, int y){
        moveCoordinator.handleAiMove(x, y);
    }

    public void handleRemoteMove(int x,int y){
        moveCoordinator.handleRemoteMove(x, y);
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
