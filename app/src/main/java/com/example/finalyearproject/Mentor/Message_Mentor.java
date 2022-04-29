package com.example.finalyearproject.Mentor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalyearproject.Adapters.MentorMessageAdapter;
import com.example.finalyearproject.Models.MentorMessages;
import com.example.finalyearproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Message_Mentor extends AppCompatActivity
{
    private Toolbar ChattoolBar;
    private ImageButton SendMessageButton;
    private EditText message;
    private RecyclerView mUserMessagesList;
    private String mentorID, menteeID, mentorName, date, time;
    private TextView mMenteeName;
    private ImageView mHome;
    private CircleImageView mMenteeProfileImage;
    private DatabaseReference RootRef, Messaging;
    private FirebaseAuth mAuth;
    private final List<MentorMessages> messagesList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private MentorMessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_mentor);

        mAuth = FirebaseAuth.getInstance();
        menteeID = mAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();
        mHome = findViewById(R.id.home);

        ChattoolBar = (Toolbar) findViewById(R.id.messageMentorToolbar);
        setSupportActionBar(ChattoolBar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View action_bar_view = layoutInflater.inflate(R.layout.message_mentor_bar, null);
        actionBar.setCustomView(action_bar_view);


        mMenteeName = (TextView) findViewById(R.id.messageMentorName);
        mMenteeProfileImage = (CircleImageView) findViewById(R.id.messageMentorProfileImage);

        SendMessageButton = (ImageButton) findViewById(R.id.messageMentorSendMessage);
        message = (EditText) findViewById(R.id.messageMentorMessage);

        messageAdapter = new MentorMessageAdapter(messagesList);
        mUserMessagesList = (RecyclerView) findViewById(R.id.messageMentorRecyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        mUserMessagesList.setHasFixedSize(true);
        mUserMessagesList.setLayoutManager(linearLayoutManager);
        mUserMessagesList.setAdapter(messageAdapter);

        //Get mentors name and id
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();

            if (extras != null) {
                mentorID = (String) extras.get("mentorid").toString();
                mentorName = (String) extras.get("name").toString();
            }
        }

        Messaging = FirebaseDatabase.getInstance().getReference().child("MessagesUpdate").child(menteeID).child(mentorID);

        Messaging.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if(!snapshot.exists()){
                                                    Messaging = FirebaseDatabase.getInstance().getReference().child("MessagesUpdate").child(menteeID).child(mentorID);
                                                    HashMap messaging = new HashMap();
                                                    messaging.put("isRead", false);
                                                }else {
                                                    String isRead = snapshot.child("isRead").getValue().toString();
                                                    if (isRead.equals("true")) {
                                                    }
                                                    if (isRead.equals("false")) {

                                                    }

                                                }

                                                }


                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });


         DisplayReceiverInfo();

        SendMessageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SendMessage();
            }
        });

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        FetchMessages();
    }


    //Sets the messages
    private void FetchMessages()
    {
        RootRef.child("Messages").child(menteeID).child(mentorID).addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
                if(dataSnapshot.exists())
                {
                    MentorMessages messages = dataSnapshot.getValue(MentorMessages.class);
                    messagesList.add(messages);
                    messageAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot)
            {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }

    private void SendMessage()
    {

        String text = message.getText().toString();

        if(TextUtils.isEmpty(text))
        {
            Toast.makeText(this, "Please type a message first... ", Toast.LENGTH_SHORT).show();
        }
        else
        {
            String sender = "Messages/" + menteeID + "/" + mentorID;
            String receiver = "Messages/" + mentorID + "/" + menteeID;

            //Creates child
            DatabaseReference key = RootRef.child("Messages").child(menteeID).child(menteeID).child(mentorID).push();
            //Gets the message id
            String messageID = key.getKey();

            Calendar calendarDate = Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
            date = currentDate.format(calendarDate.getTime());
            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm aa");
            time = currentTime.format(calendarDate.getTime());

            Map messageBody = new HashMap();
            messageBody.put("message", text);
            messageBody.put("time", time);
            messageBody.put("date", date);
            messageBody.put("type", "text");
            messageBody.put("from", menteeID);

            Map messageBodyDetails = new HashMap();
            messageBodyDetails.put(sender + "/" + messageID , messageBody);
            messageBodyDetails.put(receiver + "/" + messageID , messageBody);

            RootRef.updateChildren(messageBodyDetails).addOnCompleteListener(new OnCompleteListener()
            {
                @Override
                public void onComplete(@NonNull Task task)
                {
                    if(task.isSuccessful())
                    {
                        message.setText("");
                    }
                    else
                    {
                        message.setText("");
                    }
                }
            });
        }
    }

    //Sets mentors information
    private void DisplayReceiverInfo()
    {
        mMenteeName.setText(mentorName);

        RootRef.child("users").child(mentorID).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                //Sets mentors profile image
                if(dataSnapshot.exists())
                {
                    final String profileImage = dataSnapshot.child("profileimage").getValue().toString();

                    Glide.with(getApplicationContext()).load(profileImage).into(mMenteeProfileImage);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }
}