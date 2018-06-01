package com.example.hella.proxym;

import android.content.Context;
import android.content.Intent;
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

public class Signup extends AppCompatActivity {

    private EditText username, password, password_repeat;
    private Button sign_up_button;
    private FirebaseAuth mAuth;
    final Context _context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //initialize edit texts
        username=(EditText) findViewById(R.id.username);
        password=(EditText) findViewById(R.id.password);
        password_repeat=(EditText) findViewById(R.id.password_repeat);

        //initialize firebase
        mAuth=FirebaseAuth.getInstance();



        //initialize button
        sign_up_button=(Button) findViewById(R.id.sign_up_button);
        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }


    private void registerUser(){
        String _Username=username.getText().toString().trim();
        String _password=password.getText().toString().trim();
        String _password_repeat=password_repeat.getText().toString().trim();

        if(_Username.isEmpty()){
            username.setError("email is required");
            username.requestFocus();
            return;
        }
        if(_password.isEmpty()){
            password.setError("password is required");
            password.requestFocus();
            return;
        }
        if(_password.length()<6){
            password.setError("minimum length 6");
            password.requestFocus();
            return;
        }
        if(_password_repeat.isEmpty() || !_password_repeat.matches(_password) || _password_repeat.length()<6){
            password_repeat.setError("repeat password");
            password_repeat.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(_Username, _password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"User Signed up yey", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        //directing to choose avatar screen
        Intent _intent = new Intent(_context, Avatarscreen.class);
        startActivity(_intent);

    }
}
