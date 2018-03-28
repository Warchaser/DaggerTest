package com.warchaser.daggertest.activities.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.warchaser.daggertest.R;
import com.warchaser.daggertest.bean.Movie;
import com.warchaser.daggertest.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 主页面GridView adapter
 * */
public class MainAdapter extends BaseAdapter{

    private Context mContext;
    private List<Movie> mDataList = new ArrayList<>();

    private RequestOptions mOptions;

    public MainAdapter(Context context){
        this.mContext = context;

        mOptions = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .priority(Priority.HIGH);
    }

    public void notifyDataSetAllChanged(List<Movie> dataList){
        if(mDataList != null){
            mDataList.clear();
            mDataList.addAll(dataList);
        }

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        Movie movie = mDataList.get(position);
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_main_activity_gridview, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mTvTitle.setText(movie.getTitle());

        Glide.with(mContext)
                .asBitmap()
                .load(ImageUtils.getPosterUri(movie.getPosterPath()))
                .apply(mOptions)
                .into(new BitmapImageViewTarget(viewHolder.mIvPic){

                    @Override
                    public void onResourceReady(Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        super.onResourceReady(resource, transition);
                        Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(@NonNull Palette palette) {
                                setTitleBackgroundColor(palette, viewHolder);
                            }
                        });
                    }
                });

        return convertView;
    }

    private void setTitleBackgroundColor(Palette palette, ViewHolder viewHolder){
//        Palette.Swatch swatch = palette.getVibrantSwatch();
        viewHolder.mTvTitleBackground.setBackgroundColor(palette.getVibrantColor(mContext.getResources().getColor(R.color.black_translucent_60)));
//        if(swatch != null){
//            viewHolder.mTvTitle.setTextColor(swatch.getTitleTextColor());
//        }
    }

    class ViewHolder{

        /**
         * 电影封面
         * */
        @BindView(R.id.iv_pic)
        ImageView mIvPic;

        /**
         * 电影标题
         * */
        @BindView(R.id.tv_title)
        TextView mTvTitle;

        /**
         * 电影标题背景
         * */
        @BindView(R.id.tv_title_background)
        View mTvTitleBackground;

        ViewHolder(View v){
            ButterKnife.bind(this, v);
        }
    }
}
