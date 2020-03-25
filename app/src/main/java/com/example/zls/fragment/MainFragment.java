package com.example.zls.fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.zls.R;
import com.example.zls.databinding.FragmentMainBinding;
import com.example.zls.utils.ToastUtils;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import me.yokeyword.fragmentation.SupportFragment;

@Route(path = "/main/fragment")
public class MainFragment extends SupportFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        FragmentMainBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_main, container, false);
        binding.testDemo.setOnClickListener(v -> {
            ARouter.getInstance().build("/test_demo/activity").navigation();
        });
        binding.secondFloor.setOnClickListener(v -> {
            ARouter.getInstance().build("/pull/activity").navigation();
        });
        binding.henCoder.setOnClickListener(v -> {
            ARouter.getInstance().build("/hen_coder_practice/activity").navigation();
        });
        binding.unityGame.setOnClickListener(v -> {
            ARouter.getInstance().build("/unity_game/activity").navigation();
        });
        binding.func5.setOnClickListener(v -> {
            // TODO: 2020/3/25
            ToastUtils.toastInfo(R.string.default_toast);
        });
        binding.func6.setOnClickListener(v -> {
            // TODO: 2020/3/25
            ToastUtils.toastInfo(R.string.default_toast);
        });
        binding.func7.setOnClickListener(v -> {
            // TODO: 2020/3/25
            ToastUtils.toastInfo(R.string.default_toast);
        });
        return binding.getRoot();
    }
}
