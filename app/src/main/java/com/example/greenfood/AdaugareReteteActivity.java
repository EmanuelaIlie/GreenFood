package com.example.greenfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdaugareReteteActivity extends AppCompatActivity {
    EditText newNume, newIngrediente, newDescriere, newPreparare, newImagine,newCategorie;
    Button buttonAdaugaReteta;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    String nume="",ingrediente="",preparare="",descriere="",categorie="",imagine="";

    int lg=0,x=0,lungime=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adaugare_retete);

        newNume = (EditText) findViewById(R.id.editTextAdaugareNumeReteta);
        newIngrediente = (EditText) findViewById(R.id.editTextAdaugareIngredienteReteta);
        newDescriere = (EditText) findViewById(R.id.editTextAdaugareDescriereReteta);
        newPreparare = (EditText) findViewById(R.id.editTextAdaugarePreparareReteta);
        newImagine = (EditText) findViewById(R.id.editTextAdaugareImagineReteta);
        newCategorie = (EditText) findViewById(R.id.editTextCategorieReteta);
        buttonAdaugaReteta = (Button) findViewById(R.id.buttonAdReteta);




        buttonAdaugaReteta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nume=newNume.getText().toString();
                ingrediente=newIngrediente.getText().toString();
                descriere=newDescriere.getText().toString();
                preparare=newPreparare.getText().toString();
                imagine=newImagine.getText().toString();
                categorie=newCategorie.getText().toString();
                extrage();
                Toast.makeText(AdaugareReteteActivity.this, "Adaugare cu succes",Toast.LENGTH_LONG).show();
                Log.d("EMA",String.valueOf(lungime)+"nr caategorii in button");
            }
        });

    }

    public void extrage(){
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            private static final String TAG = "EMA";
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                lg = (int) dataSnapshot.child("category").getChildrenCount();
                for(x=1;x<=lg;x++) {
                    if (String.valueOf(dataSnapshot.child("category").child(String.valueOf(x)).child("name").getValue()).equals(categorie))
                        break;
                }

                lungime = (int) dataSnapshot.child("category").child(String.valueOf(x)).child("receipe").getChildrenCount();

                Log.d("EMA",String.valueOf(lungime)+"nr caategorii in bd");
                myRef.child("category").child(String.valueOf(x)).child("receipe").child(String.valueOf(lungime+1)).child("description").setValue(descriere);
                myRef.child("category").child(String.valueOf(x)).child("receipe").child(String.valueOf(lungime+1)).child("ingredients").setValue(ingrediente);
                myRef.child("category").child(String.valueOf(x)).child("receipe").child(String.valueOf(lungime+1)).child("name").setValue(nume);
                myRef.child("category").child(String.valueOf(x)).child("receipe").child(String.valueOf(lungime+1)).child("preparation").setValue(preparare);
                myRef.child("category").child(String.valueOf(x)).child("receipe").child(String.valueOf(lungime+1)).child("image").setValue(imagine);


            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
            //Log.d("EMA",String.valueOf(lungime)+"nr caategorii in bd");
        });
    }

}