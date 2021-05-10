package com.example.greenfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AlimenteActivity extends AppCompatActivity implements MyGreenFoodAdapterAlimente.OnItemClickListenerAlimente {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    MyGreenFoodData[] myGreenFoodData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alimente);

        String numeAliment=getIntent().getStringExtra("numeAliment");
        extrageLista(numeAliment);
    }

    public void extrageLista(String numeCategorie){
        RecyclerView recyclerView=findViewById(R.id.recyclerViewCategories);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myRef.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int lg = (int) dataSnapshot.child("listaAlimente").getChildrenCount();
                int x=0;
                for(x=1;x<=lg;x++)
                    if(String.valueOf(dataSnapshot.child("listaAlimente").child(String.valueOf(x)).child("name").getValue()).equals(numeCategorie))
                        break;

                int lungime = (int) dataSnapshot.child("listaAlimente").child(String.valueOf(x)).child("alimente").getChildrenCount();

                myGreenFoodData = new MyGreenFoodData[lungime];
                for(int i=1;i<=lungime;i++){

                    //String descriere = String.valueOf(dataSnapshot.child("listaAlimente").child(String.valueOf(x)).child("receipe").child(String.valueOf(i)).child("description").getValue());
                    String nume = String.valueOf(dataSnapshot.child("listaAlimente").child(String.valueOf(x)).child("alimente").child(String.valueOf(i)).child("name").getValue());
                    String imagine = String.valueOf(dataSnapshot.child("listaAlimente").child(String.valueOf(x)).child("alimente").child(String.valueOf(i)).child("image").getValue());
                    //Log.d("EMA",nume);

                    myGreenFoodData[i-1]=new MyGreenFoodData(nume,"",imagine);


                }


                MyGreenFoodAdapterAlimente myGreenFoodAdapterCategories = new MyGreenFoodAdapterAlimente(myGreenFoodData, AlimenteActivity.this::onItemClickAlimente);
                recyclerView.setAdapter(myGreenFoodAdapterCategories);

            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("EMA", "Failed to read value.", error.toException());
            }
        });

    }


    @Override
    public void onItemClickAlimente(int position) {

    }
}