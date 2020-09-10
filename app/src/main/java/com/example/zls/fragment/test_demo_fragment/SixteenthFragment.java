package com.example.zls.fragment.test_demo_fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.zls.R;
import com.example.zls.databinding.FragmentSixteenthBinding;

import android.graphics.drawable.RotateDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import me.yokeyword.fragmentation.SupportFragment;
import timber.log.Timber;

@Route(path = "/sixteenth/fragment")
public class SixteenthFragment extends SupportFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        FragmentSixteenthBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_sixteenth, container, false);
        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Timber.d("seekbar onProgressChanged");
                binding.testDrawable.getDrawable()
                        .setLevel((int) (10000 * (progress / (float) binding.seekBar.getMax())));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Timber.d("seekbar onStartTrackingTouch");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Timber.d("seekbar onStopTrackingTouch");
            }
        });
        binding.seekBarInternal.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ((RotateDrawable) binding.testDrawable.getDrawable()).getDrawable()
                        .setLevel((int) (10000 * (progress / (float) binding.seekBar.getMax())));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Timber.d("seekbar internal onStartTrackingTouch");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Timber.d("seekbar internal onStopTrackingTouch");
            }
        });

        binding.seekBarInternalInternal
                .setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        ((RotateDrawable) ((RotateDrawable) binding.testDrawable.getDrawable())
                                .getDrawable()).getDrawable()
                                .setLevel((int) (10000 * (progress / (float) binding.seekBar
                                        .getMax())));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        Timber.d("seekbar internal internal onStartTrackingTouch");
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        Timber.d("seekbar internal internal onStopTrackingTouch");
                    }
                });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
