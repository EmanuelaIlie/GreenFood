package com.example.greenfood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReceipesActivity extends AppCompatActivity {

    private List<String> nameReceipes = new ArrayList<String>();
    private java.util.List<String> imageReceipes = new ArrayList<String>();
    private java.util.List<String> preparationReceipes = new ArrayList<String>();
    private java.util.List<String> ingredientsReceipes = new ArrayList<String>();

    TextView nume;
    TextView ingrediente;
    TextView preparare;
    ImageView imagine;

   // String nameReteta = getIntent().getStringExtra("nume");
    String nameReteta="pizza";
    String name="cina";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipes);

        extrage();
        //construieste();



    }

    private void construieste(){
        nume=findViewById(R.id.textViewNameReceipes);
        ingrediente=findViewById(R.id.editTextTextMultiLineIngredientReicepes);
        preparare=findViewById(R.id.editTextTextMultiLinePreparationReceipes);
        imagine=findViewById(R.id.imageViewReceipes);

        int lung=nameReceipes.size();
        int i=0;
        Toast.makeText(ReceipesActivity.this,nameReceipes.get(0),Toast.LENGTH_LONG).show();

        /*for(i=0;i<lung;i++){


            if(nameReteta.equals(nameReceipes.get(i)))
                break;
        }*/

        /*Integer id=Integer.parseInt(imageReceipes.get(i));
        nume.setText(nameReceipes.get(i));
        ingrediente.setText(ingredientsReceipes.get(i));
        preparare.setText(preparationReceipes.get(i));
        imagine.setImageResource(id);*/

    }
    private void extrage(){
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            private static final String TAG = "ema";
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int lungime = (int) dataSnapshot.child("category").child(name).getChildrenCount();
                String n="",i="",ii="",p="";
                for(int j=1;j<=lungime;j++){
                    n = String.valueOf(dataSnapshot.child("category").child(name).child("receipe").child(String.valueOf(j)).child("name").getValue());
                    if(n.equals(nameReteta)){
                        i = String.valueOf(dataSnapshot.child("category").child(name).child("receipe").child(String.valueOf(j)).child("image").getValue());
                        ii = String.valueOf(dataSnapshot.child("category").child(name).child("receipe").child(String.valueOf(j)).child("ingredients").getValue());
                        p = String.valueOf(dataSnapshot.child("category").child(name).child("receipe").child(String.valueOf(j)).child("preparation").getValue());
                        break;
                    }


                    // imageCategory.add(imagine);
                  //  Toast.makeText(ReceipesActivity.this,nameReceipes.get(i-1),Toast.LENGTH_LONG).show();
                }



                nume=findViewById(R.id.textViewNameReceipes);
                ingrediente=findViewById(R.id.editTextTextMultiLineIngredientReicepes);
                preparare=findViewById(R.id.editTextTextMultiLinePreparationReceipes);
                imagine=findViewById(R.id.imageViewReceipes);

                Integer id=Integer.parseInt(i);
                nume.setText(n);
                ingrediente.setText(ii);
                preparare.setText(p);
                imagine.setImageResource(id);

            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }
}