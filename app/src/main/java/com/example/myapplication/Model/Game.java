package com.example.myapplication.Model;

import android.util.Log;

import java.util.Arrays;

public class Game {
    public int boardSize = 3;
    private static final String TAG = "MainTagName";

    public ElState[][][] board = new ElState[boardSize][boardSize][boardSize];

    public Game() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Arrays.fill(board[i][j], ElState.E);
            }
        }
    }

    public void setElement(int xIndex, int yIndex, int zIndex, ElState value) {
        board[xIndex][yIndex][zIndex] = value;
    }

    public void print() {
        Log.d(TAG, "--- BOARD BEGIN ---");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                Log.d(TAG, board[i][j][0] + " " + board[i][j][1] + " " + board[i][j][2]);
                Log.d(TAG, "\n");
            }
            Log.d(TAG, "--------\n");
        }
        Log.d(TAG, "--- BOARD END ---");
    }

    public ElState checkRow2() {
        for (int i = 0; i < board.length; i++) {
            int winnerCounterCross = 0;
            int winnerCounterZero = 0;
            int winnerCounterCross1 = 0;
            int winnerCounterZero1 = 0;
            int winnerCounterCross2 = 0;
            int winnerCounterZero2 = 0;
            for (int j = 0; j < board.length; j++) {
                if (board[0][i][j] == ElState.X)
                    winnerCounterCross += 1;
                if (board[1][i][j] == ElState.X)
                    winnerCounterCross1 += 1;
                if (board[2][i][j] == ElState.X)
                    winnerCounterCross2 += 1;
                if (board[0][i][j] == ElState.O)
                    winnerCounterZero += 1;
                if (board[1][i][j] == ElState.O)
                    winnerCounterZero1 += 1;
                if (board[2][i][j] == ElState.O)
                    winnerCounterZero2 += 1;
            }
            if (winnerCounterCross == board.length || winnerCounterCross1 == board.length || winnerCounterCross2 == board.length) {
                return ElState.X;
            }
            if (winnerCounterZero == board.length || winnerCounterZero1 == board.length || winnerCounterZero2 == board.length) {
                return ElState.O;
            }
        }
        return ElState.E;
    }

    public ElState checkRow1() {
        for (int i = 0; i < board.length; i++) {
            int winnerCounterCross = 0;
            int winnerCounterZero = 0;
            int winnerCounterCross1 = 0;
            int winnerCounterZero1 = 0;
            int winnerCounterCross2 = 0;
            int winnerCounterZero2 = 0;
            for (int j = 0; j < board.length; j++) {
                if (board[i][j][0] == ElState.X)
                    winnerCounterCross += 1;
                if (board[i][j][1] == ElState.X)
                    winnerCounterCross1 += 1;
                if (board[i][j][2] == ElState.X)
                    winnerCounterCross2 += 1;
                if (board[i][j][0] == ElState.O)
                    winnerCounterZero += 1;
                if (board[i][j][1] == ElState.O)
                    winnerCounterZero1 += 1;
                if (board[i][j][2] == ElState.O)
                    winnerCounterZero2 += 1;
            }
            if (winnerCounterCross == board.length || winnerCounterCross1 == board.length || winnerCounterCross2 == board.length) {
                return ElState.X;
            }
            if (winnerCounterZero == board.length || winnerCounterZero1 == board.length || winnerCounterZero2 == board.length) {
                return ElState.O;
            }
        }
        return ElState.E;
    }

    public ElState checkCol1() {
        for (int i = 0; i < board.length; i++) {
            int winnerCounterCross = 0;
            int winnerCounterZero = 0;
            int winnerCounterCross1 = 0;
            int winnerCounterZero1 = 0;
            int winnerCounterCross2 = 0;
            int winnerCounterZero2 = 0;
            for (int j = 0; j < board.length; j++) {
                if (board[j][i][0] == ElState.X)
                    winnerCounterCross += 1;
                if (board[j][i][1] == ElState.X)
                    winnerCounterCross1 += 1;
                if (board[j][i][2] == ElState.X)
                    winnerCounterCross2 += 1;
                if (board[j][i][0] == ElState.O)
                    winnerCounterZero += 1;
                if (board[j][i][1] == ElState.O)
                    winnerCounterZero1 += 1;
                if (board[j][i][2] == ElState.O)
                    winnerCounterZero2 += 1;
            }
            if (winnerCounterCross == board.length || winnerCounterCross1 == board.length || winnerCounterCross2 == board.length) {
                return ElState.X;
            }
            if (winnerCounterZero == board.length || winnerCounterZero1 == board.length || winnerCounterZero2 == board.length) {
                return ElState.O;
            }
        }
        return ElState.E;
    }

    public ElState checkCol2() {
        for (int i = 0; i < board.length; i++) {
            int winnerCounterCross = 0;
            int winnerCounterZero = 0;
            int winnerCounterCross1 = 0;
            int winnerCounterZero1 = 0;
            int winnerCounterCross2 = 0;
            int winnerCounterZero2 = 0;
            for (int j = 0; j < board.length; j++) {
                if (board[0][j][i] == ElState.X)
                    winnerCounterCross += 1;
                if (board[1][j][i] == ElState.X)
                    winnerCounterCross1 += 1;
                if (board[2][j][i] == ElState.X)
                    winnerCounterCross2 += 1;
                if (board[0][j][i] == ElState.O)
                    winnerCounterZero += 1;
                if (board[1][j][i] == ElState.O)
                    winnerCounterZero1 += 1;
                if (board[2][j][i] == ElState.O)
                    winnerCounterZero2 += 1;
            }
            if (winnerCounterCross == board.length || winnerCounterCross1 == board.length || winnerCounterCross2 == board.length) {
                return ElState.X;
            }
            if (winnerCounterZero == board.length || winnerCounterZero1 == board.length || winnerCounterZero2 == board.length) {
                return ElState.O;
            }
        }
        return ElState.E;
    }

    public ElState checkDiag1() {
        for (int i = 0; i < board.length; i++) {
            int winnerCounterCross = 0;
            int winnerCounterZero = 0;
            int winnerCounterCross1 = 0;
            int winnerCounterZero1 = 0;
            for (int j = 0; j < board.length; j++) {
                int diag = (board.length - 1) - j;
                if (board[diag][diag][i] == ElState.X)
                    winnerCounterCross += 1;
                if (board[diag][j][i] == ElState.X)
                    winnerCounterCross1 += 1;
                if (board[diag][diag][i] == ElState.O)
                    winnerCounterZero += 1;
                if (board[diag][j][i] == ElState.O)
                    winnerCounterZero1 += 1;
            }
            if (winnerCounterCross == board.length || winnerCounterCross1 == board.length)
                return ElState.X;
            if (winnerCounterZero == board.length || winnerCounterZero1 == board.length)
                return ElState.O;
        }
        return ElState.E;
    }

    public ElState checkDiag2() {
        for (int i = 0; i < board.length; i++) {
            int winnerCounterCross = 0;
            int winnerCounterZero = 0;
            int winnerCounterCross1 = 0;
            int winnerCounterZero1 = 0;
            for (int j = 0; j < board.length; j++) {
                int diag = (board.length - 1) - j;
                if (board[i][j][diag] == ElState.X)
                    winnerCounterCross += 1;
                if (board[i][diag][diag] == ElState.X)
                    winnerCounterCross1 += 1;
                if (board[i][j][diag] == ElState.O)
                    winnerCounterZero += 1;
                if (board[i][diag][diag] == ElState.O)
                    winnerCounterZero1 += 1;
            }
            if (winnerCounterCross == board.length || winnerCounterCross1 == board.length) {
                return ElState.X;
            }
            if (winnerCounterZero == board.length || winnerCounterZero1 == board.length) {
                return ElState.O;
            }
        }
        return ElState.E;
    }
        public ElState checkDiag3() {
            for (int i = 0; i < board.length; i++) {
                int winnerCounterCross = 0;
                int winnerCounterZero = 0;
                int winnerCounterCross1 = 0;
                int winnerCounterZero1 = 0;
                for (int j = 0; j < board.length; j++) {
                    int diag = (board.length - 1) - j;
                    if (board[diag][i][diag] == ElState.X)
                        winnerCounterCross += 1;
                    if (board[diag][i][j] == ElState.X)
                        winnerCounterCross1 += 1;
                    if (board[diag][i][diag] == ElState.O)
                        winnerCounterZero += 1;
                    if (board[diag][i][j] == ElState.O)
                        winnerCounterZero1 += 1;
                }
                if (winnerCounterCross == board.length || winnerCounterCross1 == board.length)
                    return ElState.X;
                if (winnerCounterZero == board.length || winnerCounterZero1 == board.length)
                    return ElState.O;
            }
            return ElState.E;
        }

    public ElState checkDiag4() {
        for (int i = 0; i < board.length; i++) {
            int winnerCounterCross = 0;
            int winnerCounterCross1 = 0;
            int winnerCounterCross2 = 0;
            int winnerCounterCross3 = 0;
            int winnerCounterZero = 0;
            int winnerCounterZero1 = 0;
            int winnerCounterZero2 = 0;
            int winnerCounterZero3 = 0;
            for (int j = 0; j < board.length; j++) {
                int diag = (board.length - 1) - j;
                if (board[j][diag][j] == ElState.X)
                    winnerCounterCross += 1;
                if (board[diag][diag][diag] == ElState.X)
                    winnerCounterCross1 += 1;
                if (board[j][diag][diag] == ElState.X)
                    winnerCounterCross2 += 1;
                if (board[diag][diag][j] == ElState.X)
                    winnerCounterCross3 += 1;
                if (board[j][diag][j] == ElState.O)
                    winnerCounterZero += 1;
                if (board[diag][diag][diag] == ElState.O)
                    winnerCounterZero1 += 1;
                if (board[j][diag][diag] == ElState.O)
                    winnerCounterZero2 += 1;
                if (board[j][diag][j] == ElState.O)
                    winnerCounterZero3 += 1;
            }
            if (winnerCounterCross == board.length || winnerCounterCross1 == board.length || winnerCounterCross2 == board.length || winnerCounterCross3 == board.length)
                return ElState.X;
            if (winnerCounterZero == board.length || winnerCounterZero1 == board.length || winnerCounterZero2 == board.length || winnerCounterZero3 == board.length)
                return ElState.O;
        }
        return ElState.E;
    }
}