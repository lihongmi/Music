package com.gavynzhang.music.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.gavynzhang.music.R;
import com.gavynzhang.music.db.SongDatabaseHelper;
import com.gavynzhang.music.model.Song;

import java.io.IOException;

/**
 * Created by z.z.hang on 2016/5/15.
 */
public class PlayActivity extends Activity implements View.OnClickListener{

    private SongDatabaseHelper dbHelper;

    private MediaPlayer mPlayer = new MediaPlayer();

    private SongDatabaseHelper mHelper;

    Song song = null;

    ImageView playBack;

    TextView playSongName;
    TextView playSingerName;

    ImageView albumnBigPic;

    ImageView playBigBtn;
    ImageView before;
    ImageView next;

    //ImageView playItem;
    ImageView playDownload;

    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_layout);

        song = (Song) getIntent().getSerializableExtra("song");

        playBack = (ImageView)findViewById(R.id.play_back);

        playSongName = (TextView)findViewById(R.id.play_songname);
        playSingerName = (TextView)findViewById(R.id.play_singername);

        albumnBigPic = (ImageView)findViewById(R.id.albumn_big_pic);

        playBigBtn = (ImageView)findViewById(R.id.play_big_btn);
        before = (ImageView)findViewById(R.id.before);
        next = (ImageView)findViewById(R.id.next);

        playDownload = (ImageView)findViewById(R.id.play_download);

        seekBar = (SeekBar)findViewById(R.id.seekbar);

        playBigBtn.setOnClickListener(this);
        playBack.setOnClickListener(this);
        before.setOnClickListener(this);
        next.setOnClickListener(this);
        playDownload.setOnClickListener(this);
        mHelper = new SongDatabaseHelper(PlayActivity.this,"SongData.db", null,1);
        SQLiteDatabase db1 = mHelper.getWritableDatabase();
        Cursor cursor1 = db1.query("Song", null, null, null, null, null, null);

        int isSave = 0;
        if(cursor1.moveToFirst()){
            do {
                if(song.getSongName().equals(cursor1.getString(cursor1.getColumnIndex("songname")))){
                    isSave = 1;
                }

            }while(cursor1.moveToNext());
        }
        cursor1.close();

        if(isSave == 0){
            saveToDb();
        }

        setData();
        initMediaPlayer();
    }

    public void saveToDb(){
        dbHelper = new SongDatabaseHelper(this,"SongData.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("songname",song.getSongName());
        values.put("songid",song.getSongid());
        values.put("singername",song.getSingerName());
        values.put("singerid",song.getSingerid());
        values.put("albumid",song.getAlbumid());
        values.put("picbigurl",song.getAlbumpic_big_uri());
        values.put("picsmallurl",song.getAlbumpic_small_uri());
        values.put("downurl",song.getDownUrl());
        values.put("playurl",song.getPlayUrl());
        db.insert("Song", null, values);
        values.clear();
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.play_back:
                finish();
                break;
            case R.id.play_big_btn:
                Toast.makeText(PlayActivity.this,"play touched",Toast.LENGTH_SHORT).show();
                mPlayer.start();
                break;
            case R.id.before:
                break;
            case R.id.next:
                break;

            case R.id.play_download:
                break;
        }
    }

    private void setData(){

        RequestQueue mQueue = Volley.newRequestQueue(PlayActivity.this);

        playSongName.setText(song.getSongName());
        playSingerName.setText(song.getSingerName());

        ImageLoader imageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
            }

            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }
        });
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(albumnBigPic, R.mipmap.loading, R.mipmap.fail);
        imageLoader.get( song.getAlbumpic_big_uri(),listener);

    }

//    class MySeekbar implements SeekBar.OnSeekBarChangeListener {
//        public void onProgressChanged(SeekBar seekBar, int progress,
//                                      boolean fromUser) {
//        }
//
//        public void onStartTrackingTouch(SeekBar seekBar) {
//
//        }
//
//        public void onStopTrackingTouch(SeekBar seekBar) {
//            mPlayer.seekTo(seekbar.getProgress());
//            isChanging=false;
//        }
//
//    }

    private void initMediaPlayer() {
        try {
            Toast.makeText(PlayActivity.this,song.getPlayUrl(),Toast.LENGTH_SHORT).show();
            mPlayer.setDataSource(song.getPlayUrl());
        } catch (IOException e) {
            e.printStackTrace();
        }
        mPlayer.prepareAsync();
        mPlayer.stop();
    }

    @Override
    protected void onDestroy(){
        mPlayer.stop();
        super.onDestroy();
    }

}