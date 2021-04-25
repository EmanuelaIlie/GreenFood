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

public class CategoriesActivity extends AppCompatActivity {


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    String numeCategorie="desert";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));

        extrageLista();
    }


    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

           // numeCategorie = intent.getStringExtra("numeCategorie");


        }
    };


    public void extrageLista(){
        RecyclerView recyclerView=findViewById(R.id.recyclerViewCategories);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

            myRef.addValueEventListener(new ValueEventListener() {


                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    int lg = (int) dataSnapshot.child("category").getChildrenCount();
                    int x=0;
                    for(x=1;x<=lg;x++)
                        if(String.valueOf(dataSnapshot.child("category").child(String.valueOf(x)).child("name").getValue()).equals(numeCategorie))
                            break;


                    int lungime = (int) dataSnapshot.child("category").child(String.valueOf(x)).child("receipe").getChildrenCount();

                    MyGreenFoodData[] myGreenFoodData = new MyGreenFoodData[lungime];
                    for(int i=1;i<=lungime;i++){

                        String descriere = String.valueOf(dataSnapshot.child("category").child(String.valueOf(x)).child("receipe").child(String.valueOf(i)).child("description").getValue());
                        String nume = String.valueOf(dataSnapshot.child("category").child(String.valueOf(x)).child("receipe").child(String.valueOf(i)).child("name").getValue());
                        String imagine = String.valueOf(dataSnapshot.child("category").child(String.valueOf(x)).child("receipe").child(String.valueOf(i)).child("image").getValue());
                        Log.d("EMA",nume);
                        int img=Integer.parseInt(imagine);
                        myGreenFoodData[i-1]=new MyGreenFoodData(nume,descriere,img);


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


}