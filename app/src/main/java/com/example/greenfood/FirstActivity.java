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
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirstActivity extends AppCompatActivity implements MyGreenFoodAdapter.OnItemClickListener{

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();


    String numeCategorie="";

    MyGreenFoodData[] myGreenFoodData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);



        extrageNumeSiImagine();
/*        MyGreenFoodData[] myGreenFoodData = new MyGreenFoodData[]{
                new MyGreenFoodData("cina","",R.drawable.poza01),
                new MyGreenFoodData("Pranz","",R.drawable.poza02),
        };
*/

    }


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
                        int img = Integer.parseInt(imagine);
                        myGreenFoodData[i - 1] = new MyGreenFoodData(nume, " ", img);
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


    @Override
    public void onItemClick(int position) {

        String numeCategorie=myGreenFoodData[position].getFoodName();
        Intent intent=new Intent(FirstActivity.this,CategoriesActivity.class);
        Toast.makeText(FirstActivity.this, "o afisez din interfata", Toast.LENGTH_LONG).show();
        intent.putExtra("numeCategorie",numeCategorie);
        Log.d("EMA",numeCategorie+" in interfata");


        startActivity(intent);

    }
}