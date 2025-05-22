package net.lesson7;

import java.util.ArrayList;
import java.util.List;

public class Company {
    private String name;
    private String year;
    private List<Movie1> movies;

    public Company(String name, String year) {
        this.name = name;
        this.year = year;
        this.movies = new ArrayList<>();
    }

    public void addMovie (Movie1 movie){
        movies.add(movie);
    }

    public String getName() {
        return name;
    }

    public List<Movie1> getMovies() {
        return movies;
    }
}
