package com.t2porn.beautiful.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.t2porn.beautiful.R;

import butterknife.ButterKnife;

/**
 * Created by Kay on 15/4/20.
 */
public class DiscoverFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

}
