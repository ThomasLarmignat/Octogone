package com.example.octogone.Survi;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.octogone.R;

import pl.droidsonroids.gif.GifImageView;

//Cette classe permet de gerer les billets (collectables )
public class Collectible {
    private GifImageView perso;
    private ImageView dollard1;
    private ImageView dollard2;
    private ImageView dollard3;
    private GifImageView posH1;
    private GifImageView posH2;
    private TextView score;
    private ObjectAnimator up;
    private Handler handler;
    private float width;
    private int posInit;
    private boolean vie = true;
    private boolean degat = true;
    private Joueur j;

    //Constructeur prenant les positions des billets ainsi que le joueur car la dectection des collisions ce fait dans cette classe.
    public Collectible(ImageView pos1, ImageView pos2, ImageView pos3,GifImageView posH1,GifImageView posH2, int largeur, Joueur j){
        this.perso = j.get_position();
        dollard1 = pos1;
        dollard2 = pos2;
        dollard3 = pos3;
        posInit = pos1.getLeft();
        handler = new Handler();
        width = 2*largeur;
        this.j = j;
        this.posH1 = posH1;
        this.posH2 = posH2;
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
    private void move_collectable(final ImageView pos){
        pos.setImageResource(R.drawable.dollard);
        up = ObjectAnimator.ofFloat(pos, "translationX",-width);
        up.setDuration(3000); // duration 5 seconds
        up.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if(isCollisionDetected(perso,pos)){//Si il ya collision, on augmente le score.
                    j.update_score(1);
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


    //Animation des coeurs
    private void move_collectableHeart(final GifImageView pos){
        pos.setImageResource(R.drawable.heart);//
        up = ObjectAnimator.ofFloat(pos, "translationX",-width);
        up.setDuration(3000); // duration 5 seconds
        up.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if(isCollisionDetected(perso,pos)){//Si il ya collision, +1 Coeur
                    pos.setImageResource(R.drawable.vide);
                    if(vie){
                        j.plus_vie();
                        vie = false;
                    }
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            vie = true;
                        }
                    }, 1500);
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






    private void move_collectableCut(final GifImageView pos){
        pos.setImageResource(R.drawable.cut);//
        up = ObjectAnimator.ofFloat(pos, "translationX",-width);
        up.setDuration(3000); // duration 5 seconds
        up.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if(isCollisionDetected(perso,pos)){//Si il ya collision, -1 Coeur
                    pos.setImageResource(R.drawable.vide);
                    if(degat){
                        j.degat();
                        degat = false;
                    }
                    handler.postDelayed(new Runnable() {
                        public void run() {
                                degat = true;
                        }
                    }, 1500);
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




    public void move_collectable1(){move_collectable(dollard1);}
    public void move_collectable2(){move_collectable(dollard2);}
    public void move_collectable3(){move_collectable(dollard3);}

    public void move_collectableHeart1(){move_collectableHeart(posH1);}
    public void move_collectableHeart2(){move_collectableHeart(posH2);}

    public void move_collectableCut1(){move_collectableCut(posH1);}
    public void move_collectableCut2(){move_collectableCut(posH2);}
}
