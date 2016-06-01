package com.t2porn.beautiful.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.t2porn.beautiful.cloud.APIService;
import com.t2porn.beautiful.R;
import com.t2porn.beautiful.bean.ImageItem;
import com.t2porn.beautiful.bean.StaggerItem;
import com.t2porn.beautiful.ui.recycler.AnimRFRecyclerView;
import com.t2porn.beautiful.ui.recycler.AnimRFStaggeredGridLayoutManager;
import com.t2porn.beautiful.ui.recycler.DownImgAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by t2porn on 16/5/30
 */
public class MoveFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    @InjectView(R.id.stagger_view)
    AnimRFRecyclerView mRFRecyclerView;

    private DownImgAdapter mImgLayoutAdapter;
    private List<StaggerItem> mPictures; //图片list
    private Handler mHandler = new Handler();
    private int pictrueLoadNumber = 20;
    private int pictrueNumber;   // 图片的总数量
    private int gallertclassID = 1;
    private Context mContent;
   /* @InjectView(R.id.refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;*/

    //标记加载页数
    private int pageNumber = 1;

    private static Handler handler;

    public MoveFragment(int pageNumber ,int gallertclassID) {
        this.pageNumber = pageNumber;
        this.gallertclassID = gallertclassID;
    }

    public MoveFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stagger, container, false);
        ButterKnife.inject(this, view);
        handler = new Handler();
        initView();
        mContent = getActivity();
        initData();
        return view;
    }

    private void initView() {
        mRFRecyclerView.setLayoutManager(new AnimRFStaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
        mPictures = new ArrayList<>();
        mImgLayoutAdapter = new DownImgAdapter(mPictures);
        mRFRecyclerView.setAdapter(mImgLayoutAdapter);
        mRFRecyclerView.setLoadDataListener(new AnimRFRecyclerView.LoadDataListener() {
            @Override
            public void onRefresh() {
                new Thread(new RefreshRunnable(true)).start();
            }

            @Override
            public void onLoadMore() {
                new Thread(new RefreshRunnable(false)).start();
            }
        });
        //mStaggeredGridView.setAdapter(new StaggerItemAdapter(getActivity(), true));
 /*       mSwipeRefreshLayout.setColorSchemeResources(R.color.blue);
        mSwipeRefreshLayout.setOnRefreshListener(this);*/
    }

    class RefreshRunnable implements Runnable {

        boolean isRefresh;

        public RefreshRunnable(boolean isRefresh) {
            this.isRefresh = isRefresh;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (isRefresh) {
                        initData();
                        refreshComplate();
                        mRFRecyclerView.refreshComplate();
                    } else {
                        loadMoreData();
                        loadMoreComplate();
                        mRFRecyclerView.loadMoreComplate();
                    }
                }
            });
        }
    }

    public void refreshComplate() {
        mRFRecyclerView.getAdapter().notifyDataSetChanged();
    }

    public void loadMoreComplate() {
        /*
         * 用notifyDataSetChanged()的话，加载完重新滚动到顶部的时候会产生错位并自动调整布局，
         * 所以用requestLayout()刷新布局
         */
        // mRecyclerView.getAdapter().notifyDataSetChanged();
        mRFRecyclerView.requestLayout();
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //mSwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), getString(R.string.refresh_success), Toast.LENGTH_SHORT).show();
            }
        }, 3000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacksAndMessages(null);
    }

    //刷新
    private void initData() {
        pageNumber = 1;
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIService.IMAGER_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();

        final APIService apiService = retrofit.create(APIService.class);
        apiService.imagelist(pageNumber, pictrueLoadNumber, gallertclassID)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ImageItem>() {
                            @Override
                            public void onCompleted() {
                                Log.d("wg_log","onCompleted" + pictrueLoadNumber);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("wg_log","onError:" + e.toString());
                            }

                            @Override
                            public void onNext(ImageItem s) {

                                Log.e("wg_log", "total:" + s.total);
                                pictrueNumber = s.total;
                                for(int i = 0; i < pictrueLoadNumber; i++){

                                    mPictures.add(new StaggerItem(
                                            APIService.IMAGET_LOOK + s.mTngou.get(i).img, s.mTngou.get(i).id));
                                }
                                mRFRecyclerView.getAdapter().notifyDataSetChanged();
                            }
                        }
                );
    }

    //下拉加载
    private void loadMoreData() {
        pageNumber ++ ;

        Log.e("wg_log", "pageNumber:"+  pageNumber);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIService.IMAGER_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();

        final APIService apiService = retrofit.create(APIService.class);
        apiService.imagelist(pageNumber, 20, gallertclassID).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ImageItem>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("wg_log","onError:" + e.toString());
                    }

                    @Override
                    public void onNext(ImageItem s) {
                        pictrueNumber -= pictrueLoadNumber;
                        if(pictrueNumber >= pictrueLoadNumber) {
                            for (int i = 0; i < pictrueLoadNumber; i++) {
                                mPictures.add(new StaggerItem(
                                        APIService.IMAGET_LOOK + s.mTngou.get(i).img, s.mTngou.get(i).id));
                            }
                        }else if(pictrueNumber < pictrueLoadNumber && pictrueNumber > 0){
                            for (int i = 0; i < pictrueNumber; i++) {
                                mPictures.add(new StaggerItem(
                                        APIService.IMAGET_LOOK + s.mTngou.get(i).img, s.mTngou.get(i).id));
                            }
                        }else{
                            Toast.makeText(mContent, mContent.getText(R.string.refresh_last), Toast.LENGTH_SHORT).show();
                        }
                        Log.e("wg_log", "pictrueNumber:" + pictrueNumber);
                        mRFRecyclerView.getAdapter().notifyDataSetChanged();
                    }
                });
    }
}
