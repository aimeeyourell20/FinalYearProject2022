package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.finalyearproject.LocationReports.Mentor_Reports;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class Mentor_Profile_Activity extends AppCompatActivity {

    private TextView mname, mskills, mlocation, mlanguage, mjobTitle, mbio, mindustry, mtype,mskills2;
    private ImageView reports, mentees, settings, profile;
    private Button home;
    private DatabaseReference dr;
    private FirebaseAuth firebaseAuth;
    private String currentUser;
    private static int PICK_IMAGE = 123;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser().getUid();
        dr = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser);

        mname = findViewById(R.id.profileNameTxt);
        mtype = findViewById(R.id.typeProfileTxt);
        mskills = findViewById(R.id.skills1);
        mskills2 = findViewById(R.id.skills2);
        mlanguage = findViewById(R.id.langauge);
        mlocation = findViewById(R.id.location);
        mjobTitle = findViewById(R.id.jobTitle);
        mbio = findViewById(R.id.bio);
        mindustry = findViewById(R.id.industry);
        mentees = findViewById(R.id.menteesProfile);
        reports = findViewById(R.id.reportsProfile);
        settings = findViewById(R.id.editProfile);
        home = findViewById(R.id.home);
        profile = findViewById(R.id.profileImageProfile);

        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("name").getValue().toString();
                    String bio = snapshot.child("bio").getValue().toString();
                    String jobTitle = snapshot.child("jobTitle").getValue().toString();
                    String industry = snapshot.child("industry").getValue().toString();
                    String type = snapshot.child("type").getValue().toString();
                    String skills = snapshot.child("skill1").getValue().toString();
                    String skills2 = snapshot.child("skill2").getValue().toString();
                    String language = snapshot.child("language").getValue().toString();
                    String location = snapshot.child("location").getValue().toString();
                    String photo = snapshot.child("profileimage").getValue().toString();
                    Glide.with(getApplicationContext()).load(photo).into(profile);


                    mname.setText(name);
                    mbio.setText(bio);
                    mjobTitle.setText(jobTitle);
                    mindustry.setText(industry);
                    mtype.setText(type);
                    mskills.setText(skills);
                    mskills2.setText(skills2);
                    mlanguage.setText(language);
                    mlocation.setText(location);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentor_Profile_Activity.this, Mentor_Reports.class);
                startActivity(i);            }
        });

        mentees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentor_Profile_Activity.this, MentorList.class);
                startActivity(i);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentor_Profile_Activity.this, Profile_Settings_Mentor_Activity.class);
                startActivity(i);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentor_Profile_Activity.this, MentorMainActivity.class);
                startActivity(i);
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

                                                Intent i = new Intent(Mentor_Profile_Activity.this, Mentor_Profile_Activity.class);
                                                startActivity(i);

                                                Toast.makeText(Mentor_Profile_Activity.this, "Profile image stored to firebase database successfully", Toast.LENGTH_SHORT).show();

                                            } else {


                                                Toast.makeText(Mentor_Profile_Activity.this, "Error", Toast.LENGTH_SHORT).show();

                                            }

                                        }
                                    });
                                }
                            });
                        }


                    });
                }
                else{

                    Toast.makeText(Mentor_Profile_Activity.this, "Error image can not be cropped", Toast.LENGTH_SHORT).show();


                }

            }

        }

    }