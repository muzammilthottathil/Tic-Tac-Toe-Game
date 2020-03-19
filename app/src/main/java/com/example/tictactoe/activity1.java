package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class activity1 extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn = true;

    private int roundCount;
    String player1name;
    String player2name;

    private int player1Points = 0;
    private int player2Points = 0;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    private ImageView arrow1;
    private ImageView arrow2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity1);


        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);
        arrow1 = findViewById(R.id.arrow1);
        arrow2 = findViewById(R.id.arrow2);
        player1name = getIntent().getExtras().getString("player1");
        player2name = getIntent().getExtras().getString("player2");

        if(player1name.equals("")){
            player1name = "Player 1";
        }
        if(player2name.equals("")){
            player2name = "Player 2";
        }
        textViewPlayer1.setText(player1name+ " : " + player1Points);
        textViewPlayer2.setText(player2name+ " : " + player2Points);


//        Initial chance to player 1 :
        arrow(player1Turn);

        for (int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MediaPlayer mediaPlayer = MediaPlayer.create(activity1.this, R.raw.bearing);
                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
                mediaPlayer.start();
                resetGame();

            }
        });

    }

    @Override
    public void onClick(View v) {
        final MediaPlayer mediaPlayer = MediaPlayer.create(activity1.this, R.raw.game);
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
        mediaPlayer.start();
        if(!((Button) v).getText().toString().equals("")){
            return;
        }
//        Changing Arrow according to player
        arrow(!player1Turn);

        if (player1Turn) {
            ((Button) v).setText("X");
            ((Button) v).setTextColor(Color.parseColor("#f50b16"));
        } else {
            ((Button) v).setText("O");
            ((Button) v).setTextColor(Color.parseColor("#190bf5"));
        }

        roundCount++;

        if (checkForWin()){
            final MediaPlayer mediaPlayer1 = MediaPlayer.create(activity1.this, R.raw.win);
            mediaPlayer1.start();
            if(player1Turn){
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 9) {
            final MediaPlayer mediaPlayer1 = MediaPlayer.create(activity1.this, R.raw.draw);
            mediaPlayer1.start();
            draw();
        } else {
            player1Turn = !player1Turn;
        }
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for(int i=0; i<3;i++){
            for(int j=0; j<3; j++){
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i=0;i<3;i++){
            if(field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals("")){
                return true;
            }
        }

        for (int i=0;i<3;i++) {
            if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals("")) {
                return true;
            }
        }
        if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals("")) {
            return true;
        }
        if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals("")) {
            return true;
        }

        return false;

    }

    private void player1Wins(){
        player1Points++;
        Toast.makeText(this, player1name + " Wins!!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }
    private void player2Wins(){
        player2Points++;
        Toast.makeText(this, player2name+" Wins!!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }
    private void draw(){
        Toast.makeText(this, "Draw !!" , Toast.LENGTH_SHORT).show();
        resetBoard();

    }

    private void updatePointsText(){
        textViewPlayer1.setText(player1name+ " : " + player1Points);
        textViewPlayer2.setText(player2name+ " : " + player2Points);
    }
    private void resetBoard(){
        for (int i=0 ; i<3; i++){
            for(int j=0; j<3; j++){
                buttons[i][j].setText("");
            }
        }
        roundCount =0;
        player1Turn = true;
        arrow(true);
    }

    private void resetGame(){
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard();
    }
    private void arrow(boolean i){
        if(i){
            arrow1.setVisibility(View.VISIBLE);
            arrow2.setVisibility(View.INVISIBLE);
        }else {
            arrow2.setVisibility(View.VISIBLE);
            arrow1.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }
}

