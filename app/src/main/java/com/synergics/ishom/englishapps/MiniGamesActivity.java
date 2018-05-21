package com.synergics.ishom.englishapps;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.synergics.ishom.englishapps.Controller.AppConfig;
import com.synergics.ishom.englishapps.Controller.RestFullConfig.ApiClient;
import com.synergics.ishom.englishapps.Controller.RestFullConfig.ApiInterface;
import com.synergics.ishom.englishapps.Controller.SqliteHandler;
import com.synergics.ishom.englishapps.Model.ListGamesCategory;
import com.synergics.ishom.englishapps.Model.RestFullObject.GamesCategory;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MiniGamesActivity extends AppCompatActivity {

    private View btnAlphabet, btnMath, btnAnimals, btnTransportation,
            btnMyFamily, btnFruit, btnHowToPlay;

    private RoundCornerProgressBar progressAlphabet, progressMath, progressAnimals, progressTransportation,
            progressFamily, progressFruit;

    private TextView textProsesAlphabet, textProsesMath, textProsesANimals, textProsesTransportation,
            textProsesFamily, textProsesFruit;

    private ArrayList<ListGamesCategory> items = new ArrayList<>();
    private SqliteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_games);

        setToolbar();

        db = new SqliteHandler(getApplicationContext());

//        btnHowToPlay = findViewById(R.id.btnHowToPlay);

        btnAlphabet = findViewById(R.id.btnAlphabet);
        btnMath = findViewById(R.id.btnMath);
        btnAnimals = findViewById(R.id.btnAnimals);
        btnTransportation = findViewById(R.id.btnTransportation);
        btnMyFamily = findViewById(R.id.btnFamily);
        btnFruit = findViewById(R.id.btnFruit);

        progressAlphabet = findViewById(R.id.prosesAlphabet);
        progressMath = findViewById(R.id.prosesMath);
        progressAnimals = findViewById(R.id.prosesAnimals);
        progressTransportation = findViewById(R.id.prosesTransportation);
        progressFamily = findViewById(R.id.prosesFamily);
        progressFruit = findViewById(R.id.prosesFruit);

        textProsesAlphabet = findViewById(R.id.textProsesAlphabet);
        textProsesMath = findViewById(R.id.textProsesMath);
        textProsesANimals = findViewById(R.id.textProsesAnimals);
        textProsesTransportation = findViewById(R.id.textProsesTransportation);
        textProsesFamily = findViewById(R.id.textProsesFamily);
        textProsesFruit = findViewById(R.id.textProsesFruit);

        btnAlphabet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HangManActivity.class);
                if (db.getIdGame(AppConfig.idAlphabet) != null){
                    intent.putExtra("id", db.getIdGame(AppConfig.idAlphabet));
                    intent.putExtra("idCategory", AppConfig.idAlphabet);
                    startActivity(intent);
                }else {
                    Toast.makeText(MiniGamesActivity.this, "Sorry data not found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnMath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HangManActivity.class);
                if (db.getIdGame(AppConfig.idMath) != null){
                    intent.putExtra("id", db.getIdGame(AppConfig.idMath));
                    intent.putExtra("idCategory", AppConfig.idMath);
                    startActivity(intent);
                }else {
                    Toast.makeText(MiniGamesActivity.this, "Sorry data not found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnAnimals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HangManActivity.class);
                if (db.getIdGame(AppConfig.idAnimals) != null){
                    intent.putExtra("id", db.getIdGame(AppConfig.idAnimals));
                    intent.putExtra("idCategory", AppConfig.idAnimals);
                    startActivity(intent);
                }else {
                    Toast.makeText(MiniGamesActivity.this, "Sorry data not found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnTransportation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HangManActivity.class);
                if (db.getIdGame(AppConfig.idTransportation) != null){
                    intent.putExtra("id", db.getIdGame(AppConfig.idTransportation));
                    intent.putExtra("idCategory", AppConfig.idTransportation);
                    startActivity(intent);
                }else {
                    Toast.makeText(MiniGamesActivity.this, "Sorry data not found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnFruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HangManActivity.class);
                if (db.getIdGame(AppConfig.idFruit) != null){
                    intent.putExtra("id", db.getIdGame(AppConfig.idFruit));
                    intent.putExtra("idCategory", AppConfig.idFruit);
                    startActivity(intent);
                }else {
                    Toast.makeText(MiniGamesActivity.this, "Sorry data not found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnMyFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HangManActivity.class);
                if (db.getIdGame(AppConfig.idFamily) != null){
                    intent.putExtra("id", db.getIdGame(AppConfig.idFamily));
                    intent.putExtra("idCategory", AppConfig.idFamily);
                    startActivity(intent);
                }else {
                    Toast.makeText(MiniGamesActivity.this, "Sorry data not found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        setData();
    }

    private void setData() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiInterface apiInterface = ApiClient.EnglishApi().create(ApiInterface.class);
        Call call = apiInterface.getGamesCategory();
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                if (response.isSuccessful()){
                    GamesCategory object = (GamesCategory) response.body();
                    if (object.status){
                        db.addItems(object);
                        displayItem();
                    }else {
                        Toast.makeText(MiniGamesActivity.this, object.message, Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(MiniGamesActivity.this, "Failed to access server", Toast.LENGTH_SHORT).show();
                }

                progressDialog.hide();
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

    private void displayItem() {
        items = db.getGamesCategory();

        if (items.size() != 0){
            progressAlphabet.setProgress(items.get(0).getDone());
            textProsesAlphabet.setText((int) items.get(0).getDone() +" %");

            progressMath.setProgress(items.get(1).getDone());
            textProsesMath.setText((int) items.get(1).getDone() +" %");

            progressAnimals.setProgress(items.get(2).getDone());
            textProsesANimals.setText((int) items.get(2).getDone() +" %");

            progressTransportation.setProgress(items.get(3).getDone());
            textProsesTransportation.setText((int) items.get(3).getDone() +" %");

            progressFamily.setProgress(items.get(4).getDone());
            textProsesFamily.setText((int) items.get(4).getDone() +" %");

            progressFruit.setProgress(items.get(5).getDone());
            textProsesFruit.setText((int) items.get(5).getDone() +" %");
        }
    }

    private void setToolbar() {
        Toolbar toolbar = this.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));

        TextView title = findViewById(R.id.title);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        displayItem();
        super.onResume();
    }
}
