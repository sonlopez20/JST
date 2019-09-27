package com.example.jst;

public class Years {
    String title, amount, description, date, category, postimage, uid;

    public Years()
    {

    }

    public Years(String title, String amount, String description, String date, String category, String postimage, String uid) {
        this.title = title;
        this.description = description;
        this.postimage = postimage;
        this.amount = amount;
        this.date = date;
        this.uid = uid;
        this.category = category;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String username) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostimage() {
        return postimage;
    }

    public void setPostimage(String postimage) {
        this.postimage = postimage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String time) { this.amount = amount; }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
