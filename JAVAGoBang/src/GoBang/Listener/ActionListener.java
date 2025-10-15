package GoBang.Listener;

import GoBang.main;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.*;

import static GoBang.main.game;
import static GoBang.main.score;

public class ActionListener implements java.awt.event.ActionListener {

    private static final String MODE_PVP = "玩家VS玩家";
    private static final String MODE_AI = "玩家VS高級AI";
    private static final String MODE_AI_ALT = "玩家VS電腦";
    private static final String MODE_ONLINE = "聯機對戰";

    private GoBang.UI UI = main.getUI();

    @Override
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        switch (command) {
            case MODE_PVP:
                selectHumanVsHuman();
                break;
            case MODE_AI:
            case MODE_AI_ALT:
                selectHumanVsAI();
                break;
            case MODE_ONLINE:
                selectOnlineMode();
                break;
            case "重新":
                resetGame();
                break;
            case "開始":
                startGame();
                break;
            case "認輸":
                surrender();
                break;
            case "悔棋":
                undoMove();
                break;
            case "確定":
                confirmDialog();
                break;
            case "回復棋子":
                restorePieces();
                break;
            case "先手":
                chooseFirstHand();
                break;
            case "後手":
                chooseBackHand();
                break;
            default:
                break;
        }
    }

    private void selectHumanVsHuman(){
        if(game.Started){
            return;
        }
        main.networkManager.close();
        game.vsHumanMode = true;
        game.vsComputerMode = false;
        game.onlineMode = false;
        game.firstHand = false;
        game.backHand = false;
        UI.setPlayersLabel("玩家一  VS  玩家二");
    }

    private void selectHumanVsAI(){
        if(game.Started){
            return;
        }
        main.networkManager.close();
        game.vsComputerMode = true;
        game.vsHumanMode = false;
        game.onlineMode = false;
        game.firstHand = false;
        game.backHand = false;
        UI.setPlayersLabel("玩家  VS  高級AI");
    }

    private void selectOnlineMode(){
        if(game.Started){
            JOptionPane.showMessageDialog(UI.windows, "請先重新遊戲後再切換模式。", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        main.networkManager.close();
        game.vsHumanMode = false;
        game.vsComputerMode = false;
        game.onlineMode = true;
        game.firstHand = false;
        game.backHand = false;
        UI.setPlayersLabel("本地玩家  VS  遠端玩家");

        String[] options = {"建立房間","加入房間","取消"};
        int result = JOptionPane.showOptionDialog(UI.windows, "請選擇聯機方式", "聯機對戰", JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if(result == 0){
            hostOnlineGame();
        }else if(result == 1){
            joinOnlineGame();
        }else{
            cancelOnlineSelection();
        }
    }

    private void hostOnlineGame(){
        String portText = JOptionPane.showInputDialog(UI.windows, "請輸入連線埠號", "5000");
        if(portText == null){
            cancelOnlineSelection();
            return;
        }
        try{
            int port = Integer.parseInt(portText.trim());
            game.onlineHost = true;
            game.onlineMyTurn = true;
            main.networkManager.host(port);
            JOptionPane.showMessageDialog(UI.windows, "等待玩家連線...", "聯機對戰", JOptionPane.INFORMATION_MESSAGE);
        }catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(UI.windows, "無效的埠號。", "錯誤", JOptionPane.ERROR_MESSAGE);
            cancelOnlineSelection();
        }catch (IOException ex){
            JOptionPane.showMessageDialog(UI.windows, "無法建立房間: " + ex.getMessage(), "錯誤", JOptionPane.ERROR_MESSAGE);
            cancelOnlineSelection();
        }
    }

    private void joinOnlineGame(){
        String host = JOptionPane.showInputDialog(UI.windows, "請輸入主機位址", "127.0.0.1");
        if(host == null || host.trim().isEmpty()){
            cancelOnlineSelection();
            return;
        }
        String portText = JOptionPane.showInputDialog(UI.windows, "請輸入連線埠號", "5000");
        if(portText == null){
            cancelOnlineSelection();
            return;
        }
        try{
            int port = Integer.parseInt(portText.trim());
            game.onlineHost = false;
            game.onlineMyTurn = false;
            main.networkManager.connect(host.trim(), port);
        }catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(UI.windows, "無效的埠號。", "錯誤", JOptionPane.ERROR_MESSAGE);
            cancelOnlineSelection();
        }catch (IOException ex){
            JOptionPane.showMessageDialog(UI.windows, "連線失敗: " + ex.getMessage(), "錯誤", JOptionPane.ERROR_MESSAGE);
            cancelOnlineSelection();
        }
    }

    private void cancelOnlineSelection(){
        game.onlineMode = false;
        game.onlineHost = false;
        game.onlineMyTurn = false;
        UI.setPlayersLabel("");
        main.networkManager.close();
    }

    private void resetGame(){
        UI.setPlayersLabel("");
        UI.reSetUIAndGame();
        game.chessMove=0;
        game.reGamePlayChess();
    }

    private void startGame(){
        if(game.onlineMode){
            if(!main.networkManager.isConnected()){
                JOptionPane.showMessageDialog(UI.windows, "尚未建立連線。", "提示", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if(!game.Started){
                game.Started = true;
                UI.setRoundLabel();
            }
            return;
        }

        if(game.vsComputerMode){
            if(!(game.firstHand || game.backHand)){
                JOptionPane.showMessageDialog(UI.windows, "請先選擇先手或後手。", "提示", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }else if(!game.vsHumanMode){
            JOptionPane.showMessageDialog(UI.windows, "請先選擇遊戲模式。", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if(!game.Started){
            if(game.vsComputerMode && game.backHand){
                game.changeCurrentPlayer();
            }
            game.Started = true;
            UI.setRoundLabel();
            if(game.vsComputerMode && game.backHand){
                SwingUtilities.invokeLater(() -> score.allChess());
            }
        }
    }

    private void surrender(){
        if(game.Started){
            UI.lossFarm();
            game.chessMove=0;
        }
    }

    private void undoMove(){
        if(!game.Started){
            return;
        }
        if(game.onlineMode){
            JOptionPane.showMessageDialog(UI.windows, "聯機對戰不支援悔棋。", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int totalSize = game.getBlackPlacedPieces().size()+game.getWhitePlacedPieces().size();
        if(totalSize==0){
            return;
        }

        if(totalSize==1){
            int lastIndex = game.getWhitePlacedPieces().size()-1;
            if(lastIndex>=0){
                int[] loc = game.getWhitePlacedPieces().get(lastIndex);
                game.removePiecesFromWhiteList(lastIndex);
                game.allPlayChessed[loc[0]][loc[1]] = 0;
            }
            game.changeCurrentPlayer();
            game.chessMove--;
        }else{
            int blackIndex = game.getBlackPlacedPieces().size()-1;
            int whiteIndex = game.getWhitePlacedPieces().size()-1;
            if(blackIndex>=0){
                int[] loc = game.getBlackPlacedPieces().get(blackIndex);
                game.removePiecesFromBlackList(blackIndex);
                game.allPlayChessed[loc[0]][loc[1]] = 0;
            }
            if(whiteIndex>=0){
                int[] loc = game.getWhitePlacedPieces().get(whiteIndex);
                game.removePiecesFromWhiteList(whiteIndex);
                game.allPlayChessed[loc[0]][loc[1]] = 0;
            }
            game.chessMove = Math.max(0, game.chessMove-2);
        }
        UI.reDisplayLabelAndPainting();
        UI.setRoundLabel();
    }

    private void confirmDialog(){
        UI.setPlayersLabel("");
        UI.reSetUIAndGame();
        UI.lossFram.setVisible(false);
        UI.winFram.setVisible(false);
        UI.andBureauFram.setVisible(false);
        game.reGamePlayChess();
        game.chessMove=0;
    }

    private void restorePieces(){
        if(game.Started) {
            UI.reDisplayLabelAndPainting();
            UI.setRoundLabel();
        }
    }

    private void chooseFirstHand(){
        if(game.Started){
            return;
        }
        if(game.vsComputerMode){
            game.firstHand=true;
            game.backHand=false;
        }
    }

    private void chooseBackHand(){
        if(game.Started){
            return;
        }
        if(game.vsComputerMode){
            game.backHand=true;
            game.firstHand=false;
        }
    }
}
