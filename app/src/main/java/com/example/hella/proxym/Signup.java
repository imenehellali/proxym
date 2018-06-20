package com.example.hella.proxym;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hella.proxym.Util.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Signup extends AppCompatActivity {

    private EditText username, password, password_repeat;
    private ImageView profile_pic;
    private Button sign_up_button;
    private FirebaseAuth mAuth;
    private Context _context;
    String _Username;String _password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        _context = this;
        //initialize edit texts
        username=(EditText) findViewById(R.id.username);
        password=(EditText) findViewById(R.id.password);
        password_repeat=(EditText) findViewById(R.id.password_repeat);
        profile_pic=(ImageView) findViewById(R.id.profile_pic);

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
        _Username=username.getText().toString().trim();
        _password=password.getText().toString().trim();
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
                            Toast.makeText(getApplicationContext(),"User Sign up successful", Toast.LENGTH_SHORT).show();
                            sendUserData();
                            Toast.makeText(getApplicationContext(),"Upload successful", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent _intent = new Intent(_context, Avatarscreen.class);
                            startActivity(_intent);
                        }
                        else
                            Toast.makeText(getApplicationContext(),"Error occurred", Toast.LENGTH_SHORT).show();
                    }
                });

    }
    private void sendUserData(){
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference mRef= firebaseDatabase.getReference(mAuth.getUid());

        //First Step profile with email password
        UserProfile userProfile=new UserProfile(_Username, _password);
        mRef.setValue(userProfile);
    }
}
