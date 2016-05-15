package com.gavynzhang.music.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.gavynzhang.music.R;
import com.gavynzhang.music.adapter.SongItemAdapter;
import com.gavynzhang.music.model.Song;
import com.gavynzhang.music.model.TopList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class MainActivity extends Activity implements View.OnClickListener {

    private MediaPlayer mPlayer = new MediaPlayer();

    //请求参数
    public static final String hotList = "http://route.showapi.com/213-4";
    public static final String queryByName = "http://route.showapi.com/213-1";
    public static final String appId = "18986";
    public static final String secret = "c83912157f3c4a168d77e47b72fd90da";

    //热门榜单请求id
    public int westHotId = 3;
    public int chinaHotId = 5;
    public int hktwHotId = 6;
    public int koreanHotId = 16;
    public int japanHotId = 17;
    public int countryHotId = 18;
    public int rockHotId = 19;
    public int saleHotId = 23;
    public int hotId = 26;

    //热门榜单对象集合
    public List<Song> westHotSong = new ArrayList<>();
    public List<Song> chinaHotSong = new ArrayList<>();
    public List<Song> hktwHotSong = new ArrayList<>();
    public List<Song> koreanHotSong = new ArrayList<>();
    public List<Song> japanHotSong = new ArrayList<>();
    public List<Song> countryHotSong = new ArrayList<>();
    public List<Song> rockHotSong = new ArrayList<>();
    public List<Song> saleHotSong = new ArrayList<>();
    public List<Song> hotSong = new ArrayList<>();

    //搜索结果
    public List<Song> searchSong = new ArrayList<>();

    //单曲
    RecyclerView topSongRecyclerView;

    //其他热榜，已设置不可见，点击其他按钮（TextView）将其设置为可见
    RelativeLayout othersBtn;
    RelativeLayout title;
    RelativeLayout hotNameBar;
    RelativeLayout searchBarR;
    RelativeLayout otherHot;


    //点此打开播放列表
    FloatingActionButton fab;

    //选择热榜
    TextView west;
    TextView china;
    TextView hktw;
    TextView otherOption;
    TextView korean;
    TextView japan;
    TextView country;
    TextView rock;
    TextView sale;
    TextView hot;

    ImageView search_btn;
    ImageView playBtn;

    EditText keyword;
    Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        west = (TextView) findViewById(R.id.west);
        china = (TextView) findViewById(R.id.china);
        hktw = (TextView) findViewById(R.id.hk_tw);
        otherOption = (TextView) findViewById(R.id.other_btn);
        korean = (TextView) findViewById(R.id.korean_hot_btn);
        japan = (TextView) findViewById(R.id.japan_hot_btn);
        country = (TextView) findViewById(R.id.country_hot_btn);
        rock = (TextView) findViewById(R.id.rock_hot_btn);
        sale = (TextView) findViewById(R.id.sale_hot_btn);
        hot = (TextView) findViewById(R.id.hot_btn);

        keyword = (EditText)findViewById(R.id.search_text);
        search = (Button)findViewById(R.id.search);

        search_btn = (ImageView)findViewById(R.id.search_Btn);

        topSongRecyclerView = (RecyclerView) findViewById(R.id.top_song_recyclerview);

        title = (RelativeLayout)findViewById(R.id.title);
        othersBtn = (RelativeLayout) findViewById(R.id.other_top_btn);
        hotNameBar = (RelativeLayout)findViewById(R.id.hot_name_bar);
        searchBarR = (RelativeLayout)findViewById(R.id.search_bar_r);
        otherHot = (RelativeLayout)findViewById(R.id.rh);

        fab.setOnClickListener(this);
        search_btn.setOnClickListener(this);

        search.setOnClickListener(this);

        west.setOnClickListener(this);
        china.setOnClickListener(this);
        hktw.setOnClickListener(this);
        otherOption.setOnClickListener(this);
        korean.setOnClickListener(this);
        japan.setOnClickListener(this);
        country.setOnClickListener(this);
        rock.setOnClickListener(this);
        sale.setOnClickListener(this);
        hot.setOnClickListener(this);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest westRequest = new StringRequest(hotList + "?showapi_appid=" + appId + "&topid=" + westHotId + "&showapi_sign=" + secret,
                new Response.Listener<String>() {
                    public void onResponse(String result) {
                        new parseJSON(result.toString(), westHotSong,1);
                        initData(westHotSong);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("top", "请求失败");
            }
        });
        requestQueue.add(westRequest);



    }

    private void initData(List<Song> song) {
        topSongRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        topSongRecyclerView.setAdapter(new SongAdapter(song));
    }

    //Adapter
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

            RequestQueue mQueue = Volley.newRequestQueue(MainActivity.this);

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
                    Intent intent = new Intent(MainActivity.this,PlayActivity.class);
                    intent.putExtra("song",songData);
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            Log.d("Test","song.size()");
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

    private void initMediaPlayer(String url) {
        try {
            mPlayer.setDataSource(url);
            mPlayer.prepareAsync();
            mPlayer.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 解析JSON并返回Song对象数组
     */
    public class parseJSON {

        public parseJSON(String jsonData, List<Song> song, int requestCode) {

            try {
                JSONArray songlist = null;

                JSONObject jsonObject = new JSONObject(jsonData.toString()).getJSONObject("showapi_res_body");
                JSONObject jsonObject1 = jsonObject.getJSONObject("pagebean");
                if(requestCode == 1) {
                    songlist = jsonObject1.getJSONArray("songlist");
                }else{
                    songlist = jsonObject1.getJSONArray("contentlist");
                }

                for (int i = 0; i < songlist.length(); i++) {
                    song.add(new Song());
                }

                for (int i = 0; i < songlist.length(); i++) {

                    String tmpUrl = null;

                    JSONObject tmpObj = songlist.getJSONObject(i);
                    String tmpSongName = tmpObj.optString("songname");
                    String tmpSongId = tmpObj.optString("songid");
                    String tmpSingerName = tmpObj.optString("singername");
                    String tmpSingerId = tmpObj.optString("singerid");
                    String tmpAlbumId = tmpObj.optString("albumid");
                    String tmpAlbumpic_big_uri = tmpObj.optString("albumpic_big");
                    String tmpAlbumpic_small_uri = tmpObj.optString("albumpic_small");
                    String tmpDownUrl = tmpObj.optString("downUrl");
                    if(requestCode == 1) {
                        tmpUrl = tmpObj.optString("url");
                    }else{
                        tmpUrl = tmpObj.optString("m4a");
                    }

                    //Toast.makeText(MainActivity.this, tmpSongName + tmpSongId + tmpSingerName + tmpSingerId + tmpAlbumId + tmpAlbumpic_big_uri + tmpAlbumpic_small_uri + tmpDownUrl, Toast.LENGTH_SHORT).show();
                    song.get(i).setData(tmpSongName, tmpSongId, tmpSingerName, tmpSingerId, tmpAlbumId, tmpAlbumpic_big_uri, tmpAlbumpic_small_uri, tmpDownUrl, tmpUrl);


                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }



    /**
     * 设置监听事件
     */
    public void onClick(View v) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        switch (v.getId()) {

            case R.id.west:
                StringRequest westRequest = new StringRequest(hotList + "?showapi_appid=" + appId + "&topid=" + westHotId + "&showapi_sign=" + secret,
                        new Response.Listener<String>() {
                            public void onResponse(String result) {
                                new parseJSON(result.toString(), westHotSong, 1);
                                initData(westHotSong);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("top", "请求失败");
                    }
                });
                requestQueue.add(westRequest);
                Log.d("MainActivity", "song.size()" + westHotSong.size());
                break;

            case R.id.china:
                StringRequest chinaRequest = new StringRequest(hotList + "?showapi_appid=" + appId + "&topid=" + chinaHotId + "&showapi_sign=" + secret,
                        new Response.Listener<String>() {
                            public void onResponse(String result) {
                                new parseJSON(result.toString(),chinaHotSong,1);
                                initData(chinaHotSong);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("top", "请求失败");
                    }
                });
                requestQueue.add(chinaRequest);
                break;

            case R.id.hk_tw:
                StringRequest hktwRequest = new StringRequest(hotList + "?showapi_appid=" + appId + "&topid=" + hktwHotId + "&showapi_sign=" + secret,
                        new Response.Listener<String>() {
                            public void onResponse(String result) {
                                new parseJSON(result.toString(),hktwHotSong,1);
                                initData(hktwHotSong);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("top", "请求失败");
                    }
                });
                requestQueue.add(hktwRequest);
                break;

            case R.id.other_btn:
                topSongRecyclerView.setVisibility(View.GONE);
                othersBtn.setVisibility(View.VISIBLE);
                break;

            case R.id.korean_hot_btn:
                StringRequest koreanRequest = new StringRequest(hotList + "?showapi_appid=" + appId + "&topid=" + koreanHotId + "&showapi_sign=" + secret,
                        new Response.Listener<String>() {
                            public void onResponse(String result) {
                                new parseJSON(result.toString(),koreanHotSong,1);
                                initData(koreanHotSong);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("top", "请求失败");
                    }
                });
                requestQueue.add(koreanRequest);
                othersBtn.setVisibility(View.GONE);
                topSongRecyclerView.setVisibility(View.VISIBLE);
                break;

            case R.id.japan_hot_btn:
                StringRequest japanRequest = new StringRequest(hotList + "?showapi_appid=" + appId + "&topid=" + japanHotId + "&showapi_sign=" + secret,
                        new Response.Listener<String>() {
                            public void onResponse(String result) {
                                new parseJSON(result.toString(),japanHotSong,1);
                                initData(japanHotSong);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("top", "请求失败");
                    }
                });
                requestQueue.add(japanRequest);
                othersBtn.setVisibility(View.GONE);
                topSongRecyclerView.setVisibility(View.VISIBLE);
                break;

            case R.id.country_hot_btn:
                StringRequest countryRequest = new StringRequest(hotList + "?showapi_appid=" + appId + "&topid=" + countryHotId + "&showapi_sign=" + secret,
                        new Response.Listener<String>() {
                            public void onResponse(String result) {
                                new parseJSON(result.toString(),countryHotSong,1);
                                initData(countryHotSong);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("top", "请求失败");
                    }
                });
                requestQueue.add(countryRequest);
                othersBtn.setVisibility(View.GONE);
                topSongRecyclerView.setVisibility(View.VISIBLE);
                break;

            case R.id.rock_hot_btn:
                StringRequest rockRequest = new StringRequest(hotList + "?showapi_appid=" + appId + "&topid=" + rockHotId + "&showapi_sign=" + secret,
                        new Response.Listener<String>() {
                            public void onResponse(String result) {
                                new parseJSON(result.toString(),rockHotSong,1);
                                initData(rockHotSong);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("top", "请求失败");
                    }
                });
                requestQueue.add(rockRequest);
                othersBtn.setVisibility(View.GONE);
                topSongRecyclerView.setVisibility(View.VISIBLE);
                break;

            case R.id.sale_hot_btn:
                StringRequest saleRequest = new StringRequest(hotList + "?showapi_appid=" + appId + "&topid=" + saleHotId + "&showapi_sign=" + secret,
                        new Response.Listener<String>() {
                            public void onResponse(String result) {
                                new parseJSON(result.toString(),saleHotSong,1);
                                initData(saleHotSong);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("top", "请求失败");
                    }
                });
                requestQueue.add(saleRequest);
                othersBtn.setVisibility(View.GONE);
                topSongRecyclerView.setVisibility(View.VISIBLE);
                break;

            case R.id.hot_btn:
                StringRequest hotRequest = new StringRequest(hotList + "?showapi_appid=" + appId + "&topid=" + hotId + "&showapi_sign=" + secret,
                        new Response.Listener<String>() {
                            public void onResponse(String result) {
                                new parseJSON(result.toString(),hotSong,1);
                                initData(hotSong);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("top", "请求失败");
                    }
                });
                requestQueue.add(hotRequest);
                othersBtn.setVisibility(View.GONE);
                topSongRecyclerView.setVisibility(View.VISIBLE);
                break;

            case R.id.fab:

                Intent intent = new Intent(MainActivity.this,SavedList.class);
                startActivity(intent);

                break;
            case R.id.search_Btn:
                title.setVisibility(View.GONE);
                hotNameBar.setVisibility(View.GONE);
                topSongRecyclerView.setVisibility(View.GONE);
                searchBarR.setVisibility(View.VISIBLE);
                break;
            case R.id.search:
                othersBtn.setVisibility(View.GONE);
                topSongRecyclerView.setPadding(0, 160, 0, 0);
                otherHot.setVisibility(View.GONE);
                if(keyword.getText().toString() != null) {
                    String tmpText = keyword.getText().toString();
                    Toast.makeText(MainActivity.this,tmpText,Toast.LENGTH_SHORT).show();
                    StringRequest searchRequest = new StringRequest(queryByName + "?showapi_appid=" + appId + "&keyword=" + tmpText + "&showapi_sign=" + secret,
                            new Response.Listener<String>() {
                                public void onResponse(String result) {
                                    new parseJSON(result.toString(), searchSong,2);
                                    initData(searchSong);
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Log.d("top", "请求失败");
                        }
                    });
                    requestQueue.add(searchRequest);
                    topSongRecyclerView.setVisibility(View.VISIBLE);
                }else{
                    Toast.makeText(MainActivity.this,"请输入内容",Toast.LENGTH_SHORT).show();
                }

        }
    }

    public void onBackPressed(){
        if(searchBarR.getVisibility() == View.VISIBLE){
            searchBarR.setVisibility(View.GONE);
            title.setVisibility(View.VISIBLE);
            hotNameBar.setVisibility(View.VISIBLE);
            topSongRecyclerView.setPadding(0, 0, 0, 0);
            topSongRecyclerView.setVisibility(View.VISIBLE);
        }else{
            topSongRecyclerView.setPadding(0,0,0,0);
            finish();
        }
    }


}
