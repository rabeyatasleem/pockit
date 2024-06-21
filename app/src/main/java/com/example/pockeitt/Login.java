package com.example.pockeitt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {
    TextView create_acc;
    FirebaseAuth mauth;
    FirebaseDatabase database;
    Button button;
    GoogleSignInClient gsc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        create_acc = findViewById(R.id.create_acc);
        button = findViewById(R.id.button);

        mauth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        if (mauth.getCurrentUser() != null) {
            //user already signed in
            Log.d("AUTH", mauth.getCurrentUser().getEmail());

        } else {

        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        create_acc.setOnClickListener(v -> {
            Intent i = new Intent(Login.this, SignUp.class);
            startActivity(i);
            finish();
        });
    }

}