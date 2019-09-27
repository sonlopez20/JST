package com.example.jst;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddYearActivity extends AppCompatActivity {


    private ImageView calendar;
    private EditText year_text;
    private Button add_year;
    private DatabaseReference yearRef;
    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    private String current_user_id;
    private String current_user;
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_year);

        calendar = (ImageView) findViewById(R.id.calendar);
        year_text = (EditText) findViewById(R.id.year_text);
        add_year = (Button) findViewById(R.id.add_year);

        mAuth = FirebaseAuth.getInstance();
        current_user = mAuth.getCurrentUser().getUid();
        Log.d(current_user,"currentUserAddYear");
        yearRef = FirebaseDatabase.getInstance().getReference().child("Users").child(current_user_id).child("Years");

        mToolbar = findViewById(R.id.add_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Añadir Año");

        loadingbar = new ProgressDialog(this);

        add_year.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ValidateYear();
            }
        });
    }

    private void ValidateYear()
    {
        String year = year_text.getText().toString();
        if(TextUtils.isEmpty(year))
        {
            Toast.makeText(this, "Por favor ingrese numero de año...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingbar.setTitle("Añadiendo año");
            loadingbar.setMessage("Por favor espere mientras añadimos el año");
            loadingbar.show();
            loadingbar.setCanceledOnTouchOutside(true);
            SavingYearToDatabase(year);
        }
    }
    private void SavingYearToDatabase(String year)
    {
        HashMap yMap = new HashMap();
        yMap.put("year",year);
        yearRef.updateChildren(yMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task)
            {
                if (task.isSuccessful())
                {
                    SendUserToMainActivity();
                    Toast.makeText(AddYearActivity.this, "Año fue añadido",Toast.LENGTH_SHORT);
                    loadingbar.dismiss();
                }
                else
                {
                    Toast.makeText(AddYearActivity.this, "Error occurrió",Toast.LENGTH_SHORT);
                    loadingbar.dismiss();
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        int id= item.getItemId();
        if(id == android.R.id.home)
        {
            SendUserToMainActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    private void SendUserToMainActivity()
    {
        Intent mainIntent = new Intent (AddYearActivity.this,MainActivity.class );
        startActivity(mainIntent);
    }
}
