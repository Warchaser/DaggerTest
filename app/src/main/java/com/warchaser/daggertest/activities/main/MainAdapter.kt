package com.warchaser.daggertest.activities.main

import android.content.Context
import android.graphics.Bitmap
import android.support.v7.graphics.Palette
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.transition.Transition
import com.warchaser.daggertest.R
import com.warchaser.daggertest.bean.Movie
import com.warchaser.daggertest.utils.ImageUtils

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife


/**
 * 主页面GridView adapter
 */
class MainAdapter(private val mContext: Context) : BaseAdapter() {
    private val mDataList = ArrayList<Movie>()

    private val mInflater: LayoutInflater
    private val mOptions: RequestOptions

    init {

        mOptions = RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .priority(Priority.HIGH)

        mInflater = LayoutInflater.from(mContext)
    }

    fun notifyDataSetAllChanged(dataList: List<Movie>) {
        if (mDataList != null) {
            mDataList.clear()
            mDataList.addAll(dataList)
        }

        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return mDataList.size
    }

    override fun getItem(position: Int): Any {
        return mDataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val viewHolder: ViewHolder
        val movie = mDataList[position]
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_main_activity_gridview, parent, false)
            viewHolder = ViewHolder(convertView)
            convertView!!.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
        }

        viewHolder.mTvTitle!!.text = movie.title

        Glide.with(mContext)
                .asBitmap()
                .load(ImageUtils.getPosterUri(movie.posterPath))
                .apply(mOptions)
                .into(object : BitmapImageViewTarget(viewHolder.mIvPic!!) {

                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        super.onResourceReady(resource, transition)
                        Palette.from(resource).generate { palette -> setTitleBackgroundColor(palette, viewHolder) }
                    }
                })

        return convertView
    }

    private fun setTitleBackgroundColor(palette: Palette, viewHolder: ViewHolder) {
        //        Palette.Swatch swatch = palette.getVibrantSwatch();
        viewHolder.mTvTitleBackground!!.setBackgroundColor(palette.getVibrantColor(mContext.resources.getColor(R.color.black_translucent_60)))
        //        if(swatch != null){
        //            viewHolder.mTvTitle.setTextColor(swatch.getTitleTextColor());
        //        }
    }

    internal inner class ViewHolder(v: View) {

        /**
         * 电影封面
         */
        @BindView(R.id.iv_pic) lateinit var mIvPic: ImageView

        /**
         * 电影标题
         */
        @BindView(R.id.tv_title) lateinit var mTvTitle: TextView

        /**
         * 电影标题背景
         */
        @BindView(R.id.tv_title_background) lateinit var mTvTitleBackground: View

        init {
            ButterKnife.bind(this, v)
        }
    }
}
