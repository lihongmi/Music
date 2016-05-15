package com.gavynzhang.music.model;

import java.util.List;

/**
 * Created by z.z.hang on 2016/5/14.
 */
public class TopList {

    private int showapi_res_code;

    public static class showapi_res_body{
        public static class pagebean{

            public List<songlist> songlist;

            public List<pagebean.songlist> getSonglist() {
                return songlist;
            }

            public void setSonglist(List<pagebean.songlist> songlist) {
                this.songlist = songlist;
            }

            public static class songlist{

                private long albumid;
                private String albumpic_big;
                private String albumpic_small;
                private String downUrl;
                private int seconds;
                private long singerid;
                private String singername;
                private long songid;
                private String songname;
                private String url;

                public long getAlbumid() {
                    return albumid;
                }

                public void setAlbumid(long albumid) {
                    this.albumid = albumid;
                }

                public String getAlbumpic_big() {
                    return albumpic_big;
                }

                public void setAlbumpic_big(String albumpic_big) {
                    this.albumpic_big = albumpic_big;
                }

                public String getAlbumpic_small() {
                    return albumpic_small;
                }

                public void setAlbumpic_small(String albumpic_small) {
                    this.albumpic_small = albumpic_small;
                }

                public String getDownUrl() {
                    return downUrl;
                }

                public void setDownUrl(String downUrl) {
                    this.downUrl = downUrl;
                }

                public int getSeconds() {
                    return seconds;
                }

                public void setSeconds(int seconds) {
                    this.seconds = seconds;
                }

                public long getSingerid() {
                    return singerid;
                }

                public void setSingerid(long singerid) {
                    this.singerid = singerid;
                }

                public String getSingername() {
                    return singername;
                }

                public void setSingername(String singername) {
                    this.singername = singername;
                }

                public long getSongid() {
                    return songid;
                }

                public void setSongid(long songid) {
                    this.songid = songid;
                }

                public String getSongname() {
                    return songname;
                }

                public void setSongname(String songname) {
                    this.songname = songname;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

            }
        }
    }

    public int getShowapi_res_code() {
        return showapi_res_code;
    }

    public void setShowapi_res_code(int showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }
}
