package com.example.octogone.Combat_boss;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
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
import com.example.octogone.Survi.GOActivity;


import pl.droidsonroids.gif.GifImageView;


public class BossActivity extends AppCompatActivity {
    //Elements du jeu
    private Joueur perso = null;
    private Boss ennemi;
    private Combat cb;
    private TextView score;
    //boutons
    private Button buttonHit = null;
    private Button buttonJump = null;
    private Button buttonBlock = null;
    private int kaaris[] = {R.drawable.stand_kaaris, R.drawable.block_kaaris,R.drawable.hit_kaaris};
    private int lorenzo[] = {R.drawable.stand_e_lorenzo, R.drawable.stunt_e_lorenzo,R.drawable.hit_e_lorenzo,R.drawable.degat_e_lorenzo};
    private int jul[] = {R.drawable.stand_e_jul, R.drawable.stunt_e_jul,R.drawable.hit_e_jul,R.drawable.degat_e_jul};
    private int booba[] = {R.drawable.stand_e_booba, R.drawable.stunt_e_booba,R.drawable.hit_e_booba,R.drawable.degat_e_booba};
    private int resPerso[] = {R.drawable.lorenzo_logo, R.drawable.jul_logo,R.drawable.booba_logo};
    private int bossIndex;
    private GifImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boss_activity);

        //récuperation des boutons
        buttonHit = (Button) findViewById(R.id.button);
        buttonJump = (Button) findViewById(R.id.buttonJump);
        buttonBlock = (Button) findViewById(R.id.block);
        logo = (GifImageView) findViewById(R.id.logo);



        Intent intent = getIntent();
        bossIndex = intent.getIntExtra("BossIndex",0);



        //creation du personnage (avec le choix)
        final GifImageView persoPos = (GifImageView) findViewById(R.id.kaaris);
        final ImageView barreVie = (ImageView) findViewById(R.id.imageVie);
        final ImageView barreVie2 = (ImageView) findViewById(R.id.imageVie2);

        score = (TextView) findViewById(R.id.score);
        perso = new Joueur(persoPos,barreVie,score, this,kaaris);




        //creation des ennemis
        final GifImageView ennemiPos = (GifImageView) findViewById(R.id.ennemi);
        logo.setImageResource(resPerso[bossIndex]);


        switch(bossIndex){
            case 0 : ennemi = new Boss(ennemiPos, persoPos, this,lorenzo,barreVie2);break;
            case 1 : ennemi = new Boss(ennemiPos, persoPos,this, jul,barreVie2);break;
            case 2 : ennemi = new Boss(ennemiPos, persoPos, this,booba,barreVie2);break;

        }

        //creation du combat
        cb = new Combat(ennemi,perso);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cb.startCombat();
            }
        },1000);// temps ou le bt est bloqué (1000 = 1s).

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





    }
    public void win(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        this.finish();
    }
    public void gameOver(){
        Intent intent = new Intent(getApplicationContext(), GOBossActivity.class);
        intent.putExtra("BossIndex", bossIndex);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        return;
    }
}

