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

    String numeCategorie="cina";

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

            numeCategorie = intent.getStringExtra("numeCategorie");


        }
    };


    public void extrageLista(){
        RecyclerView recyclerView=findViewById(R.id.recyclerViewCategories);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Log.d("EMA",numeCategorie);
        //Log.d("EMA","peste");

            // Read from the database
            myRef.addValueEventListener(new ValueEventListener() {
                private static final String TAG = "emanuela";

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    int lungime = (int) dataSnapshot.child("category").child(numeCategorie).child("receipe").getChildrenCount();
                    MyGreenFoodData[] myGreenFoodData = new MyGreenFoodData[lungime];
                    for(int i=1;i<=lungime;i++){

                        String descriere = String.valueOf(dataSnapshot.child("category").child(numeCategorie).child("receipe").child(String.valueOf(i)).child("description").getValue());
                        String nume = String.valueOf(dataSnapshot.child("category").child(numeCategorie).child("receipe").child(String.valueOf(i)).child("name").getValue());
                        String imagine = String.valueOf(dataSnapshot.child("category").child(numeCategorie).child("receipe").child(String.valueOf(i)).child("image").getValue());
                        int img=Integer.parseInt(imagine);
                        myGreenFoodData[i-1]=new MyGreenFoodData(nume,descriere,img);
                    }


                    MyGreenFoodAdapterCategories myGreenFoodAdapterCategories = new MyGreenFoodAdapterCategories(myGreenFoodData, CategoriesActivity.this);
                    recyclerView.setAdapter(myGreenFoodAdapterCategories);

                }
                @Override
                public void onCancelled(DatabaseError error) {
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });

    }


}