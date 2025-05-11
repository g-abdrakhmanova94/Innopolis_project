package net.lesson4;

public class Movie {
    String name;
    int rating;
    String genre;
    String country;
    boolean isOscar;
    String year;


    public Movie(String name, int rating, String genre, String country, boolean isOscar, String year) {
        this.name = name;
        this.rating = rating;
        this.genre = genre;
        this.country = country;
        this.isOscar = isOscar;
        this.year = year;
    }
}
