package com.example.hella.proxym;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.hella.proxym.Util.UserProfile;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PopupScreen extends AppCompatActivity {
    private Button attack_button, ask_for_resource_button;
    private LinearLayout ask_for_resource_layout;
    private int width, height;
    private UserProfile TUP_OTHER;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        TUP_OTHER=(UserProfile)this.getIntent().getExtras().getParcelable("OTHER_USER");

        setContentView(R.layout.popupscreen);
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        LinearLayout layout=(LinearLayout) findViewById(R.id.popup_layout);


        width=dm.widthPixels;
        height=dm.heightPixels;

        getWindow().setLayout((int)(width*0.248), (int)(height*0.1));

        ask_for_resource_layout=(LinearLayout) findViewById(R.id.ask_resource_layout);

        attack_button=(Button)findViewById(R.id.attack_button);
        ask_for_resource_button=(Button)findViewById(R.id.ask_for_resource_button);

        ask_for_resource_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setLayout((int)(width*0.4), (int)(height*0.2));
                ask_for_resource_layout.setVisibility(View.VISIBLE);

                ImageView animal=(ImageView) findViewById(R.id.animal);
                ImageView iron=(ImageView) findViewById(R.id.iron);
                ImageView leaf=(ImageView) findViewById(R.id.leaf);
                ImageView bomb=(ImageView) findViewById(R.id.bomb);

                animal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TUP_OTHER.addAskedResources("AnimalResource", TUP_OTHER.getUserprofilepic().substring(9));

                        Toast.makeText(PopupScreen.this,TUP_OTHER.getUserprofilepic().substring(9), Toast.LENGTH_LONG).show();

                        DatabaseReference otherRef=FirebaseDatabase.getInstance().getReference(TUP_OTHER.getUserprofilepic().substring(9));

                        Log.d("USER INFO", TUP_OTHER.getUsereaskedresources().get(0));

                        otherRef.setValue(TUP_OTHER);
                        finish();
                    }
                }); iron.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TUP_OTHER.addAskedResources("Leaf",TUP_OTHER.getUserprofilepic().substring(9));
                        Toast.makeText(PopupScreen.this,TUP_OTHER.getUserprofilepic().substring(9), Toast.LENGTH_LONG).show();

                        DatabaseReference otherRef=FirebaseDatabase.getInstance().getReference(TUP_OTHER.getUserprofilepic().substring(9));

                        Log.d("USER INFO", TUP_OTHER.getUsereaskedresources().get(0));
                        otherRef.setValue(TUP_OTHER);
                        finish();
                    }
                });leaf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TUP_OTHER.addAskedResources("Iron",TUP_OTHER.getUserprofilepic().substring(9));
                        Toast.makeText(PopupScreen.this,TUP_OTHER.getUserprofilepic().substring(9), Toast.LENGTH_LONG).show();

                        DatabaseReference otherRef=FirebaseDatabase.getInstance().getReference(TUP_OTHER.getUserprofilepic().substring(9));

                        Log.d("USER INFO", TUP_OTHER.getUsereaskedresources().get(0));
                        otherRef.setValue(TUP_OTHER);
                        finish();
                    }
                }); bomb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TUP_OTHER.addAskedResources("Bomb",TUP_OTHER.getUserprofilepic().substring(9));
                        Toast.makeText(PopupScreen.this,TUP_OTHER.getUserprofilepic().substring(9), Toast.LENGTH_LONG).show();

                        DatabaseReference otherRef=FirebaseDatabase.getInstance().getReference(TUP_OTHER.getUserprofilepic().substring(9));

                        Log.d("USER INFO", TUP_OTHER.getUsereaskedresources().get(0));
                        otherRef.setValue(TUP_OTHER);
                        finish();
                    }
                });

            }
        });

    }
}
