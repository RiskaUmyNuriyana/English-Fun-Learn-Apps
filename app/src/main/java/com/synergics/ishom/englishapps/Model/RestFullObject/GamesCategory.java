package com.synergics.ishom.englishapps.Model.RestFullObject;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by asmarasusanto on 10/8/17.
 */

public class GamesCategory {

    @SerializedName("response") public int response;
    @SerializedName("status") public boolean status;
    @SerializedName("message") public String message;
    @SerializedName("data") public List<Data> data;

    public class Data {

        @SerializedName("id") public String id;
        @SerializedName("name") public String name;
        @SerializedName("jumlah") public int jumlah;
        @SerializedName("update") public String update;
        @SerializedName("items") public List<Items> items;

        public class Items {

            @SerializedName("id") public String id;
            @SerializedName("name") public String name;

        }
    }

}
