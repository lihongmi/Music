package com.gavynzhang.music.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by z.z.hang on 2016/5/15.
 */
public class SongDatabaseHelper extends SQLiteOpenHelper {

    public static final String SONG_DATA = "create table song(" +
            "id integer primary key autoincrement," +
            "songname text," +
            "songid text," +
            "singername text," +
            "singerid text," +
            "albumid text," +
            "picbigurl text," +
            "picsmallurl text," +
            "downurl text," +
            "playurl text)";

    private Context mContext;

    public SongDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SONG_DATA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
