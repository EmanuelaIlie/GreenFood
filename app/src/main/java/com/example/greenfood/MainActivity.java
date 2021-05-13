package com.example.greenfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Prima fereastra, cea de autentificare
 */
public class MainActivity extends AppCompatActivity {

    Button  login;
    TextView createAccount;
    EditText emailMain, passwordMain;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    public List<String> emails = new ArrayList<String>();
    public List<String> parole = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createAccount = findViewById(R.id.textViewCreateAccount);
        login = (Button) findViewById(R.id.buttonLogin);
        emailMain = (EditText) findViewById(R.id.editTextEmail);
        passwordMain = (EditText) findViewById(R.id.editTextPassword);

        extrageEmailuriSiParole();
        butoane();

    }

    /**
     * Metoda unde se creeaza functionalitatile pentru cele 2 butoane
     * Butonul de Conectare
     * Butonul de Creeare cont nou
     */
    private void butoane(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verificareAutentificare() && verificareAutentificareAdministrator()==false){
                    Intent intent = new Intent(MainActivity.this, FirstActivity.class);
                    startActivity(intent);
                }else
                if(verificareAutentificareAdministrator()){
                    Intent intent = new Intent(MainActivity.this, AdministratorActivity.class);
                    startActivity(intent);
                }else
                    {
                    Toast.makeText(MainActivity.this,"Aceste date nu exista in sistem.",Toast.LENGTH_LONG).show();
                }
            }
        });
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreareContActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Functie care verifica daca datele introduse sunt pentru un cont obisnuit, sau  administrator
     * @return true daca e administrator, altfel false
     */

    private boolean verificareAutentificareAdministrator(){
        String email = emailMain.getText().toString();
        String parola = passwordMain.getText().toString();
        if(email.equals("emanuela.ilie99@e-uvt.ro") && parola.equals("ema123"))
            return true;
        return false;
    }

    /***
     * Metoda verifica daca email-ul si parola introduse se gasesc in baza de date
     * @return true daca exista, false altfel
     */
    private boolean verificareAutentificare(){
        String email = emailMain.getText().toString();
        String parola = passwordMain.getText().toString();

        for(int i=0;i<emails.size();i++){
            if(email.equals(emails.get(i)) && parola.equals(parole.get(i)))
                return true;
        }
        return false;
    }

    /**
     * Metoda extrage email-urile si parolele din baza de date
     */
    private void extrageEmailuriSiParole(){
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            private static final String TAG = "ema";
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int lungime = (int) dataSnapshot.child("users").getChildrenCount();
                for(int i=1;i<=lungime;i++){
                    String email = String.valueOf(dataSnapshot.child("users").child(String.valueOf(i)).child("email").getValue());
                    String parola = String.valueOf(dataSnapshot.child("users").child(String.valueOf(i)).child("password").getValue());
                    emails.add(email);
                    parole.add(parola);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Eroare la citirea datelor", error.toException());
            }
        });
    }
}