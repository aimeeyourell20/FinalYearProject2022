package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Mentor_Login_Activity extends AppCompatActivity {

    private TextView mnoAccount, mforgotPassword;
    private TextInputEditText mLoginEmail, mLoginPassword;
    private Button mloginButton;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private Boolean emailAddressCheckers;
    private String usertype = "Mentor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_login);

        mLoginEmail = findViewById(R.id.LoginEmail);
        mLoginPassword = findViewById(R.id.LoginPassword);
        mloginButton = findViewById(R.id.LoginButton);
        mnoAccount = findViewById(R.id.noAccount);
        mforgotPassword = findViewById(R.id.forgotPassword);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        mnoAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SendUserToRegisterActivity();
            }
        });

       /* ForgetPasswordLink.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });*/

        mloginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AllowingUserToLogin();
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if(currentUser != null)
        {
            SendUserToMainActivity();
        }
    }

    private void AllowingUserToLogin()
    {
        String email = mLoginEmail.getText().toString();
        String password = mLoginPassword.getText().toString();

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please write your email...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        else
        {
/*
            loadingBar.setTitle("Login");
            loadingBar.setMessage("Please wait, while we are allowing you to login into your new Account...");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();
*/

            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful()) {
                                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                                String RegisteredUserID = currentUser.getUid();
                                DatabaseReference dr = FirebaseDatabase.getInstance().getReference().child("Users").
                                        child(RegisteredUserID);

                                dr.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String type = snapshot.child("type").getValue().toString();

                                        if (type.equals("Mentor")) {

                                            Toast.makeText(Mentor_Login_Activity.this, "Mentor log in successful", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(Mentor_Login_Activity.this, MentorMainActivity.class);
                                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(i);
                                            finish();

                                        } else if (type.equals("Mentee")) {

                                            Toast.makeText(Mentor_Login_Activity.this, "Mentee log in successful", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(Mentor_Login_Activity.this, MenteeMainActivity.class);
                                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(i);
                                            finish();
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                            else
                            {
                                String message = task.getException().getMessage();
                                Toast.makeText(Mentor_Login_Activity.this, "Error occured: " + message, Toast.LENGTH_SHORT).show();
                                //loadingBar.dismiss();
                            }
                        }
                    });
        }
    }

    private void VerifyEmailAddress()
    {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        emailAddressCheckers = user.isEmailVerified();

        if(emailAddressCheckers)
        {
            SendUserToMainActivity();
        }
        else
        {
            Toast.makeText(this, "Please verify your Account first... ", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }

    private void SendUserToMainActivity()
    {
        Intent mainIntent = new Intent(Mentor_Login_Activity.this, MentorMainActivity. class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

    private void SendUserToRegisterActivity()
    {
        Intent registerIntent = new Intent(Mentor_Login_Activity.this, Mentor_Registration_Activity.class);
        startActivity(registerIntent);
    }
}