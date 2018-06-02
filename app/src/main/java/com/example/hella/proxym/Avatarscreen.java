package com.example.hella.proxym;

import android.content.Context;
import android.content.Intent;
import android.gesture.Gesture;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Avatarscreen extends AppCompatActivity {

    private ViewPager view_pager;
    private SlideAdapter _adapter;
    private ImageButton validate;
    private EditText _username;
    private Context _context=this;

    private String UserAvatarString;
    public String AvatarString;
    public Uri UserAvatar;
    public String AvatarName;
    FirebaseAuth mAuth;
    StorageReference mStorageRef;
    int currentID;
    int i;
    ID temp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar_screen);

        mAuth =FirebaseAuth.getInstance();

        view_pager=(ViewPager) findViewById(R.id.view_pager);
        _adapter=new SlideAdapter(this);
        view_pager.setAdapter(_adapter);
        i=0;



        //todo working but faulty need to change the onScroll to something more appropriate -> untill then get used to it
        // todo-> problem need to try real phone to be sure if that is the problem : range of scrollX and OldScroll int presents same as dp ?
        view_pager.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                try {
                    if(oldScrollX<scrollX && Math.abs(oldScrollX-scrollX)>250){
                        i++;
                        temp=setUpID(v,i);
                        Toast.makeText(getApplicationContext(), "id i+1 "+i+"  viewID "+temp.getView().getId(),Toast.LENGTH_LONG).show();
                        return;
                    }

                    else if(oldScrollX>scrollX && Math.abs(oldScrollX-scrollX)>250){
                        i--;
                        temp=setUpID(v,i);
                        Toast.makeText(getApplicationContext(), "id i-1 "+i+"  viewID "+temp.getView().getId(),Toast.LENGTH_LONG).show();
                        return;
                    }
                    else{
                        temp=setUpID(v,i);
                        Toast.makeText(getApplicationContext(), "id same "+i+"  viewID "+temp.getView().getId(),Toast.LENGTH_LONG).show();
                        return;
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });




        //UserName handling
        _username=(EditText) findViewById(R.id.username_define);


        //Validate Button handling
        validate=(ImageButton) findViewById(R.id.is_selected);
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentID= temp.getId();
                switch (currentID) {
                    case 0: {
                        UserAvatarString =getURLForResource(R.drawable.fighterman);
                        Toast.makeText(getApplicationContext(), "fighterman saved in int UserAvatar",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case 1: {
                        UserAvatarString =getURLForResource(R.drawable.fighterwoman);
                        Toast.makeText(getApplicationContext(), "fighterWomansaved in int UserAvatar",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case 2: {
                        UserAvatarString =getURLForResource(R.drawable.collectorman);
                        Toast.makeText(getApplicationContext(), "Collectorrman saved in int UserAvatar",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case 3: {
                        UserAvatarString =getURLForResource(R.drawable.collectorwoman);
                        Toast.makeText(getApplicationContext(), "CollectorWoman saved in int UserAvatar",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case 4: {
                        UserAvatarString =getURLForResource(R.drawable.crafterman);
                        Toast.makeText(getApplicationContext(), "CraftierMan saved in int UserAvatar",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case 5: {
                        UserAvatarString =getURLForResource(R.drawable.craftwomantwo);
                        Toast.makeText(getApplicationContext(), "CraftierWoman saved in int UserAvatar",Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                UserAvatar=Uri.parse(UserAvatarString);
                uploadAvatarToFirebase();
                saveUserInformation();
                startActivity(new Intent(_context, Mainmenu.class));
            }
        });

    }
    private ID setUpID(View v, int id){
            return temp=new ID(v,id);
    }

    private void saveUserInformation(){
        AvatarName=_username.getText().toString().trim();
        if(AvatarName.isEmpty()){
            _username.setError("Username required");
            _username.requestFocus();
            return;
        }
        FirebaseUser user= mAuth.getCurrentUser();
        if(user!=null && UserAvatar!=null){
            UserProfileChangeRequest profile=new UserProfileChangeRequest.Builder().setDisplayName(AvatarName).setPhotoUri(UserAvatar).build();
            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                        Toast.makeText(_context, "profile updated", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void uploadAvatarToFirebase() {
        mStorageRef = FirebaseStorage.getInstance().getReference("Avatar/"+System.currentTimeMillis()+".jpg");
        if(UserAvatar!=null) {
            mStorageRef.putFile(UserAvatar).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    AvatarString=taskSnapshot.getMetadata().getPath();
                    Toast.makeText(getApplicationContext(), AvatarString,Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "couldn't upload image png",Toast.LENGTH_SHORT).show();

                }
            });

        }
    }

    public String getURLForResource (int resourceId) {
        return Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +resourceId).toString();
    }

}

