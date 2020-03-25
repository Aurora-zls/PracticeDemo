package com.example.zls.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import me.yokeyword.fragmentation.SupportFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.zls.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SecondFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SecondFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

@Route(path = "/second/fragment")
public class SecondFragment extends SupportFragment {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        init(view);
        return view;
    }

    private void init(View view) {

//        DialogFragment dialogFragment = findFragment(DialogFragment.class);
        view.findViewById(R.id.dialogfragment).setOnClickListener(view1 -> {
           MyDialogFragment myDialogFragment = new MyDialogFragment();
           myDialogFragment.show(getChildFragmentManager(),"MyDialogFragment");
        });
    }

}
