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

    private int[][] field;
    private int fieldSizeX;
    private int fieldSizeY;
    private int winLen;
    CrossNull crossNull;

    int cellHeight;
    int cellWidth;

    boolean isInitialized = false;

    Map() {
        CURRENT_DOT = 1;

       setBackground(Color.ORANGE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                //super.mouseReleased(e);
                update(e);
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
        if(crossNull.checkWin(crossNull.getHumanDot())) {
            System.exit(0);
        }

        if(crossNull.checkWin(crossNull.getAiDot())) {
            System.exit(0);
        }

    }

    private void answer() {
        if(CrossNull.gameMode == 1) crossNull.AiMove();
        else crossNull.playerMove();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }

    void startNewGame(int mode, int fieldSizeX, int fieldSizeY, int winLen) {
        field = new int[fieldSizeY][fieldSizeX];
        crossNull = new CrossNull(this, fieldSizeX, fieldSizeY, winLen);
        System.out.println(mode + " " + fieldSizeX + " " + fieldSizeY + " " + winLen);
        this.fieldSizeX = fieldSizeX;
        this.fieldSizeY = fieldSizeY;
        this.winLen = winLen;
        isInitialized = true;
        repaint();
    }

    void render(Graphics g) {
        if (!isInitialized) return;

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        cellWidth = panelWidth / fieldSizeX;
        cellHeight = panelHeight / fieldSizeY;

        // рисуем горизонтальные полоски
        for (int i = 0; i < fieldSizeY; i++) {
            int y = i * cellHeight;
            g.drawLine(0, y, panelWidth, y);
        }

        // рисуем вертикальные полоски
        for (int i = 0; i < fieldSizeX; i++) {
            int x = i * cellWidth;
            g.drawLine(x, 0, x, panelHeight);
        }


        for(int i = 0; i < fieldSizeY; i++){
            for(int j = 0; j < fieldSizeX; j++){
                System.out.println(field[j][i]);
                if(field[i][j] == 1){
                    g.drawLine(panelWidth / fieldSizeX * j,
                            panelHeight / fieldSizeY * i,
                            panelWidth / fieldSizeX * (j + 1),
                            panelHeight / fieldSizeY * (i + 1));

                    g.drawLine(panelWidth / fieldSizeX * (j + 1),
                            panelHeight / fieldSizeY * (i + 1),
                            panelWidth / fieldSizeX * j,
                            panelHeight / fieldSizeY * i);
                }

                if(field[i][j] == 2){
                    g.drawOval(panelWidth / fieldSizeX * j,
                            panelHeight / fieldSizeY * i,
                            panelWidth / fieldSizeX,
                            panelHeight / fieldSizeY);
                }
            }
        }
    }

    public int[][] getField() {
        return field;
    }
}
