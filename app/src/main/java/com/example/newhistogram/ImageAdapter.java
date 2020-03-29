package com.example.newhistogram;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.android.AndroidEventTarget;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;



public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Upload> mUploads;
    private  onlikeclic  mlistener;
    public ImageAdapter(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;

    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, final int position) {
        final Upload uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getName());
        Picasso.with(mContext)
                .load(uploadCurrent.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageView);
        int number=uploadCurrent.getNumber_likes();
        String num=String.valueOf(number);
        holder.NO_likes.setText(num);

    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textViewName;
        public ImageView imageView;
        public TextView NO_likes;
        private Button like;
        private Button share;



        public ImageViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_view_name);
            imageView = itemView.findViewById(R.id.image_view_upload);
            NO_likes = itemView.findViewById(R.id.number_of_likes);

            like = itemView.findViewById(R.id.like);
            like.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
        if (mlistener!=null){
            int position=getAdapterPosition();
            if (position!=RecyclerView.NO_POSITION){
                mlistener.onitemclick(position);
            }
             }

        }
    }
    public interface  onlikeclic{
        void onitemclick(int position);

    }
    public void setonitemclicklistener(onlikeclic listener){
        mlistener=listener;
    }
}











/*   like.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  x = getAdapterPosition();
              }
          });
            Intent intent=new Intent("custom_message");
            intent.putExtra("position",x);
            LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
        }*/
