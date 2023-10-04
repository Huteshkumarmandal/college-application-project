package com.huteshbijay.collegeproject.ViewNotice;

public class NoticeViewDataModel {

    private final int id;

    private final String title;
    private final String department;
    private final String semester;
    private final String description;
    private final String date;

    private final String image_url;


    public NoticeViewDataModel(int id, String title, String department, String semester, String description, String date, String image_url) {
        this.id = id;
        this.title = title;
        this.department = department;
        this.semester = semester;
        this.description = description;
        this.date = date;
        this.image_url = image_url;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDepartment() {
        return department;
    }

    public String getSemester() {
        return semester;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getImageUrl() {
        return image_url;
    }
}
