package com.example.liubo.world_of_movie.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.example.liubo.world_of_movie.R;

/**
 * Created by Liubo on 2017/12/27.
 */

public class FragmentSurround extends Fragment {
    private BaiduMap baiduMap;
    private View view;
    private ImageView right_add;
    private ImageView left_back;
    private TextView title;
    private MapView mapView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SDKInitializer.initialize(getActivity().getApplicationContext());
        view = inflater.inflate(R.layout.fragment_surround,null);
        init();
        baiduMap = mapView.getMap();
        return view;
    }

    public void init(){
        mapView = view.findViewById(R.id.bd_map);
        right_add = (ImageView)view.findViewById(R.id.right_add);
        right_add.setVisibility(View.GONE);
        left_back = (ImageView)view.findViewById(R.id.left_back);
        left_back.setVisibility(View.GONE);
        title = (TextView)view.findViewById(R.id.title);
        title.setText("周边");
        //lv.setAdapter(circleAdapter);
    }
}
