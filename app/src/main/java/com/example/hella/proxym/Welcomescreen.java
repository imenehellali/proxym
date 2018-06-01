package com.example.hella.proxym;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Welcomescreen extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }


    private Button sign_in,sign_up;
    private Context _context = this;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // todo Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        startActivity(new Intent(_context, Mainmenu.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        sign_in=(Button) findViewById(R.id.sign_in);
        sign_up=(Button) findViewById(R.id.sign_up);
        mAuth = FirebaseAuth.getInstance();


        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(_context, Signin.class));
            }
        });


        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(_context, Signup.class));
            }
        });

    }
}
