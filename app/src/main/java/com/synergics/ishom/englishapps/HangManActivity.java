package com.synergics.ishom.englishapps;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;
import com.synergics.ishom.englishapps.Controller.RestFullConfig.ApiClient;
import com.synergics.ishom.englishapps.Controller.RestFullConfig.ApiInterface;
import com.synergics.ishom.englishapps.Controller.SqliteHandler;
import com.synergics.ishom.englishapps.Model.RestFullObject.MiniGames;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HangManActivity extends AppCompatActivity {

    private ImageView hp1, hp2, hp3;
    private ImageView imageGames;

    private LinearLayout btnBantuan;
    private LinearLayout btnPlay;

    private RecyclerView recyclerViewName;
    private SlimAdapter adapterName;
    private LinearLayoutManager managerName1;
    private GridLayoutManager managerName2;
    private ArrayList<MiniGames.Data.Spell> itemName = new ArrayList<>();

    private RecyclerView recyclerViewGame;
    private SlimAdapter adapterGame;
    private GridLayoutManager managerGame;
    private ArrayList<MiniGames.Data.Char> itemGame = new ArrayList<>();

    private int live = 3;
    private String image = "", name = "", audio = "";

    private String id, idCategory;

    private boolean playPause;
    private MediaPlayer mediaPlayer;

    private boolean initialStage = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hang_man);

        imageGames = findViewById(R.id.image);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        hp1 = findViewById(R.id.hp1);
        hp2 = findViewById(R.id.hp2);
        hp3 = findViewById(R.id.hp3);

        btnBantuan = findViewById(R.id.btnBantuan);
        btnPlay = findViewById(R.id.btnPlay);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (audio.isEmpty()){
                    Toast.makeText(HangManActivity.this, "Audio not found", Toast.LENGTH_SHORT).show();
                }else {
                    if (!playPause) {

                        if (initialStage) {
                            new Player().execute(audio);
                        } else {
                            if (!mediaPlayer.isPlaying())
                                mediaPlayer.start();
                        }

                        playPause = true;

                    } else {

                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                        }

                        playPause = false;
                    }
                }
            }
        });

        recyclerViewName = findViewById(R.id.recycle);
        managerName1  = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewName.setLayoutManager(managerName1);
