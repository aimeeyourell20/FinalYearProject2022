package com.example.finalyearproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class Message_Mentor extends AppCompatActivity
{
    private Toolbar ChattoolBar;
    private ImageButton SendMessageButton, SendImagefileButton;
    private EditText userMessageInput;
    private RecyclerView userMessagesList;

    private String messageReceiverID, messageReceiverName, messageSenderID, saveCurrentDate, saveCurrentTime;

    private TextView receiverName, userLastSeen;
    private CircleImageView receiverProfileImage;

    private DatabaseReference RootRef, UserRef;
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
        messageSenderID = mAuth.getCurrentUser().getUid();

        RootRef = FirebaseDatabase.getInstance().getReference();
        UserRef = FirebaseDatabase.getInstance().getReference().child("users");

        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();

            if (extras != null) {
                messageReceiverID = (String) extras.get("mentorid").toString();
                messageReceiverName = (String) extras.get("name").toString();
            }
        }



       // messageReceiverID = getIntent().getExtras().get("mentorid").toString();
        //messageReceiverName = getIntent().getExtras().get("name").toString();

        IntializeFields();

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
        RootRef.child("Messages").child(messageSenderID).child(messageReceiverID).addChildEventListener(new ChildEventListener()
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
        //updateUserStatus("online");

        String messageText = userMessageInput.getText().toString();

        if(TextUtils.isEmpty(messageText))
        {
            Toast.makeText(this, "Please type a message first... ", Toast.LENGTH_SHORT).show();
        }
        else
        {
            String message_sender_ref = "Messages/" + messageSenderID + "/" + messageReceiverID;
            String message_receiver_ref = "Messages/" + messageReceiverID + "/" + messageSenderID;

            DatabaseReference user_message_key = RootRef.child("Messages").child(messageSenderID).child(messageSenderID).child(messageReceiverID).push();
            String message_push_id = user_message_key.getKey();

            Calendar calFordDate = Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
            saveCurrentDate = currentDate.format(calFordDate.getTime());

            Calendar calFordTime = Calendar.getInstance();
            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm aa");
            saveCurrentTime = currentTime.format(calFordDate.getTime());

            Map messageTextBody = new HashMap();
            messageTextBody.put("message", messageText);
            messageTextBody.put("time", saveCurrentTime);
            messageTextBody.put("date", saveCurrentDate);
            messageTextBody.put("type", "text");
            messageTextBody.put("from", messageSenderID);

            Map messageBodyDetails = new HashMap();
            messageBodyDetails.put(message_sender_ref + "/" + message_push_id , messageTextBody);
            messageBodyDetails.put(message_receiver_ref + "/" + message_push_id , messageTextBody);

            RootRef.updateChildren(messageBodyDetails).addOnCompleteListener(new OnCompleteListener()
            {
                @Override
                public void onComplete(@NonNull Task task)
                {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(Message_Mentor.this, "Message Sent Successfully.", Toast.LENGTH_SHORT).show();
                        userMessageInput.setText("");
                    }
                    else
                    {
                        String message = task.getException().getMessage();
                        Toast.makeText(Message_Mentor.this, "Error Occur: " + message, Toast.LENGTH_SHORT).show();
                        userMessageInput.setText("");
                    }
                }
            });
        }
    }

    /*public void updateUserStatus(String state)
    {
        String saveCurrentDate, saveCurrentTime;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMM-YYYY");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm:ss a");
        saveCurrentTime = currentTime.format(calForTime.getTime());

        Map currentStateMap = new HashMap();
        currentStateMap.put("time", saveCurrentTime);
        currentStateMap.put("date", saveCurrentDate);
        currentStateMap.put("type", state);

        UserRef.child(messageSenderID).child("userState").updateChildren(currentStateMap);
    }
*/
    private void DisplayReceiverInfo()
    {
        receiverName.setText(messageReceiverName);

        RootRef.child("users").child(messageReceiverID).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    //final String profileImage = dataSnapshot.child("profileimage").getValue().toString();
                   /* final String type = dataSnapshot.child("userState").child("type").getValue().toString();
                    final String lastDate = dataSnapshot.child("userState").child("date").getValue().toString();
                    final String lastTime = dataSnapshot.child("userState").child("time").getValue().toString();*/

                    /*if(type.equals("online"))
                    {
                        userLastSeen.setText("online");
                    }
                    else
                    {
                        userLastSeen.setText("last seen: " + lastTime + "  " + lastDate);
                    }*/

                    //Picasso.get().load(profileImage).placeholder(R.drawable.profile).into(receiverProfileImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }

    private void IntializeFields()
    {
        ChattoolBar = (Toolbar) findViewById(R.id.messageMentorToolbar);
        setSupportActionBar(ChattoolBar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View action_bar_view = layoutInflater.inflate(R.layout.message_mentor_bar, null);
        actionBar.setCustomView(action_bar_view);

        receiverName = (TextView) findViewById(R.id.messageMentorName);
        //userLastSeen = (TextView) findViewById(R.id.custom_user_last_seen);
        receiverProfileImage = (CircleImageView) findViewById(R.id.messageMentorProfileImage);

        SendMessageButton = (ImageButton) findViewById(R.id.messageMentorSendMessage);
        SendImagefileButton = (ImageButton) findViewById(R.id.messageMentorSendImage);
        userMessageInput = (EditText) findViewById(R.id.messageMentorMessage);

        messageAdapter = new MentorMessageAdapter(messagesList);
        userMessagesList = (RecyclerView) findViewById(R.id.messageMentorRecyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        userMessagesList.setHasFixedSize(true);
        userMessagesList.setLayoutManager(linearLayoutManager);
        userMessagesList.setAdapter(messageAdapter);
    }
}
