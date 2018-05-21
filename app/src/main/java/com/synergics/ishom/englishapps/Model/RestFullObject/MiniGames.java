package com.synergics.ishom.englishapps.Model.RestFullObject;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by asmarasusanto on 10/8/17.
 */

public class MiniGames {

    @SerializedName("response") public int response;
    @SerializedName("status") public boolean status;
    @SerializedName("message") public String message;
    @SerializedName("data") public Data data;

    public class Data {

        @SerializedName("id") public String id;
        @SerializedName("name") public String name;
        @SerializedName("image") public String image;
        @SerializedName("audio") public String audio;
        @SerializedName("spell") public List<Spell> spells;
        @SerializedName("char") public List<Char> chars;

        public class Spell {
            @SerializedName("char") public String chracter;
            @SerializedName("status") public int status;
        }

        public class Char {
            @SerializedName("char") public String chracter;
            @SerializedName("status") public int status;
        }

    }

}