//        managerName2 = new GridLayoutManager(this, 7, GridLayoutManager.VERTICAL, false);
//        recyclerViewName.setLayoutManager(managerName2);

        adapterName = SlimAdapter.create()
                .register(R.layout.item_chars_grey, new SlimInjector<MiniGames.Data.Spell>() {
                    @Override
                    public void onInject(final MiniGames.Data.Spell data, IViewInjector injector) {
                        injector.with(R.id.item, new IViewInjector.Action() {
                                    @Override
                                    public void action(View view) {
                                        TextView items = view.findViewById(R.id.text);
                                        if (data.status == 0){
                                            items.setText("_");
                                        }else {
                                            items.setText(data.chracter);
                                        }
                                    }
                                });
                    }
                })
                .attachTo(recyclerViewName);

        recyclerViewGame= findViewById(R.id.recycle1);
        managerGame = new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        recyclerViewGame.setLayoutManager(managerGame);

        btnBantuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayBantuanDialog();
            }
        });

        adapterGame = SlimAdapter.create()
                .register(R.layout.item_games_white, new SlimInjector<MiniGames.Data.Char>() {
                    @Override
                    public void onInject(final MiniGames.Data.Char data, IViewInjector injector) {
                        injector.with(R.id.item, new IViewInjector.Action() {
                            @Override
                            public void action(View view) {
                                TextView items = view.findViewById(R.id.text);
                                items.setText(data.chracter);

                                Log.d("status : ",  "Data : " + data.chracter + " - " + data.status);

                                if (data.status == 2){
                                    view.setBackground(getResources().getDrawable(R.drawable.bg_grey_round_8dp));
                                }else if (data.status == 3){
                                    view.setBackground(getResources().getDrawable(R.drawable.bg_red_round_8dp));
                                }else {
                                    view.setBackground(getResources().getDrawable(R.drawable.bg_white_round_8dp));
                                }

                                view.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (live == 0){
                                            Toast.makeText(HangManActivity.this, "Game Over !!", Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            if (data.status == 0 || data.status == 1) {
                                                if (checkString(data)){
                                                    setItemStatusRight(data);
                                                }else {
                                                    live = live - 1;
                                                    setLivePoint(live);
                                                    setItemStatusFalse(data);
                                                }
                                            }else {
                                                Toast.makeText(HangManActivity.this, "Text Has Already Used !", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                });
                            }
                        });
                    }
                })
                .attachTo(recyclerViewGame);

        setData();

        setToolbar();

    }

    private void setItemStatusRight(MiniGames.Data.Char data) {
        for (int i = 0; i < itemGame.size(); i++) {
            if (itemGame.get(i).chracter.equals(data.chracter)){
                data.status = 2;
                itemGame.set(i, data);
                adapterGame.notifyDataSetChanged();
                break;
            }
        }
        checkWin();
    }

    private void setItemStatusFalse(MiniGames.Data.Char data) {
        for (int i = 0; i < itemGame.size(); i++) {
            if (itemGame.get(i).chracter.equals(data.chracter)){
                data.status = 3;
                itemGame.set(i, data);
                adapterGame.notifyDataSetChanged();
                break;
            }
        }
    }

    private void checkWin(){
        boolean check = true;
        for (int i = 0; i < itemName.size(); i++) {
            if (itemName.get(i).status == 0){
                check = false;
            }
        }
        if (check){
            displaySuccess(name, image);
        }
    }

    private boolean checkString(MiniGames.Data.Char chars){

        boolean bol = false;

        for (int i = 0; i < itemName.size(); i++) {
            MiniGames.Data.Spell item = itemName.get(i);
            if (item.chracter.equals(chars.chracter)){
                item.status = 1;
                itemName.set(i, item);
                adapterName.notifyDataSetChanged();
                bol = true;
            }
        }

        return bol;
    }

    private void setData() {
        id = getIntent().getExtras().getString("id");
        idCategory = getIntent().getExtras().getString("idCategory");

        if (idCategory.equals("1") || idCategory.equals("2")){
            imageGames.setVisibility(View.GONE);
            btnPlay.setVisibility(View.VISIBLE);
        }

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiInterface apiInterface = ApiClient.EnglishApi().create(ApiInterface.class);
        Call call = apiInterface.getMiniGames(id);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                if (response.isSuccessful()){

                    MiniGames object = (MiniGames) response.body();

                    if (object.status){
                        for (MiniGames.Data.Char items1 : object.data.chars){
                            itemGame.add(items1);
                        }
                        for (MiniGames.Data.Spell items2 : object.data.spells){
                            itemName.add(items2);
                        }

                        image = object.data.image;
                        name = object.data.name;
                        audio = object.data.audio;

                    }else {
                        Toast.makeText(getApplicationContext(), object.message, Toast.LENGTH_SHORT).show();
                    }

                    Picasso.with(getApplicationContext())
                            .load(image)
                            .into(imageGames);

                    adapterGame.updateData(itemGame);
                    adapterGame.notifyDataSetChanged();

                    adapterName.updateData(itemName);
                    adapterName.notifyDataSetChanged();

                }else {
                    Toast.makeText(getApplicationContext(), "Failed to access server", Toast.LENGTH_SHORT).show();
                }

                progressDialog.hide();
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });

    }

    private void setLivePoint(int live){
        if (live == 1){
            hp1.setImageDrawable(getResources().getDrawable(R.drawable.ic_like));
            hp2.setImageDrawable(getResources().getDrawable(R.drawable.ic_like_off));
            hp3.setImageDrawable(getResources().getDrawable(R.drawable.ic_like_off));


            YoYo.with(Techniques.Shake)
                    .duration(200)
                    .repeat(2)
                    .playOn(hp1);

        }else if (live == 2){
            hp1.setImageDrawable(getResources().getDrawable(R.drawable.ic_like));
            hp2.setImageDrawable(getResources().getDrawable(R.drawable.ic_like));
            hp3.setImageDrawable(getResources().getDrawable(R.drawable.ic_like_off));

            YoYo.with(Techniques.Shake)
                    .duration(200)
                    .repeat(2)
                    .playOn(hp1);

            YoYo.with(Techniques.Shake)
                    .duration(200)
                    .repeat(2)
                    .playOn(hp2);
        }else if (live == 0){
            hp1.setImageDrawable(getResources().getDrawable(R.drawable.ic_like_off));
            hp2.setImageDrawable(getResources().getDrawable(R.drawable.ic_like_off));
            hp3.setImageDrawable(getResources().getDrawable(R.drawable.ic_like_off));

            displayGameOver();

//            Toast.makeText(this, "Game Over !", Toast.LENGTH_SHORT).show();
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

    private void displayBantuanDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_bantuan);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Button btnUndestand = dialog.findViewById(R.id.btnUndestand);

        dialog.show();
        dialog.setCancelable(false);

        btnUndestand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
            }
        });
    }

    private void displayGameOver(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_game_over);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Button btnBack  = dialog.findViewById(R.id.btnBack);
        Button btnTryAgain = dialog.findViewById(R.id.btnTryAgain);

        dialog.show();
        dialog.setCancelable(false);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HangManActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("idCategory", idCategory);
                finish();
                startActivity(intent);
            }
        });
    }

    class Player extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            Boolean prepared = false;

            try {
                mediaPlayer.setDataSource(strings[0]);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        initialStage = true;
                        playPause = false;
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });

                mediaPlayer.prepare();
                prepared = true;

            } catch (Exception e) {
                Log.e("MyAudioStreamingApp", e.getMessage());
                prepared = false;
            }

            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            mediaPlayer.start();
            initialStage = false;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    private void displaySuccess (String name, String imageUrl){

        final SqliteHandler db = new SqliteHandler(getApplicationContext());
        db.updateItem(id);

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_game_success);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Button btnBack = dialog.findViewById(R.id.btnBack);
        Button btnNext = dialog.findViewById(R.id.btnNext);
        TextView text = dialog.findViewById(R.id.answer);
        ImageView image = dialog.findViewById(R.id.image);

        text.setText(name);
        Picasso.with(getApplicationContext())
                .load(imageUrl)
                .into(image);

        dialog.show();
        dialog.setCancelable(false);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newId = db.getIdGame(idCategory);
                Intent intent = new Intent(getApplicationContext(), HangManActivity.class);
                intent.putExtra("id", newId);
                intent.putExtra("idCategory", idCategory);

                startActivity(intent);
                finish();
            }
        });
    }
}
