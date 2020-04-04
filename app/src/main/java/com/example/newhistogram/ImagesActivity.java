package com.example.newhistogram;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;

import android.view.View;

import android.widget.ProgressBar;

import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImagesActivity extends AppCompatActivity implements ImageAdapter.onlikeclic {

    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    private ProgressBar mProgressCircle;
    private DatabaseReference mDatabaseRef;


    private List<Upload> uploads ;

    private int position_of_image;







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
        int num1=uploads.get(position_of_image).getNumber_likes();
        uploads.get(position_of_image).setNumber_likes(num1+1);

       String id=uploads.get(position_of_image).getId();
       int number= uploads.get(position_of_image).getNumber_likes();
       String name= uploads.get(position_of_image).getName();
       String url=uploads.get(position_of_image).getImageUrl();

       //updating the tables

     Map<String,Object> map=new HashMap<>();
     map.put(id,new Upload(name,url,number,id));
    //  mDatabaseRef.child(id).child("number_likes").setValue(number);
        mDatabaseRef.updateChildren(map);



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






