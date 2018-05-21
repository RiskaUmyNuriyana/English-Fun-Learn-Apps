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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.synergics.ishom.englishapps.Controller.RestFullConfig.ApiClient;
import com.synergics.ishom.englishapps.Controller.RestFullConfig.ApiInterface;
import com.synergics.ishom.englishapps.Model.RestFullObject.ResponsePractice;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LearnTaskActivity extends AppCompatActivity {

    //pada soal
    private TextView number, soal;
    private ImageView image;
    private Button btnBefore, btnAfter;
    private LinearLayout btnPlay;

    private RadioGroup options;
    private RadioButton option1, option2, option3;

    //pada indikator
    private RecyclerView recyclerView;
    private SlimAdapter adapter;
    private LinearLayoutManager manager;
    private ArrayList<ResponsePractice> items = new ArrayList<>();

    private int nomer = 1;

    private boolean playPause;
    private MediaPlayer mediaPlayer;

    private boolean initialStage = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_task);

        number = findViewById(R.id.number);
        soal = findViewById(R.id.soal);

        image = findViewById(R.id.image);
        btnPlay = findViewById(R.id.btnPlay);

        btnBefore = findViewById(R.id.btnBefore);
        btnAfter = findViewById(R.id.btnAfter);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        options = findViewById(R.id.options);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);

        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);

        adapter = SlimAdapter.create()
                .register(R.layout.item_practice, new SlimInjector<ResponsePractice>() {

                    @Override
                    public void onInject(final ResponsePractice data, IViewInjector injector) {
                        injector.with(R.id.item, new IViewInjector.Action() {
                            @Override
                            public void action(View view) {
//
                                if (nomer == data.getNumber()) {
                                    view.setBackground(getResources().getDrawable(R.drawable.bg_black_round_32dp));
                                    setContent(data);
                                }else {
                                    if (data.getStatus() == 1 || data.getStatus() == 2){
                                        view.setBackground(getResources().getDrawable(R.drawable.bg_grey_round_32dp));
                                    }else {
                                        view.setBackground(getResources().getDrawable(R.drawable.bg_dark_blue_round_32dp));
                                    }
                                }

                                TextView text = view.findViewById(R.id.text);
                                text.setText(data.getNumber() + "");

                                view.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        setContent(data);
                                        options.clearCheck();
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        });
                    }
                })
                .attachTo(recyclerView);

        setData();
        setToolbar();
    }

    private void setData() {

        String id = getIntent().getExtras().getString("id");

        if (id.equals("1") || id.equals("2")){
            image.setVisibility(View.GONE);
            btnPlay.setVisibility(View.VISIBLE);
        }

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiInterface apiInterface = ApiClient.EnglishApi().create(ApiInterface.class);
        Call call = apiInterface.getPractice(id);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                if (response.isSuccessful()){
                    List<ResponsePractice> object = (List<ResponsePractice>) response.body();
                    for (ResponsePractice item : object){
                        items.add(item);
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Failed to access server", Toast.LENGTH_SHORT).show();
                }

                adapter.updateData(items);
                adapter.notifyDataSetChanged();

                progressDialog.hide();
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

    private void setToolbar() {
        Toolbar toolbar = this.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));

        TextView title = findViewById(R.id.title);
        title.setText("Practice " + getIntent().getExtras().getString("nama"));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setContent(final ResponsePractice item){

        displayCache();
        nomer = item.getNumber();

        number.setText("Task Number : " + nomer);

        Picasso.with(getApplicationContext())
                .load(item.getImage())
                .into(image);

        soal.setText(item.getQuestion());

        option1.setText(item.getOptions().get(0).getAnswer());
        option2.setText(item.getOptions().get(1).getAnswer());
        option3.setText(item.getOptions().get(2).getAnswer());

        if (item.getOptions().get(0).getStatus() == 1){
            options.clearCheck();
            options.check(R.id.option1);
            item.getOptions().get(1).setStatus(0);
            item.getOptions().get(2).setStatus(0);
        }else if (item.getOptions().get(1).getStatus() == 1){
            options.clearCheck();
            options.check(R.id.option2);
            item.getOptions().get(0).setStatus(0);
            item.getOptions().get(2).setStatus(0);
        }else if (item.getOptions().get(2).getStatus() == 1){
            options.clearCheck();
            options.check(R.id.option3);
            item.getOptions().get(1).setStatus(0);
            item.getOptions().get(0).setStatus(0);
        }

        options.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.option1){
                    item.getOptions().get(0).setStatus(1);
                    item.getOptions().get(1).setStatus(0);
                    item.getOptions().get(2).setStatus(0);
                    if (item.getOptions().get(0).getAnswer().contains(item.getAnswer())){
                        item.setStatus(2);
                    }else {
                        item.setStatus(1);
                    }
                }else if (checkedId == R.id.option2){
                    item.getOptions().get(0).setStatus(0);
                    item.getOptions().get(1).setStatus(1);
                    item.getOptions().get(2).setStatus(0);
                    if (item.getOptions().get(1).getAnswer().contains(item.getAnswer())){
                        item.setStatus(2);
                    }else {
                        item.setStatus(1);
                    }
                }else if (checkedId == R.id.option3){
                    item.getOptions().get(0).setStatus(0);
                    item.getOptions().get(1).setStatus(0);
                    item.getOptions().get(2).setStatus(1);
                    if (item.getOptions().get(2).getAnswer().contains(item.getAnswer())){
                        item.setStatus(2);
                    }else {
                        item.setStatus(1);
                    }
                }
            }
        });

        if (nomer == 1){
            btnBefore.setVisibility(View.INVISIBLE);
        }else {
            btnBefore.setVisibility(View.VISIBLE);
        }

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!playPause) {

                    if (initialStage) {
                        new Player().execute(item.getAudio());
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
        });

        if (item.getNumber() == items.size()){
            btnAfter.setText("Submit");
            btnAfter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapter.notifyDataSetChanged();
                    displayCacheAfter();
                    displayResult();
                }
            });
        }else {
            btnAfter.setText("Next");
            btnAfter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nomer = nomer + 1;
                    manager.scrollToPositionWithOffset(nomer-4, 0);
                    recyclerView.setLayoutManager(manager);
                    options.clearCheck();
                    adapter.notifyDataSetChanged();
                    displayCacheAfter();
                }
            });
        }

        btnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nomer = nomer - 1;
                manager.scrollToPositionWithOffset(nomer-4, 0);
                recyclerView.setLayoutManager(manager);
                options.clearCheck();
                adapter.notifyDataSetChanged();
                displayCacheAfter();
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

    private void displayCache(){
        for (int i = 0; i < items.size() ; i++) {
            Log.d("display before : ke-" + items.get(i).getNumber(), "Opt 1 : " + items.get(i).getOptions().get(0).getStatus() + " | Opt 2 : " + items.get(i).getOptions().get(1).getStatus() + " | Opt 3 : " + items.get(i).getOptions().get(2).getStatus());
        }
    }

    private void displayCacheAfter(){
        for (int i = 0; i < items.size() ; i++) {
            Log.d("display after : ke-" + items.get(i).getNumber(), "Opt 1 : " + items.get(i).getOptions().get(0).getStatus() + " | Opt 2 : " + items.get(i).getOptions().get(1).getStatus() + " | Opt 3 : " + items.get(i).getOptions().get(2).getStatus());
        }
    }

    private void displayResult() {
        int count = 0;
        for (int i = 0; i < items.size() ; i++) {
            if (items.get(i).getStatus() == 2){
                count++;
            }
        }

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_quis_result);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Button btnBack = dialog.findViewById(R.id.btnBack);
        Button btnRetry = dialog.findViewById(R.id.btnNext);
        TextView result = dialog.findViewById(R.id.result);
        TextView total = dialog.findViewById(R.id.total);
        TextView status = dialog.findViewById(R.id.status);

        result.setText(count + "");
        total.setText(items.size() + "");

        int percent = (count * 100) / items.size();
        if (percent == 100){
            status.setText("WELL DONE");
        }else  if (percent < 100 && 79 < percent ){
            status.setText("GOOD JOB");
        }else if (percent < 80 && 59 < percent){
            status.setText("NOT BAD");
        }else {
            status.setText("TRY AGAIN");
        }

        dialog.setCancelable(false);
        dialog.show();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (percent < 60){
            btnRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                    Intent intent = new Intent(getApplicationContext(), LearnTaskActivity.class);
                    intent.putExtra("id", getIntent().getExtras().getString("id"));
                    intent.putExtra("nama", getIntent().getExtras().getString("nama"));
                    startActivity(intent);
                }
            });
        }else {
            btnRetry.setText("Finish");
            btnRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }


    }
}
