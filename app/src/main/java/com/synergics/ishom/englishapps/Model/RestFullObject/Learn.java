package com.synergics.ishom.englishapps.Model.RestFullObject;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by asmarasusanto on 10/8/17.
 */

public class Learn {

    @SerializedName("response") public int response;
    @SerializedName("status") public boolean status;
    @SerializedName("message") public String message;
    @SerializedName("data") public List<Data> data;

    public class Data {

        @SerializedName("id") public String id;
        @SerializedName("id_category") public String id_category;
        @SerializedName("name_english") public String name_english;
        @SerializedName("pronounce") public String pronounce;
        @SerializedName("name_indo") public String name_indo;
        @SerializedName("image") public String image;
        @SerializedName("audio") public String audio;
        @SerializedName("date") public String date;

    }

}
