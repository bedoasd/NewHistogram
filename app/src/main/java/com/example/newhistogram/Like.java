package com.example.newhistogram;

public class Like {
    String ImageUri;
    int likes_number;

    public Like() {
    }

    public Like(String imageUri, int likes_number) {
        ImageUri = imageUri;
        this.likes_number = likes_number;
    }

    public String getImageUri() {
        return ImageUri;
    }

    public void setImageUri(String imageUri) {
        ImageUri = imageUri;
    }

    public int getLikes_number() {
        return likes_number;
    }

    public void setLikes_number(int likes_number) {

        this.likes_number = likes_number;
    }
}
