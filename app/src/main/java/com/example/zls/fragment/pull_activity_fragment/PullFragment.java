package com.example.zls.fragment.pull_activity_fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.zls.R;
import com.example.zls.databinding.FragmentPullBinding;
import com.example.zls.widget.pullextend.ExtendHeadAdapter;
import com.example.zls.widget.pullextend.ExtendListFooter;
import com.example.zls.widget.pullextend.ExtendListHeader;
import com.example.zls.widget.pullextend.OverFlyingLayoutManager;
import com.example.zls.widget.pullextend.PullExtendLayout;
import com.example.zls.widget.toast.ToastHelper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import me.yokeyword.fragmentation.SupportFragment;

@Route(path = "/pull/fragment")
public class PullFragment extends SupportFragment {

    ExtendListHeader mPullNewHeader;

    ExtendListFooter mPullNewFooter;

    PullExtendLayout mPullExtendLayout;

    RecyclerView listHeader, listFooter;

    List<String> mDatas = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pull, container, false);
        FragmentPullBinding binding = DataBindingUtil.bind(view);

        if (binding != null) {
            mPullNewHeader = binding.header;
            mPullNewFooter = binding.footer;
            mPullExtendLayout = binding.extend;
            listHeader = mPullNewHeader.getRecyclerView();
            listFooter = mPullNewFooter.getRecyclerView();
            mPullExtendLayout.setPullLoadEnabled(true);
            listHeader.setLayoutManager(new OverFlyingLayoutManager(OrientationHelper.HORIZONTAL));
            listFooter.setLayoutManager(
                    new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            listFooter.setItemAnimator(new DefaultItemAnimator());

            listFooter.setItemAnimator(new DefaultItemAnimator());
            mDatas.add("历史记录");
            mDatas.add("无痕浏览");
            mDatas.add("新建窗口");
            mDatas.add("无图模式");
            mDatas.add("夜间模式");
            mDatas.add("网页截图");
            mDatas.add("禁用JS");
            mDatas.add("下载内容");
            mDatas.add("查找");
            mDatas.add("拦截广告");
            mDatas.add("全屏浏览");
            mDatas.add("翻译");
            mDatas.add("切换UA");
            listHeader.setAdapter(new ExtendHeadAdapter(mDatas).setItemClickListener(
                    (position, view1) -> ToastHelper.makeToast(mDatas.get(position) + " 功能待实现")));
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
