package com.gavynzhang.music.model;

import android.graphics.Bitmap;
import android.media.Image;
import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by z.z.hang on 2016/5/14.
 * 序列化，使其能用intent传递
 */
public class Song implements Serializable{

    private String songName;
    private String songid;
    private String singerName;
    private String singerid;
    private String albumid;
    private String albumpic_big_uri;
    private String albumpic_small_uri;
    private String downUrl;
    private String playUrl;

    public String getSongid() {
        return songid;
    }

    public String getSingerid() {
        return singerid;
    }

    public String getAlbumid() {
        return albumid;
    }

    public String getAlbumpic_big_uri() {
        return albumpic_big_uri;
    }

    public String getAlbumpic_small_uri() {
        return albumpic_small_uri;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public String getSongName() {
        return songName;
    }

    public String getSingerName() {
        return singerName;
    }

    public String getPlayUrl(){
        return playUrl;
    }


    public void setData(String songName, String songid, String singerName,
                        String singerid, String albumid, String albumpic_big_uri,
                        String albumpic_small_uri, String downUrl, String playUrl){
        this.songName = songName;
        this.songid = songid;
        this.singerName = singerName;
        this.singerid = singerid;
        this.albumid = albumid;
        this.albumpic_big_uri = albumpic_big_uri;
        this.albumpic_small_uri = albumpic_small_uri;
        this.downUrl = downUrl;
        this.playUrl = playUrl;
    }

    public void setData(String songName, String singerName,String albumpic_big_uri,String albumpic_small_uri,String downUrl,String playUrl){
        this.songName = songName;
        this.singerName = singerName;
        this.albumpic_big_uri = albumpic_big_uri;
        this.albumpic_small_uri = albumpic_small_uri;
        this.downUrl = downUrl;
        this.playUrl = playUrl;
    }


}
