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

import com.example.octogone.MainActivity;
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
    private Button buttonMENU = null;
    private Button buttonHit = null;
    private Button buttonJump = null;
    private Button buttonBlock = null;
    private Button buttonDebug = null;
    SharedPreferences sharedPreferences;
    private int kaaris[] = {R.drawable.stand_kaaris, R.drawable.block_kaaris,R.drawable.hit_kaaris};
    private int lorenzo[] = {R.drawable.stand_lorenzo, R.drawable.block_lorenzo,R.drawable.hit_lorenzo};
    private int jul[] = {R.drawable.stand_jul, R.drawable.block_jul,R.drawable.hit_jul};
    private int booba[] = {R.drawable.stand_booba, R.drawable.block_booba,R.drawable.hit_booba};
    public static Activity a;
    private boolean pause = false;
    private int timeSaveS;
    private int timeSaveM;
    private GifImageView pause_logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survi_activity);
        a =this;
        //récuperation des boutons
        buttonMENU = (Button) findViewById(R.id.menu);
        buttonHit = (Button) findViewById(R.id.button);
        buttonJump = (Button) findViewById(R.id.buttonJump);
        buttonBlock = (Button) findViewById(R.id.block);
        buttonDebug = (Button) findViewById(R.id.debug);


        //Recuperation des infos pour la map et le personnage
        Intent intent = getIntent();
        int map = intent.getIntExtra("map",0);
        int joueur = intent.getIntExtra("joueur",0);
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

        pause_logo = (GifImageView) findViewById(R.id.pause_logo);
        final GifImageView persoPos = (GifImageView) findViewById(R.id.kaaris);
        final ImageView barreVie = (ImageView) findViewById(R.id.imageVie);
        score = (TextView) findViewById(R.id.score);


        //creation du personnage (avec le choix)
        switch(joueur){
            case 0: perso = new Joueur(persoPos,barreVie,score, this,kaaris);break;
            case 1: perso = new Joueur(persoPos,barreVie,score, this,lorenzo);break;
            case 2: perso = new Joueur(persoPos,barreVie,score, this,jul);break;
            case 3: perso = new Joueur(persoPos,barreVie,score, this,booba);break;
        }








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
                JeuPause();
            }
        });

        buttonMENU.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                quitter();
            }
        });
    }

    @Override
    public void onPause() {
        pause = false ;
        super.onPause();
        chrono.finish();
        cb.endCombat();
        cb.ennemiMeurt();
        JeuPause();
    }

    public void quitter(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        this.finish();
    }
    public void JeuPause(){
        if(pause){//si en pause
            chrono = new Chrono(textChrono,collectables,cb);
            chrono.setMinutes(timeSaveM);
            chrono.setSecondes(timeSaveS);
            chrono.startChrono();
            buttonMENU.setVisibility(View.INVISIBLE);
            pause_logo.setVisibility(View.INVISIBLE);
            pause = false;
        }else{//si pas en pause
            timeSaveM = chrono.getMinutes();
            timeSaveS = chrono.getSecondes();
            chrono.annuleChrono();
            buttonMENU.setVisibility(View.VISIBLE);
            pause_logo.setVisibility(View.VISIBLE);
            cb.ennemiMeurt();
            pause = true;
        }
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
        cb.ennemiMeurt();
        this.finish();
    }

    @Override
    public void onBackPressed() {
        return;
    }
}

