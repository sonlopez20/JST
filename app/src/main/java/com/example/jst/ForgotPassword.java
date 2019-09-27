package com.example.jst;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

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

public class ForgotPassword extends AppCompatActivity {

    private Toolbar toolbar;

    private EditText userInput;
    private Button button;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mAuth = FirebaseAuth.getInstance();

        userInput = findViewById(R.id.forgot_password_input);
        button = findViewById(R.id.forgot_password_button);
        toolbar = findViewById(R.id.forgot_password_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Cambiar contraseña");


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String email = userInput.getText().toString();

                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(ForgotPassword.this, "Por favor ingrese correo electronico", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(ForgotPassword.this, "Correo electronico fue enviado para cambiar su contraseña", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ForgotPassword.this, LoginActivity.class));
                            }
                            else
                            {
                                String error = task.getException().getMessage();
                                Toast.makeText(ForgotPassword.this, "Error Occurrio: " + error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}
