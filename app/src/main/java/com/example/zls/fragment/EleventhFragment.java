package com.example.zls.fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.zls.R;
import com.example.zls.adapter.TestRecyclerAdapter;
import com.example.zls.databinding.FragmentEleventhBinding;
import com.example.zls.types.RecyclerTestItem;
import com.example.zls.widget.custom_header.CustomRefreshHeader;
import com.scwang.smartrefresh.layout.header.TwoLevelHeader;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import me.yokeyword.fragmentation.SupportFragment;

@Route(path = "/eleventh/fragment")
public class EleventhFragment extends SupportFragment {

    private TestRecyclerAdapter mTestRecyclerAdapter;

    private List<RecyclerTestItem> mTestItems;

    public EleventhFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eleventh, container, false);
        FragmentEleventhBinding binding = DataBindingUtil.bind(view);
        loadData();
        mTestRecyclerAdapter = new TestRecyclerAdapter();
        if (binding != null) {
            initRefreshHeader(binding);
            mTestRecyclerAdapter.bindToRecyclerView(binding.recycler);
            mTestRecyclerAdapter.replaceData(mTestItems);
        }
        return binding != null ? binding.getRoot() : null;
    }

    private void initRefreshHeader(FragmentEleventhBinding binding) {
        TwoLevelHeader header = new TwoLevelHeader(_mActivity);
        header.setRefreshHeader(new CustomRefreshHeader(_mActivity));
        binding.refresh.setRefreshHeader(header, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
    }

    private void loadData() {
        mTestItems = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mTestItems.add(new RecyclerTestItem("text" + i, R.drawable.timg));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
