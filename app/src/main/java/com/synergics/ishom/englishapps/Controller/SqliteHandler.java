package com.synergics.ishom.englishapps.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.synergics.ishom.englishapps.Model.ListGamesCategory;
import com.synergics.ishom.englishapps.Model.RestFullObject.GamesCategory;

import java.util.ArrayList;

public class SqliteHandler extends SQLiteOpenHelper {

    private static final String TAG = SqliteHandler.class.getSimpleName();

    private static final int DB_VERSION = 1;

    public static final String DB_NAME = "EnglishApps";


    //================================ Tabel Name ===================================//

    public static final String Tb_GamesCategory = "tbCategory";

    //================================ Kolom Tabel Kategori ===================================//

    public static final String Key_Id_DB = "idGameDB";                      //0
    public static final String Key_Id_Category = "idGameCategory";          //1
    public static final String Key_Id_Learn = "idGameLearn";                //2
    public static final String Key_Id_Date = "idGameDate";                  //3
    public static final String Key_Id_Status = "idGameStatus";              //4

    private  static  final String Create_Tb_GameCategory = "CREATE TABLE " + Tb_GamesCategory + "("
            + Key_Id_DB+ " INTEGER PRIMARY KEY,"
            + Key_Id_Category+ " TEXT,"
            + Key_Id_Learn+ " TEXT,"
            + Key_Id_Date+ " TEXT,"
            + Key_Id_Status+ " TEXT" + ")";

    public SqliteHandler(Context context) {
        super(context, Tb_GamesCategory, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_Tb_GameCategory);
        Log.d(TAG, "Database tables created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Tb_GamesCategory);

        // Create tables again
        onCreate(db);
    }


    public Boolean checkUpdate (String id_category, String updateDate){

        String nullDate = "0000-00-00";

        String selectQuery = "SELECT * FROM " + Tb_GamesCategory + " WHERE " + Key_Id_Category + " = " + id_category;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        while (cursor.moveToNext()){
            String dataDateString = cursor.getString(3);

            if (nullDate.compareTo(dataDateString) < 0){
                nullDate = dataDateString;
            }
        }

        if (nullDate.compareTo(updateDate) < 0){
            return false;
        }else {
            return true;
        }
    }

    public void addItems(GamesCategory gamesCategory){
        Log.i(TAG, "addItems: doing");
        for(GamesCategory.Data data : gamesCategory.data){
            if (checkUpdate(data.id, data.update)){
                for(GamesCategory.Data.Items items : data.items){
                    saveItems(items, data);
                }
            }
        }
    }

    public void saveItems(GamesCategory.Data.Items items, GamesCategory.Data category){
        String selectQuery = "SELECT * FROM " + Tb_GamesCategory + " WHERE " + Key_Id_Learn + " = " + items.id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Log.i(TAG, "saveItems: doing");

        if (cursor.getCount() == 0){

            ContentValues values = new ContentValues();
            values.put(Key_Id_Category, category.id);
            values.put(Key_Id_Learn, items.id);
            values.put(Key_Id_Date, category.update);
            values.put(Key_Id_Status, "0");

            // masukan ke tabel
            db.insert(Tb_GamesCategory, null, values);

            Log.i(TAG, "saveItems: saving");

        }
    }

    public String updateItem(String id){

        String selectQuery = "SELECT * FROM " + Tb_GamesCategory + " WHERE " + Key_Id_Learn + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.getCount() != 0){

            cursor.moveToFirst();

            ContentValues values = new ContentValues();
            values.put(Key_Id_Category, cursor.getString(1));
            values.put(Key_Id_Learn, cursor.getString(2));
            values.put(Key_Id_Date, cursor.getString(3));
            values.put(Key_Id_Status, "1");

            // masukan ke tabel
            long status =  db.update(Tb_GamesCategory, values, Key_Id_Learn + "=" + id, null);

            Log.i(TAG, "saveItems: saving");

            return status + "";

        }

        return "";
    }

    public String getIdGame (String id_category){

        String selectQuery = "SELECT * FROM " + Tb_GamesCategory + " WHERE " + Key_Id_Category + " = " + id_category + " AND " + Key_Id_Status + " = 0 ORDER BY RANDOM() LIMIT 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            return cursor.getString(2);
        }else {
            return null;
        }

    }


    public ArrayList<ListGamesCategory> getGamesCategory(){
        ArrayList<ListGamesCategory> items = new ArrayList<>();

        String array[] = {AppConfig.idAlphabet, AppConfig.idMath, AppConfig.idAnimals, AppConfig.idTransportation, AppConfig.idFamily, AppConfig.idFruit};

        SQLiteDatabase db = this.getReadableDatabase();

        for (int i = 0; i < array.length; i++ ){

            String id = array[i];
            int min = 0;
            int max = 100;

            String selectQuery1 = "SELECT * FROM " + Tb_GamesCategory + " WHERE " + Key_Id_Category + " = " + id + " AND " + Key_Id_Status + " = 1";
            Cursor cursor1 = db.rawQuery(selectQuery1, null);

            String selectQuery2 = "SELECT * FROM " + Tb_GamesCategory + " WHERE " + Key_Id_Category + " = " + id + " AND " + Key_Id_Status + " = 0";
            Cursor cursor2 = db.rawQuery(selectQuery2, null);

            int jumlahDimainkan = cursor1.getCount();
            int jumalahYangBelumDimainkan = cursor2.getCount();
            int total = jumalahYangBelumDimainkan + jumlahDimainkan;

            float percent = 0f;

            if (total != 0){
                percent =(jumlahDimainkan * 100) / total;
            }

            min = jumlahDimainkan;

            items.add(new ListGamesCategory(id, "", percent, max, total));
        }

        return  items;
    }





}
