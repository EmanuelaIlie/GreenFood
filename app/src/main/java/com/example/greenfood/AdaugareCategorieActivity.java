package com.example.greenfood;

import androidx.appcompat.app.AppCompatActivity;

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

public class AdaugareCategorieActivity extends AppCompatActivity {

    EditText newNume, newImagine;
    Button buttonAdaugaCategorie;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    String nume="",ingrediente="",preparare="",descriere="",categorie="",imagine="";
    int lg=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adaugare_categorie);

        newNume = (EditText) findViewById(R.id.editTextAdaugareNumeCategorie);
        newImagine = (EditText) findViewById(R.id.editTextAdaugareImagineCategorie);
        buttonAdaugaCategorie = (Button) findViewById(R.id.buttonAdCategorie);

        buttonAdaugaCategorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nume=newNume.getText().toString();
                imagine=newImagine.getText().toString();
                extrage();
                Toast.makeText(AdaugareCategorieActivity.this, "Adaugare cu succes",Toast.LENGTH_LONG).show();
            }
        });
    }
    public void extrage(){
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            private static final String TAG = "EMA";
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                lg = (int) dataSnapshot.child("category").getChildrenCount();


                Log.d("EMA",String.valueOf(lg)+"nr caategorii in bd");
                myRef.child("category").child(String.valueOf(lg+1)).child("name").setValue(nume);
                myRef.child("category").child(String.valueOf(lg+1)).child("image").setValue(imagine);

            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
            //Log.d("EMA",String.valueOf(lungime)+"nr caategorii in bd");
        });
    }

}