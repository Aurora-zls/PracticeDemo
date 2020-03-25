package com.example.zls.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.zls.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
@Route(path = "/tenth/fragment")
public class TenthFragment extends Fragment {


    public TenthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tenth, container, false);
        ListView listView = view.findViewById(R.id.rv);
        String[] from = {"content"};
        int[] to = {R.id.text_content};
        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(),getData(),R.layout.item_recycler_test,from,to);
        listView.setAdapter(simpleAdapter);
        return view;
    }

    private List<HashMap<String, Object>> getData() {
        // 新建一个集合类，用于存放多条数据
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        HashMap<String, Object> map;
        for (int i = 1; i <= 40; i++) {
            map = new HashMap<>();
            map.put("content", "itemView" + i);
            list.add(map);
        }
        return list;
    }

}
