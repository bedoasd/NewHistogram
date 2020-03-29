package com.example.newhistogram;


public class Upload {


        private String mName;
        private String mImageUrl;
        private int number_likes;
        private String id;



    public Upload() {
        //empty constructor needed
    }

    public Upload(String name, String imageUrl,int number_likes1,String idd) {
        if (name.trim().equals("")) {
            name = "No Name";
        }

        mName = name;
        mImageUrl = imageUrl;
        number_likes=number_likes1;
        id=idd;

    }






        public String getName() {
            return mName;
        }

        public void setName(String name) {
            mName = name;
        }

        public String getImageUrl() {
            return mImageUrl;
        }

        public void setImageUrl(String imageUrl) {
            mImageUrl = imageUrl;
        }

    public int getNumber_likes() {
        return number_likes;
    }

    public int setNumber_likes(int number_likes) {
        this.number_likes = number_likes;
        return number_likes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}