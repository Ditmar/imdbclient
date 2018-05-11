package com.example.ditmar.imdbapi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ditmar.imdbapi.ListDataSource.CustomAdapter;
import com.example.ditmar.imdbapi.ListDataSource.ItemList;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity  implements AdapterView.OnItemClickListener{

    private ListView LIST;
    private ArrayList<ItemList> LISTINFO;
    private Context root;
    private CustomAdapter ADAPTER;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        root = this;
        LISTINFO = new ArrayList<ItemList>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //loadInitialRestData();
        loadComponents();
    }

    private void loadInitialRestData(String keystr) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://www.omdbapi.com/?s=" + keystr + "&page=1&apikey=e1c80c83";
        client.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                int a = 1;
                try {
                    JSONArray list =  (JSONArray) response.get("Search");
                    LISTINFO.clear();
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject itemJson = list.getJSONObject(i);
                        String title = itemJson.getString("Title");
                        String year = itemJson.getString("Year");
                        String imdbID = itemJson.getString("imdbID");
                        String type = itemJson.getString("Type");
                        String Poster = itemJson.getString("Poster");

                        ItemList item = new ItemList(Poster, title, year, type, imdbID);
                        LISTINFO.add(item);
                    }
                    //FIX
                    ADAPTER = new  CustomAdapter(root, LISTINFO);
                    LIST.setAdapter(ADAPTER);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(root, "FAIL", Toast.LENGTH_LONG).show();
            }

        });
    }

    private void loadComponents() {
        LIST  = (ListView) this.findViewById(R.id.listviewlayout);
        //LISTINFO.add(new ItemList("https://images-na.ssl-images-amazon.com/images/M/MV5BMjA4MzAyNDE1MF5BMl5BanBnXkFtZTgwODQxMjU5MzE@._V1_SX300.jpg","prueba","prueba","movie"));
        EditText search = (EditText)this.findViewById(R.id.searchmovie);
        //click event
        LIST.setOnItemClickListener(this);

        //EVENTOSSSSSSSS
        search.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString();
                loadInitialRestData(str);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String idImdb = this.LISTINFO.get(position).getIdimdb();
        Intent moviewDetaild = new Intent(this, MovieDetaild.class);
        moviewDetaild.putExtra("id", idImdb);
        this.startActivity(moviewDetaild);
    }
}
