package com.synergics.ishom.englishapps.Model;

public class Learn {

    private String id;
    private String english;
    private String pronounce;
    private String indo;
    private String image;
    private String audio;

    public Learn(String id, String english, String pronounce, String indo, String image, String audio) {
        this.id = id;
        this.english = english;
        this.pronounce = pronounce;
        this.indo = indo;
        this.image = image;
        this.audio = audio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getPronounce() {
        return pronounce;
    }

    public void setPronounce(String pronounce) {
        this.pronounce = pronounce;
    }

    public String getIndo() {
        return indo;
    }

    public void setIndo(String indo) {
        this.indo = indo;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }
}
