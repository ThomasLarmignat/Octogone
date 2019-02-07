package com.example.myapplication;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.widget.TextView;


public class Chrono extends Activity {
    private TextView text = null;
    private Timer timer = null;
    private Timer actu = null;
    private Collectible collectables;
    private int secondes = 0;
    private int minutes = 0;
    private int randomCollectable = 0;
    private String tmp;
    private Combat cb;

    public Chrono(TextView text, Collectible c, Combat cb){
        this.text = text;
        this.collectables = c;
        this.randomCollectable = getRamdom1_10();
        this.cb = cb;
    }

    public void startChrono() {
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        convert();
                        check_collectables();
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }


    public void annuleChrono(){
        timer.cancel();
    }








    public void startIA() {
        actu = new Timer();
        TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(cb.enCours()){
                            if(cb.estPret() && !cb.ennemiStunt()){
                                cb.ennemiFrappe();
                                if(cb.ennemiVie() == 0){
                                    annuleIA();
                                    cb.finCombat();
                                }
                            }else{
                                annuleIA();
                            }
                        }

                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 400);
    }


    public void annuleIA(){
        actu.cancel();
    }








    public void check_collectables(){
        Random rand = new Random();
        if (randomCollectable == secondes) {
            switch(rand.nextInt(4)+1){
                case 1: collectables.move_collectable1();break;
                case 2: collectables.move_collectable2();break;
                case 3: collectables.move_collectable3();break;
                case 4: if(!cb.enCours() || cb.ennemiVie() == 0){ cb.lanceCombat();startIA();}break;
            }

            randomCollectable = getRamdom1_10();
        }
    }


    public int getRamdom1_10(){
        Random rand = new Random();
        if (secondes<50){
            return(secondes + rand.nextInt(6)+3);
        }else{
            return(rand.nextInt(6)+3);
        }

    }

    public int get_secondes(){
        return secondes;
    }



    public void convert(){
        secondes++;

        if(secondes>59){
            minutes++;
            secondes = 0;
        }

        if(secondes<10){
            tmp = "Time : 0" + minutes + ":0" + secondes;
        }else{
            tmp = "Time : 0" + minutes +":"+ secondes;
        }

        text.setText(tmp);
    }

    public int get_minutes(){
        return minutes;
    }

}

