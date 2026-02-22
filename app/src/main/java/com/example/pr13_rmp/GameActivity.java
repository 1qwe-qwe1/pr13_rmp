package com.example.pr13_rmp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GameActivity extends AppCompatActivity {

    private boolean isGameFinished = false;
    private boolean isGameStarted = false;

    private ImageView auto1, auto2;
    private Button btnDrive1, btnDrive2, btnStart;
    private TextView tvWinner;

    private float auto1StartX;
    private float auto2StartX;
    private final float FINISH_X = 750f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initViews();

        auto1StartX = auto1.getX();
        auto2StartX = auto2.getX();
    }

    private void initViews() {
        auto1 = findViewById(R.id.auto1);
        auto2 = findViewById(R.id.auto2);
        btnDrive1 = findViewById(R.id.btnDrive1);
        btnDrive2 = findViewById(R.id.btnDrive2);
        btnStart = findViewById(R.id.btnStart);

        btnDrive1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveCar1();
            }
        });

        btnDrive2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveCar2();
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });
    }

    public void startGame() {
        if (!isGameFinished) {
            if (!isGameStarted) {
                isGameStarted = true;
                btnStart.setText("Пауза");

                btnDrive1.setEnabled(true);
                btnDrive2.setEnabled(true);

                Toast.makeText(this, "Гонка началась!", Toast.LENGTH_SHORT).show();
            } else {
                isGameStarted = false;
                btnStart.setText("Старт");

                Toast.makeText(this, "Пауза", Toast.LENGTH_SHORT).show();
            }
        } else {
            resetGame();
        }
    }

    private void moveCar1() {
        if (!isGameStarted || isGameFinished) {
            Toast.makeText(this, "Начните игру!", Toast.LENGTH_SHORT).show();
            return;
        }

        float newX = auto1.getX() + 30;
        if (newX >= FINISH_X) {
            auto1.setX(FINISH_X);
            finishGame(1);
        } else {
            auto1.setX(newX);
        }
    }

    private void moveCar2() {
        if (!isGameStarted || isGameFinished) {
            Toast.makeText(this, "Начните игру!", Toast.LENGTH_SHORT).show();
            return;
        }

        float newX = auto2.getX() + 30;

        if (newX >= FINISH_X) {
            auto2.setX(FINISH_X);
            finishGame(2);
        } else {
            auto2.setX(newX);
        }
    }

    private void finishGame(int winner) {
        isGameFinished = true;
        isGameStarted = false;

        btnDrive1.setEnabled(false);
        btnDrive2.setEnabled(false);
        btnStart.setText("Рестарт");

        String winnerMessage = (winner == 1) ?
                "Победила первая машина!" :
                "Победила вторая машина!";

        Toast.makeText(this, winnerMessage, Toast.LENGTH_LONG).show();
    }

    private void resetGame() {
        auto1.setX(auto1StartX);
        auto2.setX(auto2StartX);

        isGameFinished = false;
        isGameStarted = false;

        btnStart.setText("Старт");
        btnDrive1.setEnabled(false);
        btnDrive2.setEnabled(false);

        Toast.makeText(this, "Новая игра!", Toast.LENGTH_SHORT).show();
    }
}