package GoBang;

import GoBang.Game.Judging;
import GoBang.Game.Score;
import GoBang.network.NetworkManager;

import javax.swing.*;
import java.util.ArrayList;

public class MoveCoordinator {

    public enum MoveSource {
        LOCAL,
        AI,
        REMOTE
    }

    private Game game;
    private UI ui;
    private Judging judging;
    private Score score;
    private NetworkManager networkManager;

    public MoveCoordinator(Game game, UI ui, Judging judging, Score score, NetworkManager networkManager) {
        this.game = game;
        this.ui = ui;
        this.judging = judging;
        this.score = score;
        this.networkManager = networkManager;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setUi(UI ui) {
        this.ui = ui;
    }

    public void setJudging(Judging judging) {
        this.judging = judging;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public void setNetworkManager(NetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    // === 玩家本地落子 ===
    public void handleLocalMove(int boardX, int boardY) {
        placePiece(boardX, boardY, MoveSource.LOCAL);
    }

    // === AI 落子 ===
    public void handleAiMove(int boardX, int boardY) {
        if (game.getCurrentPlayer() == 2) {
            placePiece(boardX, boardY, MoveSource.AI);
        }
    }

    // === 遠端玩家落子 ===
    public void handleRemoteMove(int boardX, int boardY) {
        if (game.onlineMode) {
            placePiece(boardX, boardY, MoveSource.REMOTE);
        }
    }

    // === 通用落子處理 ===
    private void placePiece(int boardX, int boardY, MoveSource source) {
        if (!game.Started) return;
        if (game.LocIsPlaced(boardX, boardY)) return;

        int placedPlayer = game.getCurrentPlayer();

        // 在 UI 上繪製棋子
        JLabel targetCell = ui.labels.boardButtons[boardX][boardY];
        ui.DrawPiecesInBoard(targetCell.getX(), targetCell.getY() + 28, placedPlayer);

        // 更新遊戲資料
        game.addPointToPlacedPieces(placedPlayer, boardX, boardY);
        game.allPlayChessed[boardX][boardY] = placedPlayer;
        game.chessMove++;

        // 判斷勝負
        judging.judgingAndBureau();
        judgingBlockLeftLine(placedPlayer);

        // 換手
        game.changeCurrentPlayer();

        // 更新畫面
        ui.refreshBoard();

        // 根據來源後續處理（AI、自動同步、遠端）
        handlePostMove(source, boardX, boardY);
    }

    // === 後續動作（網路 / AI） ===
    private void handlePostMove(MoveSource source, int boardX, int boardY) {
        if (game.onlineMode) {
            if (source == MoveSource.LOCAL) {
                if (networkManager != null) {
                    networkManager.sendMove(boardX, boardY);
                }
                game.onlineMyTurn = false;
            } else if (source == MoveSource.REMOTE) {
                game.onlineMyTurn = true;
            }
            ui.setRoundLabel();
            return;
        }

        // 玩家 vs AI 模式，玩家落子後 AI 行動
        if (game.vsComputerMode && source == MoveSource.LOCAL) {
            SwingUtilities.invokeLater(() -> score.allChess());
        }

        ui.setRoundLabel();
    }

    // === 勝負判定 ===
    private void judgingBlockLeftLine(int placedPlayer) {
        if (placedPlayer == 1) {
            int[] lastWhite = game.getWhitePlacedPieces().get(game.getWhitePlacedPieces().size() - 1);
            int count = game.getWhitePlacedPieces().size();
            ArrayList<int[]> list = game.getWhitePlacedPieces();

            judging.setNumberWinLine();
            judging.judgingHorizontalineLeft(lastWhite, count, list);
            judging.judgingHorizontalineRight(lastWhite, count, list);
            judging.judgingStraightlineDown(lastWhite, count, list);
            judging.judgingStraightlineOn(lastWhite, count, list);
            judging.judgingRightslashDown(lastWhite, count, list);
            judging.judgingRightslashOn(lastWhite, count, list);
            judging.judgingLeftslashDown(lastWhite, count, list);
            judging.judgingLeftslashOn(lastWhite, count, list);
            judging.lineWin(placedPlayer);
        }

        if (placedPlayer == 2) {
            int[] lastBlack = game.getBlackPlacedPieces().get(game.getBlackPlacedPieces().size() - 1);
            int count = game.getBlackPlacedPieces().size();
            ArrayList<int[]> list = game.getBlackPlacedPieces();

            judging.setNumberWinLine();
            judging.judgingHorizontalineLeft(lastBlack, count, list);
            judging.judgingHorizontalineRight(lastBlack, count, list);
            judging.judgingStraightlineDown(lastBlack, count, list);
            judging.judgingStraightlineOn(lastBlack, count, list);
            judging.judgingRightslashDown(lastBlack, count, list);
            judging.judgingRightslashOn(lastBlack, count, list);
            judging.judgingLeftslashDown(lastBlack, count, list);
            judging.judgingLeftslashOn(lastBlack, count, list);
            judging.lineWin(placedPlayer);
        }
    }
}
