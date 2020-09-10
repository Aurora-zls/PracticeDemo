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
@Route(path = "/eighth/fragment")
public class EighthFragment extends Fragment {


    public EighthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_eighth, container, false);
    }

}
