package com.example.octogone.Survi;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.octogone.R;

import pl.droidsonroids.gif.GifImageView;

public class Joueur {
    private int vie = 5;
    private GifImageView joueur;
    private Handler handler;
    private int posSol;
    private boolean peutAttack = true;
    private boolean estBloquer = false;
    private ImageView barreVie;
    private int score = 0;
    private TextView texteScore;
    private SurviActivity s;

    public Joueur(GifImageView j, ImageView barreVie, TextView score,SurviActivity s){
        joueur = j;
        handler = new Handler();
        posSol = joueur.getTop();
        this.barreVie = barreVie;
        texteScore = score;
        this.s = s;
    }


    public GifImageView get_position(){
        return joueur;
    }


    public void frapper(){
        joueur.setImageResource(R.drawable.hit_kaaris);
        handler.postDelayed(new Runnable() {
            public void run() {
                normal();
            }
        }, 200);
    }


    public void sauter(){
        peutAttack =false;
        ObjectAnimator up = ObjectAnimator.ofFloat(joueur, "translationY", -1300);
        up.setDuration(500); // duration 5 seconds
        up.start();
        handler.postDelayed(new Runnable() {
            public void run() {
                ObjectAnimator up = ObjectAnimator.ofFloat(joueur, "translationY", posSol);
                up.setDuration(500); // duration 5 seconds
                up.start();
                peutAttack = true;
            }
        }, 500);
    }


    public void bloquer(){
        estBloquer = true;
        joueur.setImageResource(R.drawable.block_kaaris);
        handler.postDelayed(new Runnable() {
            public void run() {
                normal();
                estBloquer = false;
            }
        }, 300);
    }

    public void normal(){
        joueur.setImageResource(R.drawable.stand_kaaris);
    }

    public boolean peutAttack(){return(peutAttack);}

    public boolean estBloquer(){return(estBloquer);}

    public void degat(){
        vie = vie-1;
        actu_vie(vie);
    }

    public void plus_vie(){
        if(vie<5){
            vie = vie+1;
            actu_vie(vie);
        }
    }

    public int getVie(){
        return this.vie;
    }

    public void actu_vie(int v){
        switch(v){
            case 0:barreVie.setImageResource(R.drawable.life);s.gameOver();break;
            case 1:barreVie.setImageResource(R.drawable.life1);break;
            case 2:barreVie.setImageResource(R.drawable.life2);break;
            case 3:barreVie.setImageResource(R.drawable.life3);break;
            case 4:barreVie.setImageResource(R.drawable.life4);break;
            case 5:barreVie.setImageResource(R.drawable.life5);break;
        }
    }

    public void update_score(int s){
        if(s == 1){
            score += 10;
            texteScore.setText(score+"");
        }else{
            score += 1000;
            texteScore.setText(score+"");
        }
    }

    public int getScore() {
        return score;
    }
}
