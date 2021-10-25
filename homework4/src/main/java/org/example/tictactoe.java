package org.example;

import java.util.Random;
import java.util.Scanner;

public class tictactoe {

    public static final int BOARD_SIZE = 5;
    public static final int CELLS_TO_WIN = BOARD_SIZE - 1;

    public static final char EMPTY_CELL = '.';
    public static final char PLAYER1_CELL = 'X';
    public static final char PLAYER2_CELL = 'O';

    public static Scanner input = new Scanner(System.in);
    public static char[][] board;

    public static void main(String[] args) {
        initBoard();
        System.out.printf("Цель игры - заполнить подряд линию по вертикали, горизонтали или диагонали из %d Ваш%s символ%s.\n", CELLS_TO_WIN, (CELLS_TO_WIN % 10 == 1 && CELLS_TO_WIN % 100 != 11) ? "его" : "их", (CELLS_TO_WIN % 10 == 1 && CELLS_TO_WIN % 100 != 11) ? "а" : "ов");
        printBoard();

        switch (new Random().nextInt(2)) {
            case 0: {
                System.out.println("Ваш ход первый!");
                while (true) {
                    player1Move();
                    printBoard();
                    if (isWin(PLAYER1_CELL)) {
                        System.out.println("Победил Игрок 1");
                        break;
                    }
                    if (isDraw()) {
                        System.out.println("Ничья");
                        break;
                    }
                    player2Move();
                    printBoard();
                    if (isWin(PLAYER2_CELL)) {
                        System.out.println("Победил компьютер");
                        break;
                    }
                    if (isDraw()) {
                        System.out.println("Ничья");
                        break;
                    }
                }
                break;
            }
            case 1: {
                System.out.println("Первый ход за компьютером!");
                while (true) {
                    player2Move();
                    printBoard();
                    if (isWin(PLAYER2_CELL)) {
                        System.out.println("Победил компьютер!");
                        break;
                    }
                    if (isDraw()) {
                        System.out.println("Ничья");
                        break;
                    }
                    player1Move();
                    printBoard();
                    if (isWin(PLAYER1_CELL)) {
                        System.out.println("Победил Игрок 1");
                        break;
                    }
                    if (isDraw()) {
                        System.out.println("Ничья");
                        break;
                    }
                }
            }
        }
    }

    public static void initBoard() {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = EMPTY_CELL;
            }
        }
    }

    public static void printBoard() {
        for (int i = 0; i <= board.length; i++) {
            System.out.print(i == 0 ? "  " : i + " ");
        }
        System.out.println();
        for (int i = 0; i < board.length; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static boolean isCellAvailable(int x, int y) {
        return x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE && board[y][x] == EMPTY_CELL;
    }

    public static void player1Move() {
        int x, y;
        do {
            System.out.printf("Введите координаты через пробел в формате 'горизонталь пробел вертикаль':\n", BOARD_SIZE, BOARD_SIZE);
            x = Integer.valueOf(input.next()) - 1;
            y = Integer.valueOf(input.next()) - 1;
        } while (!isCellAvailable(x, y));
        board[y][x] = PLAYER1_CELL;
    }

    public static void player2Move() {
        int x, y;
        do {
            x = new Random().nextInt(BOARD_SIZE);
            y = new Random().nextInt(BOARD_SIZE);
        } while (!isCellAvailable(x, y));
        System.out.println("Компьютер сделал ход " + (x + 1) + " " + (y + 1));
        board[y][x] = PLAYER2_CELL;
    }

    public static boolean isWin(char playerDot) {
        int hor, ver;
        int diagMain, diagReverse;
        for (int i = 0; i < BOARD_SIZE; i++) {
            hor = 0;
            ver = 0;
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == playerDot) {
                    hor++;
                } else if (board[i][j] != playerDot && hor < CELLS_TO_WIN) {
                    hor = 0;
                }
                if (board[j][i] == playerDot) {
                    ver++;
                }   else if (board[j][i] != playerDot && ver < CELLS_TO_WIN) {
                    ver = 0;
                }
            }
            if (hor >= CELLS_TO_WIN || ver >= CELLS_TO_WIN) {
                System.out.println("По горизонтали или вертикали " + hor + " " + ver);
                return true;
            }
        }

        for (int j = 0; j < BOARD_SIZE; j++) {
            diagMain = 0;
            for (int i = 0; i < BOARD_SIZE; i++) {
                int k = j + i;
                if (k < BOARD_SIZE) {
                    if (board[i][k] == playerDot) {
                        diagMain++;
                    } else if (board[i][k] != playerDot && diagMain < CELLS_TO_WIN) {
                        diagMain = 0;
                    }
                }
                if (diagMain >= CELLS_TO_WIN) {
                    System.out.println("По главной диагонали от центральной оси вправо " + diagMain);
                    return true;
                }
            }
        }
        for (int j = 1; j < BOARD_SIZE; j++) {
            diagMain = 0;
            for (int i = 0; i < BOARD_SIZE; i++) {
                int k = j + i;
                if (k < BOARD_SIZE) {
                    if (board[k][i] == playerDot) {
                        diagMain++;
                    } else if (board[k][i] != playerDot && diagMain < CELLS_TO_WIN) {
                        diagMain = 0;
                    }
                }
                if (diagMain >= CELLS_TO_WIN) {
                    System.out.println("По главной диагонали от центральной оси вниз " + diagMain);
                    return true;
                }
            }
        }
        for (int j = 0; j < BOARD_SIZE; j++) {
            diagReverse = 0;
            for (int i = 0; i < BOARD_SIZE; i++) {
                int k = (BOARD_SIZE - 1) - i;
                int l = j + i;
                if (k >= 0 && l < BOARD_SIZE) {
                    if (board[l][k] == playerDot) {
                        diagReverse++;
                    } else if (board[l][k] != playerDot && diagReverse < CELLS_TO_WIN) {
                        diagReverse = 0;
                    }
                }
                if (diagReverse >= CELLS_TO_WIN) {
                    System.out.println("По побочной диагонали от центральной оси вниз " + diagReverse);
                    return true;
                }
            }
        }
        for (int j = 1; j < BOARD_SIZE; j++) {
            diagReverse = 0;
            for (int i = 0; i < BOARD_SIZE; i++) {
                int k = (BOARD_SIZE - 1) - j - i;
                if (k >= 0) {
                    if (board[i][k] == playerDot) {
                        diagReverse++;
                    } else if (board[i][k] != playerDot && diagReverse < CELLS_TO_WIN) {
                        diagReverse = 0;
                    }
                }
                if (diagReverse >= CELLS_TO_WIN) {
                    System.out.println("По побочной диагонали от центральной оси влево " + diagReverse);
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isDraw() {
        for (char[] aGameField : board) {
            for (int j = 0; j < board.length; j++) {
                if (aGameField[j] == EMPTY_CELL) {
                    return false;
                }
            }
        }
        return true;
    }


}
