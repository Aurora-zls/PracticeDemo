package com.example.zls.controller.hen_coder;

import com.google.android.material.tabs.TabLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.zls.R;

import android.os.Bundle;
import android.view.Menu;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import me.yokeyword.fragmentation.SupportActivity;

@Route(path = "/hen_coder/7_activity")
public class HenCoder7Activity extends SupportActivity {

    TabLayout tabLayout;

    ViewPager pager;

    List<PageModel> pageModels = new ArrayList<>();

    {
        pageModels.add(new PageModel(R.layout.sample_argb_evaluator, R.string.title_argb_evaluator,
                R.layout.practice_argb_evaluator));
        pageModels.add(new PageModel(R.layout.sample_hsv_evaluator, R.string.title_hsv_evaluator,
                R.layout.practice_hsv_evaluator));
        pageModels.add(new PageModel(R.layout.sample_of_object, R.string.title_of_object,
                R.layout.practice_of_object));
        pageModels.add(new PageModel(R.layout.sample_property_values_holder,
                R.string.title_property_values_holder, R.layout.practice_property_values_holder));
        pageModels.add(new PageModel(R.layout.sample_animator_set, R.string.title_animator_set,
                R.layout.practice_animator_set));
        pageModels.add(new PageModel(R.layout.sample_keyframe, R.string.title_keyframe,
                R.layout.practice_keyframe));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        setContentView(R.layout.activity_hen_coder_7);

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                PageModel pageModel = pageModels.get(position);
                return PageFragment
                        .newInstance(pageModel.sampleLayoutRes, pageModel.practiceLayoutRes);
            }

            @Override
            public int getCount() {
                return pageModels.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return getString(pageModels.get(position).titleRes);
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private class PageModel {

        @LayoutRes
        int sampleLayoutRes;

        @StringRes
        int titleRes;

        @LayoutRes
        int practiceLayoutRes;

        PageModel(@LayoutRes int sampleLayoutRes, @StringRes int titleRes,
                @LayoutRes int practiceLayoutRes) {
            this.sampleLayoutRes = sampleLayoutRes;
            this.titleRes = titleRes;
            this.practiceLayoutRes = practiceLayoutRes;
        }
    }
}
