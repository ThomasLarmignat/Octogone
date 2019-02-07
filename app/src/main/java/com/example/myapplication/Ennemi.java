package com.example.myapplication;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.os.Handler;

import pl.droidsonroids.gif.GifImageView;


public abstract class Ennemi {
    protected GifImageView e;
    protected GifImageView p;
    protected boolean estEnVie = false;
    protected boolean combat = false;
    protected Handler handler;
    protected boolean estStunt = false;
    protected int pv;
    protected int coupsStunt = 0;
    //protected ObjectAnimator up;

    public Ennemi(GifImageView e, GifImageView p){
        this.e = e;
        this.p = p;
    }

    public void moveAtoB(View viewOne, View viewTwo){
        TranslateAnimation animation = new TranslateAnimation(0, viewTwo.getX()-viewOne.getX()+(viewOne.getX()/5),0 , viewTwo.getY()-viewOne.getY());
        animation.setRepeatMode(0);
        animation.setDuration(3000);
        animation.setFillAfter(true);
        viewOne.startAnimation(animation);
    }
    public abstract void apparaitre();

    public abstract  void avancer();

    public abstract void degats();

    public abstract boolean enCombat();

    public abstract void disparaitre();

    public abstract void meurt();

    public abstract void frapper();

    public abstract void normal();

    public abstract  void stunt();

    public boolean estStunt(){
        return estStunt;
    }

    public boolean estVivant(){
        return estEnVie;
    }
    public void setVie(int vie){
        this.pv = vie;
    }

    public int  getVie(){return pv;}

}
