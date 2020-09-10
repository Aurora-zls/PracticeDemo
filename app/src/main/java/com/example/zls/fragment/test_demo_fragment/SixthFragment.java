package com.example.zls.fragment.test_demo_fragment;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.zls.R;
import com.example.zls.utils.ScreenUtils;
import com.example.zls.utils.SpanUtils;
import com.example.zls.utils.TextJustificationUtil;
import com.example.zls.widget.custom_view.custom_text_view.AlignTextView;
import com.example.zls.widget.custom_view.custom_text_view.JustifyTextView;
import com.example.zls.widget.custom_view.custom_text_view.SelectableTextView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
@Route(path = "/sixth/fragment")
public class SixthFragment extends Fragment {

    public SixthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sixth, container, false);
        SpanUtils.with(view.findViewById(R.id.ctv_content))
                .appendLine(getString(R.string.test_justify_content_slice_1))
                .setBullet(Color.GREEN, 20, 10, 0)
                .append(getString(R.string.test_justify_content_slice_2))
                .setBullet(Color.GREEN, 20, 10, 0)
                .create();
        ((SelectableTextView) view.findViewById(R.id.ctv_content)).setTextJustify(true);

        TextView textView = view.findViewById(R.id.textView);
        SpanUtils.with(view.findViewById(R.id.textView))
                .append(getString(R.string.test_justify_content_slice_1))
                .setLineHeight(ScreenUtils.dip2px(44))
                .setBullet(Color.GREEN, 20, 10, textView.getLineSpacingExtra())
                .create();

        AlignTextView alignTextView = view.findViewById(R.id.align_text_view);
        SpanUtils.with(view.findViewById(R.id.align_text_view))
                .append(getString(R.string.test_justify_content_slice_2))
                .setBullet(Color.GREEN, 20, 10, alignTextView.getLineSpacingExtra())
                .create();

        JustifyTextView justifyTextView = view.findViewById(R.id.justify);
        SpanUtils.with(view.findViewById(R.id.justify))
                .append(getString(R.string.test_justify_content_slice_2))
                .setBullet(Color.GREEN, 20, 10, justifyTextView.getLineSpacingExtra())
                .create();

        TextView textView2 = view.findViewById(R.id.textView2);
        textView2.post(() -> TextJustificationUtil.justify(textView2, textView2.getMeasuredWidth(),
                getString(R.string.test_justify_content_slice_2)));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
