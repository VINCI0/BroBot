package com.example.brobot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class chatActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView message;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private FloatingActionButton fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = findViewById(R.id.chat_Toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar
                , R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ChatFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_Chat);
        }

    }

    //        FloatingActionButton fab = findViewById(R.id.fbMessageSend);
//        fab.setImageResource(R.drawable.audio);
//        message=findViewById(R.id.etMessage);
//        message.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (message.getText().length()>0){
//                    FloatingActionButton fb=findViewById(R.id.fbMessageSend);
//                    fb.setImageResource(R.drawable.message_send);
//                }
//                else{
//                    fb.setImageResource(R.drawable.audio);
//                }
//            }
//        });
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Snackbar.make(view, "Record Audio", Snackbar.LENGTH_LONG)
//                        .setAction("No Action", null).show();
//            }
//        });

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_Chat:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ChatFragment()).commit();
                break;
            case R.id.nav_MoodTracker:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MoodFragment()).commit();
                break;
            case R.id.nav_DailyJournal:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new DailyJournalFragment()).commit();
                break;
            case R.id.nav_Interest:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new InterestsFragment()).commit();
                break;
            case R.id.nav_Settings:
                Intent intent=new Intent(this,SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.themeChanger:
                if (AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_NO){
//                       editor.putBoolean("DarkMode",true);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    getDelegate().applyDayNight();
                }
                else{
//                    editor.putBoolean("DarkMode",false);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    getDelegate().applyDayNight();

                }
                break;
        }

        if (drawer!=null)
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




}









    //        NavigationView navigationView = findViewById(R.id.nav_view);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_Chat, R.id.nav_MoodTracker, R.id.nav_DailyJournal)
//                .setDrawerLayout(drawer)
//                .build();
//
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.logout_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }

