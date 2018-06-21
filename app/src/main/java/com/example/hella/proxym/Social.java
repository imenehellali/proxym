package com.example.hella.proxym;


import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hella.proxym.Util.UserProfile;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Social extends AppCompatActivity {


    private TabLayout social_tabs;
    private LinearLayout fragmentProfile,fragmentUser,fragmentChat,fragmentQuest;

    private TextView EMAIL, USERNAME, AVATARNAME;
    private ImageView PROFILEPIC,AVATARPIC;

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mRef;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.social);



        firebaseDatabase=FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        mRef= firebaseDatabase.getReference(mAuth.getUid());

        EMAIL=(TextView)findViewById(R.id.EMAIL);
        USERNAME=(TextView)findViewById(R.id.USERNAME);
        AVATARNAME=(TextView)findViewById(R.id.AVATARNAME);

        PROFILEPIC=(ImageView)findViewById(R.id.PROFILEPIC);
        AVATARPIC=(ImageView) findViewById(R.id.AVATARPIC);

        social_tabs = (TabLayout) findViewById(R.id.social_tabs);

        social_tabs.addTab(social_tabs.newTab(),0);
        social_tabs.addTab(social_tabs.newTab(),1);
        social_tabs.addTab(social_tabs.newTab(),2);
        social_tabs.addTab(social_tabs.newTab(),3);


        social_tabs.getTabAt(0).setIcon(R.drawable.profile);
        social_tabs.getTabAt(1).setIcon(R.drawable.user);
        social_tabs.getTabAt(2).setIcon(R.drawable.chat_double);
        social_tabs.getTabAt(3).setIcon(R.drawable.quest);


        fragmentProfile=(LinearLayout) findViewById(R.id.fragmentProfile);
        fragmentUser=(LinearLayout)findViewById(R.id.fragmentUser);
        fragmentChat=(LinearLayout)findViewById(R.id.fragmentChat);
        fragmentQuest=(LinearLayout)findViewById(R.id.fragmentQuest);

       social_tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
           @Override
           public void onTabSelected(TabLayout.Tab tab) {
               switch (tab.getPosition()){
                   case 0:{
                       fragmentProfile.setVisibility(View.VISIBLE);
                       fragmentChat.setVisibility(View.GONE);
                       fragmentQuest.setVisibility(View.GONE);
                       fragmentUser.setVisibility(View.GONE);
                       break;

                   }
                   case 1:{
                       fragmentProfile.setVisibility(View.GONE);
                       fragmentChat.setVisibility(View.GONE);
                       fragmentQuest.setVisibility(View.GONE);
                       fragmentUser.setVisibility(View.VISIBLE);
                       break;

                   }
                   case 2:{
                       fragmentProfile.setVisibility(View.GONE);
                       fragmentChat.setVisibility(View.VISIBLE);
                       fragmentQuest.setVisibility(View.GONE);
                       fragmentUser.setVisibility(View.GONE);
                       break;

                   }
                   case 3:{
                       fragmentProfile.setVisibility(View.GONE);
                       fragmentChat.setVisibility(View.GONE);
                       fragmentQuest.setVisibility(View.VISIBLE);
                       fragmentUser.setVisibility(View.GONE);
                       break;

                   }
               }
           }

           @Override
           public void onTabUnselected(TabLayout.Tab tab) {

           }

           @Override
           public void onTabReselected(TabLayout.Tab tab) {
               switch (tab.getPosition()){
                   case 0:{
                       fragmentProfile.setVisibility(View.VISIBLE);
                       fragmentChat.setVisibility(View.GONE);
                       fragmentQuest.setVisibility(View.GONE);
                       fragmentUser.setVisibility(View.GONE);
                       break;

                   }
                   case 1:{
                       fragmentProfile.setVisibility(View.GONE);
                       fragmentChat.setVisibility(View.GONE);
                       fragmentQuest.setVisibility(View.GONE);
                       fragmentUser.setVisibility(View.VISIBLE);
                       break;

                   }
                   case 2:{
                       fragmentProfile.setVisibility(View.GONE);
                       fragmentChat.setVisibility(View.VISIBLE);
                       fragmentQuest.setVisibility(View.GONE);
                       fragmentUser.setVisibility(View.GONE);
                       break;

                   }
                   case 3:{
                       fragmentProfile.setVisibility(View.GONE);
                       fragmentChat.setVisibility(View.GONE);
                       fragmentQuest.setVisibility(View.VISIBLE);
                       fragmentUser.setVisibility(View.GONE);
                       break;

                   }
               }

           }
       });

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final UserProfile userProfile=dataSnapshot.getValue(UserProfile.class);



                USERNAME.setText("Username: "+userProfile.getUsername());
                EMAIL.setText("Email: "+userProfile.getUseremail());
                AVATARNAME.setText("Avatar: "+userProfile.getUseravatarname());
                AVATARPIC.setImageResource(getResourceFromString(userProfile.getUseravatarpic()));

                //ToDo cannot find the pic we uploaded Oo
                StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
                mStorageRef.child(userProfile.getUserprofilepic()+"jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).fit().centerCrop().into(PROFILEPIC);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Social.this, e.getMessage()+userProfile.getUserprofilepic(),Toast.LENGTH_LONG ).show();
                    }
                });

                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Social.this, ""+databaseError.getCode(),Toast.LENGTH_LONG).show();
            }
        });

    }
    public int getResourceFromString(String imagePath){
        String intString= imagePath.replace("android.resource://"+R.class.getPackage().getName()+"/", "").trim();
        return (int)Integer.parseInt(intString);
    }
}
