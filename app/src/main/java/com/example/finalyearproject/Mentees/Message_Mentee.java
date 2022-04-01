package com.example.finalyearproject.Mentees;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Message_Mentee extends AppCompatActivity
{
    private Toolbar ChattoolBar;
    private ImageButton SendMessageButton;
    private EditText message;
    private RecyclerView RecyclerView;
    private String mentorID, date, time, mentorName;
    private String menteeID = "";
    private TextView mMentorName;
    private CircleImageView mMentorProfileImage;
    private DatabaseReference RootRef;
    private FirebaseAuth mAuth;
    private final List<MentorMessages> messagesList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private MentorMessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_mentee);

        ChattoolBar = (Toolbar) findViewById(R.id.messageMentorToolbar);
        setSupportActionBar(ChattoolBar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View action_bar_view = layoutInflater.inflate(R.layout.message_mentor_bar, null);
        actionBar.setCustomView(action_bar_view);

        mMentorName = (TextView) findViewById(R.id.messageMentorName);
        mMentorProfileImage = (CircleImageView) findViewById(R.id.messageMentorProfileImage);
        SendMessageButton = (ImageButton) findViewById(R.id.messageMentorSendMessage);
        message = (EditText) findViewById(R.id.messageMentorMessage);

        messageAdapter = new MentorMessageAdapter(messagesList);
        RecyclerView = (RecyclerView) findViewById(R.id.messageMentorRecyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        RecyclerView.setHasFixedSize(true);
        RecyclerView.setLayoutManager(linearLayoutManager);
        RecyclerView.setAdapter(messageAdapter);

        mAuth = FirebaseAuth.getInstance();
        mentorID = mAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();

            if (extras != null) {
                menteeID = (String) extras.get("menteeid");
                mentorName = (String) extras.get("name");
            }
        }

        DisplayReceiverInfo();

        SendMessageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SendMessage();
            }

        });

        FetchMessages();
    }

    private void FetchMessages()
    {
        RootRef.child("Messages").child(mentorID).child(menteeID).addChildEventListener(new ChildEventListener()
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

        String messageText = message.getText().toString();

        if(TextUtils.isEmpty(messageText))
        {
            Toast.makeText(this, "Please type a message first... ", Toast.LENGTH_SHORT).show();
        }
        else
        {
            String sender = "Messages/" + mentorID + "/" + menteeID;
            String receiver = "Messages/" + menteeID + "/" + mentorID;

            DatabaseReference Messages = RootRef.child("Messages").child(mentorID).child(mentorID).child(menteeID).push();
            String messageID = Messages.getKey();

            Calendar calendarDate = Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
            date = currentDate.format(calendarDate.getTime());
            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm aa");
            time = currentTime.format(calendarDate.getTime());

            Map messageMap = new HashMap();
            messageMap.put("message", messageText);
            messageMap.put("time", time);
            messageMap.put("date", date);
            messageMap.put("type", "text");
            messageMap.put("from", mentorID);

            Map messageBodyDetails = new HashMap();
            messageBodyDetails.put(sender + "/" + messageID , messageMap);
            messageBodyDetails.put(receiver + "/" + messageID , messageMap);


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

    private void DisplayReceiverInfo()
    {
        mMentorName.setText(mentorName);

        RootRef.child("users").child(menteeID).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    final String profileImage = dataSnapshot.child("profileimage").getValue().toString();
                    Glide.with(getApplicationContext()).load(profileImage).into(mMentorProfileImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }
}
