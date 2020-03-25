package com.example.zls.adapter;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.zls.fragment.EighteenthFragment;
import com.example.zls.fragment.EighthFragment;
import com.example.zls.fragment.EleventhFragment;
import com.example.zls.fragment.FifteenthFragment;
import com.example.zls.fragment.FifthFragment;
import com.example.zls.fragment.FirstFragment;
import com.example.zls.fragment.ForthFragment;
import com.example.zls.fragment.FourteenthFragment;
import com.example.zls.fragment.NineteenthFragment;
import com.example.zls.fragment.NinthFragment;
import com.example.zls.fragment.SecondFragment;
import com.example.zls.fragment.SeventeenthFragment;
import com.example.zls.fragment.SeventhFragment;
import com.example.zls.fragment.SixteenthFragment;
import com.example.zls.fragment.SixthFragment;
import com.example.zls.fragment.TenthFragment;
import com.example.zls.fragment.ThirdFragment;
import com.example.zls.fragment.ThirteenthFragment;
import com.example.zls.fragment.TwelfthFragment;
import com.example.zls.fragment.TwentiethFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewAdapter extends FragmentPagerAdapter {

    public ViewAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return (FirstFragment) ARouter.getInstance().build("/first/fragment").navigation();
        } else if (position == 1) {
            return (SecondFragment) ARouter.getInstance().build("/second/fragment").navigation();
        } else if (position == 2) {
            return (ThirdFragment) ARouter.getInstance().build("/third/fragment").navigation();
        } else if (position == 3) {
            return (ForthFragment) ARouter.getInstance().build("/forth/fragment").navigation();
        } else if (position == 4) {
            return (FifthFragment) ARouter.getInstance().build("/fifth/fragment").navigation();
        } else if (position == 5) {
            return (SixthFragment) ARouter.getInstance().build("/sixth/fragment").navigation();
        } else if (position == 6) {
            return (SeventhFragment) ARouter.getInstance().build("/seventh/fragment").navigation();
        } else if (position == 7) {
            return (EighthFragment) ARouter.getInstance().build("/eighth/fragment").navigation();
        } else if (position == 8) {
            return (NinthFragment) ARouter.getInstance().build("/ninth/fragment").navigation();
        } else if (position == 9) {
            return (TenthFragment) ARouter.getInstance().build("/tenth/fragment").navigation();
        } else if (position == 10) {
            return (EleventhFragment) ARouter.getInstance().build("/eleventh/fragment")
                    .navigation();
        } else if (position == 11) {
            return (TwelfthFragment) ARouter.getInstance().build("/twelfth/fragment").navigation();
        } else if (position == 12) {
            return (ThirteenthFragment) ARouter.getInstance().build("/thirteenth/fragment")
                    .navigation();
        } else if (position == 13) {
            return (FourteenthFragment) ARouter.getInstance().build("/fourteenth/fragment")
                    .navigation();
        } else if (position == 14) {
            return (FifteenthFragment) ARouter.getInstance().build("/fifteenth/fragment")
                    .navigation();
        } else if (position == 15) {
            return (SixteenthFragment) ARouter.getInstance().build("/sixteenth/fragment")
                    .navigation();
        } else if (position == 16) {
            return (SeventeenthFragment) ARouter.getInstance().build("/seventeenth/fragment")
                    .navigation();
        } else if (position == 17) {
            return (EighteenthFragment) ARouter.getInstance().build("/eighteenth/fragment")
                    .navigation();
        } else if (position == 18) {
            return (NineteenthFragment) ARouter.getInstance().build("/nineteenth/fragment")
                    .navigation();
        } else {
            return (TwentiethFragment) ARouter.getInstance().build("/twentieth/fragment")
                    .navigation();
        }
    }

    @Override
    public int getCount() {
        return 20;
    }
}
