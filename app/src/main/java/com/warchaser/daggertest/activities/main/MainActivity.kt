package com.warchaser.daggertest.activities.main

import android.os.Bundle
import android.widget.GridView

import com.warchaser.daggertest.R
import com.warchaser.daggertest.app.BaseActivity
import com.warchaser.daggertest.bean.Login
import com.warchaser.daggertest.bean.Movie
import com.warchaser.daggertest.bean.MoviesWrapper
import com.warchaser.daggertest.net.TmdbWebService
import com.warchaser.daggertest.utils.RxUtils

import javax.inject.Inject

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.*

class MainActivity : BaseActivity() {

    @JvmField
    @Inject
    var mXiaoMing : Login? = null

//    @BindView(R.id.tv) lateinit var  mTv : TextView

    @BindView(R.id.grid_view) lateinit var mGridView : GridView

    @Inject
    @JvmField
    var mService : TmdbWebService? = null

    private var mUnBinder : Unbinder? = null

    private var mFetchSubscription : Disposable? = null

    private var mAdapter : MainAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        mUnBinder = ButterKnife.bind(this)
        mAdapter = MainAdapter(this)
        mGridView.adapter = mAdapter

//        val name : String = mXiaoMing.name
//        mTv.text = name

        val observable : Observable<List<Movie>> = mService!!.popularMovies().map(object : Function<MoviesWrapper, List<Movie>> {
            override fun apply(moviesWrapper : MoviesWrapper) : List<Movie> {
                return moviesWrapper.movies
            }
        })

        mFetchSubscription = observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                object : Consumer<List<Movie>>{

                    override fun accept(t: List<Movie>?) {
                        mAdapter!!.notifyDataSetAllChanged(t!!)
                    }
                },
                object : Consumer<Throwable>{

                    override fun accept(t: Throwable?) {
                        t!!.printStackTrace()
                    }
                }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        clearFindViewByIdCache()

        if(mUnBinder != null){
            mUnBinder?.unbind()
        }

        mUnBinder = null

        RxUtils.unsubscribe(mFetchSubscription!!)
        mFetchSubscription = null

        mAdapter = null
    }

}
