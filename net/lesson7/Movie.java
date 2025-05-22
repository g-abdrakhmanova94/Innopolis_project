package net.lesson7;

public class Movie {
    private String name;
    private int rating;
    private String genre;
    private String country;
    private boolean isOscar;


    public Movie(String name, int rating, String genre, String country, boolean isOscar) {
        this.name = name;
        this.rating = rating;
        this.genre = genre;
        this.country = country;
        this.isOscar = isOscar;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", rating=" + rating +
                ", genre='" + genre + '\'' +
                ", country='" + country + '\'' +
                ", isOscar=" + isOscar +
                '}';
    }
}