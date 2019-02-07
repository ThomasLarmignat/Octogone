package com.example.myapplication;
import android.animation.ObjectAnimator;
import android.media.Image;
import android.os.Handler;
import android.widget.ImageView;

import pl.droidsonroids.gif.GifImageView;

public class Joueur {
    private int vie = 5;
    private GifImageView joueur;
    private Ennemi ennemi;
    private Handler handler;
    private int posSol;
    private boolean peutAttack = true;
    private boolean estBloquer = false;
    private ImageView barreVie;

    public Joueur(GifImageView j, Ennemi e, ImageView barreVie){
        joueur = j;
        handler = new Handler();
        ennemi = e;
        posSol = joueur.getTop();
        this.barreVie = barreVie;
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
                estBloquer =false;
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
        switch(vie){
            case 0:barreVie.setImageResource(R.drawable.life);break;
            case 1:barreVie.setImageResource(R.drawable.life1);break;
            case 2:barreVie.setImageResource(R.drawable.life2);break;
            case 3:barreVie.setImageResource(R.drawable.life3);break;
            case 4:barreVie.setImageResource(R.drawable.life4);break;
            case 5:barreVie.setImageResource(R.drawable.life5);break;
        }
    }

    public int getVie(){
        return this.vie;
    }
}
