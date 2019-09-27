package com.example.jst;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.Toolbar;

public class YearActivity extends AppCompatActivity {

    private Button ene;
    private Button feb;
    private Button mar;
    private Button abr;
    private Button may;
    private Button jun;
    private Button jul;
    private Button ago;
    private Button sep;
    private Button oct;
    private Button nov;
    private Button dec;
    private Button todos;
    private Toolbar mToolbar;
    private String year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year);

        year = getIntent().getExtras().get("year").toString();


        mToolbar = findViewById(R.id.year_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("J & S TRANSPORT");

        ene = findViewById(R.id.january);
        feb = findViewById(R.id.february);
        mar = findViewById(R.id.march);
        abr = findViewById(R.id.april);
        may = findViewById(R.id.may);
        jun = findViewById(R.id.june);
        jul = findViewById(R.id.july);
        ago = findViewById(R.id.august);
        sep = findViewById(R.id.september);
        oct = findViewById(R.id.october);
        nov = findViewById(R.id.november);
        dec = findViewById(R.id.december);
        todos = findViewById(R.id.FullYear);

        ene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent monthIntent = new Intent(YearActivity.this, NewActivity.class);
                monthIntent.putExtra("month", "Enero");
                monthIntent.putExtra("year",year );
                startActivity(monthIntent);
            }
        });
        feb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent monthIntent = new Intent(YearActivity.this, NewActivity.class);
                monthIntent.putExtra("month", "Febrero");
                monthIntent.putExtra("year",year );
                startActivity(monthIntent);
            }
        });
        mar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent monthIntent = new Intent(YearActivity.this, NewActivity.class);
                monthIntent.putExtra("month", "Marzo");
                monthIntent.putExtra("year",year );
                startActivity(monthIntent);
            }
        });
        abr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent monthIntent = new Intent(YearActivity.this, NewActivity.class);
                monthIntent.putExtra("month", "Abril");
                monthIntent.putExtra("year",year );
                startActivity(monthIntent);
            }
        });
        may.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent monthIntent = new Intent(YearActivity.this, NewActivity.class);
                monthIntent.putExtra("month", "Mayo");
                monthIntent.putExtra("year",year );
                startActivity(monthIntent);
            }
        });
        jun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent monthIntent = new Intent(YearActivity.this, NewActivity.class);
                monthIntent.putExtra("month", "Junio");
                monthIntent.putExtra("year",year );
                startActivity(monthIntent);
            }
        });
        jul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent monthIntent = new Intent(YearActivity.this, NewActivity.class);
                monthIntent.putExtra("month", "Julio");
                monthIntent.putExtra("year",year );
                startActivity(monthIntent);
            }
        });
        ago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent monthIntent = new Intent(YearActivity.this, NewActivity.class);
                monthIntent.putExtra("month", "Agosto");
                monthIntent.putExtra("year",year );
                startActivity(monthIntent);
            }
        });
        sep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent monthIntent = new Intent(YearActivity.this, NewActivity.class);
                monthIntent.putExtra("month", "Septiembre");
                monthIntent.putExtra("year",year );
                startActivity(monthIntent);
            }
        });
        oct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent monthIntent = new Intent(YearActivity.this, NewActivity.class);
                monthIntent.putExtra("month", "Octubre");
                monthIntent.putExtra("year",year );
                startActivity(monthIntent);
            }
        });
        nov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent monthIntent = new Intent(YearActivity.this, NewActivity.class);
                monthIntent.putExtra("month", "Noviembre");
                monthIntent.putExtra("year",year );
                startActivity(monthIntent);
            }
        });
        dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent monthIntent = new Intent(YearActivity.this, NewActivity.class);
                monthIntent.putExtra("month", "Diciembre");
                monthIntent.putExtra("year",year );
                startActivity(monthIntent);
            }
        });
        todos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent monthIntent = new Intent(YearActivity.this, NewActivity.class);
                monthIntent.putExtra("month", "NA");
                monthIntent.putExtra("year",year );
                startActivity(monthIntent);
            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id= item.getItemId();
        if(id == android.R.id.home)
        {
            SendUserToMainActivity();

        }
        return super.onOptionsItemSelected(item);
    }

    private void SendUserToMainActivity()
    {
        Intent mainIntent = new Intent (YearActivity.this,MainActivity.class );
        startActivity(mainIntent);
    }
}