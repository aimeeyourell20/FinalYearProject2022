package com.example.finalyearproject.Mentor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.finalyearproject.Login_Activity;
import com.example.finalyearproject.Mentees.Mentee_Registration_Activity;
import com.example.finalyearproject.R;
import com.example.finalyearproject.Welcome_Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class Mentor_Registration_Activity extends AppCompatActivity {


    private TextInputEditText mRegisterEmail, mRegisterName, mRegisterPassword;
    private Button mMenteeSignUpButton, mBackButton;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference RootRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_registration);

        mRegisterEmail = findViewById(R.id.registerEmail);
        mRegisterName = findViewById(R.id.registerName);
        mRegisterPassword = findViewById(R.id.registerPassword);
        mMenteeSignUpButton = findViewById(R.id.mentorSignUpButton);

        firebaseAuth = FirebaseAuth.getInstance();

        mBackButton = findViewById(R.id.backButton1);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentor_Registration_Activity.this, Welcome_Activity.class);
                startActivity(i);
                finish();
            }
        });

        mMenteeSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupUser();
            }
        });
    }


    private void signupUser() {
        String email = mRegisterEmail.getText().toString().trim();
        String password = mRegisterPassword.getText().toString().trim();
        String name = mRegisterName.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            mRegisterEmail.setError("Email required");
            return;
        }
        if (TextUtils.isEmpty(name)) {
            mRegisterName.setError("Fullname required");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            mRegisterPassword.setError("Password required");
            return;
        }
        if (password.length() < 6) {
            mRegisterPassword.setError("Password must be more than 6 characters");
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (!task.isSuccessful()) {
                    Toast.makeText(Mentor_Registration_Activity.this, "Registration unsuccessful", Toast.LENGTH_SHORT).show();
                } else {
                    String currentUser = firebaseAuth.getCurrentUser().getUid();
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    user.sendEmailVerification();
                    RootRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser);
                    HashMap mentee = new HashMap();
                    mentee.put("name", name);
                    mentee.put("email", email);
                    mentee.put("skill1", "");
                    mentee.put("skill2", "");
                    mentee.put("skill3", "");
                    mentee.put("skill4", "");
                    mentee.put("location", "");
                    mentee.put("language", "");
                    mentee.put("bio", "");
                    mentee.put("jobType", "");
                    mentee.put("industry", "");
                    mentee.put("industry2", "");
                    mentee.put("industry3", "");
                    mentee.put("company", "");
                    mentee.put("type", "Mentor");
                    mentee.put("all1", "all1");
                    mentee.put("profileimage", "");
                    mentee.put("AverageRating", 0);



                    RootRef.updateChildren(mentee).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Mentor_Registration_Activity.this, "User info successfully added", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Mentor_Registration_Activity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                            finish();
                        }
                    });

                }
            }
        });

        Intent i = new Intent(Mentor_Registration_Activity.this, Login_Activity.class);
        startActivity(i);
        finish();;
    }
}