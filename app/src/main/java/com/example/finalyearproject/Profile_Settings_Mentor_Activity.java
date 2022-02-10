package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

public class Profile_Settings_Mentor_Activity extends AppCompatActivity {

    private Toolbar mtoolbar;
    private EditText  mskill1,mskill2, mlanguage, mjobTitle, mbio, mcompany;
    private Spinner mlocation, mindustry;
    private TextView mname, mtype;
    private Button mupdateMentor;
    private DatabaseReference dr;
    private String currentUser;
    private ImageView profile;
    private static int PICK_IMAGE = 123;
    private StorageReference storageReference;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings_mentor);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser().getUid();
        dr = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser);
        storageReference = FirebaseStorage.getInstance().getReference().child("Profile Images");


        mname = findViewById(R.id.profileNameTxt);
        mtype = findViewById(R.id.typeProfileTxt);
        mskill1 = findViewById(R.id.skills1);
        mskill2 = findViewById(R.id.skills2);
        mlanguage = findViewById(R.id.langauge);
        mlocation = findViewById(R.id.locationSpinner);
        mjobTitle = findViewById(R.id.jobTitle);
        mbio = findViewById(R.id.bio);
        mcompany = findViewById(R.id.company);
        mindustry = findViewById(R.id.industrySpinner);
        mupdateMentor = findViewById(R.id.updateMentee);
        profile = findViewById(R.id.profileImageProfile);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent();
                profileIntent.setType("image/*");
                profileIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(profileIntent, PICK_IMAGE);
            }
        });


        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String name = snapshot.child("name").getValue().toString();
                    String bio = snapshot.child("bio").getValue().toString();
                    String jobTitle = snapshot.child("jobTitle").getValue().toString();
                    String industry = snapshot.child("industry").getValue().toString();
                    String type = snapshot.child("type").getValue().toString();
                    String skill1 = snapshot.child("skill1").getValue().toString();
                    String skill2 = snapshot.child("skill2").getValue().toString();
                    String language = snapshot.child("language").getValue().toString();
                    String location = snapshot.child("location").getValue().toString();
                    String company = snapshot.child("company").getValue().toString();
                    String photo = snapshot.child("profileimage").getValue().toString();
                    Glide.with(getApplicationContext()).load(photo).into(profile);


                    mname.setText(name);
                    mbio.setText(bio);
                    mjobTitle.setText(jobTitle);
                    mindustry.setSelected(Boolean.parseBoolean(industry));
                    mtype.setText(type);
                    mskill1.setText(skill1);
                    mskill2.setText(skill2);
                    mlanguage.setText(language);
                    mcompany.setText(company);
                    mlocation.setSelected(Boolean.parseBoolean(location));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mupdateMentor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validation();
            }
        });

    }

    private void Validation() {

        String name = mname.getText().toString();
        String company = mcompany.getText().toString();
        String bio = mbio.getText().toString();
        String jobtitle = mjobTitle.getText().toString();
        String industry = mindustry.getSelectedItem().toString();
        String skill1 = mskill1.getText().toString();
        String skill2 = mskill2.getText().toString();
        String language = mlanguage.getText().toString();
        String location = mlocation.getSelectedItem().toString();
        String type = mtype.getText().toString();

        if(TextUtils.isEmpty(name)){
            mname.setError("Name can not be left blank");
        }
        if(TextUtils.isEmpty(bio)){
            mbio.setError("Bio can not be left blank");
        }
        if(TextUtils.isEmpty(jobtitle)){
            mjobTitle.setError("Job title can not be left blank");
        }
        if(TextUtils.isEmpty(skill1)){
            mskill1.setError("Skills can not be left blank");
        }
        if(TextUtils.isEmpty(language)){
            mlanguage.setError("Language can not be left blank");
        }
        else{
            UpdateMentee(name, bio, jobtitle,industry, skill1, skill2,language, location, type, company);
        }


    }

    private void UpdateMentee(String name, String goals, String jobTitle, String industry, String skill1,String skill2,String language, String location, String type, String company) {
        HashMap<String, Object> MentorMap = new HashMap<>();
        MentorMap.put("name", name);
        MentorMap.put("bio", goals);
        MentorMap.put("jobType", jobTitle);
        MentorMap.put("industry", industry);
        MentorMap.put("skill1", skill1);
        MentorMap.put("skill2", skill2);
        MentorMap.put("language", language);
        MentorMap.put("company", company);
        MentorMap.put("location", location);
        MentorMap.put("all1", skill1 + " " + skill2);
        MentorMap.put("all2", skill2 + " " + skill1);
        MentorMap.put("search", type + skill1 + industry);
        MentorMap.put("search1", type + skill1);


        dr.updateChildren(MentorMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(Profile_Settings_Mentor_Activity.this, "Mentor details updated successfully", Toast.LENGTH_SHORT).show();
                    SendMenteeMainActivity();
                }else{
                    Toast.makeText(Profile_Settings_Mentor_Activity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void SendMenteeMainActivity() {
        Intent i = new Intent(Profile_Settings_Mentor_Activity.this, MentorMainActivity.class);
        startActivity(i);
        finish();
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

                                            Intent i = new Intent(Profile_Settings_Mentor_Activity.this, Profile_Settings_Mentor_Activity.class);
                                            startActivity(i);

                                            Toast.makeText(Profile_Settings_Mentor_Activity.this, "Profile image stored to firebase database successfully", Toast.LENGTH_SHORT).show();

                                        } else {


                                            Toast.makeText(Profile_Settings_Mentor_Activity.this, "Error", Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                });
                            }
                        });
                    }


                });
            }
            else{

                Toast.makeText(Profile_Settings_Mentor_Activity.this, "Error image can not be cropped", Toast.LENGTH_SHORT).show();


            }

        }

    }


}