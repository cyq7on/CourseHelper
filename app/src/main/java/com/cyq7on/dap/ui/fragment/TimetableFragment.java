package com.cyq7on.dap.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyq7on.dap.base.ParentWithNaviFragment;

/**
 * Created by cyq7on on 18-3-31.
 */

public class TimetableFragment extends ParentWithNaviFragment {
    @Override
    protected String title() {
        return "课程表";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
