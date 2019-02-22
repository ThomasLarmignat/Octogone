package com.example.octogone.Survi;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.octogone.MainActivity;
import com.example.octogone.R;

import org.w3c.dom.Text;


public class GOActivity extends AppCompatActivity {
    private Button buttonMenu;
    private Button buttonRestart;
    private TextView scoretext;
    private TextView timetext;
    private Background bc = null;
    public static Activity a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over_acticity);
        //Recuperation des infos pour la map et le personnage
        a = this;

        Intent intent = getIntent();
        int map = intent.getIntExtra("map",0);
        int score = intent.getIntExtra("score",0);
        String time = intent.getStringExtra("time");

        final TextView scoretext = (TextView) findViewById(R.id.score);
        final TextView timetext = (TextView) findViewById(R.id.timer);

        scoretext.setText(score+"");
        timetext.setText(time);









        //traitement map
        final ImageView backgroundOne = (ImageView) findViewById(R.id.background_one);
        final ImageView backgroundTwo = (ImageView) findViewById(R.id.background_two);
        final ImageView background3 = (ImageView) findViewById(R.id.background_3);
        final ImageView background4 = (ImageView) findViewById(R.id.background_4);

        if(map == 0){
            background3.setVisibility(View.INVISIBLE);
            background4.setVisibility(View.INVISIBLE);
            bc = new Background(backgroundOne,backgroundTwo);
        }


        if(map == 1){
            backgroundOne.setVisibility(View.INVISIBLE);
            backgroundTwo.setVisibility(View.INVISIBLE);
            bc = new Background(background3,background4);
        }
        bc.run();

        buttonMenu = (Button)findViewById(R.id.menu);

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
        Intent intent = new Intent(getApplicationContext(), SurviActivity.class);
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