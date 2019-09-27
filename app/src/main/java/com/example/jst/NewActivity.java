package com.example.jst;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;

public class NewActivity extends AppCompatActivity {
    private String year;
    private String month;
    private ImageView money;
    private ImageView card;
    private ImageView graph;
    private Button income;
    private Button payment;
    private Button report;
    private Toolbar mToolbar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        year = getIntent().getExtras().get("year").toString();
        month = getIntent().getExtras().get("month").toString();

        money = (ImageView) findViewById(R.id.money);
        card = (ImageView) findViewById(R.id.card);
        graph = (ImageView) findViewById(R.id.graph);
        income = (Button) findViewById(R.id.income);
        payment = (Button) findViewById(R.id.payment);
        report = (Button) findViewById(R.id.report);

        mToolbar = (Toolbar) findViewById(R.id.new_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);;
        getSupportActionBar().setTitle("J & S TRANSPORT");

        income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendtoIncome();
            }
        });
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendtoPayment();
            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendtoReport();
            }
        });
    }

    private void SendtoPayment()
    {
        Intent paymentIntent = new Intent(NewActivity.this, ExpenseActivity.class);
        paymentIntent.putExtra("month", month);
        paymentIntent.putExtra("year",year );
        startActivity(paymentIntent);
    }
    private void SendtoReport()
    {
        Intent reportIntent = new Intent(NewActivity.this, ReportActivity.class);
        reportIntent.putExtra("month", month);
        reportIntent.putExtra("year",year );
        startActivity(reportIntent);
    }
    private void SendtoIncome()
    {
        Intent incomeIntent = new Intent(NewActivity.this, IncomeActivity.class);
        incomeIntent.putExtra("month", month);
        incomeIntent.putExtra("year",year );
        startActivity(incomeIntent);
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
        Intent mainIntent = new Intent (NewActivity.this,MainActivity.class );
        startActivity(mainIntent);
    }
}