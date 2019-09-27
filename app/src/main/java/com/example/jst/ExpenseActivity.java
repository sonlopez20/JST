package com.example.jst;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ExpenseActivity extends AppCompatActivity {

    private ImageButton money;
    private EditText title;
    private EditText amount;
    private EditText description;
    private EditText month;
    private EditText day;
    private EditText year;
    private Button add;
    private Toolbar mToolbar;
    private ProgressDialog loadingbar;
    private Uri ImageUri;

    private StorageReference receipts;
    private DatabaseReference yexpense;
    private DatabaseReference mexpense;
    private DatabaseReference aexpense;
    private DatabaseReference yaexpense;
    private FirebaseAuth mAuth;

    private String current_user_id;
    private String Smoney;
    private String Stitle;
    private String Samount;
    private String Sdescription;
    private String Smonth;
    private String Sday;
    private String Syear;
    private String Eyear;
    private String Emonth;
    private String saveCurrentDate;
    private String saveCurrentTime;
    private String postRandomName;
    private String postName;
    private String downloadUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        mAuth = FirebaseAuth.getInstance();
        current_user_id = mAuth.getCurrentUser().getUid();

        Eyear = getIntent().getExtras().get("year").toString();
        Emonth = getIntent().getExtras().get("month").toString();

        receipts = FirebaseStorage.getInstance().getReference().child("Receipt Images");
        yexpense = FirebaseDatabase.getInstance().getReference().child("Users").child(current_user_id).child(Eyear).child("Expenses");
        yaexpense = FirebaseDatabase.getInstance().getReference().child("Users").child(current_user_id).child(Eyear).child("All");
        mexpense = FirebaseDatabase.getInstance().getReference().child("Users").child(current_user_id).child(Eyear).child(Emonth).child("Expenses");
        aexpense = FirebaseDatabase.getInstance().getReference().child("Users").child(current_user_id).child(Eyear).child(Emonth).child("All");


        mToolbar = findViewById(R.id.expense_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Crear Gasto");

        money = (ImageButton) findViewById(R.id.money);
        title = (EditText) findViewById(R.id.title);
        amount = (EditText) findViewById(R.id.amount);
        description = (EditText) findViewById(R.id.description);
        month = (EditText) findViewById(R.id.month);
        day = (EditText) findViewById(R.id.day);
        year = (EditText) findViewById(R.id.year);
        add = (Button) findViewById(R.id.add);

        loadingbar = new ProgressDialog(this);

        money.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                OpenGallery();
            }
        });

        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ValidatePost();
            }
        });
    }

    private void OpenGallery()
    {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if(resultCode == RESULT_OK)
            {
                loadingbar.setTitle("Nuevo Gasto");
                loadingbar.setMessage("Por favor espere mientras ajustamos la imagen...");
                loadingbar.show();
                loadingbar.setCanceledOnTouchOutside(true);

                ImageUri = result.getUri();
                money.setImageURI(ImageUri);

                loadingbar.dismiss();
            }

        }

    }

    private void ValidatePost()
    {
        Stitle = title.getText().toString();
        Samount = amount.getText().toString();
        Sdescription = description.getText().toString();
        Smonth = month.getText().toString();
        Sday = day.getText().toString();
        Syear = year.getText().toString();

        if(ImageUri == null)
        {
            Toast.makeText(this, "Por favor seleccione su imagen...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Stitle))
        {
            Toast.makeText(this, "Por favor escriba un titulo...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Samount))
        {
            Toast.makeText(this, "Por favor ingrese cantidad...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Sdescription))
        {
            Toast.makeText(this, "Por favor escriba una descripción...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Smonth))
        {
            Toast.makeText(this, "Por favor escriba que mes...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Sday))
        {
            Toast.makeText(this, "Por favor escriba que día...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Syear))
        {
            Toast.makeText(this, "Por favor escriba que año...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingbar.setTitle("Agregando nuevo gasto");
            loadingbar.setMessage("Por favor espere mientras guardamos el nuevo gasto");
            loadingbar.show();
            loadingbar.setCanceledOnTouchOutside(true);
            StoringImageToFirebaseStorage();
        }
    }

    private void StoringImageToFirebaseStorage()
    {
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());
        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        saveCurrentTime = currentTime.format(calForTime.getTime());

        postRandomName = saveCurrentDate + saveCurrentTime;
        StorageReference filePath = receipts.child(ImageUri.getLastPathSegment() + postRandomName + ".jpg");

        filePath.putFile(ImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task)
            {
                if(task.isSuccessful())
                {
                    downloadUrl = task.getResult().getDownloadUrl().toString();
                    Toast.makeText(ExpenseActivity.this,"Imagen subio con exito...",Toast.LENGTH_SHORT);
                    SavingPostInfoToDatabase();
                }
                else
                {
                    String message = task.getException().getMessage();
                    Toast.makeText(ExpenseActivity.this,"Error occurrio: " + message,Toast.LENGTH_SHORT);
                }
            }
        });
    }

    private void SavingPostInfoToDatabase()
    {
        HashMap incomeMap = new HashMap();
        String temp = Smonth + "-" + Sday + "-" + year;

        postName = current_user_id + postRandomName;
        incomeMap.put("title",Stitle);
        incomeMap.put("amount",Samount);
        incomeMap.put("description",Sdescription);
        incomeMap.put("date",temp);
        incomeMap.put("category","Gasto");
        incomeMap.put("postImage",downloadUrl);

        yaexpense.child(postName).updateChildren(incomeMap);
        aexpense.child(postName).updateChildren(incomeMap);
        mexpense.child(postName).updateChildren(incomeMap);
        yexpense.child(postName).updateChildren(incomeMap)
                .addOnCompleteListener(new OnCompleteListener()
                {
                    @Override
                    public void onComplete(@NonNull Task task)
                    {
                        if (task.isSuccessful())
                        {
                            SendUserToMainActivity();
                            Toast.makeText(ExpenseActivity.this, "Nuevo gasto fue añadido con exito",Toast.LENGTH_SHORT);
                            loadingbar.dismiss();
                        }
                        else
                        {
                            Toast.makeText(ExpenseActivity.this, "Error occurrio añadiendo el nuevo gasto ",Toast.LENGTH_SHORT);
                            loadingbar.dismiss();
                        }
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
        Intent mainIntent = new Intent (ExpenseActivity.this,MainActivity.class );
        startActivity(mainIntent);
    }
}