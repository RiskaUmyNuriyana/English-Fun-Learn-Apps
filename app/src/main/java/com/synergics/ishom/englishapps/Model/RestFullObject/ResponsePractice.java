package com.synergics.ishom.englishapps.Model.RestFullObject;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by asmarasusanto on 10/8/17.
 */

public class ResponsePractice {

    @SerializedName("id") private String id;
    @SerializedName("number") private int number;
    @SerializedName("type") private int type;
    @SerializedName("question") private String question;
    @SerializedName("image") private String image;
    @SerializedName("audio") private String audio;
    @SerializedName("options") private List<Option> options;

    public class Option {
        @SerializedName("answer") private String answer;
        @SerializedName("status") private int status;

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

    }

    @SerializedName("answer") private String answer;
    @SerializedName("status") private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
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

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
