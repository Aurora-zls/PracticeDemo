package com.example.zls.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zls.R;
import com.example.zls.base.BaseRecyclerAdapter;
import com.example.zls.databinding.ItemRecyclerTestBinding;
import com.example.zls.types.RecyclerTestItem;

import androidx.databinding.DataBindingUtil;

public class TestRecyclerAdapter extends BaseRecyclerAdapter<RecyclerTestItem> {

    public TestRecyclerAdapter() {
        super(R.layout.item_recycler_test);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecyclerTestItem item) {
        ItemRecyclerTestBinding binding = DataBindingUtil.bind(helper.itemView);
        if (binding != null) {
            binding.img.setImageResource(item.getDrawableRes());
            binding.textContent.setText(item.getContent());
            binding.executePendingBindings();
        }
    }
}
