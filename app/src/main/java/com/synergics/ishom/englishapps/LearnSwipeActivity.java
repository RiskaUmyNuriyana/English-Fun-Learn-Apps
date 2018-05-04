package com.synergics.ishom.englishapps;

import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.synergics.ishom.englishapps.Model.Learn;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.Orientation;
import com.yarolegovich.discretescrollview.transform.Pivot;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.util.ArrayList;

public class LearnSwipeActivity extends AppCompatActivity {

    private SlimAdapter adapter;
    private DiscreteScrollView slideView;
    private LinearLayoutManager manager;
    private ArrayList<Object> items= new ArrayList<>();

    private Bundle bundle;
    private int position;

    private boolean playPause;
    private MediaPlayer mediaPlayer;

    private boolean initialStage = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_swipe);

        bundle = getIntent().getExtras();
        position = bundle.getInt("id");

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        slideView = findViewById(R.id.picker);
        slideView.setOrientation(Orientation.HORIZONTAL);
        slideView.addOnItemChangedListener(new DiscreteScrollView.OnItemChangedListener<RecyclerView.ViewHolder>() {
            @Override
            public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {

            }
        });
        slideView.setAdapter(adapter);
        slideView.setItemTransitionTimeMillis(300);
        slideView.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.6f)
                .setPivotX(Pivot.X.CENTER)
                .setPivotX(Pivot.X.CENTER)
                .build());

        adapter = SlimAdapter.create()
                .register(R.layout.item_learn_swipe, new SlimInjector<Learn>() {

                    @Override
                    public void onInject(final Learn data, IViewInjector injector) {
                        injector.with(R.id.item, new IViewInjector.Action() {
                            @Override
                            public void action(View view) {

                                ImageView image = view.findViewById(R.id.image);

                                TextView english = view.findViewById(R.id.englishName);
                                TextView pronounce = view.findViewById(R.id.pronounce);
                                TextView indo = view.findViewById(R.id.indoName);

                                Button button = view.findViewById(R.id.btnPlay);

                                english.setText(data.getEnglish());
                                pronounce.setText(data.getPronounce());
                                indo.setText(data.getIndo());

                                Picasso.with(view.getContext())
                                        .load(data.getImage())
                                        .into(image);

                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (!playPause) {

                                            if (initialStage) {
                                                new Player().execute(data.getAudio());
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

                            }
                        });
                    }
                })
                .attachTo(slideView);

        getData();

        setToolbar();

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

    private void getData() {
        items.add(new Learn(0,"1", "English", "/english/", "Inggris", "https://yt3.ggpht.com/a-/AJLlDp1UE5tJ0OoMR6zrd439x4og5YhOYiQRS8Gz-w=s900-mo-c-c0xffffffff-rj-k-no", "http://pens.ardihikaru.com/2017/wsim/grup4/audio/puppy.mp3"));
        items.add(new Learn(1,"2", "English", "/english/", "Inggris", "https://yt3.ggpht.com/a-/AJLlDp1UE5tJ0OoMR6zrd439x4og5YhOYiQRS8Gz-w=s900-mo-c-c0xffffffff-rj-k-no", "http://pens.ardihikaru.com/2017/wsim/grup4/audio/puppy.mp3"));
        items.add(new Learn(2,"3", "English", "/english/", "Inggris", "https://yt3.ggpht.com/a-/AJLlDp1UE5tJ0OoMR6zrd439x4og5YhOYiQRS8Gz-w=s900-mo-c-c0xffffffff-rj-k-no", "http://pens.ardihikaru.com/2017/wsim/grup4/audio/puppy.mp3"));
        items.add(new Learn(3,"4", "English", "/english/", "Inggris", "https://yt3.ggpht.com/a-/AJLlDp1UE5tJ0OoMR6zrd439x4og5YhOYiQRS8Gz-w=s900-mo-c-c0xffffffff-rj-k-no", "http://pens.ardihikaru.com/2017/wsim/grup4/audio/puppy.mp3"));
        items.add(new Learn(4,"5", "English", "/english/", "Inggris", "https://yt3.ggpht.com/a-/AJLlDp1UE5tJ0OoMR6zrd439x4og5YhOYiQRS8Gz-w=s900-mo-c-c0xffffffff-rj-k-no", "http://pens.ardihikaru.com/2017/wsim/grup4/audio/puppy.mp3"));
        items.add(new Learn(5,"6", "English", "/english/", "Inggris", "https://yt3.ggpht.com/a-/AJLlDp1UE5tJ0OoMR6zrd439x4og5YhOYiQRS8Gz-w=s900-mo-c-c0xffffffff-rj-k-no", "http://pens.ardihikaru.com/2017/wsim/grup4/audio/puppy.mp3"));
        items.add(new Learn(6,"7", "English", "/english/", "Inggris", "https://yt3.ggpht.com/a-/AJLlDp1UE5tJ0OoMR6zrd439x4og5YhOYiQRS8Gz-w=s900-mo-c-c0xffffffff-rj-k-no", "http://pens.ardihikaru.com/2017/wsim/grup4/audio/puppy.mp3"));
        items.add(new Learn(7,"8", "English", "/english/", "Inggris", "https://yt3.ggpht.com/a-/AJLlDp1UE5tJ0OoMR6zrd439x4og5YhOYiQRS8Gz-w=s900-mo-c-c0xffffffff-rj-k-no", "http://pens.ardihikaru.com/2017/wsim/grup4/audio/puppy.mp3"));
        items.add(new Learn(8,"9", "English", "/english/", "Inggris", "https://yt3.ggpht.com/a-/AJLlDp1UE5tJ0OoMR6zrd439x4og5YhOYiQRS8Gz-w=s900-mo-c-c0xffffffff-rj-k-no", "http://pens.ardihikaru.com/2017/wsim/grup4/audio/puppy.mp3"));

        adapter.updateData(items);
        adapter.notifyDataSetChanged();

        slideView.scrollToPosition(position);
    }

    private void setToolbar() {
        Toolbar toolbar = this.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));

        TextView title = findViewById(R.id.title);
        title.setText("Learn");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
