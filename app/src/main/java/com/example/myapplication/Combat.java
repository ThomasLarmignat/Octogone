package com.example.myapplication;

import android.os.Handler;

public class Combat {
    private Ennemi e;
    private Joueur j;
    private Handler handler;
    private boolean enCombat = false;
    private Background bc;

    public Combat(Ennemi e, Joueur j, Background bc){
        this.e = e;
        this.j = j;
        this.handler = new Handler();
        this.bc = bc;
    }

    public void lanceCombat(){
        enCombat = true;
        e.apparaitre();
        e.avancer();
        bc.pause();
    }

    public boolean enCours(){
        return(enCombat);
    }

    public void joueurFrappe(){
        j.frapper();
        if(e.enCombat() && j.peutAttack()){
                e.degats();
            }else{
            finCombat();
        }
    }


    public void ennemiFrappe() {
        if (!e.estStunt()) {
            e.frapper();
            if (j.estBloquer()) {
                e.stunt();
            } else {
                j.degat();
                if (j.getVie() == 0) {
                    //finCombat();
                }
            }
        }
    }


    public boolean estPret(){
       return e.enCombat();
    }
    public void joueurBloque(){
        j.bloquer();
    }

    public void joueurSaute(){
        j.sauter();
    }

    public void finCombat(){
        enCombat = false;
        e.meurt();
        bc.resume();
    }

    public int joueurVie(){
        return j.getVie();
    }

    public int ennemiVie(){
        return e.getVie();
    }

    public boolean ennemiStunt(){
        return e.estStunt();
    }
}
