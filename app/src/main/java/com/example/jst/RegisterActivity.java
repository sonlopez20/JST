package com.example.jst;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText UserEmail, emailConfirm, UserPassword, passwordConfirm;
    private Button createAccount;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private DatabaseReference UsersRef;

    String currentUserID;
    Uri resultUri;
    final static int GallaryPick = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        UserEmail = findViewById(R.id.register_email);
        emailConfirm = findViewById(R.id.register_email2);
        UserPassword = findViewById(R.id.register_password);
        passwordConfirm = findViewById(R.id.register_password2);
        createAccount = findViewById(R.id.register_create_account);

        toolbar = findViewById(R.id.register_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Registrarse");

        loadingBar = new ProgressDialog(this);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                CreateNewAccount();

                FirebaseUser currentUser = mAuth.getCurrentUser();

                if(currentUser != null)
                {
                    sendUserToMainActivity();
                }
            }
        });
    }

    private void sendUserToMainActivity()
    {
        Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    private void CreateNewAccount()
    {
        String email = UserEmail.getText().toString();
        String email_confirm = emailConfirm.getText().toString();

        String password = UserPassword.getText().toString();
        String password_confirm = passwordConfirm.getText().toString();

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(RegisterActivity.this, "Por favor ingrese cuenta de correo electronico", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(email_confirm))
        {
            Toast.makeText(RegisterActivity.this, "Por favor vuelva a ingresar cuenta de correo electronico", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(RegisterActivity.this, "Por favor ingrese contraseña", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password_confirm))
        {
            Toast.makeText(RegisterActivity.this, "Por favor vuelva a ingresar contraseña", Toast.LENGTH_SHORT).show();
        }
        else if(!email.equals(email_confirm))
        {
            Toast.makeText(RegisterActivity.this, "Sus correos electronicos no son igual", Toast.LENGTH_SHORT).show();
        }
        else if(!password.equals(password_confirm))
        {
            Toast.makeText(RegisterActivity.this, "Sus contraseñas no son igual", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Creando Cuenta");
            loadingBar.setMessage("Por favor espere mientras le creamos su cuenta");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {
                                sendUserToSetupActivity();
                                Toast.makeText(RegisterActivity.this, "Su cuenta ha sido creada", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                            else
                            {
                                String message = task.getException().getMessage();
                                Toast.makeText(RegisterActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }
    }

    private void sendUserToSetupActivity()
    {
        Intent setupIntent = new Intent(RegisterActivity.this, SetupActivity.class);
        setupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(setupIntent);
        finish();
    }
}