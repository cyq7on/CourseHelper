package com.cyq7on.coursehelper.ui;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

import com.cyq7on.coursehelper.R;
import com.cyq7on.coursehelper.base.ParentWithNaviActivity;
import com.orhanobut.logger.Logger;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by cyq7on on 18-4-2.
 */

public class NewsDetailActivity extends ParentWithNaviActivity {

    @Bind(R.id.tv_news)
    TextView tvNews;
    private TextToSpeech textToSpeech;


    @Override
    protected String title() {
        return "语音读报";
    }

    @Override
    public Object right() {
        return R.drawable.laba2;
    }

    @Override
    public ParentWithNaviActivity.ToolBarListener setToolBarListener() {
        return new ParentWithNaviActivity.ToolBarListener() {
            @Override
            public void clickLeft() {
                finish();
            }

            @Override
            public void clickRight() {
                Logger.d("ddd");
                textToSpeech.speak(tvNews.getText().toString(),
                        TextToSpeech.QUEUE_ADD, null);
            }
        };
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
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.CHINA);
                    Logger.d(result + "");
                    if (result != TextToSpeech.LANG_COUNTRY_AVAILABLE
                            && result != TextToSpeech.LANG_AVAILABLE){
                        Toast.makeText(getApplicationContext(), "暂时无法语音播报！",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        textToSpeech.shutdown();
    }
}
