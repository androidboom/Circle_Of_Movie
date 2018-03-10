package com.example.liubo.world_of_movie.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.liubo.world_of_movie.BaseView.MyScrollView;
import com.example.liubo.world_of_movie.R;

/**
 * Created by Liubo on 2017/12/27.
 */

public class FragmentMe extends Fragment {
    private View headerView;
    private ImageView imgView;
    private View view;
    private MyScrollView scrollview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_me,container,false);
        init();
        return view;
    }

    private void init() {
        scrollview = (MyScrollView)view.findViewById(R.id.scrollview);
        headerView = view.findViewById(R.id.lay_header);
        imgView = (ImageView) view.findViewById(R.id.civ_avatar);

        headerView.post(new Runnable() {
            @Override
            public void run() {
                scrollview.setHeaderView(headerView, imgView);
            }
        });
    }

}
