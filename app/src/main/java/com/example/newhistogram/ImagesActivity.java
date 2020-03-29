package com.example.newhistogram;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Image;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.widget.ImageView;
import android.widget.ProgressBar;

import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

public class ImagesActivity extends AppCompatActivity implements ImageAdapter.onlikeclic {

    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    private ProgressBar mProgressCircle;
    private DatabaseReference mDatabaseRef;
    private FirebaseDatabase mfiredatabase;

    private List<Upload> uploads ;

    private int position_of_image;
    String image_liked_url;
    int No_likes=0;

  //  firebase for like node
    private DatabaseReference   likereference;
    private  FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);


        mRecyclerView = findViewById(R.id.recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);


        mProgressCircle = findViewById(R.id.progress_circle);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");



        uploads = new ArrayList<>();

        mDatabaseRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    uploads.add(upload);
                }

                mAdapter = new ImageAdapter(ImagesActivity.this, uploads);

                mRecyclerView.setAdapter(mAdapter);
                mAdapter.setonitemclicklistener(ImagesActivity.this);
                mProgressCircle.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ImagesActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }

    ///interface to get position
    @Override
    public void onitemclick(int position) {
        position_of_image=position;
        int num=uploads.get(position_of_image).getNumber_likes();
        int num2=num++;
        int num1=uploads.get(position_of_image).setNumber_likes(num2);
        Toast.makeText(this, ""+num1, Toast.LENGTH_SHORT).show();

    }


}


       /* @Override
        protected void onStart () {
            super.onStart();
            FirebaseRecyclerAdapter<Upload,> adapter = new FirebaseRecyclerAdapter<Upload, ImageHolder>(
                    Upload.class,
                    R.layout.image_item,
                    ImageHolder.class,
                    mDatabaseRef
            ) {
                @Override
                protected void populateViewHolder(ImageHolder imageHolder, Upload upload, int i) {

                    imageHolder.SetName(upload.getName());
                    imageHolder.SetImage(upload.getImageUrl());

                }
            };
            mRecyclerView.setAdapter(adapter);
        }
    }
        public class ImageHolder extends RecyclerView.ViewHolder {

            View view;

            public ImageHolder(@NonNull View itemView) {

                super(itemView);
                view = itemView;
            }

            private void SetName(String name) {
                TextView tv_name = view.findViewById(R.id.text_view_name);
                tv_name.setText(name);
            }

            private void SetImage(final String ImageUrl) {
                final ImageView image = view.findViewById(R.id.image_view_upload);
                Picasso.get().load(ImageUrl).networkPolicy(NetworkPolicy.OFFLINE).into(image, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.get().load(ImageUrl).into(image);
                    }


                });
            }
        }*/






