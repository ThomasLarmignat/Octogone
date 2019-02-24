package com.example.octogone.Survi;

import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

public class Combat {
    private Policier e;
    private Joueur j;
    private Background bc;
    private Handler handler;
    private Timer timer;
    private int stuntCount = 0;

    public Combat(Policier e, Joueur j, Background bc) {
        this.e = e;
        this.j = j;
        this.bc = bc;
        this.handler = new Handler();
    }

    public void startCombat(){
        e.apparaitre();
        bc.pause();
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
                        j.update_score(2);
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
                    }
                    if(e.getVie() > 0 && e.estStunt()){
                        e.stunt();
                    }
                    if(e.getVie() == 0){
                        endCombat();
                    }
                }
            }, 300);
        }
    }


    public void ennemiMeurt(){
        e.meurt();
    }
    public void startEnnemAttack(){
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    EnnemiAttack();
                }
            }, 0, 2000);
    }

    public void annuleEnnemAttack(){
        timer.cancel();
    }

    public void endCombat(){
        e.meurt();
        annuleEnnemAttack();
        bc.resume();
        handler.removeCallbacksAndMessages(null);
        //timer.cancel();//a verifier source de bug
        stuntCount = 0;
    }
}