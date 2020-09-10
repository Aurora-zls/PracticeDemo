package com.example.zls.fragment.test_demo_fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.zls.R;
import com.example.zls.widget.custom_view.animator_view.RewardCollectLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import me.yokeyword.fragmentation.SupportFragment;

@Route(path = "/fourteenth/fragment")
public class FourteenthFragment extends SupportFragment {

    private RewardCollectLayout mCollectLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fourteenth, container, false);
        mCollectLayout = view.findViewById(R.id.collect);
        mCollectLayout.setBackgroundColor(Color.parseColor("#240000FF"));
        view.findViewById(R.id.restart).setOnClickListener(v -> {
            mCollectLayout
                    .showRewardAddAnimation(RewardCollectLayout.REWARD_TYPE_CASH,
                            new int[]{200, 1500},
                            new int[]{900, 100});
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mCollectLayout
                .showRewardAddAnimation(RewardCollectLayout.REWARD_TYPE_CASH, new int[]{200, 1500},
                        new int[]{900, 100});
    }
}
