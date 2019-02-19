package com.example.octogone.Survi;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.octogone.R;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;


public class SurviActivity extends AppCompatActivity {
    //Elements du jeu
    private Background bc = null;
    private Joueur perso = null;
    private Policier ennemi;
    private Combat cb;
    private Chrono chrono;
    private TextView textChrono;
    private ImageView posDollar1;
    private ImageView posDollar2;
    private ImageView posDollar3;
    private GifImageView posH1;
    private GifImageView posH2;
    private Collectible collectables;
    private TextView score;
    //boutons
    private Button buttonHit = null;
    private Button buttonJump = null;
    private Button buttonBlock = null;
    private Button buttonDebug = null;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survi_activity);

        //récuperation des boutons
        buttonHit = (Button) findViewById(R.id.button);
        buttonJump = (Button) findViewById(R.id.buttonJump);
        buttonBlock = (Button) findViewById(R.id.block);
        buttonDebug = (Button) findViewById(R.id.debug);


        //Recuperation des infos pour la map et le personnage
        Intent intent = getIntent();
        int map = intent.getIntExtra("map",0);
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





        //creation du personnage (avec le choix)
        final GifImageView persoPos = (GifImageView) findViewById(R.id.kaaris);
        final ImageView barreVie = (ImageView) findViewById(R.id.imageVie);
        score = (TextView) findViewById(R.id.score);
        perso = new Joueur(persoPos,barreVie,score, this);




        //creation des ennemis
        final GifImageView ennemiPos = (GifImageView) findViewById(R.id.ennemi);
        ennemi = new Policier(ennemiPos, persoPos);



        //Gestion billets
        posDollar1 = (ImageView) findViewById(R.id.dollar1);//Position billet 1
        posDollar2 = (ImageView) findViewById(R.id.dollar2);//Position billet 2
        posDollar3 = (ImageView) findViewById(R.id.dollar3);//Position billet 3
        posH1 = (GifImageView) findViewById(R.id.pos1);//Postion couteau ou coeur 1
        posH2 = (GifImageView) findViewById(R.id.pos2);//Postion couteau ou coeur 2
        Display display = getWindowManager().getDefaultDisplay();
        Point point=new Point();
        display.getSize(point);

        collectables = new Collectible(posDollar1,posDollar2,posDollar3,posH1,posH2,point.x,perso);





        //InitChrono
        textChrono =(TextView) findViewById(R.id.timer);
        //creation et lancement du background
        bc.run();

        //creation du combat
        cb = new Combat(ennemi,perso,bc);
        chrono = new Chrono(textChrono,collectables,cb);


        chrono.startChrono();

        buttonHit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cb.playerAttack();
            }
        });


        buttonJump.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                perso.sauter();
                buttonJump.setEnabled(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        buttonJump.setEnabled(true);
                    }
                },1000);// temps ou le bt est bloqué (1000 = 1s).
            }
        });


        buttonBlock.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                perso.bloquer();

            }
        });


        buttonDebug.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cb.startCombat();
            }
        });



    }

    public void gameOver(){
        Intent intent = new Intent(getApplicationContext(), GOActivity.class);
        intent.putExtra("score", perso.getScore());
        intent.putExtra("time", chrono.get_string());

        sharedPreferences = getBaseContext().getSharedPreferences("PREFS", MODE_PRIVATE);
        sharedPreferences.edit().putInt("Score",sharedPreferences.getInt("Score", 0)+ perso.getScore()).apply();
        if(sharedPreferences.getInt("minutes", 0) < chrono.getMinutes()){
            sharedPreferences.edit().putInt("minutes",chrono.getMinutes()).apply();
            sharedPreferences.edit().putInt("secondes",chrono.getSecondes()).apply();
            sharedPreferences.edit().putString("strTime",chrono.get_string()).apply();
        }else if(sharedPreferences.getInt("minutes", 0) == chrono.getMinutes()){
            if(sharedPreferences.getInt("secondes", 0) < chrono.getSecondes()){
                sharedPreferences.edit().putInt("minutes",chrono.getMinutes()).apply();
                sharedPreferences.edit().putInt("secondes",chrono.getSecondes()).apply();
                sharedPreferences.edit().putString("strTime",chrono.get_string()).apply();
            }
        }
        startActivity(intent);
        chrono.finish();
        this.finish();
    }
}

