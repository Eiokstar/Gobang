package GoBang.Listener;

import GoBang.Game;
import GoBang.main;

import java.awt.event.ActionEvent;

import static GoBang.main.game;
import static GoBang.main.score;

public class ActionListener implements java.awt.event.ActionListener {

    private GoBang.UI UI = main.getUI();
    private GoBang.Listener.MouseListener mouseListener = main.mouseListener;

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand().equals("玩家VS玩家")) {
            if(game.Started==false){
                if(game.vsHumanMode ==false){
                    UI.setPlayersLabel("玩家一  VS  玩家二");
                    game.vsComputerMode = false;
                    game.vsHumanMode = true;
                    return;
                }
                return;
            }
        }
        if(event.getActionCommand().equals("玩家VS電腦")){
            if(game.Started==false){
                if(game.vsComputerMode == false){
                    UI.setPlayersLabel("    玩家  VS  電腦");
                    game.vsComputerMode = true;
                    game.vsHumanMode = false;
                    return;
                }
            }
            return;
        }
        if(event.getActionCommand().equals("重新")){
            UI.setPlayersLabel("");
            UI.reSetUIAndGame();
            game.chessMove=0;
            game.reGamePlayChess();
            return;
        }
        if(event.getActionCommand().equals("開始")){
            if(((game.vsComputerMode)&((game.firstHand)|(game.backHand)))|(game.vsHumanMode)){
                if(game.Started ==false){
                    if(game.backHand){
                        game.changeCurrentPlayer();
                    }
                    game.Started = true;
                    mouseListener.aiPlayChess(7,7);
                    UI.setRoundLabel();
                }
            }
            return;
        }
        if (event.getActionCommand().equals("認輸")){
            if(game.Started){
                UI.lossFarm();
                game.chessMove=0;
            }
        }

        if(event.getActionCommand().equals("悔棋")) {
            if(game.Started){
                int size=game.getBlackPlacedPieces().size()+game.getWhitePlacedPieces().size();
                if(size==1){
                    game.removePiecesFromWhiteList(game.getWhitePlacedPieces().size()-1);
                    game.changeCurrentPlayer();
                    UI.setRoundLabel();
                    UI.reDisplayLabelAndPainting();
                    game.chessMove--;
                }else if (size >=2){
                    game.removePiecesFromBlackList(game.getBlackPlacedPieces().size()-1);
                    game.removePiecesFromWhiteList(game.getWhitePlacedPieces().size()-1);
                    UI.setRoundLabel();
                    UI.reDisplayLabelAndPainting();
                    game.chessMove-=2;
                }


            }


        }
        if(event.getActionCommand().equals("確定")){
            UI.setPlayersLabel("");
            UI.reSetUIAndGame();
            UI.lossFram.setVisible(false);
            UI.winFram.setVisible(false);
            UI.andBureauFram.setVisible(false);
            game.reGamePlayChess();
            game.chessMove=0;
        }
        if(event.getActionCommand().equals("回復棋子")){
            if(game.Started) {
                UI.reDisplayLabelAndPainting();
                UI.setRoundLabel();
                UI.reDisplayLabelAndPainting();
            }

        }
        if(event.getActionCommand().equals("先手")){
            if(game.Started==false){
                if(game.vsComputerMode){
                    game.firstHand=true;
                    game.backHand=false;
                    return;
                }
            }
            return;
        }
        if(event.getActionCommand().equals("後手")){
            if(game.Started==false){
                if(game.vsComputerMode){
                    game.backHand=true;
                    game.firstHand=false;
                    return;
                }
            }
            return;
        }

    }
}
