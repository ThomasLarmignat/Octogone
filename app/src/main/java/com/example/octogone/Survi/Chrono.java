package com.example.octogone.Survi;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.widget.TextView;


public class Chrono extends Activity {
    private TextView text = null;
    private Timer timer = null;
    private Timer actu = null;
    private int secondes = 0;
    private int minutes = 0;
    private Collectible collectables;
    private int randomCollectable = 0;
    private String tmp;
    private Combat cb;

    public Chrono(TextView text,Collectible c, Combat cb){
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
            if(minutes > 9){
                tmp =  minutes + ":0" + secondes;
            }else{
                tmp = "0" + minutes + ":0" + secondes;
            }

        }else{
            if(minutes > 9){
                tmp = minutes +":"+ secondes;
            }else{
                tmp = "0" + minutes +":"+ secondes;
            }
        }

        text.setText(tmp);
    }


    public void setMinutes(int m){
        this.minutes = m;
    }

    public void setSecondes(int s){
        this.secondes = s;
    }


    public int getMinutes(){
        return minutes;
    }

    public int getSecondes(){
        return secondes;
    }

    public String get_string(){
        return(tmp);
    }


    public void check_collectables(){
        Random rand = new Random();
        if (randomCollectable == secondes) {
            switch(rand.nextInt(10)+1){
                case 1: collectables.move_collectable1();break;
                case 2: collectables.move_collectable2();break;
                case 3: collectables.move_collectable3();break;
                case 4: collectables.move_collectableHeart2();break;
                case 5: collectables.move_collectableHeart1();break;
                case 6: collectables.move_collectableCut1();break;
                case 7: collectables.move_collectableCut2();break;
                case 8: collectables.move_collectable1();break;
                case 9: cb.startCombat();break;
                case 10: cb.startCombat();break;
            }
            randomCollectable = getRamdom1_10();
        }
    }




}

