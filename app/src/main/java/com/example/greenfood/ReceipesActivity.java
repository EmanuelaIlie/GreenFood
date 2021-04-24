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

    //private List<String> nameReceipes = new ArrayList<String>();

    TextView nume;
    TextView ingrediente;
    TextView preparare;
    ImageView imagine;

    //String nameReteta = getIntent().getStringExtra("numeReteta");
    String numeReteta="pizza";
    String numeCategorie="cina";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipes);

        extrage();
        //construieste();



    }


    private void extrage(){
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            private static final String TAG = "EMA";
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nume=findViewById(R.id.textViewNameReceipes);
                ingrediente=findViewById(R.id.editTextTextMultiLineIngredientReicepes);
                preparare=findViewById(R.id.editTextTextMultiLinePreparationReceipes);
                imagine=findViewById(R.id.imageViewReceipes);


                int lg = (int) dataSnapshot.child("category").getChildrenCount();
                int x=0;
                for(x=1;x<=lg;x++)
                    if(String.valueOf(dataSnapshot.child("category").child(String.valueOf(x)).child("name").getValue()).equals(numeCategorie))
                        break;


                int lungime = (int) dataSnapshot.child("category").child(String.valueOf(x)).child("receipe").getChildrenCount();

                for(int i=1;i<=lungime;i++){
                    Log.d("EMA","am ajuns inainte de if");
                    if(String.valueOf(dataSnapshot.child("category").child(String.valueOf(x)).child("receipe").child(String.valueOf(i)).child("name").getValue()).equals(numeReteta)) {
                        ingrediente.setText(String.valueOf(dataSnapshot.child("category").child(String.valueOf(x)).child("receipe").child(String.valueOf(i)).child("ingredients").getValue()));
                        nume.setText(String.valueOf(dataSnapshot.child("category").child(String.valueOf(x)).child("receipe").child(String.valueOf(i)).child("name").getValue()));
                        preparare.setText(String.valueOf(dataSnapshot.child("category").child(String.valueOf(x)).child("receipe").child(String.valueOf(i)).child("preparation").getValue()));
                        String img = String.valueOf(dataSnapshot.child("category").child(String.valueOf(x)).child("receipe").child(String.valueOf(i)).child("image").getValue());

                        int id = Integer.parseInt(img);
                        imagine.setImageResource(id);
                        Log.d("EMA","am ajuns pana in if");
                        break;

                    }

                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }
}