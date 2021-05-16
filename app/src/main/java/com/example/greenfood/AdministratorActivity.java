package com.example.greenfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Acest activity este al administratorului
 */

public class AdministratorActivity extends AppCompatActivity {
    Button adReteta,adCategorie,spreCategorii,alimente,adCategorieAliment,adAliment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);


        adReteta=findViewById(R.id.buttonAdaugareReteta);
        adCategorie=findViewById(R.id.butonAdaugareCategorie);
        spreCategorii=findViewById(R.id.buttonSpreCategorii);
        adAliment=findViewById(R.id.buttonAdAlim);
        adCategorieAliment=findViewById(R.id.buttonAdCategorieAlimente);
        alimente=findViewById(R.id.buttonAlim);


        butoane();
    }

    /**
     * Functie care contine toate butoanele
     * Pentru fiecare buton se implementeaza functionalitatile
     */
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
        adCategorieAliment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdministratorActivity.this, AdaugareCategorieAlimenteActivity.class);
                startActivity(intent);
            }
        });
        adAliment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdministratorActivity.this, AdaugareAlimenteActivity.class);
                startActivity(intent);
            }
        });

    }
}