package com.example.newhistogram;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;

import android.view.View;

import android.widget.ImageView;
import android.widget.ProgressBar;

import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
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

public class ImagesActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
   private ImageAdapter mAdapter;
    private ProgressBar mProgressCircle;
    private DatabaseReference mDatabaseRef;
    private FirebaseDatabase mfiredatabase;
    // I prefer to decalre variables to namess describe them
    // uploadsList
    private List<Upload> uploads ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);


        // try to read about ButterKnife if you do't know MVVM
        // but you (must) learn MVVM
        // databinding and Butterknife is the alternative of findViewBy Id
        // but databinding used with mvvm, so I asked you to learn Butterknife
        // if you don't know MVVM
        // search about Coding with nerds on youtupe
        mRecyclerView = findViewById(R.id.recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);


        mProgressCircle = findViewById(R.id.progress_circle);
        mfiredatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mfiredatabase.getReference("uploads");


        // hwa enta machy ma3 firebase ma3 meen ?
      // kza 7d l3aytlma t4t3l m3ya zay coding
        // here you retreive the image data (name and URL ) from the firebase database
        // but you save the url in firebase database
        // and saved the image in FireBaseStorage
        // you get the error ?
        // you must Retrieve the image from the Storage Firebase itself
        // here you just retreive the url ok how can i connect mobilr to run?

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
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ImagesActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
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






