package com.gavynzhang.music.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gavynzhang.music.R;

/**
 * Created by z.z.hang on 2016/5/14.
 */
public class SongItemAdapter extends RecyclerView.Adapter{

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }




}

/*
public class MyViewHolder extends RecyclerView.ViewHolder{

private View root;
TextView songName;
TextView singerName;
ImageView albumnImage;

public MyViewHolder(View root){
super(root);
songName = (TextView) root.findViewById(R.id.song_name);
singerName = (TextView)root.findViewById(R.id.singer_name);
albumnImage = (ImageView)root.findViewById(R.id.album_image);
}

}

@Override
public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.song_item,null));
}

@Override
public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
MyViewHolder mHolder = onCreateViewHolder()
}

@Override
public int getItemCount() {
return 0;
}
*/



