package com.cyq7on.coursehelper.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.cyq7on.coursehelper.R;
import com.cyq7on.coursehelper.base.ParentWithNaviActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by cyq7on on 18-4-2.
 */

public class NewsDetailActivity extends ParentWithNaviActivity {

    @Bind(R.id.tv_news)
    TextView tvNews;


    @Override
    protected String title() {
        return "语音读报";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        ButterKnife.bind(this);
        initNaviView();

        Bundle bundle = getBundle();
//        bundle.getSerializable("info");
        String news = bundle.getString("info");
        tvNews.setText(news);
    }
}
