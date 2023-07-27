package com.huteshbijay.collegeproject;

public class PdfItem {
    private String title;
    private String url;

    public PdfItem() {
        // Default constructor required for Firebase Realtime Database
    }

    public PdfItem(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
