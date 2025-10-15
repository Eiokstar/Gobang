package GoBang;

import GoBang.Listener.ActionListener;
import GoBang.Listener.MouseListener;
import GoBang.Game.Judging;
import GoBang.Game.Score;
import GoBang.network.NetworkManager;

import javax.swing.*;

public class main {

    public static main instance;
    public static UI UI = new UI();
    public static Game game = new Game();
    public static Judging judging = new Judging();
    public static Score score = new Score();
    public static NetworkManager networkManager = new NetworkManager();
    public static MoveCoordinator moveCoordinator = new MoveCoordinator(game, UI, judging, score, networkManager);
    public ActionListener actionListener = new ActionListener();
    public static MouseListener mouseListener = new MouseListener();

    public static void main(String[] args) {
        instance = new main();
        UI.showUI();
    }

    /** 🔧 修正：建構子名稱需與類別名稱相同 (main)，不是 Main */
    public main() {
        // 設定網路事件處理
        networkManager.setMoveHandler(new NetworkManager.MoveHandler() {
            @Override
            public void onMoveReceived(int x, int y) {
                SwingUtilities.invokeLater(() -> moveCoordinator.handleRemoteMove(x, y));
            }

            @Override
            public void onConnectionEstablished(boolean asHost) {
                game.Started = true;
                game.onlineMode = true;
                game.onlineHost = asHost;
                game.onlineMyTurn = asHost;

                SwingUtilities.invokeLater(() -> {
                    UI.setPlayersLabel("本地玩家  VS  遠端玩家");
                    UI.setRoundLabel();
                    UI.setModeControlsVisible(false);
                    String message = asHost ? "玩家已連線，輪到您先手。" : "成功連線，等待對手落子。";
                    JOptionPane.showMessageDialog(UI.windows, message, "連線成功", JOptionPane.INFORMATION_MESSAGE);
                });
            }

            @Override
            public void onConnectionClosed(String message) {
                game.onlineMode = false;
                game.onlineHost = false;
                game.onlineMyTurn = false;
                game.Started = false;
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(UI.windows, message, "連線中斷", JOptionPane.WARNING_MESSAGE);
                    UI.reSetUIAndGame();
                });
            }
        });
    }

    /** 🔧 修正：getInstance() 的回傳型別與類別名稱一致 */
    public static main getInstance() {
        return instance;
    }

    /** 重新初始化遊戲資料，但保留難度設定 */
    public static void initGame() {
        Game.Difficulty difficulty = game.getAiDifficulty();
        game = new Game();
        game.setAiDifficulty(difficulty);

        moveCoordinator.setGame(game);
        moveCoordinator.setJudging(judging);
        moveCoordinator.setScore(score);
        moveCoordinator.setUi(UI);
        moveCoordinator.setNetworkManager(networkManager);
    }

    public static UI getUI() {
        return UI;
    }

    public static MouseListener getMouseListener() {
        return mouseListener;
    }

    public static MoveCoordinator getMoveCoordinator() {
        return moveCoordinator;
    }
}
