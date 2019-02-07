package com.example.myapplication;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import pl.droidsonroids.gif.GifImageView;

//Cette classe permet de gerer les billets (collectables )
public class Collectible {
    private int textScore = 0;//a bouger
    private GifImageView perso;
    private ImageView dollard1;
    private ImageView dollard2;
    private ImageView dollard3;
    private TextView score;
    private ObjectAnimator up;
    private Handler handler;
    private float width;
    private int posInit;

    //Constructeur prenant les positions des billets ainsi que le joueur car la dectection des collisions ce fait dans cette classe.
    public Collectible(TextView s, GifImageView p , ImageView pos1, ImageView pos2, ImageView pos3, int largeur){
        this.perso = p;
        this.score = s;
        dollard1 = pos1;
        dollard2 = pos2;
        dollard3 = pos3;
        posInit = pos1.getLeft();
        handler = new Handler();
        width = 2*largeur;
    }


    //Fonction booleenne permettant de vérifier si il y a collision
    public static boolean isCollisionDetected(View v1, View v2) {
        if (v1 == null || v2 == null) {
            return false;
        }
        Rect R1 = new Rect();
        v1.getHitRect(R1);
        Rect R2 = new Rect();
        v2.getHitRect(R2);
        return Rect.intersects(R1, R2);
    }


    //Animation des billets
    public void move_collectable(final ImageView pos){
        pos.setImageResource(R.drawable.dollard);
        up = ObjectAnimator.ofFloat(pos, "translationX",-width);
        up.setDuration(3000); // duration 5 seconds
        up.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if(isCollisionDetected(perso,pos)){//Si il ya collision, on augmente le score.
                        score.setText(updateScore());
                    pos.setImageResource(R.drawable.vide);

                }
            }
        });
        up.start();
        handler.postDelayed(new Runnable() {
            public void run() {
                pos.setImageResource(R.drawable.vide);
                ObjectAnimator upBis = ObjectAnimator.ofFloat(pos, "translationX", posInit);
                upBis.setDuration(100);
                upBis.start();
            }
        }, 3000);
    }


    public String updateScore(){
        textScore = textScore+10;
        return("Score : " + textScore+"€");
    }



    public void move_collectable1(){move_collectable(dollard1);}
    public void move_collectable2(){move_collectable(dollard2);}
    public void move_collectable3(){move_collectable(dollard3);}


}
