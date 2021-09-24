package innerLogic;


import windows.Map;

import java.util.Random;
import java.util.Scanner;

public class CrossNull {

    int SIZE_Y; //размер поля по вертикале
    int SIZE_X; //расчет поля по горизонтале
    int SIZE_WIN; //кол-во заполненных подряж полей для победы
    Map map;
    int[][] field;
    public static int gameMode;
    private static int HUMAN_DOT = 1;
    private static int AI_DOT = 2;
    private static int EMPTY_DOT = 0;
    // обявляется классов ввода и случайного числа для игры
    static Random rnd = new Random();


    public CrossNull(Map map, int fieldSizeX, int fieldSizeY, int winLen) {
        this.map = map;
        this.SIZE_Y = fieldSizeY;
        this.SIZE_X = fieldSizeX;
        this.SIZE_WIN = winLen;
        field = map.getField();
    }

    private int checkLineHorison(int v, int h, int dot) {
        int count = 0;
        for (int j = h; j < SIZE_WIN + h; j++) {
            if ((field[v][j] == dot)) count++;
        }
        return count;
    }

    //ход компьютера по горизонтали
    private boolean MoveAiLineHorison(int v, int h, int dot) {
        for (int j = h; j < SIZE_WIN; j++) {
            if ((field[v][j] == EMPTY_DOT)) {
                field[v][j] = dot;
                return true;
            }
        }
        return false;
    }

    private int checkDiaUp(int v, int h, int dot) {
        int count = 0;
        for (int i = 0, j = 0; j < SIZE_WIN; i--, j++) {
            if ((field[v + i][h + j] == dot)) count++;
        }
        return count;
    }

    //проверка заполнения всей линии по диагонале вверх

    private boolean MoveAiDiaUp(int v, int h, int dot) {
        for (int i = 0, j = 0; j < SIZE_WIN; i--, j++) {
            if ((field[v + i][h + j] == EMPTY_DOT)) {
                field[v + i][h + j] = dot;
                return true;
            }
        }
        return false;
    }

    //проверка заполнения всей линии по диагонале вниз

    private int checkDiaDown(int v, int h, int dot) {
        int count = 0;
        for (int i = 0; i < SIZE_WIN; i++) {
            if ((field[i + v][i + h] == dot)) count++;
        }
        return count;
    }

    //проверка заполнения всей линии по диагонале вниз

    private boolean MoveAiDiaDown(int v, int h, int dot) {

        for (int i = 0; i < SIZE_WIN; i++) {
            if ((field[i + v][i + h] == EMPTY_DOT)) {
                field[i + v][i + h] = dot;
                return true;
            }
        }
        return false;
    }


    //проверка заполнения всей линии по вертикале
    private int checkLineVertical(int v, int h, int dot) {
        int count = 0;
        for (int i = v; i < SIZE_WIN + v; i++) {
            if ((field[i][h] == dot)) count++;
        }
        return count;
    }
    //ход компьютера по вертикале
    private boolean MoveAiLineVertical(int v, int h, int dot) {
        for (int i = v; i < SIZE_WIN; i++) {
            if ((field[i][h] == EMPTY_DOT)) {
                field[i][h] = dot;
                return true;
            }
        }
        return false;
    }

    //проверка заполнения выбранного для хода игроком
    private boolean checkMove(int y, int x) {
        if (x < 0 || x >= SIZE_X || y < 0 || y >= SIZE_Y) return false;
        else if (!(field[y][x] == EMPTY_DOT)) return false;

        return true;
    }

    private boolean fullDesk(){

        for (int i = 0; i < SIZE_X; i++) {
            for (int j = 0; j < SIZE_Y; j++) {
                if(field[i][j] == 0) return false;
            }
        }

        return true;
    }

    //запись хода игрока на поле
    private void dotField(int y, int x, int dot) {
        field[y][x] = dot;
    }

