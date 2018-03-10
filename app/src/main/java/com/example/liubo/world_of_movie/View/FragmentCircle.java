package com.example.liubo.world_of_movie.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.liubo.world_of_movie.Adapter.CircleAdapter;
import com.example.liubo.world_of_movie.IM.ChatActivity;
import com.example.liubo.world_of_movie.R;
import com.hyphenate.easeui.EaseConstant;

import org.json.JSONArray;
import org.w3c.dom.Text;

/**
 * Created by Liubo on 2017/12/27.
 */

public class FragmentCircle extends Fragment {
    private View view;
    private ListView lv;
    private JSONArray Data;
    private Button test;
    private EditText testa;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_circle,null);
        init();
        return view;
    }

    public void init(){
        lv = (ListView)view.findViewById(R.id.lv);
        test = (Button) view.findViewById(R.id.test);
        testa = (EditText)view.findViewById(R.id.aaa);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, testa.getText().toString()));
            }
        });
        CircleAdapter circleAdapter = new CircleAdapter(getActivity(),Data);
        //lv.setAdapter(circleAdapter);
    }

}
