package GoBang.Listener;

import GoBang.MoveCoordinator;
import GoBang.main;

import java.awt.*;
import java.awt.event.MouseEvent;

import static GoBang.main.game;

public class MouseListener implements java.awt.event.MouseListener {
    private GoBang.UI UI = main.getUI();
    private MoveCoordinator moveCoordinator = main.getMoveCoordinator();

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!game.Started) {
            return;
        }
        if (!(e.getSource() instanceof GoBang.UI.BoardPanel)) {
            return;
        }

        int[] loc = UI.resolveBoardClick(e.getPoint());
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

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() instanceof GoBang.UI.BoardPanel) {
            UI.windows.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() instanceof GoBang.UI.BoardPanel) {
            UI.windows.setCursor(Cursor.getDefaultCursor());
        }
    }
}
