package com.gogo.haobutler.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;

import com.gogo.haobutler.R;
import com.gogo.haobutler.activity.base.AbstractImmerseActivity;
import com.gogo.haobutler.adapter.HomeFragmentPagerAdapter;
import com.gogo.haobutler.fragment.StudyFragment;
import com.gogo.haobutler.fragment.VideoFragment;
import com.gogo.haobutler.fragment.base.BaseFragment;
import com.gogo.haobutler.fragment.BasketballFragment;
import com.gogo.haobutler.fragment.HaoFragment;
import com.gogo.haobutler.fragment.MineFragment;
import com.gogo.haobutler.fragment.MoocFragment;
import com.gogo.haobutler.fragment.WeChatFragment;
import com.gogo.haobutler.test.TestActivityAlpha;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 闫昊
 * @date: 2018/4/12
 * @function: 主页面开发：TabLayout+ViewPager
 */

public class MainActivity extends AbstractImmerseActivity {

    /**
     * UI
     */
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FloatingActionButton mFloatingActionButton;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    /**
     * Data
     */
    private List<BaseFragment> fragmentList;
    private String[] titles;
    private HomeFragmentPagerAdapter mHomeFragmentPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        initData();
        initView();
    }

    private void initData() {
        titles = getResources().getStringArray(R.array.viewPager_title);
        fragmentList = new ArrayList<>();
        fragmentList.add(new StudyFragment());
        fragmentList.add(new VideoFragment());
        fragmentList.add(new HaoFragment());
        fragmentList.add(new WeChatFragment());
        fragmentList.add(new MoocFragment());
        fragmentList.add(new BasketballFragment());
        fragmentList.add(new MineFragment());
        mHomeFragmentPagerAdapter = new HomeFragmentPagerAdapter(getSupportFragmentManager(),
                titles, fragmentList);
    }

    private void initView() {
        mTabLayout = findViewById(R.id.table_layout_home);
        mViewPager = findViewById(R.id.view_pager_home);
        mFloatingActionButton = findViewById(R.id.fab_setting);
        mFloatingActionButton.setOnClickListener(this);
        mViewPager.setOffscreenPageLimit(fragmentList.size());
        mViewPager.setAdapter(mHomeFragmentPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_main);

        mNavigationView.setCheckedItem(R.id.nav_contact);
        mNavigationView.setNavigationItemSelectedListener((item) -> {
            switch (item.getItemId()) {
                case R.id.nav_weather:
                    startActivity(new Intent(this, WeatherActivity.class));
                    break;
                case R.id.nav_contact:
                    break;
                case R.id.nav_courier:
                    startActivity(new Intent(this, DistributionActivity.class));
                    break;
                case R.id.nav_attribution:
                    startActivity(new Intent(this, AttributionActivity.class));
                    break;
                case R.id.nav_test:
                    break;
                default:
                    break;
            }
            mDrawerLayout.closeDrawers();
            return true;
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
//                mFloatingActionButton.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_setting:
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return true;
    }
}
