package com.example.zls.controller.test_demo;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.zls.controller.test_demo.fragment.TestDemoFragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import me.yokeyword.fragmentation.SupportActivity;

@Route(path = "/test_demo/activity")
public class TestDemoActivity extends SupportActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        loadRootFragment(android.R.id.content,
                (TestDemoFragment) ARouter.getInstance().build("/test_demo/fragment").navigation());
    }
}
