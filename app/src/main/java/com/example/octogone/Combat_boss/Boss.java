package com.example.octogone.Combat_boss;
import android.os.Handler;
import android.view.View;
import android.view.animation.TranslateAnimation;

import com.example.octogone.R;

import pl.droidsonroids.gif.GifImageView;


public class Boss {
    private GifImageView e;
    private GifImageView p;
    private boolean estEnVie = false;
    static private boolean combat = false;
    private Handler handler;
    private boolean frappable = false;
    private int pv;
    private boolean stunt = false;
    private int boss[];
    private BossActivity s;


    public Boss(GifImageView e, GifImageView p, BossActivity s, int boss[] ){
        this.e = e;
        this.p = p;
        this.boss = boss;
        this.s = s;
        this.handler = new Handler();

    }


    public void apparaitre(){
        stunt = false;
        estEnVie = true;
        setVie(300);
        e.setImageResource(boss[0]);
        avancer();
        handler.postDelayed(new Runnable() {
            public void run() {
                combat = true;
                frappable = true;

            }
        }, 3000);
    }


    public void meurt(){
        s.win();
        estEnVie = false;
        e.setImageResource(R.drawable.perso_vide);
        combat = false;
    }



    public void stunt(){
        this.stunt = true;
        e.setImageResource(boss[1]);
    }


    public void avancer(){
        moveAtoB(e,p);
        this.combat = true;

    }

    public void frapper(){
        e.setImageResource(boss[2]);
    }


    public void normal(){
        e.setImageResource(boss[0]);
    }

    public void degats() {
        frappable = false;
        this.pv = this.pv-30;
        e.setImageResource(boss[3]);
    }



    public void moveAtoB(View viewOne, View viewTwo){
        TranslateAnimation animation = new TranslateAnimation(0, viewTwo.getX()-viewOne.getX()+(viewOne.getX()/2),0 , viewTwo.getY()-viewOne.getY());
        animation.setRepeatMode(0);
        animation.setDuration(3000);
        animation.setFillAfter(true);
        viewOne.startAnimation(animation);
    }

    public void setVie(int vie){
        this.pv = vie;
    }

    public int getVie(){
        return(this.pv);
    }

    public void setFrappable(boolean f){
        this.frappable = f;
    }

    public boolean estFrappable(){
        return(this.frappable);
    }

    public boolean estEnCombat(){
        return(this.combat);
    }

    public void setStunt(boolean f){
        this.stunt = f;
    }

    public boolean estStunt(){
        return(this.stunt);
    }
}
