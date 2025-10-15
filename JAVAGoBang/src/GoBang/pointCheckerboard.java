package GoBang;

import javax.swing.*;
import java.awt.*;
import static GoBang.main.game;

public class pointCheckerboard extends JComponent  {
    int bigX , bigY;
    static int bigXDistance =835,smallX,smallY,player=0;
    static boolean drawSmall =false;
    Graphics g;

    pointCheckerboard(Graphics g,int x,int y,int player){
        bigX=x;
        bigY=y;

        smallX=bigX + bigXDistance;
        smallY=bigY+92;

        this.g = g;
        this.player=player;

    }
    pointCheckerboard(Graphics g,int[] loc){
        this.g =g ;
        smallX=smallX-loc[0]*30;
        smallY=smallY-loc[1]*30;
        drawSmall=true;
    }


    public void paintComponent(){

        super.paintComponent(g);
        g.setColor(Color.BLACK);
        Graphics2D g01 = (Graphics2D) g;
        Color c = g01.getColor();

        if(player==1){
            g01.setColor(Color.BLACK);
        }
        if(player==2){
            g01.setColor(Color.WHITE);
        }
        if(drawSmall==false){
            g01.fillOval(bigX+5, bigY, 35, 35);  // 50
        }

        if(drawSmall){
            g01.fillOval(smallX,smallY,15,15);
        }



        drawSmall=false;
        g01.setColor(c);



    }
    public int getX() {
        return bigX;
    }
    public int getY() {
        return bigY;
    }

}

