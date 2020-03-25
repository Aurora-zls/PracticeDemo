package com.example.zls.controller.hen_coder;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.zls.fragment.hen_coder_practice_fragment.HenCoderPracticeFragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import me.yokeyword.fragmentation.SupportActivity;

@Route(path = "/hen_coder_practice/activity")
public class HenCoderPracticeActivity extends SupportActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        loadRootFragment(android.R.id.content, (HenCoderPracticeFragment) ARouter.getInstance()
                .build("/hen_coder_practice/fragment").navigation());
    }
}
