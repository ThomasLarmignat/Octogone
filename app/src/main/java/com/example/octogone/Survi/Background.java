package com.example.octogone.Survi;
import android.animation.ValueAnimator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

//Classe permettant de faire défiler le fond
public class Background {
    private ImageView backgroundOne;
    private ImageView backgroundTwo;
    private ValueAnimator animator;


    //Constructeur de la classe Background avec deux images en paramètres
    protected Background(ImageView b1, ImageView b2) {
        backgroundOne = b1;
        backgroundTwo = b2;
        animator = ValueAnimator.ofFloat(1.0f, 0.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(100000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = backgroundOne.getWidth();
                final float translationX = width * progress;
                backgroundOne.setTranslationX(translationX);
                backgroundTwo.setTranslationX(translationX - width);
            }
        });

    }

    //Lancer l'animation du fond
    public void run(){animator.start();}

    //Mettre en pause l'animation du fond
    public void pause(){
        animator.pause();
    }

    //Reprendre l'animation du fond
    public void resume(){
        animator.resume();
    }
}
