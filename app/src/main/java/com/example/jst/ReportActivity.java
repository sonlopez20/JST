package com.example.jst;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReportActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private RecyclerView postList;

    private CircleImageView NavProfileImage;
    private TextView NavUserName;

    private FirebaseAuth mAuth;
    private DatabaseReference Expensesref;
    private DatabaseReference Incomeref;
    private DatabaseReference userRef;
    private DatabaseReference Allref;

    private String current_user_id;
    private String year;
    private String month;
    private long num;
    private int calc_I = 0;
    private int calc_G = 0;
    private int calc_Gana = 0;

    private TextView ganacia;
    private TextView calc_Profit;
    private TextView gastos;
    private TextView calc_Expenses;
    private TextView ingresos;
    private TextView calc_Income;
    private Toolbar mToolbar;
    private RecyclerView yearsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        mAuth = FirebaseAuth.getInstance();
        current_user_id = mAuth.getCurrentUser().getUid();

        year = getIntent().getExtras().get("year").toString();
        month = getIntent().getExtras().get("year").toString();

        userRef = FirebaseDatabase.getInstance().getReference().child("Users");

        mToolbar = findViewById(R.id.report_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("J & S TRANSPORT");

        /*yearsList = (RecyclerView) findViewById(R.id.years);
        yearsList.setHasFixedSize(true);
        yearsList.setLayoutManager(new LinearLayoutManager(this));*/

        yearsList = (RecyclerView) findViewById(R.id.yearsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        yearsList.setLayoutManager(linearLayoutManager);

        if (month.equals("NA")) {
            Incomeref = FirebaseDatabase.getInstance().getReference().child("Users").child(current_user_id).child(year).child("Income");
            Expensesref = FirebaseDatabase.getInstance().getReference().child("Users").child(current_user_id).child(year).child("Expenses");
            Allref = FirebaseDatabase.getInstance().getReference().child("Users").child(current_user_id).child(year).child("All");

        }
        else {
            Incomeref = FirebaseDatabase.getInstance().getReference().child("Users").child(current_user_id).child(year).child(month).child("Income");
            Expensesref = FirebaseDatabase.getInstance().getReference().child("Users").child(current_user_id).child(year).child(month).child("Expenses");
            Allref = FirebaseDatabase.getInstance().getReference().child("Users").child(current_user_id).child(year).child(month).child("All");
        }
    }

    protected void onStart() {
        super.onStart();
        final FirebaseRecyclerAdapter<Years, YearsViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Years, YearsViewHolder>
                (
                        Years.class,
                        R.layout.layout_show,
                        YearsViewHolder.class,
                        Allref
                )
        {
            @Override
            protected void populateViewHolder(YearsViewHolder viewHolder, final Years model, int position) {
               Allref.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {
                       num = dataSnapshot.getChildrenCount();
                   }
                   @Override
                   public void onCancelled(DatabaseError databaseError) {
                   }
               });

                int i=0;
                while(i < num)
                {
                    viewHolder.setPostImage(model.getPostimage());
                    viewHolder.setTitle(model.getTitle());
                    viewHolder.setAmount(model.getAmount());
                    viewHolder.setDesc(model.getDescription());
                    viewHolder.setDate(model.getDate());
                    viewHolder.setCategory(model.getCategory());
                    viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent profileIntent = new Intent(ReportActivity.this, ClickActivity.class);
                            profileIntent.putExtra("postName", model.getUid());
                            startActivity(profileIntent);
                        }
                    });

                    if(model.getCategory().equals("Ingreso"))
                    {
                        calc_I = calc_I + Integer.parseInt(model.getAmount());
                    }
                    else if (model.getCategory().equals("Gastos"))
                    {
                        calc_G = calc_G + Integer.parseInt(model.getAmount());
                    }
                }
                calc_Income = (TextView) findViewById(R.id.calc_Income);
                calc_Income.setText(calc_I);
                calc_Expenses = (TextView) findViewById(R.id.calc_Expenses);
                calc_Expenses.setText(calc_G);
                calc_Gana = calc_I - calc_G;
                calc_Profit = (TextView) findViewById(R.id.calc_Profit);
                calc_Profit.setText(calc_Gana);
            }
        };
        yearsList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class YearsViewHolder extends ViewHolder {
        View mView;

        public YearsViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setDesc(String desc) {
            TextView view_desc = (TextView) mView.findViewById(R.id.view_description);
            view_desc.setText(desc);
        }

        public void setPostImage(String postImage) {
            ImageView view_image = (ImageView) mView.findViewById(R.id.view_image);
            Picasso.get().load(postImage).into(view_image);
        }

        public void setDate(String date) {
            TextView post_date = (TextView) mView.findViewById(R.id.view_date);
            post_date.setText(date);
        }
        public void setAmount(String amount) {
            TextView view_amount = (TextView) mView.findViewById(R.id.view_amount);
            view_amount.setText("$" + amount);
        }
        public void setTitle(String title) {
            TextView view_title = (TextView) mView.findViewById(R.id.view_title);
            view_title.setText(title);
        }
        public void setCategory(String category) {
            TextView view_cate = (TextView) mView.findViewById(R.id.view_category);
            view_cate.setText(category);
        }
    }
}

