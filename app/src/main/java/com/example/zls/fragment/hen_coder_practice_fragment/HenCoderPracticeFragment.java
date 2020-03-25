package com.example.zls.fragment.hen_coder_practice_fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.zls.R;
import com.example.zls.databinding.FragmentHenCoderPracticeBinding;
import com.example.zls.utils.ToastUtils;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import me.yokeyword.fragmentation.SupportFragment;

@Route(path = "/hen_coder_practice/fragment")
public class HenCoderPracticeFragment extends SupportFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        FragmentHenCoderPracticeBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_hen_coder_practice, container, false);
        binding.henCoder1.setOnClickListener(v -> {
            // TODO: 2020/3/25
            ToastUtils.toastInfo(R.string.default_toast);
        });
        binding.henCoder2.setOnClickListener(v -> {
            // TODO: 2020/3/25
            ToastUtils.toastInfo(R.string.default_toast);
        });
        binding.henCoder3.setOnClickListener(v -> {
            // TODO: 2020/3/25
            ToastUtils.toastInfo(R.string.default_toast);
        });
        binding.henCoder4.setOnClickListener(v -> {
            // TODO: 2020/3/25
            ToastUtils.toastInfo(R.string.default_toast);
        });
        binding.henCoder5.setOnClickListener(v -> {
            // TODO: 2020/3/25
            ToastUtils.toastInfo(R.string.default_toast);
        });
        binding.henCoder6.setOnClickListener(v -> {
            // TODO: 2020/3/25
            ToastUtils.toastInfo(R.string.default_toast);
        });
        binding.henCoder7.setOnClickListener(v -> {
            ARouter.getInstance().build("/hen_coder/7_activity").navigation();
        });

        return binding.getRoot();
    }
}
