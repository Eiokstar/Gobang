package GoBang;


import GoBang.Listener.ActionListener;
import GoBang.Listener.MouseListener;
import GoBang.Game.Judging;
import GoBang.Game.Score;
public class main {

    public static main instance;
    public static UI UI= new UI();
    public static Game game = new Game();
    public static Judging judging = new Judging();
    public static Score score = new Score();
    public ActionListener actionListener = new ActionListener();
    public static MouseListener mouseListener = new MouseListener();

    public static void main(String[] args){
        instance = new main();
        UI.showUI();
    }

    public static main getInstance(){
        return instance;
    }
    public static void initGame(){game = new Game();}
    public static GoBang.UI getUI(){
        return UI;
    }
    public static GoBang.Listener.MouseListener getMouseListener(){
        return mouseListener;
    }


}
