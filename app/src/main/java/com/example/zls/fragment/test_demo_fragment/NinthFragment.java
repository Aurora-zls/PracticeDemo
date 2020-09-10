package com.example.zls.fragment.test_demo_fragment;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.zls.R;
import com.example.zls.widget.custom_view.BackgroundCountdownView;
import com.example.zls.widget.custom_view.CustomCountdownView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import io.reactivex.disposables.Disposable;

/**
 * A simple {@link Fragment} subclass.
 */
@Route(path = "/ninth/fragment")
public class NinthFragment extends Fragment {

    private Disposable mDisposable;

    public NinthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ninth, container, false);
        BackgroundCountdownView countdownView = view.findViewById(R.id.countdown_test_view);
        EditText editText = view.findViewById(R.id.edit_duration);
        TextView textView = view.findViewById(R.id.confirm);
        textView.setOnClickListener(v -> {
            try {
                long duration = editText.getText() != null ? Long
                        .parseLong(editText.getText().toString()) : 1847847;
                countdownView.release();
                countdownView.startCountDown(duration, () -> {
                    Toast.makeText(getContext(), "倒计时结束", Toast.LENGTH_LONG).show();
                    countdownView.release();
                });
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "输入非法字符", Toast.LENGTH_SHORT).show();
            }
        });
        countdownView.setFirstLineText("AAA");
        countdownView.startCountDown(18000, () -> {
            Toast.makeText(getContext(), "倒计时结束", Toast.LENGTH_LONG).show();
            countdownView.release();
        });
        CustomCountdownView customCountdownView = view.findViewById(R.id.countdown_2);
        customCountdownView.startCountdown(1343, () -> {
            Toast.makeText(getContext(), "倒计时结束", Toast.LENGTH_LONG).show();
            customCountdownView.release();
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }
}
