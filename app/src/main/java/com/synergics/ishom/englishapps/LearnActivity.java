package com.synergics.ishom.englishapps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.synergics.ishom.englishapps.Model.Learn;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.util.ArrayList;

public class LearnActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SlimAdapter adapter;
    private GridLayoutManager manager;
    private ArrayList<Object> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

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

                                            }
                                        });
                                    }
                                });
                    }
                })
                .attachTo(recyclerView);

        setData();
    }

    private void setData() {

        items.add(new Learn("1", "English", "/english/", "Inggris", "https://yt3.ggpht.com/a-/AJLlDp1UE5tJ0OoMR6zrd439x4og5YhOYiQRS8Gz-w=s900-mo-c-c0xffffffff-rj-k-no", ""));
        items.add(new Learn("2", "English", "/english/", "Inggris", "https://yt3.ggpht.com/a-/AJLlDp1UE5tJ0OoMR6zrd439x4og5YhOYiQRS8Gz-w=s900-mo-c-c0xffffffff-rj-k-no", ""));
        items.add(new Learn("3", "English", "/english/", "Inggris", "https://yt3.ggpht.com/a-/AJLlDp1UE5tJ0OoMR6zrd439x4og5YhOYiQRS8Gz-w=s900-mo-c-c0xffffffff-rj-k-no", ""));
        items.add(new Learn("4", "English", "/english/", "Inggris", "https://yt3.ggpht.com/a-/AJLlDp1UE5tJ0OoMR6zrd439x4og5YhOYiQRS8Gz-w=s900-mo-c-c0xffffffff-rj-k-no", ""));
        items.add(new Learn("5", "English", "/english/", "Inggris", "https://yt3.ggpht.com/a-/AJLlDp1UE5tJ0OoMR6zrd439x4og5YhOYiQRS8Gz-w=s900-mo-c-c0xffffffff-rj-k-no", ""));
        items.add(new Learn("6", "English", "/english/", "Inggris", "https://yt3.ggpht.com/a-/AJLlDp1UE5tJ0OoMR6zrd439x4og5YhOYiQRS8Gz-w=s900-mo-c-c0xffffffff-rj-k-no", ""));
        items.add(new Learn("7", "English", "/english/", "Inggris", "https://yt3.ggpht.com/a-/AJLlDp1UE5tJ0OoMR6zrd439x4og5YhOYiQRS8Gz-w=s900-mo-c-c0xffffffff-rj-k-no", ""));
        items.add(new Learn("8", "English", "/english/", "Inggris", "https://yt3.ggpht.com/a-/AJLlDp1UE5tJ0OoMR6zrd439x4og5YhOYiQRS8Gz-w=s900-mo-c-c0xffffffff-rj-k-no", ""));
        items.add(new Learn("9", "English", "/english/", "Inggris", "https://yt3.ggpht.com/a-/AJLlDp1UE5tJ0OoMR6zrd439x4og5YhOYiQRS8Gz-w=s900-mo-c-c0xffffffff-rj-k-no", ""));

        adapter.updateData(items);
        adapter.notifyDataSetChanged();

    }
}
