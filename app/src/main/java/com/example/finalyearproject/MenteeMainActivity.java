package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.finalyearproject.Industry_Fragments.SearchIndustryFragment;
import com.example.finalyearproject.LocationReports.Mentee_Reports;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class MenteeMainActivity extends AppCompatActivity{

    private ImageView mentors, findMentor, settings, goals, career, cv, profile, logout, meeting, chatbot, reports;
    private TextView profileName, type;
    private DatabaseReference dr;
    private DatabaseReference user;
    private FirebaseAuth firebaseAuth;
    private ActionBarDrawerToggle toggle;
    private static int PICK_IMAGE = 123;
    private StorageReference storageReference;
    private String currentUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_main);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser().getUid();
        user = FirebaseDatabase.getInstance().getReference().child("users");

        mentors = findViewById(R.id.mentor);
        findMentor = findViewById(R.id.findMentor);
        settings = findViewById(R.id.settings);
        //messages = findViewById(R.id.messages);
        career = findViewById(R.id.careerFinder);
        cv = findViewById(R.id.resume);
        goals = findViewById(R.id.goals);
        profileName = findViewById(R.id.profilename);
        profile = findViewById(R.id.profileimage);
        type = findViewById(R.id.type);
        logout = findViewById(R.id.logout);
        meeting = findViewById(R.id.meeting);
        chatbot = findViewById(R.id.chatbot);
        reports = findViewById(R.id.reports);



        dr = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("name").getValue().toString();
                    profileName.setText(name);

                    String types = snapshot.child("type").getValue().toString();
                    type.setText(types);

                    String photo = "";
                    photo = snapshot.child("profileimage").getValue().toString();
                    Glide.with(getApplicationContext()).load(photo).into(profile);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mentors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mentors();
            }
        });


        findMentor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Search();
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings();
            }
        });


        career.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Career();
            }
        });

        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mentee_Resume();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Profile();
            }
        });

        goals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Goals();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Signout();
            }
        });


        meeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Meeting();
            }
        });

        chatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatBot();
            }
        });

        reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reports();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_IMAGE && resultCode==RESULT_OK && data!=null){

            Uri image = data.getData();

            CropImage.activity(image).setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1, 1).start(this);

        }

        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if(resultCode==RESULT_OK) {

                Uri resultUri = result.getUri();

                StorageReference r = storageReference.child(currentUser + ".jpg");

                r.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        r.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String url = uri.toString();

                                dr.child("profileimage").setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {

                                            Intent i = new Intent(MenteeMainActivity.this, MenteeMainActivity.class);
                                            startActivity(i);

                                            Toast.makeText(MenteeMainActivity.this, "Profile image stored to firebase database successfully", Toast.LENGTH_SHORT).show();

                                        } else {


                                            Toast.makeText(MenteeMainActivity.this, "Error", Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                });
                            }
                        });
                    }


                });
            }
            else{

                Toast.makeText(MenteeMainActivity.this, "Error image can not be cropped", Toast.LENGTH_SHORT).show();


            }

        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if(currentUser == null){
            
            Login();
        }else{
            CheckUser();
        }
    }

    private void CheckUser() {

        final String user1 = firebaseAuth.getCurrentUser().getUid();

        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(!snapshot.hasChild(user1)){

                    RegistrationActivity2();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void RegistrationActivity2() {
        Intent i = new Intent(MenteeMainActivity.this, Mentee_Registration_Activity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

    private void Login() {

        Intent i = new Intent(MenteeMainActivity.this, Login_Activity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }



    private void Career() {
        Intent i = new Intent(MenteeMainActivity.this, CareerOptions.class);
        startActivity(i);
    }

    private void Goals() {
        Intent i = new Intent(MenteeMainActivity.this, Goals_Activity_Mentee.class);
        startActivity(i);
        }

    private void Mentors() {

        Intent i = new Intent(MenteeMainActivity.this, MenteeList.class);
        startActivity(i);
    }

    private void Search() {
        Intent i = new Intent(MenteeMainActivity.this, SearchIndustryFragment.class);
        startActivity(i);
    }

    private void Mentee_Resume() {
        Intent i = new Intent(MenteeMainActivity.this, Mentee_Resume_Options.class);
        startActivity(i);
    }

    private void Profile() {
        Intent i = new Intent(MenteeMainActivity.this, Mentee_Profile_Activity.class);
        startActivity(i);
    }

    private void Settings() {
        Intent i = new Intent(MenteeMainActivity.this, Profile_Settings_Mentee_Activity.class);
        startActivity(i);
    }

    private void RateMentor() {
        Intent i = new Intent(MenteeMainActivity.this, Rating_Activity.class);
        startActivity(i);

    }

    private void ChatBot() {
        Intent i = new Intent(MenteeMainActivity.this, ChatbotKotlin.class);
        startActivity(i);

    }

    private void Signout() {
        Intent i = new Intent(MenteeMainActivity.this, Login_Activity.class);
        startActivity(i);
        finish();
    }

    private void Meeting() {
        Intent i = new Intent(MenteeMainActivity.this, Meetings_Activity_Mentee.class);
        startActivity(i);
        finish();
    }

    private void Reports() {
        Intent i = new Intent(MenteeMainActivity.this, Mentee_Reports.class);
        startActivity(i);
        finish();
    }

}