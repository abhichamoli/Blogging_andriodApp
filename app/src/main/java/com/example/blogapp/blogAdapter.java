package com.example.blogapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class blogAdapter extends FirebaseRecyclerAdapter<BlogApp, blogAdapter.blogViewholder> {


    public blogAdapter(@NonNull FirebaseRecyclerOptions<BlogApp> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull blogViewholder holder, int position, @NonNull BlogApp model) {
        holder.post_title.setText(model.getTitle());
        holder.post_desc.setText(model.getDesc());
        holder.postUserName.setText(model.getUsername());
        Picasso.get().load(model.getImageUrl()).into(holder.post_image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final String post_key = getRef(position).getKey();
               Log.d("msg", post_key);
                Intent singleActivity = new Intent(holder.mainLayout.getContext(), singPostActivity.class);
                singleActivity.putExtra("PostID", post_key);
                holder.itemView.getContext().startActivity(singleActivity);


            }
        });

    }

    @NonNull
    @Override
    public blogViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_items, parent, false);
        return new blogAdapter.blogViewholder(view);
    }

    public class blogViewholder extends RecyclerView.ViewHolder {
        TextView post_title, post_desc, postUserName;
        ImageView post_image;
        CardView mainLayout;
        public blogViewholder(@NonNull View itemView) {
            super(itemView);
             post_title = itemView.findViewById(R.id.post_title_txtview);
             post_desc = itemView.findViewById(R.id.post_desc_txtview);
             post_image = itemView.findViewById(R.id.post_image);
             postUserName = itemView.findViewById(R.id.post_user);
             mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
