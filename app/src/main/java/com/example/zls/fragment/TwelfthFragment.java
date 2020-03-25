package com.example.zls.fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.zls.R;
import com.example.zls.databinding.FragmentTwelfthBinding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import me.yokeyword.fragmentation.SupportFragment;

@Route(path = "/twelfth/fragment")
public class TwelfthFragment extends SupportFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_twelfth, container, false);
        FragmentTwelfthBinding binding = DataBindingUtil.bind(view);

        if (binding != null) {
            binding.button.setOnClickListener(v -> ARouter.getInstance().build("/pull/activity").navigation());
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
