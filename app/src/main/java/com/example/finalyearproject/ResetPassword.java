package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.finalyearproject.Mentees.Mentee_Registration_Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    private TextInputEditText mEmail;
    private Button mReset, mBackButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mEmail = findViewById(R.id.resetEmail);
        mReset = findViewById(R.id.resetButton);

        mAuth = FirebaseAuth.getInstance();

        mBackButton = findViewById(R.id.backButton1);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ResetPassword.this, Welcome_Activity.class);
                startActivity(i);
                finish();
            }
        });

        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(ResetPassword.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                }else{
                        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(ResetPassword.this, "Password Reset Email Sent", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(ResetPassword.this, Login_Activity.class);
                                    startActivity(i);
                                }
                                else{
                                    Toast.makeText(ResetPassword.this, "Error", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                }

            }
        });
    }
}