package com.example.hanshinchat1;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.hanshinchat1.MainMenu.MainBoardFragment;
import com.example.hanshinchat1.MainMenu.MainChatFragment;
import com.example.hanshinchat1.MainMenu.MainHomeFragment;
import com.example.hanshinchat1.MainMenu.MainProfileFragment;
import com.example.hanshinchat1.MainMenu.MainRoomFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainMenuActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private MainHomeFragment fragmentHome = new MainHomeFragment();
    private MainRoomFragment fragmentRoom = new MainRoomFragment();
    private MainChatFragment fragmentChat = new MainChatFragment();
    private MainBoardFragment fragmentBoard = new MainBoardFragment();
    private MainProfileFragment fragmentProfile = new MainProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        BottomNavigationView bottomNavigationView = findViewById(R.id.menu_bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());

        int show_fragment = getIntent().getIntExtra("show_fragment", 1);

        if (show_fragment != -1) {
            switch (show_fragment) {
                case 1:
                    bottomNavigationView.setSelectedItemId(R.id.menu_home);
                    break;
                case 2:
                    bottomNavigationView.setSelectedItemId(R.id.menu_room);
                    break;
                case 3:
                    bottomNavigationView.setSelectedItemId(R.id.menu_chat);
                    break;
                case 4:
                    bottomNavigationView.setSelectedItemId(R.id.menu_board);
                    break;
                case 5:
                    bottomNavigationView.setSelectedItemId(R.id.menu_profile);
                    break;
            }
        }


    }

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (menuItem.getItemId()) {
                case R.id.menu_home:
                    transaction.replace(R.id.menu_frame_layout, fragmentHome).commitAllowingStateLoss();
                    break;
                case R.id.menu_room:
                    transaction.replace(R.id.menu_frame_layout, fragmentRoom).commitAllowingStateLoss();
                    //transaction.commit();
                    break;
                case R.id.menu_chat:
                    transaction.replace(R.id.menu_frame_layout, fragmentChat).commitAllowingStateLoss();
                    break;
                case R.id.menu_board:
                    transaction.replace(R.id.menu_frame_layout, fragmentBoard).commitAllowingStateLoss();
                    break;
                case R.id.menu_profile:
                    transaction.replace(R.id.menu_frame_layout, fragmentProfile).commitAllowingStateLoss();
                    break;

            }
            return true;
        }
    }
}
