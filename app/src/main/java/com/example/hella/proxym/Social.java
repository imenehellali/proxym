package com.example.hella.proxym;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hella.proxym.Util.ItemClickListener;
import com.example.hella.proxym.Util.UserProfile;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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
import com.squareup.picasso.Picasso;

public class Social extends AppCompatActivity {




    private TabLayout social_tabs;
    private LinearLayout fragmentProfile, fragmentUser, fragmentChat, fragmentQuest;

    private TextView EMAIL, USERNAME, AVATARNAME;
    private ImageView PROFILEPIC, AVATARPIC;

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mRef, onlineRef, currentRef, counterRef, locations;
    private FirebaseUser user;

    private FirebaseRecyclerAdapter<UserProfile, ListOnlineViewHolder> adapter;


    private RecyclerView listOnline;
    private RecyclerView.LayoutManager layoutManager;

    private UserProfile TUP;
    private String useremail, userpassword, userprofilepic, userstatus, username, useravatarpic, useravatarname, userlat, userlng;

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

        TUP=(UserProfile) Social.this.getIntent().getExtras().getParcelable("USERPROFILE3");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.social);


        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        mRef = firebaseDatabase.getReference(user.getUid());

        onlineRef = FirebaseDatabase.getInstance().getReference().child(".info/connected");
        counterRef = FirebaseDatabase.getInstance().getReference("LastOnline");
        //creating current user folder in data base that is dynamically filled and emptied with help from onlineRef
        try {
            currentRef = FirebaseDatabase.getInstance().getReference("LastOnline").child(user.getUid());
        } catch (Exception e) {
            Toast.makeText(Social.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        //creating LastOnline folder with Uid fileNames


        EMAIL = (TextView) findViewById(R.id.EMAIL);
        USERNAME = (TextView) findViewById(R.id.USERNAME);
        AVATARNAME = (TextView) findViewById(R.id.AVATARNAME);

        PROFILEPIC = (ImageView) findViewById(R.id.PROFILEPIC);
        AVATARPIC = (ImageView) findViewById(R.id.AVATARPIC);


        fragmentProfile = (LinearLayout) findViewById(R.id.fragmentProfile);
        fragmentUser = (LinearLayout) findViewById(R.id.fragmentUser);
        fragmentChat = (LinearLayout) findViewById(R.id.fragmentChat);
        fragmentQuest = (LinearLayout) findViewById(R.id.fragmentQuest);


        listOnline = (RecyclerView) findViewById(R.id.listOnline);
        listOnline.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(Social.this);
        listOnline.setLayoutManager(layoutManager);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);

                    useremail=userProfile.getUseremail();
                    userpassword=userProfile.getUserpassword();
                    userprofilepic=userProfile.getUserprofilepic();
                    userstatus=userProfile.getUserstatus();
                    username=userProfile.getUsername();
                    useravatarpic=userProfile.getUseravatarpic();
                    useravatarname=userProfile.getUseravatarname();


                    USERNAME.setText("Username: " + username);
                    EMAIL.setText("Email: " + useremail);
                    AVATARNAME.setText("Avatar: " + useravatarname);
                    AVATARPIC.setImageResource(getResourceFromString(useravatarpic));

                //ToDo cannot find the pic we uploaded Oo
                StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
                mStorageRef.child(userProfile.getUserprofilepic()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).fit().centerCrop().into(PROFILEPIC);
                    }
                });

                onlineRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue(Boolean.class)) {
                            currentRef.onDisconnect().removeValue();
                            counterRef.child(user.getUid()).setValue(userProfile);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(Social.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
                counterRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Social.this, "" + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
        try {
            FirebaseRecyclerOptions<UserProfile> options = new FirebaseRecyclerOptions.Builder<UserProfile>().setQuery(counterRef, UserProfile.class).build();
            adapter = new FirebaseRecyclerAdapter<UserProfile, ListOnlineViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull ListOnlineViewHolder holder, int position, @NonNull final UserProfile model) {
                    holder.txt_email.setText(model.getUsername());
                    holder.itemClickListener= new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            if(!model.getUseremail().equals(user.getEmail())){
                                Intent map=new Intent(Social.this, Mainmenu.class);
                                map.putExtra("OTHERUSER", model);//one name for same user USERPROFILE2 and for other users one name OTHERUSER
                                map.putExtra("USERPROFILE2",TUP);
                                startActivity(map);
                            }
                        }
                    };
                    //ToDo add profile pic instead of online pic with picasso and same as above after figuring out the path
                }

                @NonNull
                @Override
                public ListOnlineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_layout, parent, false);
                    return new ListOnlineViewHolder(view);
                }
            };
            adapter.notifyDataSetChanged();
            listOnline.setAdapter(adapter);
        } catch (Exception e) {
            Toast.makeText(Social.this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
        }



        social_tabs = (TabLayout) findViewById(R.id.social_tabs);

        social_tabs.addTab(social_tabs.newTab(), 0);
        social_tabs.addTab(social_tabs.newTab(), 1);
        social_tabs.addTab(social_tabs.newTab(), 2);
        social_tabs.addTab(social_tabs.newTab(), 3);


        social_tabs.getTabAt(0).setIcon(R.drawable.profile);
        social_tabs.getTabAt(1).setIcon(R.drawable.user);
        social_tabs.getTabAt(2).setIcon(R.drawable.chat_double);
        social_tabs.getTabAt(3).setIcon(R.drawable.quest);


        social_tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0: {
                        fragmentProfile.setVisibility(View.VISIBLE);
                        fragmentChat.setVisibility(View.GONE);
                        fragmentQuest.setVisibility(View.GONE);
                        fragmentUser.setVisibility(View.GONE);
                        break;

                    }
                    case 1: {
                        fragmentProfile.setVisibility(View.GONE);
                        fragmentChat.setVisibility(View.GONE);
                        fragmentQuest.setVisibility(View.GONE);
                        fragmentUser.setVisibility(View.VISIBLE);
                        break;

                    }
                    case 2: {
                        fragmentProfile.setVisibility(View.GONE);
                        fragmentChat.setVisibility(View.VISIBLE);
                        fragmentQuest.setVisibility(View.GONE);
                        fragmentUser.setVisibility(View.GONE);
                        break;

                    }
                    case 3: {
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
                switch (tab.getPosition()) {
                    case 0: {
                        fragmentProfile.setVisibility(View.VISIBLE);
                        fragmentChat.setVisibility(View.GONE);
                        fragmentQuest.setVisibility(View.GONE);
                        fragmentUser.setVisibility(View.GONE);
                        break;

                    }
                    case 1: {
                        fragmentProfile.setVisibility(View.GONE);
                        fragmentChat.setVisibility(View.GONE);
                        fragmentQuest.setVisibility(View.GONE);
                        fragmentUser.setVisibility(View.VISIBLE);
                        break;

                    }
                    case 2: {
                        fragmentProfile.setVisibility(View.GONE);
                        fragmentChat.setVisibility(View.VISIBLE);
                        fragmentQuest.setVisibility(View.GONE);
                        fragmentUser.setVisibility(View.GONE);
                        break;

                    }
                    case 3: {
                        fragmentProfile.setVisibility(View.GONE);
                        fragmentChat.setVisibility(View.GONE);
                        fragmentQuest.setVisibility(View.VISIBLE);
                        fragmentUser.setVisibility(View.GONE);
                        break;

                    }
                }

            }
        });

    }

    public int getResourceFromString(String imagePath) {
        String intString = imagePath.replace("android.resource://" + R.class.getPackage().getName() + "/", "").trim();
        return (int) Integer.parseInt(intString);
    }

}

