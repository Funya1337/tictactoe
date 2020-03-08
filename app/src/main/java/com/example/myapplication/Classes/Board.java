package com.example.myapplication.Classes;

import java.util.Arrays;

public class Board {
    public int boardSize = 3;

    private ElState[][] board = new ElState[boardSize][boardSize];

    public Board() {
        clearBoard();
    }

    public void clearBoard() {
        for (int i = 0; i < board.length; i++) {
            Arrays.fill(board[i], ElState.E);
        }
    }

    public void setElement(int xIndex, int yIndex, ElState value) {
        board[xIndex][yIndex] = value;
    }

    public ElState checkRowsForWin() {
        for (int i = 0; i < board.length; i++) {
            int winnerCounterCross = 0;
            int winnerCounterZero = 0;
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == ElState.X) {
                    winnerCounterCross += 1;
                }
                if (board[i][j] == ElState.O) {
                    winnerCounterZero += 1;
                }
            }
            if (winnerCounterCross == board.length) {
                return ElState.X;
            }
            if (winnerCounterZero == board.length) {
                return ElState.O;
            }
        }
        return ElState.E;
    }

    public ElState checkColForWin() {
        for (int i = 0; i < board.length; i++) {
            int winnerCounterCross = 0;
            int winnerCounterZero = 0;
            for (int j = 0; j < board.length; j++) {
                if (board[j][i] == ElState.X) {
                    winnerCounterCross += 1;
                }
                if (board[j][i] == ElState.O) {
                    winnerCounterZero += 1;
                }
            }
            if (winnerCounterCross == board.length) {
                return ElState.X;
            }
            if (winnerCounterZero == board.length) {
                return ElState.O;
            }
        }
        return ElState.E;
    }

    public ElState checkDiagForWin() {
        int winnerCounterCross = 0;
        int winnerCounterCross1 = 0;
        int winnerCounterZero = 0;
        int winnerCounterZero1 = 0;

        for (int i = 0; i < board.length; i++) {
            int diag2 = (board.length - 1) - i;
            if (board[i][diag2] == ElState.X) {
                winnerCounterCross += 1;
            }
            if (board[i][diag2] == ElState.O) {
                winnerCounterZero1 += 1;
            }
            if (board[i][i] == ElState.X) {
                winnerCounterCross1 += 1;
            }
            if (board[i][i] == ElState.O) {
                winnerCounterZero += 1;
            }
        }
        if (winnerCounterCross == board.length) {
            return ElState.X;
        }
        if (winnerCounterCross1 == board.length) {
            return ElState.X;
        }
        if (winnerCounterZero == board.length) {
            return ElState.O;
        }
        if (winnerCounterZero1 == board.length) {
            return ElState.O;
        }
        return ElState.E;
    }

    public boolean checkBoardIsFull() {
        int checker = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != ElState.E) {
                    checker += 1;
                }
            }
        }
        if (checker == 9) {
            return true;
        } else {
            return false;
        }
    }

    public ElState checkForWinner() {
        if (checkColForWin() == ElState.X || checkRowsForWin() == ElState.X || checkDiagForWin() == ElState.X) {
            return ElState.X;
        }
        if (checkColForWin() == ElState.O || checkRowsForWin() == ElState.O || checkDiagForWin() == ElState.O) {
            return ElState.O;
        }
        if (checkBoardIsFull() == true && checkColForWin() != ElState.O && checkColForWin() != ElState.X && checkRowsForWin() != ElState.X && checkRowsForWin() != ElState.O
        && checkDiagForWin() != ElState.O && checkDiagForWin() != ElState.X) {
            return ElState.N;
        }
        return ElState.E;
    }

    public void print() {
        System.out.println("--- BOARD BEGIN ---");
        for (int i=0; i< board.length; i++) {
            System.out.println(board[i][0] + " " + board[i][1] + " " + board[i][2]);
        }
        System.out.println("--- BOARD END ---");
    }
}
