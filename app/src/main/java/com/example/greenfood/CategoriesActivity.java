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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CategoriesActivity extends AppCompatActivity implements MyGreenFoodAdapterCategories.OnItemClickListenerCategories{


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    MyGreenFoodData[] myGreenFoodData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        String numeCategorie=getIntent().getStringExtra("numeCategorie");

        Log.d("EMA",numeCategorie+" in onCreate");
        extrageLista(numeCategorie);
    }





    public void extrageLista(String numeCategorie){
        RecyclerView recyclerView=findViewById(R.id.recyclerViewCategories);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

            myRef.addValueEventListener(new ValueEventListener() {


                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d("EMA",numeCategorie+" in bd");

                    int lg = (int) dataSnapshot.child("category").getChildrenCount();
                    int x=0;
                    for(x=1;x<=lg;x++)
                        if(String.valueOf(dataSnapshot.child("category").child(String.valueOf(x)).child("name").getValue()).equals(numeCategorie))
                            break;
                    Log.d("EMA",String.valueOf(x)+" valoare in bd");

                    int lungime = (int) dataSnapshot.child("category").child(String.valueOf(x)).child("receipe").getChildrenCount();

                    myGreenFoodData = new MyGreenFoodData[lungime];
                    for(int i=1;i<=lungime;i++){

                        String descriere = String.valueOf(dataSnapshot.child("category").child(String.valueOf(x)).child("receipe").child(String.valueOf(i)).child("description").getValue());
                        String nume = String.valueOf(dataSnapshot.child("category").child(String.valueOf(x)).child("receipe").child(String.valueOf(i)).child("name").getValue());
                        String imagine = String.valueOf(dataSnapshot.child("category").child(String.valueOf(x)).child("receipe").child(String.valueOf(i)).child("image").getValue());
                        //Log.d("EMA",nume);

                        myGreenFoodData[i-1]=new MyGreenFoodData(nume,descriere,imagine);


                    }


                    MyGreenFoodAdapterCategories myGreenFoodAdapterCategories = new MyGreenFoodAdapterCategories(myGreenFoodData, CategoriesActivity.this);
                    recyclerView.setAdapter(myGreenFoodAdapterCategories);

                }
                @Override
                public void onCancelled(DatabaseError error) {
                    Log.w("EMA", "Failed to read value.", error.toException());
                }
            });

    }


    @Override
    public void onItemClickCategories(int position) {
        String numeReteta=myGreenFoodData[position].getFoodName();
        String numeCategorie=getIntent().getStringExtra("numeCategorie");
        Intent intent=new Intent(CategoriesActivity.this,ReceipesActivity.class);
        //Toast.makeText(FirstActivity.this, "o afisez din interfata", Toast.LENGTH_LONG).show();
        intent.putExtra("numeCategorie",numeCategorie);
        intent.putExtra("numeReteta",numeReteta);
        Log.d("EMA",numeReteta+" in interfata");


        startActivity(intent);
    }
}