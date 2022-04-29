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

import com.example.finalyearproject.Mentees.MenteeMainActivity;
import com.example.finalyearproject.Mentor.MentorMainActivity;
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

public class Login_Activity extends AppCompatActivity {

    private TextView mNoAccount, mForgotPassword, mTotalMentorships, mTotalUsers;
    private TextInputEditText mLoginEmail, mLoginPassword;
    private Button mLoginButton;
    private FirebaseAuth firebaseAuth;
    private Boolean emailAddressCheckers;
    private DatabaseReference TotalMentorships, TotalUsers;
    int Mentor;
    int Mentee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginEmail = findViewById(R.id.LoginEmail);
        mLoginPassword = findViewById(R.id.LoginPassword);
        mLoginButton = findViewById(R.id.LoginButton);
        mNoAccount = findViewById(R.id.noAccount);
        mForgotPassword = findViewById(R.id.forgotPassword);
        firebaseAuth = FirebaseAuth.getInstance();
        mTotalMentorships = findViewById(R.id.totalMentorships);
        mTotalUsers = findViewById(R.id.totalUsers);

        TotalMentorships = FirebaseDatabase.getInstance().getReference().child("Mentorship");
        TotalUsers = FirebaseDatabase.getInstance().getReference().child("users");

        //User will be brought to registration
        mNoAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SendUserToRegisterActivity();
            }
        });

        //User can reset password
        mForgotPassword.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Login_Activity.this, ResetPassword.class));
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AllowingUserToLogin();
            }
        });

        TotalMentorships.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                int counter = (int) snapshot.getChildrenCount();
                String userCounter = String.valueOf(counter);

                mTotalMentorships.setText(userCounter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        TotalUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    switch(dataSnapshot.child("type").getValue(String.class)){ //This statement is seeing what "category" is.
                        case "Mentor":
                            ++Mentor;
                            break;
                        case "Mentee":
                            ++Mentee;
                            break;
                    }
                    int totalUsers = Mentor + Mentee;
                    mTotalUsers.setText(String.valueOf(totalUsers));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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

            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful()) {

                                //Checks to ensure email is verified
                                VerifyEmailAddress();
                            }
                            else
                            {

                                Toast.makeText(Login_Activity.this, "Error occurred " , Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
    }

    private void VerifyEmailAddress()
    {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        emailAddressCheckers = user.isEmailVerified();

        //If email is verified continue
        if(emailAddressCheckers)
        {

            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            String RegisteredUserID = currentUser.getUid();
            DatabaseReference dr = FirebaseDatabase.getInstance().getReference().child("users").child(RegisteredUserID);

            //Checks to see if user is a mentor or mentee
            dr.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String type = snapshot.child("type").getValue().toString();

                    if (type.equals("Mentor")) {

                        Toast.makeText(Login_Activity.this, "Mentor log in successful", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Login_Activity.this, MentorMainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);

                    } else if (type.equals("Mentee")) {

                        Toast.makeText(Login_Activity.this, "Mentee log in successful", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Login_Activity.this, MenteeMainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }

                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        else
        {
            Toast.makeText(this, "Please verify your Account first... ", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }


    private void SendUserToRegisterActivity()
    {
        Intent registerIntent = new Intent(Login_Activity.this, Selection_Activity.class);
        startActivity(registerIntent);
    }


}