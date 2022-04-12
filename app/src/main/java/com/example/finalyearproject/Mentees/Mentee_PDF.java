package com.example.finalyearproject.Mentees;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalyearproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class Mentee_PDF extends AppCompatActivity {

    private TextView mName,mSkills,  mCollege, mCourse, mGraduationYear, mBio, mCompany, mRole, mStartDate,
            mEndDate, mDescription, mHobbies, mProjects;
    private DatabaseReference RootRef;
    private FirebaseAuth firebaseAuth;
    private String currentUser;
    private LinearLayout layout;
    private Bitmap bitmap;
    private String resume_id = "";
    private static final int PERMISSION_REQUEST_CODE = 200;
    private ImageView mHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_pdf);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser().getUid();

        Intent i = getIntent();
        resume_id = i.getStringExtra("resume_id");

        RootRef = FirebaseDatabase.getInstance().getReference().child("CV").child(currentUser).child(resume_id);

        mHome = findViewById(R.id.home);
        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentee_PDF.this, Mentee_Resume_Options.class);
                startActivity(i);
                finish();
            }
        });

        mName = findViewById(R.id.name);
        mSkills = findViewById(R.id.interestSkills);
        mGraduationYear = findViewById(R.id.graduationYear);
        mBio = findViewById(R.id.bio);
        mCollege = findViewById(R.id.college);
        mCourse = findViewById(R.id.course);
        mCompany = findViewById(R.id.companyName);
        mRole = findViewById(R.id.role);
        mStartDate = findViewById(R.id.startDate);
        mEndDate = findViewById(R.id.endDate);
        mDescription = findViewById(R.id.workDescription);
        mHobbies = findViewById(R.id.interestHobbies);
        mProjects = findViewById(R.id.interestProjects);

        layout = findViewById(R.id.container);




        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String name = snapshot.child("name").getValue().toString();
                    String skills = snapshot.child("skill1").getValue().toString();
                    String college = snapshot.child("college").getValue().toString();
                    String course = snapshot.child("course").getValue().toString();
                    String graduationYear = snapshot.child("graduationYear").getValue().toString();
                    String bio = snapshot.child("bio").getValue().toString();
                    String company = snapshot.child("company").getValue().toString();
                    String role = snapshot.child("role").getValue().toString();
                    String startDate = snapshot.child("startDate").getValue().toString();
                    String endDate = snapshot.child("endDate").getValue().toString();
                    String description = snapshot.child("description").getValue().toString();
                    String hobbies = snapshot.child("hobbies").getValue().toString();
                    String projects = snapshot.child("projects").getValue().toString();


                    mName.setText(name);
                    mGraduationYear.setText(graduationYear);
                    mCollege.setText(college);
                    mBio.setText(bio);
                    mCourse.setText(course);
                    mRole.setText(role);
                    mSkills.setText(skills);
                    mCompany.setText(company);
                    mStartDate.setText(startDate);
                    mEndDate.setText(endDate);
                    mDescription.setText(description);
                    mHobbies.setText(hobbies);
                    mProjects.setText(projects);

                    if (checkPermission()) {
                        Toast.makeText(Mentee_PDF.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    } else {
                        requestPermission();
                    }

                    bitmap = LoadBitMap(layout, layout.getWidth(), layout.getHeight());
                    createPDF();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private Bitmap LoadBitMap(View v, int width, int height) {

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }

    private void createPDF() {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float width = displayMetrics.widthPixels;
        float height = displayMetrics.heightPixels;
        int convertWidth = (int) width, convertHeight= (int) height;

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHeight, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);
        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHeight, true);
        canvas.drawBitmap(bitmap, 0, 0, null);
        document.finishPage(page);



        //target pdf download
        File file = new File(Environment.getExternalStorageDirectory(), "CV.pdf");

        try{
            document.writeTo(new FileOutputStream(file));
            Toast.makeText(Mentee_PDF.this, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // below line is used
            // to handle error
            e.printStackTrace();
            Toast.makeText(Mentee_PDF.this, "Error", Toast.LENGTH_SHORT).show();

        }
        // after storing our pdf to that
        // location we are closing our PDF file.
        document.close();
    }

    private boolean checkPermission() {
        // checking of permissions.
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                // after requesting permissions we are showing
                // users a toast message of permission granted.
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denined.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }
}