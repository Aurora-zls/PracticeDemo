package com.example.zls.fragment.test_demo_fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.zls.R;
import com.example.zls.utils.ScreenUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import me.yokeyword.fragmentation.SupportFragment;

@Route(path = "/first/fragment")
public class FirstFragment extends SupportFragment {


    public static FirstFragment newInstance() {
        return new FirstFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment, container, false);
        SimpleDraweeView test1 = (SimpleDraweeView) view.findViewById(R.id.test);
        Uri uri1 = Uri
                .parse("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523079111408&di=7783555b20885592a8034c6e729a6414&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01ea90595f5ca4a8012193a3d93648.jpeg");
        test1.setImageURI(uri1);
        view.findViewById(R.id.click).setOnClickListener(v -> {
            SimpleDraweeView sdv = (SimpleDraweeView) view.findViewById(R.id.fresco);
            Uri uri = Uri
                    .parse("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523079111408&di=7783555b20885592a8034c6e729a6414&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01ea90595f5ca4a8012193a3d93648.jpeg");
            sdv.setImageURI(uri);

            GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(
                    getResources());
            builder.setOverlay(new ColorDrawable());
            GenericDraweeHierarchy hierarchy = builder.build();
            test1.setHierarchy(hierarchy);
            test1.setImageURI(uri1);

        });
        view.findViewById(R.id.load_img).setOnClickListener(v -> {
            Drawable drawable = getResources().getDrawable(R.drawable.timg);
            drawable.setBounds(0, 0, ScreenUtils.dip2px(20), ScreenUtils.dip2px(20));
            TextView textView = view.findViewById(R.id.test_text);
            textView.setCompoundDrawables(drawable, null, null, null);
            //可设置图片距离文字的距离
            textView.setCompoundDrawablePadding(ScreenUtils.dip2px(15));
            textView.setPaddingRelative(ScreenUtils.dip2px(10),ScreenUtils.dip2px(10),ScreenUtils.dip2px(10),ScreenUtils.dip2px(10));

            //设置的是textview边框到内部元素的padding
            textView.setPadding(ScreenUtils.dip2px(10),ScreenUtils.dip2px(10),ScreenUtils.dip2px(10),ScreenUtils.dip2px(10));

        });
        SimpleDraweeView con = (SimpleDraweeView) view.findViewById(R.id.controller);
        PipelineDraweeControllerBuilder controllerBuilder = Fresco.newDraweeControllerBuilder();
        DraweeController draweeController = controllerBuilder.setUri(uri1)
                .setTapToRetryEnabled(true).setOldController(con.getController()).build();

        con.setController(draweeController);

        ScreenUtils.setContext(getContext());
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(uri1)
                .setAutoRotateEnabled(true)
                .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
                .setResizeOptions(
                        new ResizeOptions(ScreenUtils.dip2px(200), ScreenUtils.dip2px(200)))
                .setProgressiveRenderingEnabled(true).build();

        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append("绝代风华大是");
        builder.append("即使发生的");
        BackgroundColorSpan span = new BackgroundColorSpan(Color.parseColor("#00FF00"));
        builder.setSpan(span,0,3, Spannable.SPAN_INCLUSIVE_INCLUSIVE );
        TextView textView = view.findViewById(R.id.spannable);
        textView.setText(builder);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


}
