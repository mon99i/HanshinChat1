package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class ListActivity extends MainActivity {

    private ListView listView;
    private ListViewAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_list);

        checkCurrentUser();
        checkProfileExist();


        clickChat();
        clickHome();
        clickProfile();
        clickRoom();
        adapter = new ListViewAdapter();

        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);


    }
}
