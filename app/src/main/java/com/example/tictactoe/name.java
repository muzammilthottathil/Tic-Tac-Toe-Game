package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.widget.Toast.*;

public class name extends AppCompatActivity {

    private EditText player1_name;
    private EditText player2_name;
    String player1;
    String player2;
    Button go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        player1_name = findViewById(R.id.player1_name);
        player2_name = findViewById(R.id.player2_name);
        go = findViewById(R.id.go);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MediaPlayer mediaPlayer = MediaPlayer.create(name.this, R.raw.reset);
                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
                mediaPlayer.start();
               player1 = player1_name.getText().toString();
               player2 = player2_name.getText().toString();
                going();
            }
        });

    }
    public  void going(){
        Intent intent = new Intent(this , activity1.class);
        intent.putExtra("player1", player1);
        intent.putExtra("player2", player2);
        startActivity(intent);
        finish();
    }

}
