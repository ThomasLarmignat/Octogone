package com.example.myapplication;

import android.graphics.Point;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import pl.droidsonroids.gif.GifImageView;




public class MainActivity extends AppCompatActivity {
    private GifImageView persoPos = null;
    private GifImageView ennemiPos = null;


    private Button buttonHit = null;
    private Button buttonJump = null;
    private Button buttonBlock = null;
    private Button buttonDebug = null;
    private TextView text = null;
    private TextView score = null;
    private Chrono c = null;
    private Background bc = null;
    private Joueur perso;
    private Ennemi ennemi;
    private ConstraintLayout frameLayout;
    private ImageView posDollar1;
    private ImageView posDollar2;
    private ImageView posDollar3;
    private Combat cb;
    private Collectible collectables;
    private ImageView barreVie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.timer);
        score = (TextView) findViewById(R.id.score);

        //Personage
        persoPos = (GifImageView) findViewById(R.id.kaaris);
        ennemiPos = (GifImageView) findViewById(R.id.ennemi);

        //Recupere la largeur de l'écran
        Display display = getWindowManager().getDefaultDisplay();
        Point point=new Point();
        display.getSize(point);


        //Billets positions
        posDollar1 = (ImageView) findViewById(R.id.dollar1);
        posDollar2 = (ImageView) findViewById(R.id.dollar2);
        posDollar3 = (ImageView) findViewById(R.id.dollar3);
        barreVie = (ImageView) findViewById(R.id.imageVie);

        collectables = new Collectible(score,persoPos,posDollar1,posDollar2,posDollar3, point.x);



        //Buttons
        buttonHit = (Button) findViewById(R.id.button);
        buttonJump = (Button) findViewById(R.id.buttonJump);
        buttonBlock = (Button) findViewById(R.id.block);
        buttonDebug = (Button) findViewById(R.id.debug);
        frameLayout = (ConstraintLayout) findViewById(R.id.game_frame);



        final ImageView backgroundOne = (ImageView) findViewById(R.id.background_two);
        final ImageView backgroundTwo = (ImageView) findViewById(R.id.background_two);
        bc = new Background(backgroundOne,backgroundTwo);



        ennemi = new Policier(ennemiPos, persoPos);
        perso = new Joueur(persoPos,ennemi,barreVie);
        bc.run();
        cb = new Combat(ennemi,perso,bc);
        c = new Chrono(text,collectables,cb);
        c.startChrono();



        //First action listener (hit button)
        buttonHit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cb.joueurFrappe();
            }
        });

        buttonJump.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cb.joueurSaute();
            }
        });

        buttonDebug.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cb.lanceCombat();

            }
        });


        buttonBlock.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cb.joueurBloque();

            }
        });
    }


}

