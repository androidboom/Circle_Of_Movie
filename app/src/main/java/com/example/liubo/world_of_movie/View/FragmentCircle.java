package com.example.liubo.world_of_movie.View;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.liubo.world_of_movie.R;

import org.w3c.dom.Text;

/**
 * Created by Liubo on 2017/12/27.
 */

public class FragmentCircle extends Fragment {
    private TextView circle_tv1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_circle,null);

        circle_tv1 = (TextView)view.findViewById(R.id.circle_tv1);
        return view;
    }
}
