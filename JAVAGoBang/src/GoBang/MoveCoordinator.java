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

    public void handleLocalMove(int boardX, int boardY) {
        placePiece(boardX, boardY, MoveSource.LOCAL);
    }

    public void handleAiMove(int boardX, int boardY) {
        if (game.getCurrentPlayer() == 2) {
            placePiece(boardX, boardY, MoveSource.AI);
        }
    }

    public void handleRemoteMove(int boardX, int boardY) {
        if (game.onlineMode) {
            placePiece(boardX, boardY, MoveSource.REMOTE);
        }
    }

    private void placePiece(int boardX, int boardY, MoveSource source) {
        if (!game.Started) {
            return;
        }

        if (game.LocIsPlaced(boardX, boardY)) {
            return;
        }

        int placedPlayer = game.getCurrentPlayer();
        JLabel checkPoint = ui.labels.boardButtons[boardX][boardY];
        ui.DrawPiecesInBoard(checkPoint.getX(), checkPoint.getY() + 28, placedPlayer);
        game.addPointToPlacedPieces(placedPlayer, boardX, boardY);
        game.allPlayChessed[boardX][boardY] = placedPlayer;
        game.chessMove++;

        judging.judgingAndBureau();
        ui.DrawSamllPiecesBoard(new int[]{boardX, boardY});
        judgingBlockLeftLine(placedPlayer);

        game.changeCurrentPlayer();
        handlePostMove(source, boardX, boardY);
    }

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

        if (game.vsComputerMode && source == MoveSource.LOCAL) {
            SwingUtilities.invokeLater(() -> score.allChess());
        }

        ui.setRoundLabel();
    }

    private void judgingBlockLeftLine(int placedPlayer) {
        if (placedPlayer == 1) {
            int[] blackLoc01 = game.getWhitePlacedPieces().get(game.getWhitePlacedPieces().size() - 1);
            int blackSize = game.getWhitePlacedPieces().size();
            ArrayList<int[]> blackLoc = game.getWhitePlacedPieces();
            judging.setNumberWinLine();

            judging.judgingHorizontalineLeft(blackLoc01, blackSize, blackLoc);
            judging.judgingHorizontalineRight(blackLoc01, blackSize, blackLoc);

            judging.judgingStraightlineDown(blackLoc01, blackSize, blackLoc);
            judging.judgingStraightlineOn(blackLoc01, blackSize, blackLoc);

            judging.judgingRightslashDown(blackLoc01, blackSize, blackLoc);
            judging.judgingRightslashOn(blackLoc01, blackSize, blackLoc);

            judging.judgingLeftslashDown(blackLoc01, blackSize, blackLoc);
            judging.judgingLeftslashOn(blackLoc01, blackSize, blackLoc);

            judging.lineWin(placedPlayer);
        }
        if (placedPlayer == 2) {
            int[] whiteMaxLoc01 = game.getBlackPlacedPieces().get(game.getBlackPlacedPieces().size() - 1);
            int whiteSize = game.getBlackPlacedPieces().size();
            ArrayList<int[]> whiteLoc = game.getBlackPlacedPieces();
            judging.setNumberWinLine();

            judging.judgingHorizontalineLeft(whiteMaxLoc01, whiteSize, whiteLoc);
            judging.judgingHorizontalineRight(whiteMaxLoc01, whiteSize, whiteLoc);

            judging.judgingStraightlineDown(whiteMaxLoc01, whiteSize, whiteLoc);
            judging.judgingStraightlineOn(whiteMaxLoc01, whiteSize, whiteLoc);

            judging.judgingRightslashDown(whiteMaxLoc01, whiteSize, whiteLoc);
            judging.judgingRightslashOn(whiteMaxLoc01, whiteSize, whiteLoc);

            judging.judgingLeftslashDown(whiteMaxLoc01, whiteSize, whiteLoc);
            judging.judgingLeftslashOn(whiteMaxLoc01, whiteSize, whiteLoc);

            judging.lineWin(placedPlayer);
        }
    }
}
