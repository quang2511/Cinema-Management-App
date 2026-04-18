package com.example.cinema;

public class Seat {
    private String name;
    private int status;

    public Seat(String name, int status) {
        this.name = name;
        this.status = status;
    }

    public String getName() { return name; }
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
}