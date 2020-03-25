package com.example.zls.controller.second_floor;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.zls.fragment.MainFragment;
import com.example.zls.fragment.pull_activity_fragment.PullFragment;

import android.os.Bundle;

import me.yokeyword.fragmentation.SupportActivity;

@Route(path = "/pull/activity")
public class PullActivity extends SupportActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);

        MainFragment mainFragment = findFragment(MainFragment.class);
        if(mainFragment == null){
            loadRootFragment(android.R.id.content, (PullFragment) ARouter.getInstance().build("/pull/fragment").navigation());
        }

    }
}
