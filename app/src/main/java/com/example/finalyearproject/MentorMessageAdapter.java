package com.example.finalyearproject;


import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MentorMessageAdapter extends RecyclerView.Adapter<MentorMessageAdapter.MessageViewHolder>
{
    private List<MentorMessages> userMessagesList;
    private FirebaseAuth mAuth;
    private DatabaseReference usersDatabaseRef;

    public MentorMessageAdapter (List<MentorMessages> userMessagesList)
    {
        this.userMessagesList = userMessagesList;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder
    {
        public TextView SenderMessagetext, ReceiverMessageText;
        public CircleImageView receiverProfileImage;

        public MessageViewHolder(View itemView)
        {
            super(itemView);

            SenderMessagetext = (TextView) itemView.findViewById(R.id.sender_message_text);
            ReceiverMessageText = (TextView) itemView.findViewById(R.id.receiver_message_text);
            //receiverProfileImage = (CircleImageView) itemView.findViewById(R.id.message_profile_image);
        }
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View V = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_layout, viewGroup, false);

        mAuth = FirebaseAuth.getInstance();

        return new MessageViewHolder(V);
    }

    @Override
    public void onBindViewHolder(@NonNull final MessageViewHolder messageViewHolder, int i)
    {
        String messageSenderID = mAuth.getCurrentUser().getUid();
        MentorMessages messages = userMessagesList.get(i);

        String fromUserID = messages.getFrom();
        String fromMessageType = messages.getType();

        usersDatabaseRef = FirebaseDatabase.getInstance().getReference().child("users").child(fromUserID);
        usersDatabaseRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    //String image = dataSnapshot.child("profileimage").getValue().toString();

                    //Picasso.get().load(image).placeholder(R.drawable.profile).into(messageViewHolder.receiverProfileImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

        if(fromMessageType.equals("text"))
        {
            messageViewHolder.ReceiverMessageText.setVisibility(View.INVISIBLE);
            //messageViewHolder.receiverProfileImage.setVisibility(View.INVISIBLE);

            if(fromUserID.equals(messageSenderID))
            {
                messageViewHolder.SenderMessagetext.setBackgroundResource(R.drawable.mentee_message_text);
                messageViewHolder.SenderMessagetext.setTextColor(Color.WHITE);
                messageViewHolder.SenderMessagetext.setGravity(Gravity.LEFT);
                messageViewHolder.SenderMessagetext.setText(messages.getMessage());
            }
            else
            {
                messageViewHolder.SenderMessagetext.setVisibility(View.INVISIBLE);

                messageViewHolder.ReceiverMessageText.setVisibility(View.VISIBLE);
                //messageViewHolder.receiverProfileImage.setVisibility(View.VISIBLE);

                messageViewHolder.ReceiverMessageText.setBackgroundResource(R.drawable.mentor_message_text);
                messageViewHolder.ReceiverMessageText.setTextColor(Color.WHITE);
                messageViewHolder.ReceiverMessageText.setGravity(Gravity.LEFT);
                messageViewHolder.ReceiverMessageText.setText(messages.getMessage());
            }
        }
    }

    @Override
    public int getItemCount()
    {
        return userMessagesList.size();
    }
}