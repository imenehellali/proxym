package com.example.hella.proxym;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.hella.proxym.Util.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Skill extends AppCompatActivity {

    private UserProfile TUP; //Plan B

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mRef;

    @Override
    protected void onStart() {
        super.onStart();

        TUP=(UserProfile) Skill.this.getIntent().getExtras().getParcelable("USERPROFILE3");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skill);
    }
}
