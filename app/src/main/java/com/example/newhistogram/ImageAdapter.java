package com.example.newhistogram;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.android.AndroidEventTarget;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.LogRecord;


    public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Upload> mUploads;
    private  onlikeclic  mlistener;
    private onshareclick sharelistenr;
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

        holder.NO_likes.setText(String.valueOf(uploadCurrent.getNumber_likes()));


    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textViewName;
        public ImageView imageView;
        public TextView NO_likes;
        private ImageView like;
        private ImageView share;


        public ImageViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_view_name);
            imageView = itemView.findViewById(R.id.image_view_upload);
            NO_likes = itemView.findViewById(R.id.number_of_likes);

            like = itemView.findViewById(R.id.like);
            share=itemView.findViewById(R.id.share);

            like.setOnClickListener(this);
            share.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
        if (mlistener!=null){
            int position=getAdapterPosition();
            if (position!=RecyclerView.NO_POSITION){
                mlistener.onitemclick(position);
            }

        }
        if (sharelistenr!=null){
            int pos=getAdapterPosition();
            if (pos!=RecyclerView.NO_POSITION){
                sharelistenr.onshareiconclicked(pos);
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

    public interface onshareclick{
        void onshareiconclicked(int position);
    }
    public void setonshareclicked(onshareclick sharelistene){
        sharelistenr=sharelistene;
    }

}







/*
        //share
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                try{

                    File file=new File(mUploads.get(position).getImageUrl());
                    MimeTypeMap mim=MimeTypeMap.getSingleton();
                    String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);
                    String type = mim.getMimeTypeFromExtension(ext);
                    Intent sharingIntent = new Intent("android.intent.action.SEND");
                    sharingIntent.setType(type);
                    sharingIntent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file));
                    (Intent.createChooser(sharingIntent, "Share using"));


                }catch (Exception e){

                }
            }
        });*/