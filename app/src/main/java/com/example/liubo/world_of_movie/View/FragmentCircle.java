package com.example.liubo.world_of_movie.View;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.liubo.world_of_movie.Adapter.CircleAdapter;
import com.example.liubo.world_of_movie.R;

import org.json.JSONArray;
import org.w3c.dom.Text;

/**
 * Created by Liubo on 2017/12/27.
 */

public class FragmentCircle extends Fragment {
    private View view;
    private ListView lv;
    private JSONArray Data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_circle,null);
        init();
        return view;
    }

    public void init(){
        lv = (ListView)view.findViewById(R.id.lv);
        CircleAdapter circleAdapter = new CircleAdapter(getActivity(),Data);
        lv.setAdapter(circleAdapter);
    }

}
