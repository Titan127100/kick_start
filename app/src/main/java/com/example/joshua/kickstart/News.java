package com.example.joshua.kickstart;

public class News {

    private String description, image, longDescription, title;

    public News(){

    }

    public News(String description, String image, String longDescription, String title) {
        this.description = description;
        this.image = image;
        this.longDescription = longDescription;
        this.title = title;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
