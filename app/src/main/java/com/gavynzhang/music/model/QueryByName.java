package com.gavynzhang.music.model;

import java.util.List;

/**
 * Created by z.z.hang on 2016/5/14.
 */
public class QueryByName {
    private int showapi_res_code;

    public static class showapi_res_body{
        public static class pagebean{
            private int allNum;
            private int allPages;
            private int currentPage;
            private int maxResult;

            public List<contentlist> contentlists;

            public static class contentlist{

                private long albumid;
                private String albumname;
                private String albumpic_big;
                private String albumpic_small;
                private String downUrl;
                private String m4a;
                private int singerid;
                private String singername;
                private long songid;
                private String songname;

                public long getAlbumid() {
                    return albumid;
                }

                public void setAlbumid(long albumid) {
                    this.albumid = albumid;
                }

                public String getAlbumname() {
                    return albumname;
                }

                public void setAlbumname(String albumname) {
                    this.albumname = albumname;
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

                public String getM4a() {
                    return m4a;
                }

                public void setM4a(String m4a) {
                    this.m4a = m4a;
                }

                public int getSingerid() {
                    return singerid;
                }

                public void setSingerid(int singerid) {
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
            }

            public int getAllNum() {
                return allNum;
            }
            public void setAllNum(int allNum) {
                this.allNum = allNum;
            }

            public int getAllPages() {
                return allPages;
            }
            public void setAllPages(int allPages) {
                this.allPages = allPages;
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
