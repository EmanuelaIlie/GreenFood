package com.example.greenfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AdministratorActivity extends AppCompatActivity {
    Button adReteta,adCategorie,spreCategorii;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);


        adReteta=findViewById(R.id.buttonAdaugareReteta);
        adCategorie=findViewById(R.id.butonAdaugareCategorie);
        spreCategorii=findViewById(R.id.buttonSpreCategorii);

        //Integer im=R.drawable.poza01;
        Log.d("EMA",String.valueOf(R.drawable.poza01)+" 01");
        Log.d("EMA",String.valueOf(R.drawable.poza02)+" 02");
        Log.d("EMA",String.valueOf(R.drawable.poza03)+" 03");
        Log.d("EMA",String.valueOf(R.drawable.poza04)+" 04");
        Log.d("EMA",String.valueOf(R.drawable.poza05)+" 05");
        Log.d("EMA",String.valueOf(R.drawable.poza06)+" 06");
        Log.d("EMA",String.valueOf(R.drawable.poza07)+" 07");
        Log.d("EMA",String.valueOf(R.drawable.poza08)+" 08");
        butoane();
    }


    private void butoane(){
        adReteta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(AdministratorActivity.this, AdaugareReteteActivity.class);
                    startActivity(intent);
            }
        });
        adCategorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdministratorActivity.this, AdaugareCategorieActivity.class);
                startActivity(intent);
            }
        });
        spreCategorii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdministratorActivity.this, FirstActivity.class);
                startActivity(intent);
            }
        });

    }
}