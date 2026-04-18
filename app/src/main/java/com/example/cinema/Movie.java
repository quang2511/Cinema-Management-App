package com.example.cinema;

public class Movie {
    private int id;
    private String title;
    private String image;
    private double price;
    private String duration;
    private String language;
    private String synopsis;


    public Movie(int id, String title, String image, double price, String duration, String language, String synopsis) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.price = price;
        this.duration = duration;
        this.language = language;
        this.synopsis = synopsis;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getImage() { return image; }
    public double getPrice() { return price; }
    public String getDuration() { return duration; }
    public String getLanguage() { return language; }
    public String getSynopsis() { return synopsis; }
}