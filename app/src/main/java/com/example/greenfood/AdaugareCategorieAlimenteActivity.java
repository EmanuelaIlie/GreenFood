package com.example.greenfood;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AdaugareCategorieAlimenteActivity extends AppCompatActivity {

    EditText newNume;
    ImageView imageViewCategorie;
    Button buttonAdaugaCategorie;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    private StorageReference reference= FirebaseStorage.getInstance().getReference();


    private Uri imageUri;

    String nume="";
    int lg=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adaugare_categorie_alimente);

        newNume = (EditText) findViewById(R.id.editTextAdaugareNumeCategorieAliment);
        imageViewCategorie=findViewById(R.id.imageViewCategorieAliment);
        buttonAdaugaCategorie = (Button) findViewById(R.id.buttonAdCategorieAlimente);

        butoane();
    }

    /**
     * acesta metoda contine implementarea butonului Adaugare si a imageView-ului
     */

    public void butoane(){
        imageViewCategorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent=new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,2);
            }
        });

        buttonAdaugaCategorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nume=newNume.getText().toString();
                adauga();
                //Toast.makeText(AdaugareAlimenteActivity.this, "Adaugare cu succes",Toast.LENGTH_LONG).show();
            }
        });
    }


    /**
     * aceasta metoda adauga in firebase numele si imaginea corespunzatoare categoriei de alimente
     */
    public void adauga(){
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            private static final String TAG = "EMA";
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                lg = (int) dataSnapshot.child("listaAlimente").getChildrenCount();

                if(imageUri!=null){
                    uploadToFirebase(imageUri);
                }
                else{
                    Toast.makeText(AdaugareCategorieAlimenteActivity.this, "Please select image",Toast.LENGTH_LONG).show();
                }


                myRef.child("listaAlimente").child(String.valueOf(lg+1)).child("name").setValue(nume);
                myRef.child("listaAlimente").child(String.valueOf(lg+1)).child("image").setValue(nume);

            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
            //Log.d("EMA",String.valueOf(lungime)+"nr caategorii in bd");
        });
    }

    /**
     * aceasta metoda seteaza uri pentru imageView, doar daca requestCode e cel transmis de noi
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==2 &&  resultCode==RESULT_OK && data!=null){
            imageUri=data.getData();
            imageViewCategorie.setImageURI(imageUri);

        }
    }
    /**
     * aceasta metoda adauga in storage o imagine pe baza unui link (uri)
     * @param uri
     */

    private void uploadToFirebase(Uri uri){
        StorageReference fileRef=reference.child("listaAlimente/"+nume+".jpg");
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Toast.makeText(AdaugareCategorieAlimenteActivity.this,"Successfully",Toast.LENGTH_LONG).show();
                        Log.d("EMA","adaugare in storage");
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdaugareCategorieAlimenteActivity.this,"Uploading fail",Toast.LENGTH_LONG).show();
            }
        });
    }
}