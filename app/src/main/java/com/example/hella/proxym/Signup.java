package com.example.hella.proxym;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;

public class Signup extends AppCompatActivity {

    private static Integer PICK_IMAGE=123;

    private EditText username, password, password_repeat;
    private ImageView profile_pic;
    private Button sign_up_button;

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mRef;
    private StorageReference mStorageRef;

    private String profile_pic_string;
    private Uri profile_pic_uri;

    private Context _context;
    private String _Username;
    private String _password;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==PICK_IMAGE && resultCode==RESULT_OK && data.getData()!=null){
            profile_pic_uri=data.getData();
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),profile_pic_uri);
                profile_pic.setImageBitmap(bitmap);
                profile_pic.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } catch (IOException e) {
                Toast.makeText(Signup.this, e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        _context = this;

        username=(EditText) findViewById(R.id.username);
        password=(EditText) findViewById(R.id.password);
        password_repeat=(EditText) findViewById(R.id.password_repeat);
        profile_pic=(ImageView) findViewById(R.id.profile_pic);

        profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_OPEN_DOCUMENT).addCategory(Intent.CATEGORY_OPENABLE).setType("image/*").setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select image"),PICK_IMAGE);
            }
        });

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

        mAuth=FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(_Username, _password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mStorageRef = FirebaseStorage.getInstance().getReference();
                            StorageReference reRef= mStorageRef.child("Profiles").child(""+mAuth.getCurrentUser().getUid()+".jpg");

                            if(profile_pic_uri !=null) {
                                reRef.putFile(profile_pic_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Toast.makeText(Signup.this, "SUCCESS UPLOAD P PIC", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Signup.this, "FAIL UPLOAD P PIC  ", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                profile_pic_string= "Profiles/"+mAuth.getCurrentUser().getUid();
                                UserProfile userProfile=new UserProfile(_Username,_password,profile_pic_string,"Online");
                                Intent _intent = new Intent(_context, Avatarscreen.class).putExtra("USERPROFILE",userProfile);
                                startActivity(_intent);
                                finish();
                        }
                        else
                            Toast.makeText(getApplicationContext(),"Error occurred", Toast.LENGTH_SHORT).show();
                    }
                }});

    }
}
