package com.example.blogapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class singPostActivity extends AppCompatActivity {

    private ImageView singelImage;
    private TextView singleTitle, singleDesc;
    String post_key = null;
    private DatabaseReference mDatabase;
    private Button deleteBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_post);
        singelImage = (ImageView)findViewById(R.id.singleImageview);
        singleTitle = (TextView)findViewById(R.id.singleTitle);
        singleDesc = (TextView)findViewById(R.id.singleDesc);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("BlogApp");
        post_key = getIntent().getExtras().getString("PostID");
        deleteBtn = (Button)findViewById(R.id.deleteBtn);
        mAuth = FirebaseAuth.getInstance();
        deleteBtn.setVisibility(View.INVISIBLE);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mDatabase.child(post_key).removeValue();
                Intent mainintent = new Intent(singPostActivity.this, MainActivity.class);
                startActivity(mainintent);
            }
        });

        mDatabase.child(post_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String post_title = (String)snapshot.child("title").getValue();
                String post_desc = (String) snapshot.child("desc").getValue();
                String post_image = (String)snapshot.child("imageUrl").getValue();
                String post_uid = (String)snapshot.child("uid").getValue();
                singleTitle.setText(post_title);
                singleDesc.setText(post_desc);
                Picasso.get().load(post_image).into(singelImage);
                if(mAuth.getCurrentUser().getUid().equals(post_uid)){
                    deleteBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}