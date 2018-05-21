package com.synergics.ishom.englishapps;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
        btnFruit = findViewById(R.id.btnFruit);
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

        btnMath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SubMenuActivity.class);
                intent.putExtra("id", AppConfig.idMath);
                intent.putExtra("nama", AppConfig.textMath);
                startActivity(intent);
            }
        });

        btnAnimals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SubMenuActivity.class);
                intent.putExtra("id", AppConfig.idAnimals);
                intent.putExtra("nama", AppConfig.textAnimals);
                startActivity(intent);
            }
        });

        btnTransportation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SubMenuActivity.class);
                intent.putExtra("id", AppConfig.idTransportation);
                intent.putExtra("nama", AppConfig.textTransportation);
                startActivity(intent);
            }
        });

        btnMyFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SubMenuActivity.class);
                intent.putExtra("id", AppConfig.idFamily);
                intent.putExtra("nama", AppConfig.textFamily);
                startActivity(intent);
            }
        });

        btnFruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SubMenuActivity.class);
                intent.putExtra("id", AppConfig.idFruit);
                intent.putExtra("nama", AppConfig.textFruit);
                startActivity(intent);
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayGameOver();
            }
        });

        btnMiniGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MiniGamesActivity.class);
                startActivity(intent);
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Setting is under development", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void displayGameOver(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_tentang);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Button btnBack  = dialog.findViewById(R.id.btnBack);

        dialog.show();
        dialog.setCancelable(false);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
            }
        });
    }
}
