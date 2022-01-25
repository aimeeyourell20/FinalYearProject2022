package com.example.finalyearproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Array;
import java.util.ArrayList;

public class SkillsPage extends AppCompatActivity {
    CheckBox mC, mJava,mPython, mPHP, mR, mSwift, mGoLang, mCHash, mJavaScript,
            mKotlin, mPearl, mRuby, mAI, mML, mAndroidStudio,mIOT, mWebDev, mReactJS,
            mNodeJS, mFlutter, mFirebase, mDBMS, mDataScience, mUIUX;
    Button mUpdate;
    FirebaseAuth fAuth;
    FirebaseDatabase fData;
    String userid;
    ArrayList<String> selection = new ArrayList<String>();
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills_page);


        mC = findViewById(R.id.CP);
        mJava = findViewById(R.id.JavaP);
        mPython = findViewById(R.id.PythonP);
        mPHP = findViewById(R.id.PHPP);
        mR = findViewById(R.id.RP);
        mSwift = findViewById(R.id.SwiftP);
        mGoLang = findViewById(R.id.GoLangP);
        mCHash = findViewById(R.id.UnityP);
        mJavaScript = findViewById(R.id.JavaSciptP);

        mUpdate = findViewById(R.id.UpdateP);


        fAuth = FirebaseAuth.getInstance();
        fData = FirebaseDatabase.getInstance();
        userid = fAuth.getCurrentUser().getUid();


        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference databaseReference = fData.getReference("users");
                databaseReference.child(userid).child("skills").setValue(selection);
                DatabaseReference databaseReference1 = fData.getReference("skills");
                for(int i=0;i<selection.size();i++){
                    databaseReference1.child(selection.get(i)).child("users").child(userid).setValue(userid);
                }
                Toast.makeText(SkillsPage.this, "Added skills!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),MenteeMainActivity.class);
                startActivity(intent);

            }
        });
    }
    public void selectItem(View view) {

        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {
            case R.id.CP:
                if (checked)
                    selection.add("C++");
                else
                    selection.remove("C++");
                break;
            case R.id.JavaP:
                if (checked)
                    selection.add("Java");
                else
                    selection.remove("Java");
                break;
            case R.id.PythonP:
                if (checked)
                    selection.add("Python");
                else
                    selection.remove("Python");
                break;
            case R.id.PHPP:
                if (checked)
                    selection.add("PHP");
                else
                    selection.remove("PHP");
                break;
            case R.id.RP:
                if (checked)
                    selection.add("R");
                else
                    selection.remove("R");
                break;
            case R.id.SwiftP:
                if (checked)
                    selection.add("Swift");
                else
                    selection.remove("Swift");
                break;
            case R.id.GoLangP:
                if (checked)
                    selection.add("GoLang");
                else
                    selection.remove("GoLang");
                break;
            case R.id.UnityP:
                if (checked)
                    selection.add("Unity");
                else
                    selection.remove("Unity");
                break;
            case R.id.JavaSciptP:
                if (checked)
                    selection.add("JavaScript");
                else
                    selection.remove("JavaScript");
                break;

        }
    }
}