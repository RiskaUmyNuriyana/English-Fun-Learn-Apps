package com.synergics.ishom.englishapps;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.synergics.ishom.englishapps.Controller.RestFullConfig.ApiClient;
import com.synergics.ishom.englishapps.Controller.RestFullConfig.ApiInterface;
import com.synergics.ishom.englishapps.Model.Learn;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LearnActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SlimAdapter adapter;
    private GridLayoutManager manager;
    private ArrayList<Object> items = new ArrayList<>();

    private Bundle bundle;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        bundle = getIntent().getExtras();
        id = bundle.getString("id");

        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        manager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        adapter = SlimAdapter.create()
                .register(R.layout.item_learn_box, new SlimInjector<Learn>() {
                    @Override
                    public void onInject(final Learn data, IViewInjector injector) {
                        injector.text(R.id.text, data.getEnglish())
                                .with(R.id.item, new IViewInjector.Action() {
                                    @Override
                                    public void action(View view) {

                                        ImageView image = view.findViewById(R.id.image);

                                        Picasso.with(view.getContext())
                                                .load(data.getImage())
                                                .into(image);

                                        view.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                Intent intent = new Intent(getApplicationContext(), LearnSwipeActivity.class);
                                                intent.putExtra("position", data.getPostiion());
                                                intent.putExtra("id", id);
                                                startActivity(intent);

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
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiInterface apiInterface = ApiClient.EnglishApi().create(ApiInterface.class);
        Call call = apiInterface.getLearn(id);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                if (response.isSuccessful()){
                    com.synergics.ishom.englishapps.Model.RestFullObject.Learn object = (com.synergics.ishom.englishapps.Model.RestFullObject.Learn) response.body();

                    if (object.status){
                        int i = 0;
                        for (com.synergics.ishom.englishapps.Model.RestFullObject.Learn.Data data : object.data){
                            items.add(new Learn(i, data.id, data.name_english, data.pronounce, data.name_indo, data.image, data.audio));
                            i++;
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), object.message, Toast.LENGTH_SHORT).show();
                    }

                    adapter.updateData(items);
                    adapter.notifyDataSetChanged();
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
