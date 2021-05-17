package com.example.greenfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

import java.util.ArrayList;
import java.util.List;

/**
 * In acest activity se afiseaza toate categoriile de retete existente
 */

public class FirstActivity extends AppCompatActivity implements MyGreenFoodAdapter.OnItemClickListener{

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    Button buttonAlimenteCePotFiInlocuite;

    String numeCategorie="";

    MyGreenFoodData[] myGreenFoodData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        buttonAlimenteCePotFiInlocuite=findViewById(R.id.buttonAlimenteCePotFiInlocuite);
        butoane();
        extrageNumeSiImagine();

    }

    public void butoane(){
        buttonAlimenteCePotFiInlocuite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FirstActivity.this, ListaAlimenteActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * In aceasta functie se extrage numele ctegoriei si imaginea corespunzatoare categoriei
     */

    private void extrageNumeSiImagine(){
        RecyclerView recyclerView=findViewById(R.id.recyclerViewCategories);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            private static final String TAG = "ema";
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int lungime = (int) dataSnapshot.child("category").getChildrenCount();
                myGreenFoodData = new MyGreenFoodData[lungime];


                for(int i=1;i<=lungime;i++){

                        String nume = String.valueOf(dataSnapshot.child("category").child(String.valueOf(i)).child("name").getValue());
                        String imagine = String.valueOf(dataSnapshot.child("category").child(String.valueOf(i)).child("image").getValue());
                        myGreenFoodData[i - 1] = new MyGreenFoodData(nume, " ", imagine);
                       // Log.d("EMA", nume);

                }
                MyGreenFoodAdapter myGreenFoodAdapter = new MyGreenFoodAdapter(myGreenFoodData, FirstActivity.this::onItemClick);
                recyclerView.setAdapter(myGreenFoodAdapter);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


    }


    /**
     * Se implementeaza interfata din MyGreenFoodAdapterCategories
     * Se transmit datele urmatorului activity (pe ce s-a dat click) si se deschide noul activity
     * @param position pozitia pe care am dat click
     */
    @Override
    public void onItemClick(int position) {

        String numeCategorie=myGreenFoodData[position].getFoodName();
        Intent intent=new Intent(FirstActivity.this,CategoriesActivity.class);
        //Toast.makeText(FirstActivity.this, "o afisez din interfata", Toast.LENGTH_LONG).show();
        intent.putExtra("numeCategorie",numeCategorie);
        startActivity(intent);

    }
}