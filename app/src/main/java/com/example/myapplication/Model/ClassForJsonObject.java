package com.example.myapplication.Model;

public class ClassForJsonObject {
    public String role;
    public ElState winner;
    public ElState[][] board;
    public ClassForJsonObject(String role, ElState[][] board, ElState winner) {
        this.role = role;
        this.board = board;
        this.winner = winner;
    }
}
