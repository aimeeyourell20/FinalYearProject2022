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
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class  Mentee_Registration_Activity extends AppCompatActivity {


    private TextInputEditText mRegisterEmail, mRegisterName, mRegisterPassword;
    private Button mMenteeSignUpButton, mbackbutton;
    private Spinner mSkillSpinner, mLocationSpinner, mLanguageSpinner;
    private CircleImageView mprofile;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference dr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_registration_activity);

        firebaseAuth = FirebaseAuth.getInstance();


        mRegisterEmail = findViewById(R.id.registerEmail);
        mRegisterName = findViewById(R.id.registerName);
        mRegisterPassword = findViewById(R.id.registerPassword);
        mMenteeSignUpButton = findViewById(R.id.mentorSignUpButton);
        mSkillSpinner = findViewById(R.id.skillSpinner);
        mLocationSpinner = findViewById(R.id.locationSpinner);
        mLanguageSpinner = findViewById(R.id.languageSpinner);
        mprofile = findViewById(R.id.profile_image);

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
        //String skills = mSkillSpinner.getSelectedItem().toString();
        //String location = mLocationSpinner.getSelectedItem().toString();
       // String language = mLanguageSpinner.getSelectedItem().toString();
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
       /* if(TextUtils.isEmpty(password)){
            mconfirmPassword.setError("Confirm password required");
            return;
        }*/
        if (password.length() < 6) {
            mRegisterPassword.setError("Password must be more than 6 characters");
            return;
        }
       /* if (skills.equals("Please select the skill you would like to improve")) {
            Toast.makeText(Mentee_Registration_Activity.this, "Please select an option", Toast.LENGTH_SHORT).show();
            return;
        }
        if (location.equals("Please select the location you would prefer")) {
            Toast.makeText(Mentee_Registration_Activity.this, "Please select an option", Toast.LENGTH_SHORT).show();
            return;
        }
        if (language.equals("Please select the language you would prefer")) {
            Toast.makeText(Mentee_Registration_Activity.this, "Please select an option", Toast.LENGTH_SHORT).show();
            return;
        }*/
      /*  if(!password.equals(confirmpassword)){
            msignupPassword.setError("Passwords must match");
            return;
        }*/

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (!task.isSuccessful()) {
                    Toast.makeText(Mentee_Registration_Activity.this, "Registration unsuccessful", Toast.LENGTH_SHORT).show();
                } else {
                    String currentUser = firebaseAuth.getCurrentUser().getUid();
                    dr = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser);
                    HashMap mentee = new HashMap();
                    mentee.put("name", name);
                    mentee.put("email", email);
                    mentee.put("skill1", "Input");
                    mentee.put("location", "Input");
                    mentee.put("language", "Input");
                    mentee.put("graduationYear", "Input");
                    mentee.put("bio", "Input");
                    mentee.put("company", "Input");
                    mentee.put("role", "Input");
                    mentee.put("startDate", "Input");
                    mentee.put("endDate", "Input");
                    mentee.put("description", "Input");
                    mentee.put("hobbies", "Input");
                    mentee.put("projects", "Input");
                    mentee.put("college", "TUD");
                    mentee.put("occupation", "Student");
                    mentee.put("course", "Computer Science");
                    mentee.put("goals", "To learn about computers");
                    mentee.put("type", "Mentee");
                    mentee.put("profileimage", "");
                    mentee.put("industry", "");


                    dr.updateChildren(mentee).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Mentee_Registration_Activity.this, "User info successfully added", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Mentee_Registration_Activity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                            finish();
                        }
                    });

                    /*if(uri !=null ){
                        final StorageReference file = FirebaseStorage.getInstance().getReference().child("profile image").child(currentUser);

                        Bitmap bitmap = null;
                        try{

                            bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), uri);

                        }catch(IOException e){
                            e.printStackTrace();
                        }
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
                        byte[] data = byteArrayOutputStream.toByteArray();
                        UploadTask upload = file.putBytes(data);

                        upload.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Mentee_Registration_Application.this, "Image failed to upload", Toast.LENGTH_SHORT).show();
                            }
                        });
                       task.addOnSuccessListener(new OnSuccessListener<Task.TaskSnapshot>() {
                           @Override
                           public void onSuccess(Task.TaskSnapshot taskSnapshot) {

                           }
                       });
*/

                }
            }
        });

        Intent i = new Intent(Mentee_Registration_Activity.this, Login_Activity.class);
        startActivity(i);
        finish();
    }
}