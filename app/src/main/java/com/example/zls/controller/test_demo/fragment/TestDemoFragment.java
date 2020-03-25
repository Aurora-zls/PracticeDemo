package com.example.zls.controller.test_demo.fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.zls.R;
import com.example.zls.adapter.ViewAdapter;
import com.example.zls.databinding.FragmentTestDemoBinding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;
import me.yokeyword.fragmentation.SupportFragment;

@Route(path = "/test_demo/fragment")
public class TestDemoFragment extends SupportFragment {

    private ViewPager viewPager;

    private FragmentTestDemoBinding mTestDemoBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        if (findFragment(FirstFragment.class) == null) {
//            mSupportFragments.add((FirstFragment) ARouter.getInstance().build("/first/fragment")
//                    .navigation());
//        }
//        if (findFragment(SecondFragment.class) == null) {
//            mSupportFragments.add((SecondFragment) ARouter.getInstance().build("/second/fragment")
//                    .navigation());
//        }
//        if (findFragment(ThirdFragment.class) == null) {
//            mSupportFragments.add((ThirdFragment) ARouter.getInstance().build("/third/fragment")
//                    .navigation());
//        }
//        mSupportFragments.set(0, findFragment(FirstFragment.class));
//        mSupportFragments.set(1, findFragment(SecondFragment.class));
//        mSupportFragments.set(2, findFragment(ThirdFragment.class));
//
//        loadMultipleRootFragment(R.id.viewpager, 1, mSupportFragments.get(0),
//                mSupportFragments.get(1), mSupportFragments.get(2));
        View view = inflater.inflate(R.layout.fragment_test_demo, container, false);
        mTestDemoBinding = DataBindingUtil.bind(view);
        init(view);

        return view;
    }

    private void init(View view) {
        viewPager = view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new ViewAdapter(getChildFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                    int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mTestDemoBinding != null) {
                    mTestDemoBinding.pageText.setText("Page" + (position + 1));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(10);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
