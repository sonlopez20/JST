package com.example.jst;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.nio.charset.MalformedInputException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private RecyclerView yearsList;

    private ImageView NavProfileImage;
    private TextView NavUserName;
    private Button year20;
    private Button year19;
    private Button year18;
    private Button year17;
    private Button year16;
    private Toolbar mToolbar;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef, yearsRef, followingRef;

    ArrayList<String> followingUidList;
    private long num;

    String currentUserID;
    String TAG = "333";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        Log.d(currentUserID,"current");
        userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);

        mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);;
        //getSupportActionBar().setTitle("J & S TRANSPORT");

        year20 = (Button) findViewById(R.id.year20);
        year19 = (Button) findViewById(R.id.year19);
        year18 = (Button) findViewById(R.id.year18);
        year17 = (Button) findViewById(R.id.year17);
        year16 = (Button) findViewById(R.id.year16);



        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        View navView = navigationView.inflateHeaderView(R.layout.nav_header);
        NavProfileImage = (ImageView) navView.findViewById(R.id.nav_profile_image);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                UserMenuSelector(item);
                return false;
            }
        });

        year16.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SendtoMonths("2016");
            }
        });
        year17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendtoMonths("2017");
            }
        });
        year18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendtoMonths("2018");
            }
        });
        year19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendtoMonths("2019");
            }
        });
        year20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendtoMonths("2020");
            }
        });

    }

    private void SendtoMonths(String s)
    {
        Intent newIntent = new Intent(MainActivity.this,YearActivity.class);
        newIntent.putExtra("year", s);
        startActivity(newIntent);
    }

    private void sendUserToStartActivity() {
        Intent startIntent = new Intent(MainActivity.this, StartActivity.class);
        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(startIntent);
    }
    private void sendUserToAddYearActivity()
    {
        Intent addIntent = new Intent(MainActivity.this, AddYearActivity.class);
        startActivity(addIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void UserMenuSelector(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                //Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
                ;
                break;
            case R.id.nav_logout:
                mAuth.signOut();
                sendUserToStartActivity();
                break;

        }
    }
}
