package com.example.cloudsave;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.cloudsave.AdapterAll.SectionsPagerAdapter;
import com.example.cloudsave.Fragment.FragmentFamily;
import com.example.cloudsave.Fragment.FragmentHome;
import com.example.cloudsave.Fragment.FragmentNumber;
import com.example.cloudsave.Fragment.FragmentSet;


import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class BottomNavigationActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {
    private ViewPager viewPager;
    private BottomNavigationBar bottomNavigationBar;
    private List<Fragment> fragmentList;
    public static String username,userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navation);
        Intent intent=getIntent();
        username=intent.getStringExtra("username");
        userId=intent.getStringExtra("userId");
        viewPager=findViewById(R.id.viewpager);
        bottomNavigationBar=findViewById(R.id.bottom);
        initBottomNavigationBar();
        initViewPager();
    }

    private void initBottomNavigationBar() {
        bottomNavigationBar.setTabSelectedListener(this);
        bottomNavigationBar.clearAll();
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);//自适应大小
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_DEFAULT);
        bottomNavigationBar.setInActiveColor(R.color.purple_500);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.todo,"事务").setInactiveIconResource(R.drawable.todo2))
                .addItem(new BottomNavigationItem(R.drawable.todofamily,"成员事务").setInactiveIconResource(R.drawable.todofamily2))
                .addItem(new BottomNavigationItem(R.drawable.family,"成员").setInactiveIconResource(R.drawable.family2))
                .addItem(new BottomNavigationItem(R.drawable.set,"设置").setInactiveIconResource(R.drawable.set2))
                .setFirstSelectedPosition(0)
                .initialise();
    }

    private void initViewPager() {
        //配置viewpage
        viewPager.setOffscreenPageLimit(4);

        fragmentList=new ArrayList<Fragment>();
        fragmentList.add(new FragmentHome());
        fragmentList.add(new FragmentFamily());
        fragmentList.add(new FragmentNumber());
        fragmentList.add(new FragmentSet());
        viewPager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager(),fragmentList));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigationBar.selectTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(0);
    }

    @Override
    public void onTabSelected(int position) {
        viewPager.setCurrentItem(position);

    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}