package com.t2porn.beautiful.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.t2porn.beautiful.ui.fragment.DiscoverFragment;
import com.t2porn.beautiful.ui.fragment.MsgFragment;
import com.t2porn.beautiful.ui.fragment.PagerFragment;
import com.t2porn.beautiful.ui.fragment.MoveFragment;
import com.t2porn.beautiful.ui.fragment.PopularFragment;

/**
 * Created by t2porn on 16/5/30.
 */
public class FragmentAdapter extends FragmentStatePagerAdapter {

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    private String[] pageTitle = {"性感","韩日","丝袜","写真"}; //{"热门","发现","设置","流行"}; //发现 , "消息"

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MoveFragment.newInstance(1, 1);
            case 1:
                return MoveFragment.newInstance(1, 2);
            case 2:
                return MoveFragment.newInstance(1, 3);
            case 3:
                return MoveFragment.newInstance(1, 5);
            default:
                return PagerFragment.newInstance(position + "");
        }
       /* switch (position) {
            case 0:
                return new MoveFragment();
            case 1:
                return new DiscoverFragment();
            case 2:
                return new MsgFragment();
          *//*  case 2:
                return new PopularFragment();*//*
            case 3:
                return new PopularFragment();
            default:
                return PagerFragment.newInstance(position + "");
        }*/
    }

    @Override
    public int getCount() {
        return pageTitle.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitle[position];
    }
}
