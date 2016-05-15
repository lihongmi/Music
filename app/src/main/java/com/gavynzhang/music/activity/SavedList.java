package com.gavynzhang.music.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.gavynzhang.music.R;
import com.gavynzhang.music.db.SongDatabaseHelper;
import com.gavynzhang.music.model.Song;

import java.util.ArrayList;
import java.util.List;

public class SavedList extends Activity {

    RecyclerView savedList;

    private SongDatabaseHelper mHelper;

    public List<Song> savedSongs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_list);

        mHelper = new SongDatabaseHelper(SavedList.this,"SongData.db", null,1);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        Cursor cursor1 = db.query("Song", null, null, null, null, null, null);

        int j = 0;
        if(cursor1.moveToFirst()){
            do {
                savedSongs.add(new Song());
                j++;
            }while(cursor1.moveToNext());
        }
        cursor1.close();
        Toast.makeText(SavedList.this,"  "+j,Toast.LENGTH_SHORT).show();

        Cursor cursor = db.query("Song", null, null, null, null, null, null);
        int i = 0;
        if(cursor.moveToFirst()){
            do{
                try {
                    String songName = cursor.getString(cursor.getColumnIndex("songname"));
                    String picBigUrl = cursor.getString(cursor.getColumnIndex("picbigurl"));
                    String downUrl = cursor.getString(cursor.getColumnIndex("downurl"));
                    String singerName = cursor.getString(cursor.getColumnIndex("singername"));
                    String picSmallUrl = cursor.getString(cursor.getColumnIndex("picsmallurl"));
                    String playUrl = cursor.getString(cursor.getColumnIndex("playurl"));
//                    Toast.makeText(SavedList.this,downUrl,Toast.LENGTH_SHORT).show();
                    savedSongs.get(i).setData(songName, singerName, picBigUrl, picSmallUrl, downUrl, playUrl);
                }catch (Exception e){
                    e.printStackTrace();
                }
                i++;
            }while(cursor.moveToNext());
        }
        Toast.makeText(SavedList.this," "+i,Toast.LENGTH_SHORT).show();
        cursor.close();

        if(savedSongs.size() == 0) {
            Toast.makeText(SavedList.this, "无收藏", Toast.LENGTH_SHORT).show();
        }

        savedList = (RecyclerView)findViewById(R.id.saved_song_recyclerview);
        initData(savedSongs);

    }

    private void initData(List<Song> song) {
        savedList.setLayoutManager(new LinearLayoutManager(SavedList.this));
        savedList.setAdapter(new SongAdapter(song));
    }

    class SongAdapter extends RecyclerView.Adapter {

        List<Song> song;

        public SongAdapter(List<Song> song) {
            this.song = song;
        }

        @Override
        public SongAdapter.SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_item, null);
            SongViewHolder viewHolder = new SongViewHolder(view);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            SongViewHolder vh = (SongViewHolder)holder;

            final Song songData = song.get(position);

            RequestQueue mQueue = Volley.newRequestQueue(SavedList.this);

            ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());

            ImageLoader.ImageListener listener = ImageLoader.getImageListener(vh.getAlbumImage(), R.mipmap.loading, R.mipmap.fail);

            try {
                vh.getSongName().setText(songData.getSongName());
                vh.getSingerName().setText(songData.getSingerName());
                imageLoader.get(songData.getAlbumpic_small_uri(), listener);
            }catch (NullPointerException e){
                e.printStackTrace();
            }

            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SavedList.this,PlayActivity.class);
                    intent.putExtra("song",songData);
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            Log.d("Test", "song.size()");
            return song.size();
        }

        public class SongViewHolder extends RecyclerView.ViewHolder {

            private ImageView albumImage;
            private TextView songName;
            private TextView singerName;

            public SongViewHolder(View itemView) {
                super(itemView);

                albumImage = (ImageView) itemView.findViewById(R.id.album_image);
                songName = (TextView) itemView.findViewById(R.id.song_name);
                singerName = (TextView) itemView.findViewById(R.id.singer_name);
            }

            public ImageView getAlbumImage(){
                return albumImage;
            }

            public TextView getSongName(){
                return songName;
            }

            public TextView getSingerName(){
                return singerName;
            }


        }

    }

    public class BitmapCache implements ImageLoader.ImageCache {

        private LruCache<String, Bitmap> mCache;

        public BitmapCache() {
            int maxSize = 20 * 1024 * 1024;
            mCache = new LruCache<String, Bitmap>(maxSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    return bitmap.getRowBytes() * bitmap.getHeight();
                }
            };
        }

        @Override
        public Bitmap getBitmap(String url) {
            return mCache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            mCache.put(url, bitmap);
        }

    }
}
