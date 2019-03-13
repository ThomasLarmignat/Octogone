package com.example.octogone.Combat_boss;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

import java.util.Timer;
import java.util.TimerTask;

public class Combat {
    private Boss e;
    private Joueur j;
    private Handler handler;
    private Timer timer;
    private int stuntCount = 0;

    public Combat(Boss e, Joueur j) {
        this.e = e;
        this.j = j;
        this.handler = new Handler();
    }

    public void startCombat(){
        e.apparaitre();
        handler.postDelayed(new Runnable() {
            public void run() {
                startEnnemAttack();
            }
        }, 3000);
    }


    public void playerAttack(){
        if(e.estFrappable() && e.estEnCombat()){
            j.frapper();
            e.degats();
            if(e.estStunt()){
                stuntCount += 1;
                if(stuntCount == 2){
                    stuntCount = 0;
                    e.setStunt(false);
                }
            }
            handler.postDelayed(new Runnable() {
                public void run() {
                    if(e.getVie() == 0){
                        endCombat();
                    }else{
                        e.normal();
                        e.setFrappable(true);
                    }
                }
            }, 300);
        }
    }

    public void EnnemiAttack(){
        if(j.estBloquer()){
            e.stunt();
        }else{
            e.frapper();
            j.degat();
            handler.postDelayed(new Runnable() {
                public void run() {
                    if(e.getVie() > 0 && !e.estStunt()){
                        e.normal();
                    }else{
                        e.stunt();
                    }
                }
            }, 300);
        }
    }



    public void startEnnemAttack(){
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run(){
                    EnnemiAttack();
                }
            }, 0, 2000);
    }

    public void endCombat(){
        e.meurt();
        handler.removeCallbacksAndMessages(null);
        timer.cancel();
        stuntCount = 0;
    }
}