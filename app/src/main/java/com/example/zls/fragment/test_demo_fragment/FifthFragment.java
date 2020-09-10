package com.example.zls.fragment.test_demo_fragment;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.zls.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
@Route(path = "/fifth/fragment")
public class FifthFragment extends Fragment {


    public FifthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fifth, container, false);
        initData(view);
        bindAction(view);
        return view;
    }

    private void bindAction(View view) {
//        view.findViewById()
    }

    private void initData(View view) {
    }

}
