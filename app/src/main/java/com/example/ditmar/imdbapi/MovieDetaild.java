package com.example.ditmar.imdbapi;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ditmar.imdbapi.ListDataSource.OnLoadImage;
import com.example.ditmar.imdbapi.ListDataSource.TaskImg;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;

public class MovieDetaild extends AppCompatActivity implements OnLoadImage {
    public String idImdb;
    protected TextView title, rate, sinopsis, actors, director, year, rated;
    protected ListView listw;
    protected ImageView poster;
    protected MovieDetaild root;

    protected  com.example.ditmar.imdbapi.DataMovieDetaild.MovieDetaild DATA;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        root = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detaild);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        idImdb = this.getIntent().getExtras().getString("id");
        loadComponents();
        loadAsyncData();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void loadAsyncData() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://www.omdbapi.com/?i=" + this.idImdb + "&apikey=e1c80c83",
                new JsonHttpResponseHandler(){
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            String title = response.getString("Title");
                            String year = response.getString("Year");
                            String rated = response.getString("Rated");
                            String director = response.getString("Director");
                            String writer = response.getString("Writer");
                            String actors = response.getString("Actors");
                            String plot = response.getString("Plot");
                            String poster = response.getString("Poster");
                            String imdbrating = response.getString("imdbRating");
                            DATA = new com.example.ditmar.imdbapi.DataMovieDetaild.MovieDetaild(title, year, rated, director, writer, actors, plot, poster, imdbrating);
                            root.setInformation();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    private void setInformation() {
        this.title.setText(DATA.getTitle());
        this.rate.setText(DATA.getImdbRating());
        this.sinopsis.setText(DATA.getPlot());
        this.actors.setText(DATA.getActors());
        this.director.setText(DATA.getDirector());
        this.year.setText(DATA.getYear());
        this.rated.setText(DATA.getRated());
        TaskImg imgload = new TaskImg();
        imgload.execute(DATA.getPoster());
        imgload.setLoadImage(this.poster, this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        String [] listActos = DATA.getWriter().split(",");
        adapter.addAll(listActos);
        this.listw.setAdapter(adapter);
    }
    private void loadComponents() {
        this.title = (TextView) this.findViewById(R.id.title);
        this.rate = (TextView)this.findViewById(R.id.rate);
        this.sinopsis = (TextView)this.findViewById(R.id.sinopsis);
        this.actors = (TextView)this.findViewById(R.id.actors);
        this.director = (TextView)this.findViewById(R.id.director);
        this.year = (TextView)this.findViewById(R.id.year);
        this.rated = (TextView)this.findViewById(R.id.rated);

        this.listw = (ListView)this.findViewById(R.id.listw);

        this.poster = (ImageView)this.findViewById(R.id.poster);

    }

    @Override
    public void setLoadImage(ImageView container, Bitmap img) {
        container.setImageBitmap(img);
    }
}
