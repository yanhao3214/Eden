package com.gogo.haobutler.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.gogo.haobutler.R;
import com.gogo.haobutler.activity.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 闫昊
 * @date: 2018/4/15
 * @function:
 */

public class GuideActivity extends BaseActivity {
    private ViewPager vpGuide;
    private List<View> guidePagerList;
    private List<ImageView> pointList;
    private View viewOne;
    private View viewTwo;
    private View viewThree;
    private ImageView ivPointOne;
    private ImageView ivPointTwo;
    private ImageView ivPointThree;
    private Button btnToHome;
    private ImageView ivJump;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_layout);
        initData();
        initView();
    }

    private void initData() {
    }

    private void initView() {
        guidePagerList = new ArrayList<>();
        pointList = new ArrayList<>();
        vpGuide = findViewById(R.id.view_pager_guide);
        viewOne = View.inflate(this, R.layout.viewpager_guide_one_layout, null);
        viewTwo = View.inflate(this, R.layout.viewpager_guide_two_layout, null);
        viewThree = View.inflate(this, R.layout.viewpager_guide_three_layout, null);
        ivPointOne = findViewById(R.id.iv_point_one);
        ivPointTwo = findViewById(R.id.iv_point_two);
        ivPointThree = findViewById(R.id.iv_point_three);
        btnToHome = viewThree.findViewById(R.id.btn_turn_main);
        ivJump = findViewById(R.id.iv_jump_guide);
        guidePagerList.add(viewOne);
        guidePagerList.add(viewTwo);
        guidePagerList.add(viewThree);
        pointList.add(ivPointOne);
        pointList.add(ivPointTwo);
        pointList.add(ivPointThree);
        selectPoint(0);
        vpGuide.setAdapter(new GuidePagerAdapter());
        vpGuide.addOnPageChangeListener(new OnGuidePageChangeListener());
        btnToHome.setOnClickListener(this);
        ivJump.setOnClickListener(this);

    }

    /**
     * 根据position设置“跳过”和“进入主页”
     *
     * @param position
     */
    private void selectPoint(int position) {
        ivJump.setVisibility(position == 2 ? View.GONE : View.VISIBLE);
        for (int i = 0; i < pointList.size(); i++) {
            pointList.get(i).setImageResource(i == position ? R.drawable.point_on :
                    R.drawable.point_off);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_jump_guide:
            case R.id.btn_turn_main:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            default:
                break;
        }
    }

    private class GuidePagerAdapter extends PagerAdapter {

        /**
         * PagerAdapter默认缓存三个页面（当前，前后各一个）
         * 若超出缓存范围，则调用此方法，销毁页面
         *
         * @param container:ViewPager
         * @param position：位置
         * @param object:选中的页面
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(guidePagerList.get(position));
        }

        /**
         * 判断显示的是否是同一个页面
         *
         * @param view
         * @param object
         * @return
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        /**
         * 获取要滑动的页面/控件的数量
         *
         * @return
         */
        @Override
        public int getCount() {
            return guidePagerList.size();
        }

        /**
         * 当页面进入缓存范围内，调用此方法进行页面的初始化，并添加进ViewGroup中
         *
         * @param container
         * @param position
         * @return 初始化后的页面/控件
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(guidePagerList.get(position));
            return guidePagerList.get(position);
        }
    }

    private class OnGuidePageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            selectPoint(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
