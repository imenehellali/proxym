package com.example.hella.proxym;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.hella.proxym.Util.ID;
import com.example.hella.proxym.Util.UserProfile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

public class Avatarscreen extends AppCompatActivity {

    private ViewPager view_pager;
    private SlideAdapter _adapter;
    private ImageButton validate;

    private EditText _username;

    private Context _context=this;

    private String avatar_resource_path_string;
    public String check_for_upload_avatar_path_string;

    public Uri user_avatar_pic_uri;

    public String username_define;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseRef;


    private StorageReference mStorageRef;

    private String temp_email ;
    private String temp_password;
    private String temp_profilePic;

    int currentID;
    int i=0;
    View v_temp;
    ID temp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar_screen);

        mAuth =FirebaseAuth.getInstance();
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseRef = firebaseDatabase.getReference(mAuth.getUid());


        _username=(EditText) findViewById(R.id.username_define);

        view_pager=(ViewPager) findViewById(R.id.view_pager);
        _adapter=new SlideAdapter(this);
        view_pager.setAdapter(_adapter);



//ToDo still not working counter keeps on incrementing on its own
        view_pager.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {


                if(scrollX > oldScrollX && (Math.abs(scrollX-oldScrollX)>0.5)){
                        v_temp=v;
                        i++;
                        return;
                    }
                    if(scrollX < oldScrollX && (Math.abs(scrollX-oldScrollX)>0.5)){
                        v_temp=v;
                        i--;
                        return;
                    }

                return;
            }
        });
        temp=setUpID(v_temp,i);

        //Validate Button handling
        validate=(ImageButton) findViewById(R.id.is_selected);
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentID= temp.getId();
                switch (currentID) {
                    case 0: {
                        avatar_resource_path_string =getURLForResource(R.drawable.fighterman);
                        break;
                    }
                    case 1: {
                        avatar_resource_path_string =getURLForResource(R.drawable.fighterwoman);
                        break;
                    }
                    case 2: {
                        avatar_resource_path_string =getURLForResource(R.drawable.collectorman);
                        break;
                    }
                    case 3: {
                        avatar_resource_path_string =getURLForResource(R.drawable.collectorwoman);
                        break;
                    }
                    case 4: {
                        avatar_resource_path_string =getURLForResource(R.drawable.crafterman);
                        break;
                    }
                    case 5: {
                        avatar_resource_path_string =getURLForResource(R.drawable.craftwomantwo);
                        break;
                    }
                }
                user_avatar_pic_uri =Uri.parse(avatar_resource_path_string);
                username_define =_username.getText().toString().trim();
                if(username_define.equals("")){
                    _username.setError("Username required");
                    _username.requestFocus();
                    return;
                }
                uploadAvatarToFirebase();
                saveUserInformation();
                finish();
                startActivity(new Intent(_context, Mainmenu.class));

            }
        });

    }
    private ID setUpID(View v, int id){
            return temp=new ID(v,id);
    }

    private void saveUserInformation(){

        temp_email= this.getIntent().getExtras().getString("passed_email");
        temp_password=this.getIntent().getExtras().getString("passed_password");
        temp_profilePic=this.getIntent().getExtras().getString("passed_profilePic");

        UserProfile userProfile=new UserProfile();

        userProfile.setUserprofilepic(temp_profilePic);
        userProfile.setUseremail(temp_email);
        userProfile.setUserpassword(temp_password);
        userProfile.setUsername(username_define);
        userProfile.setUseravatarpic(avatar_resource_path_string);
        userProfile.setUseravatarname(""+temp.getId());

        databaseRef.setValue(userProfile); //second step updating user profile
    }

    private void uploadAvatarToFirebase() {
        mStorageRef = FirebaseStorage.getInstance().getReference("Avatar/"+mAuth.getUid()+".jpg");
        if(user_avatar_pic_uri !=null) {
            mStorageRef.putFile(user_avatar_pic_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        }
    }

    public String getURLForResource (int resourceId) {
        return Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +resourceId).toString();
    }

}

