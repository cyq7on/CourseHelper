package com.cyq7on.coursehelper.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.cyq7on.coursehelper.R;
import com.cyq7on.coursehelper.adapter.ListAdapter;
import com.cyq7on.coursehelper.adapter.OnRecyclerViewListener;
import com.cyq7on.coursehelper.adapter.base.IMutlipleItem;
import com.cyq7on.coursehelper.base.ParentWithNaviFragment;
import com.cyq7on.coursehelper.bean.NewsInfo;
import com.cyq7on.coursehelper.ui.NewsDetailActivity;
import com.cyq7on.coursehelper.util.FileUtils;
import com.google.gson.Gson;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * news
 */

public class NewsFragment extends ParentWithNaviFragment {
    @Bind(R.id.rc_view)
    RecyclerView rcView;
    @Bind(R.id.sw_refresh)
    SwipeRefreshLayout swRefresh;
    ListAdapter adapter;

    @Override
    protected String title() {
        return "新闻列表";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_task_list, container, false);
        ButterKnife.bind(this, rootView);
        initNaviView();
        IMutlipleItem<NewsInfo.NewsBean> iMutlipleItem = new IMutlipleItem<NewsInfo.NewsBean>() {
            @Override
            public int getItemLayoutId(int viewtype) {
                return R.layout.item_news;
            }

            @Override
            public int getItemViewType(int postion, NewsInfo.NewsBean info) {
                return 0;
            }

            @Override
            public int getItemCount(List<NewsInfo.NewsBean> list) {
                return list.size();
            }
        };
        adapter = new ListAdapter(getActivity(),iMutlipleItem,null);
        rcView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rcView.setLayoutManager(layoutManager);
        swRefresh.setEnabled(false);
        setListener();
        return rootView;
    }

    private void setListener(){
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rootView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                swRefresh.setRefreshing(true);
                query();
            }
        });
        swRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                query();
            }
        });
        adapter.setOnRecyclerViewListener(new OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                Bundle bundle = new Bundle();
//                bundle.putSerializable("info", adapter.getItem(position).conent);
                bundle.putString("info", adapter.getItem(position).conent);
                startActivity(NewsDetailActivity.class, bundle);
            }

            @Override
            public boolean onItemLongClick(final int position) {
                return false;
            }
        });
    }

    private void query() {
        String json = FileUtils.getJson(getContext(), "news.json");
        NewsInfo newsInfo = new Gson().fromJson(json, NewsInfo.class);
        adapter.bindDatas(newsInfo.news);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
