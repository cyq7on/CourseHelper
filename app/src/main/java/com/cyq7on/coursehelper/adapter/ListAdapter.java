package com.cyq7on.coursehelper.adapter;

import android.content.Context;

import com.cyq7on.coursehelper.R;
import com.cyq7on.coursehelper.adapter.base.BaseRecyclerAdapter;
import com.cyq7on.coursehelper.adapter.base.BaseRecyclerHolder;
import com.cyq7on.coursehelper.adapter.base.IMutlipleItem;
import com.cyq7on.coursehelper.bean.NewsInfo;

import java.util.Collection;

/**
 * Created by cyq7on on 18-3-22.
 */

public class ListAdapter extends BaseRecyclerAdapter<NewsInfo.NewsBean> {
    /**
     * 支持一种或多种Item布局
     *
     * @param context
     * @param items
     * @param datas
     */
    public ListAdapter(Context context, IMutlipleItem<NewsInfo.NewsBean> items, Collection<NewsInfo.NewsBean> datas) {
        super(context, items, datas);
    }

    @Override
    public void bindView(BaseRecyclerHolder holder, NewsInfo.NewsBean info, int position) {
        holder.setText(R.id.tv_recent_name,info.title);
    }
}
