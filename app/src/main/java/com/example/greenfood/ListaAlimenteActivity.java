package com.example.greenfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Acest activity contine toate categoriile de alimente ce se pot inlocui
 */

public class ListaAlimenteActivity extends AppCompatActivity implements MyGreenFoodAdapterListaAlimente.OnItemClickListenerListaAlimente{

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    Button buttonSpreRetete;

    String numeAliment="";

    MyGreenFoodData[] myGreenFoodData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alimente);

        buttonSpreRetete=findViewById(R.id.buttonSpreRetete);

        butoane();
        extrageNumeSiImagine();
    }

    public void butoane(){
        buttonSpreRetete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ListaAlimenteActivity.this, FirstActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * in aceasta metoda se extrage imaginea si numele categoriei, din baza de date
     * se adauga in adapter si se construieste recyclerView
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

                int lungime = (int) dataSnapshot.child("listaAlimente").getChildrenCount();
                myGreenFoodData = new MyGreenFoodData[lungime];


                for(int i=1;i<=lungime;i++){

                    String nume = String.valueOf(dataSnapshot.child("listaAlimente").child(String.valueOf(i)).child("name").getValue());
                    String imagine = String.valueOf(dataSnapshot.child("listaAlimente").child(String.valueOf(i)).child("image").getValue());
                    myGreenFoodData[i - 1] = new MyGreenFoodData(nume, " ", imagine);
                    // Log.d("EMA", nume);

                }
                MyGreenFoodAdapterListaAlimente myGreenFoodAdapter = new MyGreenFoodAdapterListaAlimente(myGreenFoodData, ListaAlimenteActivity.this::onItemClickListaAlimente);
                recyclerView.setAdapter(myGreenFoodAdapter);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


    }

    /**
     * Se implementeaza interfata din MyGreenFoodListaAlimente
     * Se transmit datele urmatorului activity (pe ce s-a dat click) si se deschide noul activity(AlimenteActivity)
     * @param position pozitia pe care am dat click
     */

    @Override
    public void onItemClickListaAlimente(int position) {
        String numeAliment=myGreenFoodData[position].getFoodName();
        Intent intent=new Intent(ListaAlimenteActivity.this,AlimenteActivity.class);

        intent.putExtra("numeAliment",numeAliment);

        startActivity(intent);
    }
}