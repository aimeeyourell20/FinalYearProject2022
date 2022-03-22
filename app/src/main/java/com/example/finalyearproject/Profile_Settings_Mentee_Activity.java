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

public class Profile_Settings_Mentee_Activity extends AppCompatActivity {

    private Toolbar mtoolbar;
    private EditText  moccupation, mcollege, mgoals, mcourse;
    private Spinner mlocation, mindustry, mskills, mlanguage;
    private Button mupdateMentee;
    private TextView mtype, mname;
    private DatabaseReference dr;
    private String currentUser;
    private ImageView profile;
    private static int PICK_IMAGE = 123;
    private StorageReference storageReference;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_profile_settings);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser().getUid();
        dr = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser);
        storageReference = FirebaseStorage.getInstance().getReference().child("Profile Images");



        mname = findViewById(R.id.profileNameTxt);
        mtype = findViewById(R.id.typeProfileTxt);
        mskills = findViewById(R.id.skills1Spinner);
        mlanguage = findViewById(R.id.languageSpinner);
        mlocation = findViewById(R.id.locationSpinner);
        mindustry = findViewById(R.id.industrySpinner);
        mcollege = findViewById(R.id.college);
        mgoals = findViewById(R.id.goals);
        mcourse = findViewById(R.id.course);
        mupdateMentee = findViewById(R.id.updateMentee);
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
                    String goals = snapshot.child("goals").getValue().toString();
                    String college = snapshot.child("college").getValue().toString();
                    String industry = snapshot.child("industry").getValue().toString();
                    String course = snapshot.child("course").getValue().toString();
                    String type = snapshot.child("type").getValue().toString();
                    String skill1 = snapshot.child("skill1").getValue().toString();
                    String language = snapshot.child("language").getValue().toString();
                    String location = snapshot.child("location").getValue().toString();
                    String photo = snapshot.child("profileimage").getValue().toString();
                    Glide.with(getApplicationContext()).load(photo).into(profile);


                    mname.setText(name);
                    mgoals.setText(goals);
                    mcollege.setText(college);
                    mindustry.setSelected(Boolean.parseBoolean(industry));
                    mcourse.setText(course);
                    mtype.setText(type);
                    mskills.setSelected(Boolean.parseBoolean(skill1));
                    mlanguage.setSelected(Boolean.parseBoolean(language));
                    mlocation.setSelected(Boolean.parseBoolean(location));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mupdateMentee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validation();
            }
        });

    }

    private void Validation() {

        String name = mname.getText().toString();
        String goals = mgoals.getText().toString();
        String college = mcollege.getText().toString();
        String industry = mindustry.getSelectedItem().toString();
        String course = mcourse.getText().toString();
        String skill1 = mskills.getSelectedItem().toString();
        String language = mlanguage.getSelectedItem().toString();
        String location = mlocation.getSelectedItem().toString();
        String type = mtype.getText().toString();

        if(TextUtils.isEmpty(name)){
            mname.setError("Name can not be left blank");
        }
        if(TextUtils.isEmpty(goals)){
            mgoals.setError("Goal can not be left blank");
        }
        if(TextUtils.isEmpty(college)){
            mcollege.setError("College can not be left blank");
        }
        if(TextUtils.isEmpty(course)){
            mcourse.setError("Course can not be left blank");
        }
        else{
            UpdateMentee(name, goals, college, course, skill1, language, location, type, industry);
        }


    }

    private void UpdateMentee(String name, String goals, String college, String course, String skill1, String language, String location, String type,String industry) {
        HashMap<String, Object> MenteeMap = new HashMap<>();
        MenteeMap.put("name", name);
        MenteeMap.put("goals", goals);
        MenteeMap.put("college", college);
        MenteeMap.put("industry", industry);
        MenteeMap.put("industry2", industry);
        MenteeMap.put("industry3", "");
        MenteeMap.put("course", course);
        MenteeMap.put("skill1", skill1);
        MenteeMap.put("skill4", skill1);
        MenteeMap.put("skill3", "");
        MenteeMap.put("language", language);
        MenteeMap.put("location", location);
        MenteeMap.put("search", type + skill1 + industry);
        MenteeMap.put("search1", type + skill1);


        dr.updateChildren(MenteeMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(Profile_Settings_Mentee_Activity.this, "Mentee details updated successfully", Toast.LENGTH_SHORT).show();
                    SendMenteeMainActivity();
                }else{
                    Toast.makeText(Profile_Settings_Mentee_Activity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void SendMenteeMainActivity() {
        Intent i = new Intent(Profile_Settings_Mentee_Activity.this, MenteeMainActivity.class);
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

                                            Intent i = new Intent(Profile_Settings_Mentee_Activity.this, Profile_Settings_Mentee_Activity.class);
                                            startActivity(i);

                                            Toast.makeText(Profile_Settings_Mentee_Activity.this, "Profile image stored to firebase database successfully", Toast.LENGTH_SHORT).show();

                                        } else {


                                            Toast.makeText(Profile_Settings_Mentee_Activity.this, "Error", Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                });
                            }
                        });
                    }


                });
            }
            else{

                Toast.makeText(Profile_Settings_Mentee_Activity.this, "Error image can not be cropped", Toast.LENGTH_SHORT).show();


            }

        }

    }


}