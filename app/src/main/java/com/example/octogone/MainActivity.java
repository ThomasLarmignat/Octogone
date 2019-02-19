package com.example.octogone;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.octogone.Survi.SurviActivity;


public class MainActivity extends AppCompatActivity {
    private Button buttonPlay;
    private Button buttonOptions;
    private TextView textScore;
    private TextView textTime;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textScore = (TextView)findViewById(R.id.score);
        sharedPreferences = getBaseContext().getSharedPreferences("PREFS", MODE_PRIVATE);
        int num_score = sharedPreferences.getInt("Score", 0);
        String timeStr = sharedPreferences.getString("strTime", null);

        textScore.setText(num_score+"");


        textTime = (TextView)findViewById(R.id.timeText);
        textTime.setText(timeStr);


        buttonPlay = (Button)findViewById(R.id.jouer);

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChoixSurviActivity.class);
                startActivity(intent);
            }
        });

        buttonOptions = (Button)findViewById(R.id.option);
        buttonOptions.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OptionsActivity.class);
                startActivity(intent);
            }
        });


    }


}

