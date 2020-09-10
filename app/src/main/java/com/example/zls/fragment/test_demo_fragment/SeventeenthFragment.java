package com.example.zls.fragment.test_demo_fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.zls.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import me.yokeyword.fragmentation.SupportFragment;

@Route(path = "/seventeenth/fragment")
public class SeventeenthFragment extends SupportFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_seventeenth, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
