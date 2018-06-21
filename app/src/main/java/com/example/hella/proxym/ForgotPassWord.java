package com.example.hella.proxym;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgotPassWord extends AppCompatActivity {

    private EditText email_forgot;
    private Button password_init_button;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass_word);

        email_forgot=(EditText)findViewById(R.id.email_forgot);
        password_init_button=(Button) findViewById(R.id.password_init_button);
        mAuth=FirebaseAuth.getInstance();

        password_init_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail=email_forgot.getText().toString().trim();
                if(useremail.equals("")){
                    email_forgot.setError("Email required");
                    email_forgot.requestFocus();
                }else{
                    mAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ForgotPassWord.this, "password email sent", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(ForgotPassWord.this, Signin.class));
                            }else{
                                Toast.makeText(ForgotPassWord.this, "ERROR password email sending", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });
    }
}
