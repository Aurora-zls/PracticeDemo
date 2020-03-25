package com.example.zls.fragment;


import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.zls.R;
import com.example.zls.utils.ScreenUtils;

/**
 * A simple {@link Fragment} subclass.
 */
@Route(path = "/dialog/fragment")
public class MyDialogFragment extends DialogFragment {


    public MyDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment

//            getDialog().requestWindowFeature( Window.FEATURE_NO_TITLE );
//            getDialog().getWindow().setBackgroundDrawable( new ColorDrawable( 0 ) );
////            getDialog().getWindow().setWindowAnimations(R.style.dialog_animtion_style);
//            WindowManager.LayoutParams lp=getDialog().getWindow().getAttributes();
//            lp.width=WindowManager.LayoutParams.WRAP_CONTENT;
//            lp.height=WindowManager.LayoutParams.WRAP_CONTENT;
//
//            lp.gravity= Gravity.BOTTOM;
//            setStyle();
        //去掉dialog的标题，需要在setContentView()之前
//        this.getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
//        Window window = this.getDialog().getWindow();
//        //去掉dialog默认的padding
//        window.getDecorView().setPadding(0, 0, 0, 0);
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
//        window.setAttributes(lp);
//            getDialog().getWindow().setAttributes(lp);

        View view =  inflater.inflate(R.layout.fragment_my_dialog, container, false);
//        CardView cardView = view.findViewById(R.id.mView);
//        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) cardView.getLayoutParams();
//        cardView.setLayoutParams(params);

//        Window window = getDialog().getWindow();
//        WindowManager.LayoutParams windowParams = window.getAttributes();
//        windowParams.dimAmount = 0.0f;//Dialog外边框透明
//        window.setLayout(-1, -2); //高度自适应，宽度全屏
//        windowParams.gravity = Gravity.TOP; //在顶部显示
//        windowParams.windowAnimations = R.style.TopDialogAnimation;
//        window.setAttributes(windowParams);

        this.getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = this.getDialog().getWindow();
        //去掉dialog默认的padding
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        ScreenUtils.setContext(getContext());
        lp.width = ScreenUtils.dip2px(200);
        lp.height = ScreenUtils.dip2px(450);
        //设置dialog的位置在底部
        lp.gravity = Gravity.CENTER;
        //设置dialog的动画
        lp.windowAnimations = R.style.BottomDialogAnimation;
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyMinDialogWidth);
        window.setAttributes(lp);
        window.setBackgroundDrawable(new ColorDrawable());
        getDialog().setCanceledOnTouchOutside(isCancelOnTouchOutSide());
        clickEvent(view);
        return view;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void clickEvent(View view) {
        view.findViewById(R.id.fullscreen).setOnClickListener(v -> {
            WindowManager.LayoutParams lp = getDialog().getWindow().getAttributes();
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            ScreenUtils.setContext(getContext());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
            //设置dialog的位置在底部
            lp.gravity = Gravity.CENTER;
            //设置dialog的动画
            lp.windowAnimations = R.style.BottomDialogAnimation;
            setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyMinDialogWidth);
            getDialog().getWindow().setAttributes(lp);});
        view.findViewById(R.id.specific_position).setOnClickListener(v -> {

            Window window = this.getDialog().getWindow();
            //去掉dialog默认的padding
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = window.getAttributes();
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            ScreenUtils.setContext(getContext());
            lp.width = ScreenUtils.dip2px(134);
            lp.height = ScreenUtils.dip2px(200);


//            lp.gravity = Gravity.getAbsoluteGravity(Gravity.LEFT, Gravity.HORIZONTAL_GRAVITY_MASK);
            lp.x = 102;
            lp.y = 20;
            //设置dialog的动画
            lp.windowAnimations = R.style.BottomDialogAnimation;
            setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyMinDialogWidth);
            window.setAttributes(lp);
        });
        view.findViewById(R.id.alpha).setOnClickListener(v -> {
            WindowManager.LayoutParams lp = getDialog().getWindow().getAttributes();
            //控制dialog内容外区域的透明度
            lp.dimAmount = 0.8f;//Dialog外边框透明
            getDialog().getWindow().setAttributes(lp);
        });
    }

    private boolean isCancelOnTouchOutSide() {
        return false;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        AlertDialog dialog =  new AlertDialog.Builder(getContext())
//                .setTitle("神灯")
//                .setMessage("来选择你要实现的一个愿望把")
//                .setPositiveButton("车子", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                })
//                .setNegativeButton("房子", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                }).create();
//        return dialog;
        return super.onCreateDialog(savedInstanceState);
    }
}
