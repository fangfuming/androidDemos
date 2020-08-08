package com.example.deskTop;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.deskTop.databinding.ActivityTestViewPager2Binding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * create by fangfuming
 * on 2020/7/3 0003
 * description:
 */
public class TestViewPager2 extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTestViewPager2Binding binding = ActivityTestViewPager2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewPager2Adapter pager2Adapter = new ViewPager2Adapter(this);

        binding.viewPager2.setAdapter(pager2Adapter);
        binding.viewPager2.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);
        new TabLayoutMediator(binding.tableLayout, binding.viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("first");
                        break;
                    case 1:
                        tab.setText("second");
                        break;
                    case 2:
                        tab.setText("third");
                        break;
                    case 3:
                        tab.setText("four");
                        break;
                }
            }
        }).attach();
    }
}
