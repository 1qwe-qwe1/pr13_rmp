package com.example.pr13_rmp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    private boolean isGameFinished = false;
    private boolean isGameStarted = false;
    private String gameMode;

    private ImageView auto1, auto2;
    private Button btnDrive1, btnDrive2, btnStart;


    private float auto1StartX;
    private float auto2StartX;
    private final float FINISH_X = 1750f;
    private final Handler autoDriveHandler = Handler.createAsync(Looper.getMainLooper());
    private Runnable autoDriveRunnable;
    private boolean isAutoDriving = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameMode = getIntent().getStringExtra("GAME_MODE");
        initViews();

        auto1StartX = auto1.getX();
        auto2StartX = auto2.getX();

        if (gameMode.equals("one_player")) {
            btnDrive2.setVisibility(View.GONE);
        } else {
            btnDrive2.setVisibility(View.VISIBLE);
        }

    }

    private void initViews() {
        auto1 = findViewById(R.id.auto1);
        auto2 = findViewById(R.id.auto2);
        btnDrive1 = findViewById(R.id.btnDrive2);
        btnDrive2 = findViewById(R.id.btnDrive1);
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
                if (gameMode.equals("two_players")) {
                    moveCar2();
                }
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
                btnStart.setText(getString(R.string.pause));

                btnDrive1.setEnabled(true);
                if (gameMode.equals("two_players")) {
                    btnDrive2.setEnabled(true);
                }

                if (gameMode.equals("one_player")) {
                    startAutoDriving();
                }

                Toast.makeText(this, (getString(R.string.race_begun)), Toast.LENGTH_SHORT).show();
            } else {
                isGameStarted = false;
                btnStart.setText(getString(R.string.start));

                if (gameMode.equals("one_player")) {
                    stopAutoDriving();
                }

                Toast.makeText(this, (getString(R.string.pause)), Toast.LENGTH_SHORT).show();
            }
        } else {
            resetGame();
        }
    }

    private void moveCar1() {
        if (!isGameStarted || isGameFinished) {
            Toast.makeText(this, (getString(R.string.start_game)), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, (getString(R.string.start_game)), Toast.LENGTH_SHORT).show();
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

    private void startAutoDriving() {
        isAutoDriving = true;
        autoDriveRunnable = new Runnable() {
            @Override
            public void run() {
                if (isGameStarted && !isGameFinished && isAutoDriving) {
                    float newX = auto2.getX() + 15;

                    if (newX >= FINISH_X) {
                        auto2.setX(FINISH_X);
                        finishGame(2);
                    } else {
                        auto2.setX(newX);
                    }

                    autoDriveHandler.postDelayed(this, 100);
                }
            }
        };
        autoDriveHandler.post(autoDriveRunnable);
    }

    private void stopAutoDriving() {
        isAutoDriving = false;
        if (autoDriveRunnable != null) {
            autoDriveHandler.removeCallbacks(autoDriveRunnable);
        }
    }

    private void finishGame(int winner) {
        isGameFinished = true;
        isGameStarted = false;

        if (gameMode.equals("one_player")) {
            stopAutoDriving();
        }

        btnDrive1.setEnabled(false);
        btnDrive2.setEnabled(false);
        btnStart.setText(getString(R.string.restart));

        String winnerMessage;
        if (gameMode.equals("two_players")) {
            winnerMessage = (winner == 1) ?
                    getString(R.string.first_car_win) :
                    getString(R.string.second_car_win);
        } else {
            if (winner == 1) {
                winnerMessage = getString(R.string.you_won);
            } else {
                winnerMessage = getString(R.string.you_lost);
            }
        }

        Toast.makeText(this, winnerMessage, Toast.LENGTH_LONG).show();
    }

    private void resetGame() {
        auto1.setX(auto1StartX);
        auto2.setX(auto2StartX);

        isGameFinished = false;
        isGameStarted = false;

        btnStart.setText(getString(R.string.start));
        btnDrive1.setEnabled(false);
        btnDrive2.setEnabled(false);

        Toast.makeText(this, getString(R.string.new_game), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (autoDriveRunnable != null) {
            autoDriveHandler.removeCallbacks(autoDriveRunnable);
        }
    }
}