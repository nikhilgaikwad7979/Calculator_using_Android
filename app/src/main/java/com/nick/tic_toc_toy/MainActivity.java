package com.nick.tic_toc_toy;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button[][] buttons = new Button[3][3];
     boolean playerXTurn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeButtons();
        setButtonListeners();
    }

void initializeButtons() {
        buttons[0][0] = findViewById(R.id.button1);
        buttons[0][1] = findViewById(R.id.button2);
        buttons[0][2] = findViewById(R.id.button3);
        buttons[1][0] = findViewById(R.id.button4);
        buttons[1][1] = findViewById(R.id.button5);
        buttons[1][2] = findViewById(R.id.button6);
        buttons[2][0] = findViewById(R.id.button7);
        buttons[2][1] = findViewById(R.id.button8);
        buttons[2][2] = findViewById(R.id.button9);
    }

    void setButtonListeners() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final int row = i;
                final int col = j;
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onButtonClick(row, col);
                    }
                });
            }
        }

        findViewById(R.id.resetButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    void onButtonClick(int row, int col) {
        if (buttons[row][col].getText().toString().equals("")) {
            buttons[row][col].setText(playerXTurn ? "X" : "O");

            if (checkForWinner()) {
                Toast.makeText(this, (playerXTurn ? "X" : "O") + " wins!", Toast.LENGTH_SHORT).show();
                disableButtons();
                autoResetGame(2000);
            } else if (isBoardFull()) {
                Toast.makeText(this, "It's a draw!", Toast.LENGTH_SHORT).show();
                disableButtons();
                autoResetGame(2000);
            } else {
                playerXTurn = !playerXTurn; // Switch turn if no winner or draw
            }
        }
    }

    boolean checkForWinner() {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(buttons[i][1].getText()) &&
                    buttons[i][1].getText().equals(buttons[i][2].getText()) &&
                    !buttons[i][0].getText().equals("")) {
                return true;
            }
            if (buttons[0][i].getText().equals(buttons[1][i].getText()) &&
                    buttons[1][i].getText().equals(buttons[2][i].getText()) &&
                    !buttons[0][i].getText().equals("")) {
                return true;
            }
        }
        // Check diagonals
        if (buttons[0][0].getText().equals(buttons[1][1].getText()) &&
                buttons[1][1].getText().equals(buttons[2][2].getText()) &&
                !buttons[0][0].getText().equals("")) {
            return true;
        }
        if (buttons[0][2].getText().equals(buttons[1][1].getText()) &&
                buttons[1][1].getText().equals(buttons[2][0].getText()) &&
                !buttons[0][2].getText().equals("")) {
            return true;
        }
        return false;
    }

    boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals("")) {
                    return false;
                }
            }
        }
        return true; // Board is full
    }


    void disableButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

     void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }
        playerXTurn = true;
    }

void autoResetGame(int delayMillis) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                resetGame();
            }
        }, delayMillis);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setMessage("Do you want to exit?");
        builder.setTitle("Alert!");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", (dialog, which) -> finish());
        builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
