package com.example.zls.fragment;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import me.yokeyword.fragmentation.SupportFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.zls.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ThirdFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ThirdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@Route(path = "/third/fragment")
public class ThirdFragment extends SupportFragment {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_third, container, false);
        view.findViewById(R.id.button_1).setOnClickListener(v->{

            Animation animation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.rotate);
            TextView textView = view.findViewById(R.id.test);
            textView.startAnimation(animation);
        });
        view.findViewById(R.id.button_2).setOnClickListener(v->{

//            Animation animation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.scale);
//            ImageView imageView = view.findViewById(R.id.img);
//            imageView.startAnimation(animation);
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.scale);
            view.findViewById(R.id.img).startAnimation(animation);
        });
        view.findViewById(R.id.button_3).setOnClickListener(v->{
            Animation animation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.alpha);
            TextView textView = view.findViewById(R.id.test);
            textView.startAnimation(animation);
        });
        view.findViewById(R.id.button_4).setOnClickListener(v->{
            Animation animation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.translate);
            TextView textView = view.findViewById(R.id.test);
            textView.startAnimation(animation);
        });

        view.findViewById(R.id.button_5).setOnClickListener(v->{
            Animation animation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.muliti);
            TextView textView = view.findViewById(R.id.test);
            ImageView imageView = view.findViewById(R.id.img);
            imageView.startAnimation(animation);
            textView.startAnimation(animation);
        });
        view.findViewById(R.id.button_6).setOnClickListener(v->{
            TextView textView = view.findViewById(R.id.test);
            ObjectAnimator animator = ObjectAnimator.ofFloat(textView,"alpha",1.0f,0.0f)
                    // 设置动画时常
                    .setDuration(1000);
            // 设置重复次数
            animator.setRepeatCount(2);
            animator.start();

        });
        view.findViewById(R.id.button_7).setOnClickListener(v->{
            TextView textView2 = view.findViewById(R.id.test2);
            ObjectAnimator animator = ObjectAnimator.ofFloat(textView2,"translationX",0f,200f)
                    .setDuration(1000);
            animator.start();
            ObjectAnimator animator2 = ObjectAnimator.ofFloat(textView2,"translationY",0f,200f)
                    .setDuration(1000);
            animator2.start();

        });
        view.findViewById(R.id.button_8).setOnClickListener(v->{
            TextView textView2 = view.findViewById(R.id.test2);
            ObjectAnimator animator = ObjectAnimator.ofFloat(textView2,"rotation",0f,180f)
                    .setDuration(1000);
            animator.start();
        });
        view.findViewById(R.id.button_9).setOnClickListener(v->{
            TextView textView2 = view.findViewById(R.id.test2);
            AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.property_animation);
            set.setTarget(textView2);
            set.start();
        });
        return view;
    }



}
