package com.example.note_application_neko_ru.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.note_application_neko_ru.adapter.RcvItemList;

import java.util.ArrayList;
import java.util.List;

public class MyDbManager {
    private Context context;
    private MyDbHelper myDbHelper;
    private SQLiteDatabase db;

    public MyDbManager(Context context){
        this.context = context;
        myDbHelper = new MyDbHelper(context);
    }

    public void OpenDb(){
        db = myDbHelper.getWritableDatabase();
    }

    public void InsertToDB(String title, String description, String uri){
        ContentValues cv = new ContentValues();
        cv.put(MyConstants.COLUMN_NAME_TITLE,title);
        cv.put(MyConstants.COLUMN_NAME_DESCRIPTION,description);
        cv.put(MyConstants.COLUMN_NAME_URI,uri);
        db.insert(MyConstants.TABLE_NAME,null,cv);
    }

    public void GetFromDB(String searchtitle, OnDataRecived OnDataReceived){
        List<RcvItemList> TempList = new ArrayList<>();
        String selection = MyConstants.COLUMN_NAME_TITLE + " like ?";
        Cursor cursor = db.query(MyConstants.TABLE_NAME, null, selection, new String[]{"%" + searchtitle + "%"}, null, null, null);

        while (cursor.moveToNext()){
            RcvItemList tempitem = new RcvItemList();
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(MyConstants.COLUMN_NAME_TITLE));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(MyConstants.COLUMN_NAME_DESCRIPTION));
            @SuppressLint("Range") String uri = cursor.getString(cursor.getColumnIndex(MyConstants.COLUMN_NAME_URI));
            @SuppressLint("Range") int _id = cursor.getInt(cursor.getColumnIndex(MyConstants._ID));
            tempitem.setTitle(title);
            tempitem.setDescription(description);
            tempitem.setUri(uri);
            tempitem.setId(_id);
            TempList.add(tempitem);
        }
        cursor.close();

        OnDataReceived.OnReceivedInterface(TempList);
    }

    public void CloseDb(){
        myDbHelper.close();
    }

    public void DeleteFromDb(Integer pos){
        String selection = MyConstants._ID + "=" + pos;
        db.delete(MyConstants.TABLE_NAME, selection, null);
    }

    public void Update(String title, String description, String uri, int id){
        String selection = MyConstants._ID + "=" + id;
        ContentValues cv = new ContentValues();
        cv.put(MyConstants.COLUMN_NAME_TITLE,title);
        cv.put(MyConstants.COLUMN_NAME_DESCRIPTION,description);
        cv.put(MyConstants.COLUMN_NAME_URI,uri);
        db.update(MyConstants.TABLE_NAME, cv, selection, null);
    }
}
