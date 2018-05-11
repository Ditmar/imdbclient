package com.example.ditmar.imdbapi.DataMovieDetaild;

import android.graphics.Movie;

public class MovieDetaild {
    private String Title;
    private String Year;
    private String  Rated;
    private String Director;
    private String Writer;
    private String Actors;
    private String Plot;
    private String Poster;
    private String imdbRating;
    public MovieDetaild (String title, String year, String rated, String director, String writer, String actors, String plot, String poster, String imdbrating) {
        this.Actors = actors;
        this.Title = title;
        this.Year = year;
        this.Rated = rated;
        this.Director = director;
        this.Writer = writer;
        this.Actors = actors;
        this.Plot = plot;
        this.Poster = poster;
        this.imdbRating = imdbrating;
    }
    public String getTitle(){
        return this.Title;
    }
    public String getYear(){
        return this.Year;
    }
    public String getRated(){
        return this.Rated;
    }
    public String getDirector() {
        return this.Director;
    }
    public String getWriter(){
        return this.Writer;
    }
    public String getActors() {
        return this.Actors;
    }
    public String getPlot() {
        return this.Plot;
    }
    public String getPoster() {
        return this.Poster;
    }
    public String getImdbRating () {
        return this.imdbRating;
    }
}