    //Ход компьютера
    public void AiMove() {
        int x, y;

        if(fullDesk()){
            Map.setCountOfWinner(3);
            map.checkWin();
        }
        //блокировка ходов человека
        for (int v = 0; v < SIZE_Y; v++) {
            for (int h = 0; h < SIZE_X; h++) {
                //анализ наличие поля для проверки
                if (h + SIZE_WIN <= SIZE_X) {                           //по горизонтале
                    if (checkLineHorison(v, h, HUMAN_DOT) == SIZE_WIN - 1) {
                        if (MoveAiLineHorison(v, h, AI_DOT)) return;
                    }

                    if (v - SIZE_WIN > -2) {                            //вверх по диагонале
                        if (checkDiaUp(v, h, HUMAN_DOT) == SIZE_WIN - 1) {
                            if (MoveAiDiaUp(v, h, AI_DOT)) return;
                        }
                    }
                    if (v + SIZE_WIN <= SIZE_Y) {                       //вниз по диагонале
                        if (checkDiaDown(v, h, HUMAN_DOT) == SIZE_WIN - 1) {
                            if (MoveAiDiaDown(v, h, AI_DOT)) return;
                        }
                    }
                }
                if (v + SIZE_WIN <= SIZE_Y) {                       //по вертикале
                    if (checkLineVertical(v, h, HUMAN_DOT) == SIZE_WIN - 1) {
                        if (MoveAiLineVertical(v, h, AI_DOT)) return;
                    }
                }
            }
        }
        //игра на победу
        for (int v = 0; v < SIZE_Y; v++) {
            for (int h = 0; h < SIZE_X; h++) {
                //анализ наличие поля для проверки
                if (h + SIZE_WIN <= SIZE_X) {                           //по горизонтале
                    if (checkLineHorison(v, h, AI_DOT) == SIZE_WIN - 1) {
                        if (MoveAiLineHorison(v, h, AI_DOT)) return;
                    }

                    if (v - SIZE_WIN > -2) {                            //вверх по диагонале
                        if (checkDiaUp(v, h, AI_DOT) == SIZE_WIN - 1) {
                            if (MoveAiDiaUp(v, h, AI_DOT)) return;
                        }
                    }
                    if (v + SIZE_WIN <= SIZE_Y) {                       //вниз по диагонале
                        if (checkDiaDown(v, h, AI_DOT) == SIZE_WIN - 1) {
                            if (MoveAiDiaDown(v, h, AI_DOT)) return;
                        }
                    }
                }
                if (v + SIZE_WIN <= SIZE_Y) {                       //по вертикале
                    if (checkLineVertical(v, h, AI_DOT) == SIZE_WIN - 1) {
                        if (MoveAiLineVertical(v, h, AI_DOT)) return;
                    }
                }
            }
        }
        //случайный ход
        do {
            y = rnd.nextInt(SIZE_Y);
            x = rnd.nextInt(SIZE_X);
        } while (!checkMove(y, x));
        dotField(y, x, AI_DOT);
    }

    //Ход человева
    public void playerMove() {
        if(Map.CURRENT_DOT == 1)Map.CURRENT_DOT = 2;
        else Map.CURRENT_DOT = 1;
    }

    //проверка победы
    public boolean checkWin(int dot) {
        for (int v = 0; v < SIZE_Y; v++) {
            for (int h = 0; h < SIZE_X; h++) {
                //анализ наличие поля для проверки
                if (h + SIZE_WIN <= SIZE_X) {
                    //по горизонтале
                    if (checkLineHorison(v, h, dot) >= SIZE_WIN) return true;

                    if (v - SIZE_WIN > -2) {                            //вверх по диагонале
                        if (checkDiaUp(v, h, dot) >= SIZE_WIN) return true;
                    }
                    if (v + SIZE_WIN <= SIZE_Y) {                       //вниз по диагонале
                        if (checkDiaDown(v, h, dot) >= SIZE_WIN) return true;
                    }
                }
                if (v + SIZE_WIN <= SIZE_Y) {                       //по вертикале
                    if (checkLineVertical(v, h, dot) >= SIZE_WIN) return true;
                }
            }
        }
        return false;
    }

        //проверка на ничью (все  ячейки поля заполнены ходами)
    public boolean fullField() {
        for (int i = 0; i < SIZE_Y; i++) {
            for (int j = 0; j < SIZE_X; j++) {
                if (field[i][j] == EMPTY_DOT) return false;
            }
        }
        return true;
    }

    public int getAiDot() {
        return AI_DOT;
    }

    public int getHumanDot() {
        return HUMAN_DOT;
    }


}