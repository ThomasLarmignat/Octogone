package com.example.octogone.Combat_boss;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.octogone.MainActivity;
import com.example.octogone.R;


public class GOBossActivity extends AppCompatActivity {
    private Button buttonMenu;
    private Button buttonRestart;
    public int bossIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boss_game_over);
        //Recuperation des infos pour la map et le personnage
        buttonMenu = (Button)findViewById(R.id.menu);


        Intent intent = getIntent();
        bossIndex = intent.getIntExtra("BossIndex",0);


        buttonMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                menu();
            }
        });

        buttonRestart = (Button)findViewById(R.id.restart);
        buttonRestart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                restart();
            }
        });

    }

    public void restart(){
        Intent intent = new Intent(getApplicationContext(), BossActivity.class);
        intent.putExtra("BossIndex", bossIndex);
        startActivity(intent);
        this.finish();
    }

    public void menu(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        return;
    }
}