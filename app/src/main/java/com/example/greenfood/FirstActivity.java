package com.example.greenfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class FirstActivity extends AppCompatActivity {

    EditText editText;

    private List<String> nameCategory = new ArrayList<String>();
    private List<String> imageCategory = new ArrayList<String>();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);


        RecyclerView recyclerView=findViewById(R.id.recyclerViewCategories);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //adaugaCategorii();
        extrageNumeSiImagine();
        //afisare();



        MyGreenFoodData[] myGreenFoodData = new MyGreenFoodData[]{


                new MyGreenFoodData("Mic dejun","",R.drawable.poza01),
              /*  new MyGreenFoodData("Pranz","",R.drawable.poza02),
                new MyGreenFoodData("Desert","",R.drawable.poza03),
                //new MyGreenFoodData("Briose","3 feluri de briose, decorate cu frisca si fructe",R.drawable.poza04),
                new MyGreenFoodData("Pizza","",R.drawable.poza05),
                new MyGreenFoodData("MilkShake","",R.drawable.poza06),*/

        };

        MyGreenFoodAdapter myGreenFoodAdapter = new MyGreenFoodAdapter(myGreenFoodData, FirstActivity.this);
        recyclerView.setAdapter(myGreenFoodAdapter);


    }
    private void afisare(){
        MyGreenFoodData[] myGreenFoodData = new MyGreenFoodData[100];
        //myGreenFoodData[0]= new MyGreenFoodData("Mic dejun","",R.drawable.poza01);

       /*for(int i=0;i<nameCategory.size();i++) {
            int img=Integer.parseInt(imageCategory.get(i));
            myGreenFoodData[i]=new MyGreenFoodData(nameCategory.get(i).toString()," ".toString(),img);
        }*/


    }

    private void extrageNumeSiImagine(){
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            private static final String TAG = "ema";
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int lungime = (int) dataSnapshot.child("category").getChildrenCount();
                for(int i=1;i<=lungime;i++){
                    String nume = String.valueOf(dataSnapshot.child("category").child(String.valueOf(i)).child("name").getValue());
                    String imagine = String.valueOf(dataSnapshot.child("category").child(String.valueOf(i)).child("image").getValue());
                    nameCategory.add(nume);
                    imageCategory.add(imagine);
                    Toast.makeText(FirstActivity.this,nameCategory.get(i-1),Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }
    public void adaugaCategorii(){


        String lung = String.valueOf(nameCategory.size()+1);
        myRef.child("category").child(lung).child("name").setValue("pranz");
        myRef.child("category").child(lung).child("image").setValue(R.drawable.poza02);
    }

}