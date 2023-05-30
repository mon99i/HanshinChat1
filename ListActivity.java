package com.example.hanshinchat1;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ListActivity extends AppCompatActivity {

    private ListView listView;
    private ListViewAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_list);

        adapter = new ListViewAdapter();

        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);


    }
}
