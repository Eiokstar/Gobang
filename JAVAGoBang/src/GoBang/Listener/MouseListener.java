package GoBang.Listener;

import GoBang.MoveCoordinator;
import GoBang.main;

import java.awt.*;
import java.awt.event.MouseEvent;
import javax.swing.*;

import static GoBang.main.game;

public class MouseListener implements java.awt.event.MouseListener {

    private GoBang.UI UI = main.getUI();
    private MoveCoordinator moveCoordinator = main.getMoveCoordinator();

    @Override
    public void mouseClicked(MouseEvent e) {
        // 遊戲未開始時不接受點擊
        if (!game.Started) {
            return;
        }

        // 僅限棋盤區域有效
        if (!(e.getSource() instanceof GoBang.UI.BoardPanel)) {
            return;
        }

        // 取得棋盤點擊位置
        int[] loc = UI.resolveBoardClick(e.getPoint());
        if (loc == null) {
            return;
        }

        // 若是玩家 vs AI 模式，且輪到 AI 時不允許手動點擊
        if (game.vsComputerMode && game.getCurrentPlayer() == 2) {
            return;
        }

        // 聯機模式下若不是本地回合也不允許點擊
        if (game.onlineMode && !game.onlineMyTurn) {
            return;
        }

        // 處理本地落子
        moveCoordinator.handleLocalMove(loc[0], loc[1]);
    }

    // === AI 落子 ===
    public void aiPlayChess(int x, int y) {
        moveCoordinator.handleAiMove(x, y);
    }

    // === 遠端玩家落子 ===
    public void handleRemoteMove(int x, int y) {
        moveCoordinator.handleRemoteMove(x, y);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // 不使用
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // 不使用
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
