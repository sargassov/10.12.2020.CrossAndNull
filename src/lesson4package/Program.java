package lesson4package;

import java.util.Scanner;

/*Доброго дня суток! Крести-Нолики с простейшим ИИ.

Для уменьшения количества кода одни и те же 3 метода ИИ
одновременно отвечают за слежением за ходами игрока,
собественными ходами с выстраиванием комбинации из 4 подряд
ноликов, а также за проверку победы.

Приоритет задач ИИ на каждом ходу:
1. Найти и перекрыть комбинацию из 3 крестиков игрока
2. Найти и перекрыть комбинацию из 2 крестиков игрока
3. Найти на поле и достроить свою комбинацию из 3 ноликов до 4-х
4. Найти на поле и достроить совю комбинацию из 2 ноликов до 3-х
5. Походить рандомно

Задача более низкого приоритета реализуется при полной невозможности
реализации задачи более высокого. */

public class Program {
    private static int ROW_CONST = 5;
    private static int COL_CONST = 5;
    private static String X = " X ";
    private static String O = " 0 ";
    private static String VOID = "   ";
    private static int last_row;
    private static int last_col;
    private static boolean isWon = false;

    static String playerField[][] = new String[ROW_CONST][COL_CONST];

    private static void fillPlayerField(){
        for(int x = 0; x < ROW_CONST; x++){
            for(int y = 0; y < COL_CONST; y++){
                playerField[x][y] = VOID;
            }
        }
    }

    private static boolean isEmptyPlace(int row, int col){
        return playerField[row][col].equals("   ");
    }

    private static void symChange(int row, int col, String sym){
        playerField[row][col] = sym;
    }

