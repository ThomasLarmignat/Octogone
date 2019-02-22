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


public class OptionsActivity extends AppCompatActivity {
    private ImageSwitcher simpleImageSwitcher;
    private Button btnNext;
    private Button valider;
    private int maps[] = {R.drawable.flag_english, R.drawable.flag_french};
    private int countMaps = maps.length;
    private int currentIndexMaps = 0;
    private Button reset;
    SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_activity);


        sharedPreferences = getBaseContext().getSharedPreferences("PREFS", MODE_PRIVATE);


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

        valider = (Button) findViewById(R.id.next);

        valider.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                apply();
            }
        });

        reset = (Button) findViewById(R.id.raz);

        reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sharedPreferences.edit().putInt("Level",0).apply();
                sharedPreferences.edit().putInt("Score",300000).apply();

            }
        });

    }

    public void apply(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}