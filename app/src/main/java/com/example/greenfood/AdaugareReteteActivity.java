package com.example.greenfood;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AdaugareReteteActivity extends AppCompatActivity {
    EditText newNume, newIngrediente, newDescriere, newPreparare,newCategorie;
    ImageView imageViewReteta;
    Button buttonAdaugaReteta;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    private StorageReference reference= FirebaseStorage.getInstance().getReference();


    private Uri imageUri;

    String nume="",ingrediente="",preparare="",descriere="",categorie="",imagine="";

    int lg=0,x=0,lungime=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adaugare_retete);

        newNume = (EditText) findViewById(R.id.editTextAdaugareNumeReteta);
        newIngrediente = (EditText) findViewById(R.id.editTextAdaugareIngredienteReteta);
        newDescriere = (EditText) findViewById(R.id.editTextAdaugareDescriereReteta);
        newPreparare = (EditText) findViewById(R.id.editTextAdaugarePreparareReteta);
        imageViewReteta=findViewById(R.id.imageViewReteta);
        newCategorie = (EditText) findViewById(R.id.editTextCategorieReteta);
        buttonAdaugaReteta = (Button) findViewById(R.id.buttonAdReteta);



        imageViewReteta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent=new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,2);
            }
        });

        buttonAdaugaReteta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nume=newNume.getText().toString();
                ingrediente=newIngrediente.getText().toString();
                descriere=newDescriere.getText().toString();
                preparare=newPreparare.getText().toString();
                categorie=newCategorie.getText().toString();



                extrage();
                //Toast.makeText(AdaugareReteteActivity.this, "Adaugare cu succes",Toast.LENGTH_LONG).show();
               // Log.d("EMA",String.valueOf(lungime)+"nr caategorii in button");
            }
        });

    }

    public void extrage(){
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            private static final String TAG = "EMA";

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(imageUri!=null){
                    uploadToFirebase(imageUri);
                }
                else{
                    Toast.makeText(AdaugareReteteActivity.this, "Please select image",Toast.LENGTH_LONG).show();
                }

                lg = (int) dataSnapshot.child("category").getChildrenCount();
                for(x=1;x<=lg;x++) {
                    if (String.valueOf(dataSnapshot.child("category").child(String.valueOf(x)).child("name").getValue()).equals(categorie))
                        break;
                }

                lungime = (int) dataSnapshot.child("category").child(String.valueOf(x)).child("receipe").getChildrenCount();


                myRef.child("category").child(String.valueOf(x)).child("receipe").child(String.valueOf(lungime+1)).child("description").setValue(descriere);
                myRef.child("category").child(String.valueOf(x)).child("receipe").child(String.valueOf(lungime+1)).child("ingredients").setValue(ingrediente);
                myRef.child("category").child(String.valueOf(x)).child("receipe").child(String.valueOf(lungime+1)).child("name").setValue(nume);
                myRef.child("category").child(String.valueOf(x)).child("receipe").child(String.valueOf(lungime+1)).child("preparation").setValue(preparare);
                myRef.child("category").child(String.valueOf(x)).child("receipe").child(String.valueOf(lungime+1)).child("image").setValue(nume);


            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
            //Log.d("EMA",String.valueOf(lungime)+"nr caategorii in bd");
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==2 &&  resultCode==RESULT_OK && data!=null){
            imageUri=data.getData();
            imageViewReteta.setImageURI(imageUri);

        }
    }

    private void uploadToFirebase(Uri uri){
        StorageReference fileRef=reference.child("receipes/"+nume+".jpg");
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Toast.makeText(AdaugareReteteActivity.this,"Successfully",Toast.LENGTH_LONG).show();
                        Log.d("EMA","adaugare in storage");
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdaugareReteteActivity.this,"Uploading fail",Toast.LENGTH_LONG).show();
            }
        });
    }


}