    private static void playerMove(){
        int row, col;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("Введите поле для следующего хода: ");
            do{
                row = sc.nextInt() - 1;
                col = sc.nextInt() - 1;
                if(!((row >= 0 && row < ROW_CONST) && (col >= 0 && col < COL_CONST))) System.out.println("Такого хода сдеалать нельзя");
            }while(!((row >= 0 && row < ROW_CONST) && (col >= 0 && col < COL_CONST)));
            if(!isEmptyPlace(row, col)) System.out.println("Указанное поле занято");
        }while(!isEmptyPlace(row, col));
        symChange(row, col, X);
        last_col = col;
        last_row = row;
    }

    private static void computerMove(){
        boolean isMove = false;
        isMove = generatorMove(last_row, last_col);
        if(!isMove){
            do{
                int row = (int)(Math.random() * ROW_CONST);
                int col = (int)(Math.random() * COL_CONST);
                if(isEmptyPlace(row, col)){
                    symChange(row, col, O);
                    isMove = true;
                }
            }
            while(!isMove);
        }
    }

    private static boolean checkOnRowColumnMoves(int valueOfNearMoves, int val, String arg, String X){
        int limit, secondLimit;
        if(arg.equals("row")) {limit = ROW_CONST; secondLimit = COL_CONST;}
        else {limit = COL_CONST; secondLimit = ROW_CONST;}
        if(valueOfNearMoves == 4){
            for(int xx = 0; xx < secondLimit; xx++){
                for(int x = 3; x < limit; x++){
                    if(arg.equals("row")){
                        if(playerField[xx][x].equals(X) && playerField[xx][x - 1].equals(X) && playerField[xx][x - 2].equals(X) &&
                                playerField[xx][x - 3].equals(X)) {
                            return true;
                        }
                    }
                    else{
                        if(playerField[x][xx].equals(X) && playerField[x - 1][xx].equals(X) && playerField[x - 2][xx].equals(X)
                                && playerField[x - 3][xx].equals(X)){return true;}
                    }
                }
            }
        }
        else if(valueOfNearMoves == 3){
            for(int x = 2; x < limit; x++){
                if(arg.equals("row")){
                    if(playerField[val][x].equals(X) && playerField[val][x - 1].equals(X) && playerField[val][x - 2].equals(X)){
                        if(x - 3 >= 0 && playerField[val][x - 3].equals(VOID)) {symChange(val, x - 3, O); return true;}
                        else if((x + 1 < ROW_CONST && playerField[val][x + 1].equals(VOID))) {symChange(val, x + 1, O); return true;}
                    }
                }
                else{
                    if(playerField[x][val].equals(X) && playerField[x - 1][val].equals(X) && playerField[x - 2][val].equals(X)){
                        if(x - 3 >= 0 && playerField[x - 3][val].equals(VOID)) {symChange(x - 3, val, O); return true;}
                        else if(x + 1 < COL_CONST && playerField[x + 1][val].equals(VOID)) {symChange(x + 1, val, O); return true;}
                    }
                }
            }
        }
        else{
            for(int x = 1; x < limit; x++){
                if(arg.equals("row")){
                    if(playerField[val][x].equals(X) && playerField[val][x - 1].equals(X)){
                        if(x - 2 >= 0 && playerField[val][x - 2].equals(VOID)) {symChange(val, x - 2, O); return true;}
                        else if((x + 1 < ROW_CONST && playerField[val][x + 1].equals(VOID))) {symChange(val, x + 1, O); return true;}
                    }
                }
                else{
                    if(playerField[x][val].equals(X) && playerField[x - 1][val].equals(X)){
                        if(x - 2 >= 0 && playerField[x - 2][val].equals(VOID)) {symChange(x - 2, val, O); return true;}
                        else if(x + 1 < COL_CONST && playerField[x + 1][val].equals(VOID)) {symChange(x + 1, val, O); return true;}
                    }
                }
            }
        }
        return false;
    }

    private static boolean checkOnRightDiagonalMoves(int valueOfNearMoves, int row, int col, String X){
        if(valueOfNearMoves == 4){
            for(int x = row, y = col; x >= 0 && y < COL_CONST; x--, y++){
                if(x + 3 < ROW_CONST && y - 3 >= 0 && playerField[x][y].equals(X) && playerField[x + 1][y - 1].equals(X)
                        && playerField[x + 2][y - 2].equals(X) && playerField[x + 3][y - 3].equals(X)) return true;
            }
        }
        else if(valueOfNearMoves == 3){
            for(int x = row, y = col; x >= 0; x--, y++){
                if(x + 2 < ROW_CONST && y - 2 >= 0 && playerField[x][y].equals(X)
                        && playerField[x + 1][y - 1].equals(X) && playerField[x + 2][y - 2].equals(X)){
                    if((x + 3) < ROW_CONST && (y - 3) >= 0 && playerField[x + 3][y - 3].equals(VOID)) {
                        symChange(x + 3, y - 3, O); return true;}
                    else if((x - 1) >= 0 && (y + 1) < COL_CONST && playerField[x - 1][y + 1].equals(VOID)) {
                        symChange(x - 1, y + 1, O); return true;}
                }
            }
        }
        else{
            for(int x = ++row, y = --col; x >= 0; x--, y++){
                if(x + 1 < ROW_CONST && y - 1 >= 0 && playerField[x][y].equals(X) && playerField[x + 1][y - 1].equals(X)){
                    if((x + 2) < ROW_CONST && (y - 2) >= 0 && playerField[x + 2][y - 2].equals(VOID)) {
                        symChange(x + 2, y - 2, O); return true;}
                    else if((x - 1) >= 0 && (y + 1) < COL_CONST && playerField[x - 1][y + 1].equals(VOID)) {
                        symChange(x - 1, y + 1, O); return true;}
                }
            }
        }

        return false;
    }

    private static boolean checkOnLeftDiagonalMoves(int valueOfNearMoves, int row, int col, String X){
        if(valueOfNearMoves == 4){
            for(int x = row, y = col; x < ROW_CONST && y < COL_CONST; x++, y++){
                if(x - 3 >= 0 && y - 3 >= 0 && playerField[x][y].equals(X) && playerField[x - 1][y - 1].equals(X)
                        && playerField[x - 2][y - 2].equals(X) && playerField[x - 3][y - 3].equals(X)) return true;
            }
        }
        else if(valueOfNearMoves == 3){
            for(int x = row, y = col; x < ROW_CONST && y < COL_CONST; x++, y++){
                if(playerField[x][y].equals(X) && playerField[x - 1][y - 1].equals(X) && playerField[x - 2][y - 2].equals(X)){
                    if((x - 3) >= 0 && (y - 3) >= 0 && playerField[x - 3][y - 3].equals(VOID)) {
                        symChange(x - 3, y - 3, O); return true;}
                    else if((x + 1) < ROW_CONST && (y + 1) < COL_CONST && playerField[x + 1][y + 1].equals(VOID)){
                        symChange(x + 1, y + 1, O); return true;}
                }
            }
        }
        else{
            for(int x = --row, y = --col; x < ROW_CONST && y < COL_CONST; x++, y++){
                if(playerField[x][y].equals(X) && playerField[x - 1][y - 1].equals(X)){
                    if((x - 2) >= 0 && (y - 2) >= 0 && playerField[x - 2][y - 2].equals(VOID)) {
                        symChange(x - 2, y - 2, O); return true;}
                    else if((x + 1) < ROW_CONST && (y + 1) < COL_CONST && playerField[x + 1][y + 1].equals(VOID)){
                        symChange(x + 1, y + 1, O); return true;}
                }
            }
        }

        return false;
    }

    private static boolean generatorMove(int row, int col){
        boolean isMove = false;
        String paremetrMove;
        for(int z = 0; z < 2; z++){
            if(z == 0) paremetrMove = X;
            else paremetrMove = O;
            for(int valueOfNearMoves = 3, x = 0; valueOfNearMoves > 1; valueOfNearMoves--, x = 0){
                while(!isMove && x < 8){
                    if(x == 0) isMove = checkOnRowColumnMoves(valueOfNearMoves, row, "row", paremetrMove);
                    else if(x == 1) isMove = checkOnRowColumnMoves(valueOfNearMoves, col, "col", paremetrMove);
                    else if(x == 2) isMove = checkOnRightDiagonalMoves(valueOfNearMoves, 1, 2, paremetrMove);
                    else if(x == 3) isMove = checkOnRightDiagonalMoves(valueOfNearMoves, 2, 2, paremetrMove);
                    else if(x == 4) isMove = checkOnRightDiagonalMoves(valueOfNearMoves, 2, 1, paremetrMove);
                    else if(x == 5) isMove = checkOnLeftDiagonalMoves(valueOfNearMoves, 2, 2, paremetrMove);
                    else if(x == 6) isMove = checkOnLeftDiagonalMoves(valueOfNearMoves, 2, 3, paremetrMove);
                    else if(x == 7) isMove = checkOnLeftDiagonalMoves(valueOfNearMoves, 3, 2, paremetrMove);
                    x++;
                }
            }
        }


        return isMove;
    }


    private static void fieldToPrint () {
            for (int x = 0, row_coeff = 0; x < 12; x++) {
                for (int y = 0, col_coeff = 0; y < 12; y++) {
                    if (y == 0 && x == 0) System.out.print(" ");
                    if (x % 2 == 0 && (y == 1 || y == 3 || y == 5 || y == 7 || y == 9 || y == 11))
                        System.out.print("|");
                    if ((x % 2 == 0 & x != 0) && y == 0) System.out.print(x / 2);
                    if (x == 0 && y % 2 == 0 && y != 0) System.out.print(" " + y / 2 + " ");
                    if (x % 2 == 1) System.out.print("--");
                    if (x != 0 && y != 0 && (x % 2 == 0 && y % 2 == 0)) {
                        System.out.print(playerField[row_coeff][col_coeff]);
                        col_coeff++;
                    }
                    if (col_coeff > COL_CONST) col_coeff = 0;

                }
                System.out.println();
                if (x != 1 && x % 2 == 1) row_coeff++;
            }
    }

    private static void haveWeWinner(){
        int x = 0, valueOfWinnerMoves = 4, doesntMatter = -100;
        String paremetrMove;
        for(int z = 0; z < 2; z++, x = 0){
            if(z == 0) paremetrMove = X;
            else paremetrMove = O;
            while(x < 8){
                if(x == 0) isWon = checkOnRowColumnMoves(valueOfWinnerMoves, doesntMatter, "row", paremetrMove);
                else if(x == 1) isWon = checkOnRowColumnMoves(valueOfWinnerMoves, doesntMatter, "col", paremetrMove);
                else if(x == 2) isWon = checkOnRightDiagonalMoves(valueOfWinnerMoves, 0, 3, paremetrMove);
                else if(x == 3) isWon = checkOnRightDiagonalMoves(valueOfWinnerMoves, 1, 4, paremetrMove);
                else if(x == 4) isWon = checkOnRightDiagonalMoves(valueOfWinnerMoves, 1, 3, paremetrMove);
                else if(x == 5) isWon = checkOnLeftDiagonalMoves(valueOfWinnerMoves, 3, 4, paremetrMove);
                else if(x == 6) isWon = checkOnLeftDiagonalMoves(valueOfWinnerMoves, 3, 3, paremetrMove);
                else if(x == 7) isWon = checkOnLeftDiagonalMoves(valueOfWinnerMoves, 4, 3, paremetrMove);
                if(isWon) {
                    System.out.println((paremetrMove.equals(X) ? "\n\n\t\t\t\tИгрок " : "\n\n\t\t\t\tКомпьютер ") + "победил\n\n");
                    System.exit(0);
                }
                x++;
            }

        }
    }

    public static void main (String[]args){
        System.out.println("Крестики-нолики");
        fillPlayerField();
        fieldToPrint();
        for(int x = 1; !isWon; x++) {
            if (x % 2 == 1) playerMove();
            else computerMove();
            fieldToPrint();
            haveWeWinner();
        }
    }

}
