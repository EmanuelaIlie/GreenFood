package com.example.greenfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AdministratorActivity extends AppCompatActivity {
    Button adReteta,adCategorie,spreCategorii,alimente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);


        adReteta=findViewById(R.id.buttonAdaugareReteta);
        adCategorie=findViewById(R.id.butonAdaugareCategorie);
        spreCategorii=findViewById(R.id.buttonSpreCategorii);
        alimente=findViewById(R.id.buttonAlimente);


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
        alimente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdministratorActivity.this, ListaAlimenteActivity.class);
                startActivity(intent);
            }
        });

    }
}