package com.example.zls.fragment.test_demo_fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.zls.R;
import com.example.zls.databinding.FragmentFifteenthBinding;
import com.example.zls.utils.ToastUtils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import me.yokeyword.fragmentation.SupportFragment;

@Route(path = "/fifteenth/fragment")
public class FifteenthFragment extends SupportFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        FragmentFifteenthBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_fifteenth, null, false);
        Messenger messenger1 = new Messenger(new Messenger1Handler());
        Messenger messenger2 = new Messenger(new Messenger2Handler());
        binding.messenger1.setOnClickListener(v -> {
            try {
                Message message1 = Message.obtain();
                message1.what = 1;
                message1.replyTo = messenger1;
                messenger2.send(message1);
            } catch (RemoteException e) {
                ToastUtils.toastWarning("RemoteException 1 --> 2");
                e.printStackTrace();
            }
        });

        binding.messenger2.setOnClickListener(v -> {
            try {
                Message message2 = Message.obtain();
                message2.what = 2;
                message2.replyTo = messenger2;
                messenger1.send(message2);
            } catch (RemoteException e) {
                ToastUtils.toastWarning("RemoteException 2 --> 1");
                e.printStackTrace();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private static final class Messenger1Handler extends Handler {

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            ToastUtils.toastInfo("messenger1 handler accept the message");
        }
    }

    private static final class Messenger2Handler extends Handler {

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            ToastUtils.toastInfo("messenger2 handler accept the message");
        }
    }
}
