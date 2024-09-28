package com.ssinfosys.fishgame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {
    private Button restart, exit;
    private TextView score;
    private String rcvscore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        rcvscore=getIntent().getExtras().get("Score").toString();

        restart=(Button)findViewById(R.id.btn_play_again);
        score= (TextView)findViewById(R.id.text1);
        exit = (Button)findViewById(R.id.btn_Exit);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
            }
        });
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GameOverActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        score.setText("Score : " + rcvscore);
    }

    private void showCustomDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        final ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.exit, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        Button okbtn=dialogView.findViewById(R.id.buttonOk);
        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
//              System.exit(0);
            }
        });
        final AlertDialog alertDialog = builder.create();

        Button cancelbtn = dialogView.findViewById(R.id.buttonCancel);
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             alertDialog.dismiss();
            }
        });

        //finally creating the alert dialog and displaying it

        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        showCustomDialog();
    }
}
