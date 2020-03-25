package com.example.zls.fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.zls.R;
import com.example.zls.databinding.FragmentThirteenthBinding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import me.yokeyword.fragmentation.SupportFragment;

@Route(path = "/thirteenth/fragment")
public class ThirteenthFragment extends SupportFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thirteenth, container, false);
        FragmentThirteenthBinding binding = DataBindingUtil.bind(view);
        if (binding != null) {
            binding.addBonus.setOnClickListener(v -> {
                binding.gameBonus.setText(new StringBuilder(binding.gameBonus.getText())
                        .append(binding.gameBonus.getText()));
            });
            binding.addName.setOnClickListener(v -> {
                binding.gameName.setText(new StringBuilder(binding.gameName.getText())
                        .append(binding.gameName.getText()));
            });
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
