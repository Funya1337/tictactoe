package com.example.myapplication.Model;

public class ClassForJsonObject {
    public String role;
    public ElState[][] board;
    public ClassForJsonObject(String role, ElState[][] board) {
        this.role = role;
        this.board = board;
    }
}
