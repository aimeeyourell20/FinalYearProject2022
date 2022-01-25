package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Mentee_Resume extends AppCompatActivity {

    private TextView mname, mskills,  mcollege, mcourse, mgraduationYear, mbio, mgender, mcompany, mrole, mstartDate,
    mEndDate, mDescription, mHobbies, mProjects;
    private Button mEditCV, mPDF;
    private DatabaseReference dr;
    private FirebaseAuth firebaseAuth;
    private String currentUser;
    private LinearLayout layout;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_resume);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser().getUid();
        dr = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser);

        mname = findViewById(R.id.name);
        mskills = findViewById(R.id.interestSkills);
        mgraduationYear = findViewById(R.id.graduationYear);
        mbio = findViewById(R.id.bio);
        mcollege = findViewById(R.id.college);
        mcourse = findViewById(R.id.course);
        mcompany = findViewById(R.id.companyName);
        mrole = findViewById(R.id.role);
        mstartDate = findViewById(R.id.startDate);
        mEndDate = findViewById(R.id.endDate);
        mDescription = findViewById(R.id.workDescription);
        mHobbies = findViewById(R.id.interestHobbies);
        mProjects = findViewById(R.id.interestProjects);
        mEditCV = findViewById(R.id.editCV);
        mPDF = findViewById(R.id.pdfCV);

        layout = findViewById(R.id.container);

        mPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bitmap = LoadBitMap(layout, layout.getWidth(), layout.getHeight());
                createPDF();
            }
        });


        mEditCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mentee_Resume.this, Mentee_Edit_Resume.class);
                startActivity(i);
            }
        });

        

        dr.addValueEventListener(new ValueEventListener() {
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


                    mname.setText(name);
                    mgraduationYear.setText(graduationYear);
                    mcollege.setText(college);
                    mbio.setText(bio);
                    mcourse.setText(course);
                    mrole.setText(role);
                    mskills.setText(skills);
                    mcompany.setText(company);
                    mstartDate.setText(startDate);
                    mEndDate.setText(endDate);
                    mDescription.setText(description);
                    mHobbies.setText(hobbies);
                    mProjects.setText(projects);

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

        WindowManager windowManager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
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
        File file = new File(Environment.getExternalStorageDirectory(), "CV2.pdf");
        try{
            document.writeTo(new FileOutputStream(file));
            Toast.makeText(Mentee_Resume.this, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // below line is used
            // to handle error
            e.printStackTrace();
        }
        // after storing our pdf to that
        // location we are closing our PDF file.
        document.close();
    }




    private void openPdf() {

        File file = new File(Environment.getExternalStorageDirectory() + "/My Files/Documents/Download/resume.pdf");
        if(file.exists()){
            Intent i = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            i.setDataAndType(uri, "application/pdf");
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try{
                startActivity(i);
            }catch (ActivityNotFoundException e){
                Toast.makeText(Mentee_Resume.this, "No application for pdf view", Toast.LENGTH_SHORT).show();
            }
        }

    }
}