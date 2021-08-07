package windows;

import innerLogic.CrossNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Map extends JPanel {
    public static final int MODE_H_V_A = 1;
    public static final int MODE_H_V_H = 0;
    public static int CURRENT_DOT;


    private static final String compWinner = "Computer ";
    private static final String playerWinner = "Player ";
    private static final String deuce = "DEUCE";
    private static int countOfWinner;


    private GameWindow gameWindow;
    private int[][] field;
    private int fieldSizeX;
    private int fieldSizeY;
    private int winLen;
    private static boolean isWin = false;
    CrossNull crossNull;

    int cellHeight;
    int cellWidth;

    boolean isInitialized = false;

    Map(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        CURRENT_DOT = 1;
        setBackground(Color.ORANGE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                //super.mouseReleased(e);
                if(!isWin){
                    update(e);
                }

            }
        });
    }


    void update(MouseEvent e) {
        int cellX = e.getX() / cellWidth;
        int cellY = e.getY() / cellHeight;
        System.out.println("x " + cellX + " y " + cellY);
        if(field[cellY][cellX] == 0){
            field[cellY][cellX] = CURRENT_DOT;
            checkWin();
            answer();
            checkWin();

        }
        else return;

        repaint();
    }

    public void checkWin() {

        if(crossNull.checkWin(crossNull.getHumanDot())){
            countOfWinner = 1;
            isWin = true;
        }
        if (crossNull.checkWin(crossNull.getAiDot())){
            countOfWinner = 2;
            isWin = true;
        }
        if(countOfWinner == 3){/////////////////////////////////////////////////
            isWin = true;
            //add(new paintComponent());
        }

    }

    private void answer() {
        if(CrossNull.gameMode == 1) crossNull.AiMove();
        else crossNull.playerMove();
    }


    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2 = (Graphics2D)graphics;
        g2.setStroke(new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
        if (!isInitialized) return;

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        cellWidth = panelWidth / fieldSizeX;
        cellHeight = panelHeight / fieldSizeY;

        // рисуем горизонтальные полоски
        for (int i = 0; i < fieldSizeY; i++) {
            int y = i * cellHeight;
            g2.drawLine(0, y, panelWidth, y);
        }

        // рисуем вертикальные полоски
        for (int i = 0; i < fieldSizeX; i++) {
            int x = i * cellWidth;
            g2.drawLine(x, 0, x, panelHeight);
        }

        int curWidth = panelWidth / fieldSizeX;
        int curHeight = panelHeight / fieldSizeY;

        for(int q = 0; q < fieldSizeY; q++){
            for(int w = 0; w < fieldSizeX; w++){
                System.out.println(field[w][q]);
                if(field[q][w] == 1){ drawCross(g2, q, w, curHeight, curWidth);}
                if(field[q][w] == 2){ g2.drawOval(curWidth * w, curHeight * q, curWidth, curHeight);
                }
            }
        }
        if(isWin){
            String winner = "";
            if(countOfWinner == 1) winner = playerWinner;
            if(countOfWinner == 2) winner = compWinner;
            winner += " is won!!!";

            Font font = new Font("Times New Roman", Font.BOLD, 58);
            g2.setFont(font);
            g2.setColor(Color.WHITE);
            g2.drawString(winner, 10, 250);
        }
    }

    void drawCross(Graphics2D g2, int q, int w, int curHeight, int curWidth){
        g2.drawLine(curWidth * w,
                curHeight * (q + 1),
                curWidth * (w + 1),
                curHeight * q);
        g2.drawLine(curWidth * w,
                curHeight * q,
                curWidth * (w + 1),
                curHeight * (q + 1));
    }

    void startNewGame(int mode, int fieldSizeX, int fieldSizeY, int winLen) {
        field = new int[fieldSizeY][fieldSizeX];
        GameWindow.getBottomPanel().setVisible(true);
        crossNull = new CrossNull(this, fieldSizeX, fieldSizeY, winLen);
        System.out.println(mode + " " + fieldSizeX + " " + fieldSizeY + " " + winLen);
        this.fieldSizeX = fieldSizeX;
        this.fieldSizeY = fieldSizeY;
        this.winLen = winLen;
        isInitialized = true;
        repaint();
    }

    public int[][] getField() {
        return field;
    }

    public static void setCountOfWinner(int countOfWinner) {
        Map.countOfWinner = countOfWinner;
    }
}
