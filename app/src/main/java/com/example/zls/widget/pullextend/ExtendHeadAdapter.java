package com.example.zls.widget.pullextend;

import com.example.zls.R;

import android.widget.TextView;

import java.util.List;

/**
 * Created by Renny on 2018/1/3.
 */

public class ExtendHeadAdapter extends CommonAdapter<String> {

    public ExtendHeadAdapter(  List<String> datas) {
        super( R.layout.item_header, datas);
    }

    @Override
    protected void convert(ViewHolder holder, final int position) {
        String data=getData(position);
        TextView tv = holder.getView(R.id.item_title);
        tv.setText(data);
        if (mItemClickListener != null) {
            tv.setOnClickListener(v -> mItemClickListener.onItemClicked(position, v));
        }
    }


}
