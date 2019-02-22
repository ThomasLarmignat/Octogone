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
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.octogone.Combat_boss.BossActivity;

public class MenuBossActivity extends AppCompatActivity {
    private ImageSwitcher simpleImageSwitcher;
    private TextView name;
    private Button btnNext;
    private Button retour;
    private Button suivant;
    private String round[] = {"Round 1","Round 2","Round 3"};
    private String noms[] = {"Lorenzo","Jul","Booba"};
    private int prix[] = {1000,20000,30000};
    private int persoDebloques[] = {R.drawable.stand_lorenzo, R.drawable.stand_jul,R.drawable.stand_booba1};
    private int perso[] = {R.drawable.bloque, R.drawable.bloque,R.drawable.bloque};
    private int countPerso = perso.length;
    private int currentIndex = 0;

    private int num_score ;
    private int level ;

    public static SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choix_boss_activity);
        sharedPreferences = getBaseContext().getSharedPreferences("PREFS", MODE_PRIVATE);

        retour = (Button) findViewById(R.id.retour);

        retour.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


        suivant = (Button) findViewById(R.id.next);
        name = (TextView) findViewById(R.id.nom);
        num_score = sharedPreferences.getInt("Score", 0);
        level = sharedPreferences.getInt("Level", 0);
        for(int i=0 ; i<level; i++){
            perso[i] = persoDebloques[i];
        }

        btnNext = (Button) findViewById(R.id.next_perso);
        simpleImageSwitcher = (ImageSwitcher) findViewById(R.id.perso_switcher);
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
        simpleImageSwitcher.setImageResource(perso[currentIndex]);
        if(currentIndex< level){//deja buy
            suivant.setText("✔");
            suivant.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            name.setText(noms[currentIndex]);
        }else{
            suivant.setText(prix[currentIndex] +"");
            name.setText(round[currentIndex]);
            suivant.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dollard_icone, 0, 0, 0);
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                currentIndex++;
                if (currentIndex == countPerso)
                    currentIndex = 0;
                simpleImageSwitcher.setImageResource(perso[currentIndex]);


                if(currentIndex< level){//deja buy
                    suivant.setText("✔");
                    suivant.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    name.setText(noms[currentIndex]);
                }else{
                    suivant.setText(prix[currentIndex] +"");
                    name.setText(round[currentIndex]);
                    suivant.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dollard_icone, 0, 0, 0);
                }
            }
        });

        suivant.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fight();
            }
        });


    }


    public void fight(){
        num_score = sharedPreferences.getInt("Score", 0);
        level = sharedPreferences.getInt("Level", 0);
        if(currentIndex< level){//deja buy
            suivant.setText("✔");
            Intent intent = new Intent(getApplicationContext(), BossActivity.class);
            intent.putExtra("BossIndex",currentIndex);
            startActivity(intent);
            this.finish();
        }else if(num_score >= prix[currentIndex] && (currentIndex >= level) && (currentIndex != level+1)){//assez argent
            sharedPreferences.edit().putInt("Score",sharedPreferences.getInt("Score", 0)- prix[currentIndex]).apply();
            perso[currentIndex] = persoDebloques[currentIndex];
            simpleImageSwitcher.setImageResource(perso[currentIndex]);
            suivant.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            name.setText(noms[currentIndex]);
            suivant.setText("✔");
            sharedPreferences.edit().putInt("Level",currentIndex+1).apply();
            sharedPreferences.edit().putInt("Index",currentIndex).apply();
            Intent intent = new Intent(getApplicationContext(), BossActivity.class);
            intent.putExtra("BossIndex",currentIndex);
            startActivity(intent);
            this.finish();
        }
    }
}