package com.example.deskTop;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * create by fangfuming
 * on 2020/7/3 0003
 * description:
 */
public class ViewPager2Adapter extends FragmentStateAdapter {

    List<CommonFragment> mList = new ArrayList<>();

    CommonFragment FragmentOne;
    CommonFragment FragmentTwo;
    CommonFragment FragmentThree;
    CommonFragment FragmentFour;

    public ViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        mList.add(FragmentOne);
        mList.add(FragmentTwo);
        mList.add(FragmentThree);
        mList.add(FragmentFour);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                if (FragmentOne == null) {
                    FragmentOne = new  CommonFragment(0);
                }
                return FragmentOne;
            case 1:
                if (FragmentTwo == null) {
                    FragmentTwo = new  CommonFragment(1);
                }
                return FragmentTwo;
            case 2:
                if (FragmentThree == null) {
                    FragmentThree = new  CommonFragment(2);
                }
                return FragmentThree;
            case 3:
                if (FragmentFour == null) {
                    FragmentFour = new  CommonFragment(3);
                }
                return FragmentFour;
        }
        return new CommonFragment();
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
