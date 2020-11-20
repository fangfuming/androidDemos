package com.example.navigation.customeNavigation;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigator;
import androidx.navigation.fragment.DialogFragmentNavigator;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.navigation.fragment.NavHostFragment;

/**
 * create by fangfuming
 * on 2020/10/10 0010
 * description:
 */
public class CustomNavHostFragment extends NavHostFragment {

    @NonNull
    @Override
    protected Navigator<? extends FragmentNavigator.Destination> createFragmentNavigator() {
        return new  HideSwitchFragmentNavigator(
                requireContext(), getChildFragmentManager(),
                getContainerId()
        );
    }

    private int getContainerId(){

    }
}
