package com.synergics.ishom.englishapps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.synergics.ishom.englishapps.Controller.AppConfig;

public class MainActivity extends AppCompatActivity {

    private View btnAlphabet, btnMath, btnAnimals, btnTransportation,
            btnMyFamily, btnFruit, btnMiniGames, btnSetting, btnAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAlphabet = findViewById(R.id.btnAlphabet);
        btnMath = findViewById(R.id.btnMath);
        btnAnimals = findViewById(R.id.btnAnimals);
        btnTransportation = findViewById(R.id.btnTransportation);
        btnMyFamily = findViewById(R.id.btnFamily);
        btnFruit = findViewById(R.id.btnFamily);
        btnMiniGames = findViewById(R.id.btnMiniGames);
        btnSetting = findViewById(R.id.btnSettings);
        btnAbout = findViewById(R.id.btnAbout);

        btnAlphabet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SubMenuActivity.class);
                intent.putExtra("id", AppConfig.idAlphabet);
                intent.putExtra("nama", AppConfig.textAlphabet);
                startActivity(intent);
            }
        });

    }
}
