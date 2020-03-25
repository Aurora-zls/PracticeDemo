package com.example.zls;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.zls.fragment.MainFragment;

import me.yokeyword.fragmentation.SupportActivity;

import android.os.Bundle;

@Route(path = "/main/activity")
public class MainActivity extends SupportActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        MainFragment mainFragment = findFragment(MainFragment.class);
        if (mainFragment == null) {
            loadRootFragment(android.R.id.content,
                    (MainFragment) ARouter.getInstance().build("/main/fragment").navigation());
        }
    }
}
