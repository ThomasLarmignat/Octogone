package com.example.myapplication;
import android.util.Log;
import android.os.Handler;
import java.util.Random;

import pl.droidsonroids.gif.GifImageView;

public class Policier extends Ennemi {




    public Policier(GifImageView e, GifImageView p){
        super(e,p);
        this.handler = new Handler();

    }

    @Override
    public void apparaitre(){
        estEnVie = true;
        setVie(100);
        e.setImageResource(R.drawable.stand_police);
        coupsStunt = 0;
    }

    @Override
    public void disparaitre(){
        e.setImageResource(R.drawable.perso_vide);
    }

    @Override
    public void avancer(){
        moveAtoB(e,p);
        handler.postDelayed(new Runnable() {
            public void run() {
                combat = true;

            }
        }, 2400);

    }

    @Override
    public void degats(){
        e.setImageResource(R.drawable.degats_police);
        pv = pv - 20;
        coupsStunt = coupsStunt + 1;
        if(coupsStunt == 2){
            estStunt = false;
            coupsStunt = 0;
        }
        if(pv == 0) {
            meurt();
        }else{
            reapparaitre();
        }


    }

    public void reapparaitre(){
        handler.postDelayed(new Runnable() {
            public void run() {
                if(estStunt){
                    stunt();
                }else{
                    normal();
                }
            }
        }, 750);
    }


    @Override
    public boolean enCombat(){
        return(this.combat);
    }


    @Override
    public void meurt(){
        estEnVie = false;
        combat = false;
        estStunt = false;
        coupsStunt = 0;
        disparaitre();
    }

    public void frapper(){
        e.setImageResource(R.drawable.hit_police);
        handler.postDelayed(new Runnable() {
            public void run() {
                normal();
            }
        }, 200);
    }

    public void normal(){
        e.setImageResource(R.drawable.stand_police);
    }

    public void stunt(){
        e.setImageResource(R.drawable.stunt_police);
        estStunt = true;
    }
}
