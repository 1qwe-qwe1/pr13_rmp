package com.example.pr13_rmp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e) {}
        setContentView(R.layout.activity_game);

        getWindow().setDecorFitsSystemWindows(false);

        getWindow().getInsetsController().hide(
                WindowInsets.Type.statusBars()
        );
    }

    public boolean Started = false;
    public boolean Finished = false;

    public  void Start(View view){
        Button button = (Button)findViewById(R.id.btnStart);
        if(!Finished){
            if(!Started){
                button.setBackgroundColor(Color.RED);
                button.setText("Пауза");
                Started = true;
            }
            else if(Started){
                button.setBackgroundColor(Color.GREEN);
                button.setText("Старт");
                Started = false;
            }
        }
        else{
            Intent intent=new Intent(GameActivity.this, GameActivity.class);
            startActivity(intent);
        }
    }
}