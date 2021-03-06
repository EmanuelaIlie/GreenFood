package com.example.greenfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ReceipesActivity extends AppCompatActivity {

    //private List<String> nameReceipes = new ArrayList<String>();

    TextView nume;
    TextView ingrediente;
    TextView preparare;
    ImageView imagine;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipes);

        String numeCategorie=getIntent().getStringExtra("numeCategorie");
        String numeReteta=getIntent().getStringExtra("numeReteta");

        extrage(numeCategorie,numeReteta);
    }

    /**
     * In aceasta functie se extrag toate datele pentru reteta
     * Se pun datele extrase in view-uri
     * @param numeCategorie numele categoriei pe care am dat click (transmisa din CategoriesActivity)
     * @param numeReteta numele retetei pe care am dat click (transmisa din CategoriesActivity)
     */

    private void extrage(String numeCategorie,String numeReteta){
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            private static final String TAG = "EMA";
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nume=findViewById(R.id.textViewNameReceipes);
                ingrediente=findViewById(R.id.editTextTextMultiLineIngredientReicepes);
                preparare=findViewById(R.id.editTextTextMultiLinePreparationReceipes);
                imagine=findViewById(R.id.imageViewReceipesAct);


                int lg = (int) dataSnapshot.child("category").getChildrenCount();
                int x=0;
                for(x=1;x<=lg;x++)
                    if(String.valueOf(dataSnapshot.child("category").child(String.valueOf(x)).child("name").getValue()).equals(numeCategorie))
                        break;


                int lungime = (int) dataSnapshot.child("category").child(String.valueOf(x)).child("receipe").getChildrenCount();

                for(int i=1;i<=lungime;i++){
                    //Log.d("EMA","am ajuns inainte de if");
                    if(String.valueOf(dataSnapshot.child("category").child(String.valueOf(x)).child("receipe").child(String.valueOf(i)).child("name").getValue()).equals(numeReteta)) {
                        ingrediente.setText(String.valueOf(dataSnapshot.child("category").child(String.valueOf(x)).child("receipe").child(String.valueOf(i)).child("ingredients").getValue()).replace("_n","\n"));
                        nume.setText(String.valueOf(dataSnapshot.child("category").child(String.valueOf(x)).child("receipe").child(String.valueOf(i)).child("name").getValue()));
                        preparare.setText(String.valueOf(dataSnapshot.child("category").child(String.valueOf(x)).child("receipe").child(String.valueOf(i)).child("preparation").getValue()).replace("_n","\n"));
                        String img = String.valueOf(dataSnapshot.child("category").child(String.valueOf(x)).child("receipe").child(String.valueOf(i)).child("image").getValue());

                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference storageRef = storage.getReference();
                        StorageReference imagesRef = storageRef.child("receipes/"+img+".jpg");

                        Log.d("EMA",img+" in extrage");

                        imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(ReceipesActivity.this)
                                        .load(uri)
                                        .into(imagine);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ReceipesActivity.this, "It didnt download", Toast.LENGTH_SHORT).show();
                            }
                        });

                        //Log.d("EMA","am ajuns pana in if");
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