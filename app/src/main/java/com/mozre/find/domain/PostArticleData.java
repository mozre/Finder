package com.mozre.find.domain;

import java.io.Serializable;

/**
 * Created by MOZRE on 2016/6/27.
 */
public class PostArticleData implements Serializable{

    private String username;
    private String image;
    private int forward;
    private int review;
    private int flower;
    private String title;
    private String description;
    private String imagePath;
    private String detail;
    private long seconds;
    private String postTime;

    public long getSeconds() {
        return seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUri() {
        return imagePath;
    }

    public void setImageUri(String imageUri) {
        this.imagePath = imageUri;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getForward() {
        return forward;
    }

    public void setForward(int forward) {
        this.forward = forward;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public int getFlower() {
        return flower;
    }

    public void setFlower(int flower) {
        this.flower = flower;
    }

    @Override
    public String toString() {
        return this.title + " " + this.description + " " + this.imagePath + " " + this.detail + " " + this.seconds + " "
                + this.postTime + " " + this.forward + " " + this.review + " " + this.flower + " " + username + " "
                + image;
    }

}
