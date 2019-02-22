package com.example.octogone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;

import com.example.octogone.Survi.SurviActivity;


public class ChoixSurviActivity extends AppCompatActivity {
    private Button previous;
    private Button next;
    private ImageSwitcher simpleImageSwitcher;
    private Button btnNext;

    private ImageSwitcher switcherPerso;
    private Button btnNextPerso;



    private int maps[] = {R.drawable.map_city, R.drawable.map_orly};
    private int countMaps = maps.length;
    private int currentIndexMaps = 0;

    private int perso[] = {R.drawable.stand_kaaris,R.drawable.stand_lorenzo, R.drawable.stand_jul,R.drawable.stand_booba1};
    private int persoDebloques[] = {R.drawable.bloque,R.drawable.bloque,R.drawable.bloque,R.drawable.bloque};
    private int countPerso;
    private int currentIndexPerso = 0;
    private SharedPreferences sharedPreferences;
    private int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choix_survi_activity);

        previous = (Button)findViewById(R.id.previous);
        next = (Button)findViewById(R.id.next);

        sharedPreferences = getBaseContext().getSharedPreferences("PREFS", MODE_PRIVATE);

        level = sharedPreferences.getInt("Level", 0);
        for(int i=0 ; i<level+1; i++){
            persoDebloques[i] = perso[i];
        }

        countPerso = persoDebloques.length;






        //--------------------------MAPS------------------------------------------------------------
        btnNext = (Button) findViewById(R.id.buttonNext);
        simpleImageSwitcher = (ImageSwitcher) findViewById(R.id.simpleImageSwitcher);
        simpleImageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {

            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams. MATCH_PARENT));
                return imageView;
            }
        });


        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);

        simpleImageSwitcher.setInAnimation(in);
        simpleImageSwitcher.setOutAnimation(out);
        simpleImageSwitcher.setImageResource(maps[currentIndexMaps]);


        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                currentIndexMaps++;
                if (currentIndexMaps == countMaps)
                    currentIndexMaps = 0;
                simpleImageSwitcher.setImageResource(maps[currentIndexMaps]);
            }
        });

        //--------------------------PERSO------------------------------------------------------------


        btnNext = (Button) findViewById(R.id.next_perso);
        switcherPerso = (ImageSwitcher) findViewById(R.id.perso_switcher);
        switcherPerso.setFactory(new ViewSwitcher.ViewFactory() {

            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams. MATCH_PARENT));
                return imageView;
            }
        });


        Animation in1 = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        Animation out1 = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);

        switcherPerso.setInAnimation(in1);
        switcherPerso.setOutAnimation(out1);
        switcherPerso.setImageResource(persoDebloques[currentIndexPerso]);


        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                currentIndexPerso++;

                if (currentIndexPerso == countPerso)
                    currentIndexPerso = 0;
                switcherPerso.setImageResource(persoDebloques[currentIndexPerso]);
            }
        });















































        previous.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(persoDebloques[currentIndexPerso] != R.drawable.bloque){
                    Intent intent = new Intent(getApplicationContext(), SurviActivity.class);//add more, with peronnage or map
                    intent.putExtra("map", currentIndexMaps);
                    intent.putExtra("joueur", currentIndexPerso);
                    startActivity(intent);
                }
            }
        });



    }

}
