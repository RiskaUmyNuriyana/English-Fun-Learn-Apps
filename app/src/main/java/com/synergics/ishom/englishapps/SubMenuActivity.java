package com.synergics.ishom.englishapps;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SubMenuActivity extends AppCompatActivity {

    private LinearLayout btnLearn, btnLearnVideo, btnPractice;
    private String id;

    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_menu);

        bundle = getIntent().getExtras();
        id = bundle.getString("id");

        btnLearn = findViewById(R.id.btnLearn);
        btnLearnVideo = findViewById(R.id.btnLearnVideo);
        btnPractice = findViewById(R.id.btnPractice);

        btnLearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LearnActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        btnLearnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), VideoActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        btnPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LearnTaskActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("nama", bundle.getString("nama"));
                startActivity(intent);
            }
        });

        setToolbar();
    }

    private void setToolbar() {
        Toolbar toolbar = this.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));

        TextView title = findViewById(R.id.title);
        title.setText(bundle.getString("nama"));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
