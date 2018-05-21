package com.synergics.ishom.englishapps;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.synergics.ishom.englishapps.Controller.RestFullConfig.ApiClient;
import com.synergics.ishom.englishapps.Controller.RestFullConfig.ApiInterface;
import com.synergics.ishom.englishapps.Model.RestFullObject.ResponseVideo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoActivity extends YouTubeBaseActivity{

//    private VideoView videoView;
//    private MediaController mediaController;

    public static final String API_KEY = "AIzaSyBoFfYEuwzXaVwF07dt30KvYZM9vJXUGb0";
    private String VIDEO_ID = "";

    private YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


//        mediaController = new MediaController(this);

        youTubePlayerView = findViewById(R.id.youtubeVideo);


        getVideo();
    }

    private void getVideo() {

        String id = getIntent().getExtras().getString("id");

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiInterface apiInterface = ApiClient.EnglishApi().create(ApiInterface.class);
        Call call = apiInterface.getVideo(id);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()){
                    List<ResponseVideo> videos = (List<ResponseVideo>) response.body();

                    if (videos.size() != 0){

                        String videoUrl = videos.get(0).assets;
                        String videoURl[] = videoUrl.split("=");
                        String videoId = videoURl[1];
                        VIDEO_ID = videoId;

                        youTubePlayerView.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {
                            @Override
                            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                                youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
                                youTubePlayer.setPlaybackEventListener(playbackEventListener);

                                if (!b){
                                    youTubePlayer.cueVideo(VIDEO_ID);
                                }
                            }

                            @Override
                            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                            }
                        });


                    }else {
                        Toast.makeText(VideoActivity.this, "Video not found !", Toast.LENGTH_SHORT).show();
                    }

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

    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {

        }

        @Override
        public void onPaused() {

        }

        @Override
        public void onStopped() {

        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    };

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {

        }

        @Override
        public void onVideoEnded() {

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };
}
