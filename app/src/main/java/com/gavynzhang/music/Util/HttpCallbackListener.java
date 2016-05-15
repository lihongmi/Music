package com.gavynzhang.music.Util;

/**
 * Created by z.z.hang on 2016/5/14.
 */
public interface HttpCallbackListener {

    void onFinish(String response);

    void onError(Exception e);
}
