package com.warchaser.daggertest.activities;

import android.os.Bundle;
import android.widget.TextView;

import com.warchaser.daggertest.R;
import com.warchaser.daggertest.app.BaseActivity;
import com.warchaser.daggertest.bean.Login;
import com.warchaser.daggertest.bean.Movie;
import com.warchaser.daggertest.bean.MoviesWrapper;
import com.warchaser.daggertest.net.TmdbWebService;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    @Inject
    Login mXiaoMing;

    @BindView(R.id.tv)
    TextView mTv;

    @Inject
    TmdbWebService mService;

    private Unbinder mUnBinder;

    private Disposable mFetchSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnBinder = ButterKnife.bind(this);

        String name = mXiaoMing.getName();
        mTv.setText(name);

        Observable<List<Movie>> observable = mService.popularMovies().map(new Function<MoviesWrapper, List<Movie>>() {
            @Override
            public List<Movie> apply(MoviesWrapper moviesWrapper) throws Exception {
                return moviesWrapper.getMovies();
            }
        });

        mFetchSubscription =  observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Consumer<List<Movie>>() {
            @Override
            public void accept(List<Movie> movies) throws Exception {
                List<Movie> list = movies;
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mUnBinder != null){
            mUnBinder.unbind();
        }

        mUnBinder = null;
    }
}
