package com.example.hella.proxym;

import android.content.Intent;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signin extends AppCompatActivity {


    private Button sign_in_button,forgot_password_button;
    private FirebaseAuth mAuth;
    private EditText email, password_sign_in;
    final Context _context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        sign_in_button=(Button) findViewById(R.id.sign_in_button);
        forgot_password_button=(Button) findViewById(R.id.forgot_password_button);

        email=(EditText) findViewById(R.id.email);
        password_sign_in=(EditText) findViewById(R.id.password_sign_in);

        mAuth = FirebaseAuth.getInstance();

        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _email=email.getText().toString().trim();
                String _password=password_sign_in.getText().toString().trim();
                signin(_email,_password);
            }
        });
        forgot_password_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(_context,ForgotPassWord.class));
            }
        });
    }

    //TODO need to fetch every user own Data
    public void signin(String _email, String _password){
        mAuth.signInWithEmailAndPassword(_email, _password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(_context , Mainmenu.class));

                        } else {
                            Toast.makeText(getApplicationContext(), "Authentication failed.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
