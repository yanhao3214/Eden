package com.gogo.haobutler.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gogo.haobutler.R;
import com.gogo.haobutler.activity.PhotoViewActivity;
import com.gogo.haobutler.model.study.StudyRecommend;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.gogo.haobutler.activity.PhotoViewActivity.PHOTO_VIEW_URLS;

/**
 * @author: 闫昊
 * @date: 2018/7/3 0003
 * @function: 1.点赞效果实现
 * 2.点击图片，大图观看
 */
public class StudyAdapter extends BaseAdapter implements View.OnClickListener {
    private static final int TYPE_COUNT = 4;

    /**
     * 多图类型Item
     */
    private static final int TYPE_CARD_FIRST = 1;

    /**
     * 单图类型Item
     */
    private static final int TYPE_CARD_SECOND = 2;

    /**
     * ViewPager类型Item
     */
    private static final int TYPE_CARD_THIRD = 3;

    /**
     * 视频类型Item
     */
    private static final int TYPE_VIDEO = 0;

    private Context mContext;
    private List<StudyRecommend> mList;
    private LayoutInflater mInflater;
    private RequestOptions mGlideOptions;

    public StudyAdapter(Context mContext, List<StudyRecommend> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mGlideOptions = new RequestOptions().placeholder(R.drawable.xxx2).error(R.drawable.xxx1);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        StudyRecommend item = mList.get(position);
        int type = item.type;
        if (convertView == null) {
            switch (type) {
                default:
                case TYPE_CARD_FIRST:
                case TYPE_CARD_SECOND:
                    convertView = mInflater.inflate(R.layout.item_study_first_layout, parent, false);
                    holder = new Holder();
                    holder.civLogo = convertView.findViewById(R.id.civ_logo);
                    holder.tvTitle = convertView.findViewById(R.id.tv_title);
                    holder.tvPrice = convertView.findViewById(R.id.tv_price);
                    holder.tvInfo = convertView.findViewById(R.id.tv_info);
                    holder.tvFooter = convertView.findViewById(R.id.tv_footer);
                    holder.tvFrom = convertView.findViewById(R.id.tv_from);
                    holder.ivZan = convertView.findViewById(R.id.iv_zan);
                    holder.tvZan = convertView.findViewById(R.id.tv_zan);
                    holder.llProduct = convertView.findViewById(R.id.layout_scroll_horizontal);
                    break;
                case TYPE_CARD_THIRD:
                    convertView = mInflater.inflate(R.layout.item_study_third_layout, parent, false);
                    holder = new Holder();
                    holder.viewPager = convertView.findViewById(R.id.view_pager_study);
                    break;
                case TYPE_VIDEO:
                    convertView = mInflater.inflate(R.layout.item_study_video_layout, parent, false);
                    holder = new Holder();
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        switch (type) {
            case TYPE_CARD_FIRST:
            case TYPE_CARD_SECOND:
                Glide.with(mContext).load(item.logo).apply(mGlideOptions).into(holder.civLogo);
                holder.tvTitle.setText(item.title);
                holder.tvPrice.setText(item.price);
                holder.tvInfo.setText(item.info.concat("天前"));
                holder.tvFooter.setText(item.text);
                holder.tvFrom.setText(item.from);
                holder.tvZan.setText(item.zan);

                /**
                 * 移除父容器的child，防止复用产生的错乱
                 * 动态添加多个ImageView，实现横向滑动
                 */
                holder.llProduct.removeAllViews();
                for (String url : item.url) {
                    holder.llProduct.addView(newImageView(url));
                }
                convertView.setOnClickListener(this);
                break;
            case TYPE_CARD_THIRD:
                List<StudyRecommend> pagerList = breedStudyRecommend(item);
                holder.viewPager.setPageMargin(10);
                holder.viewPager.setAdapter(new StudyPagerAdapter(mContext, pagerList));
                holder.viewPager.setCurrentItem(33 * pagerList.size(), true);
                convertView.setOnClickListener(this);
                break;
            case TYPE_VIDEO:
                break;
            default:
                break;
        }
        return convertView;
    }

    /**
     * 新建ImageView、设置参数、加载图片，准备添加进LinearLayout
     */
    private ImageView newImageView(String url) {
        ImageView imageView = new ImageView(mContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.leftMargin = 5;
        imageView.setLayoutParams(layoutParams);
        Glide.with(mContext).load(url).apply(mGlideOptions).into(imageView);
        return imageView;
    }

    /**
     * 将用“@”拼接而成的二维StudyRecommend数据，转化为一维StudyRecommend数据
     */
    private List<StudyRecommend> breedStudyRecommend(StudyRecommend data) {
        List<StudyRecommend> datas = new ArrayList<>();
        String[] titles = data.title.split("@");
        String[] infos = data.info.split("@");
        String[] prices = data.price.split("@");
        String[] texts = data.text.split("@");
        List<String> urls = data.url;
        for (int i = 0; i < titles.length; i++) {
            StudyRecommend temp = new StudyRecommend();
            temp.title = titles[i];
            temp.info = infos[i];
            temp.price = prices[i];
            temp.text = texts[i];
            temp.url = new ArrayList<>(urls.subList(i * 3, i * 3 + 3));
            datas.add(temp);
        }
        return datas;
    }

    /**
     * 点击事件无效
     */
    @Override
    public void onClick(View v) {
            ArrayList<String> urlList = new ArrayList<>();
            urlList.add("http://img.mukewang.com/549bda090001c53e06000338-590-330.jpg");
            urlList.add( "http://img.mukewang.com/5707604300018d0406000338-590-330.jpg");
            urlList.add( "http://f1.diyitui.com/b3/e1/db/5f/24/ea/d8/59/1e/ea/28/04/b3/57/d6/6f.jpg");
            urlList.add("http://upload1.techweb.com.cn/s/320/2015/0527/1432714922459.jpg");
            Log.d("yh", "onItemClick: 跳转啦---urlList.size() == " + urlList.size());
            Intent intent = new Intent(mContext, PhotoViewActivity.class);
            intent.putStringArrayListExtra(PHOTO_VIEW_URLS, urlList);
            mContext.startActivity(intent);
    }

    private class Holder {
        /**
         * 公共View
         */
        CircleImageView civLogo;
        TextView tvTitle;
        TextView tvPrice;
        TextView tvInfo;
        TextView tvFooter;
        TextView tvFrom;
        ImageView ivZan;
        TextView tvZan;

        /**
         * 单图、多图 View
         */
        LinearLayout llProduct;

        /**
         * ViewPager View
         */
        ViewPager viewPager;


//        //所有Card共有属性
//        private CircleImageView mLogoView;
//        private TextView mTitleView;
//        private TextView mInfoView;
//        private TextView mFooterView;
//        //Video Card特有属性
//        private RelativeLayout mVieoContentLayout;
//        private ImageView mShareView;
//
//        //Video Card外所有Card具有属性
//        private TextView mPriceView;
//        private TextView mFromView;
//        private TextView mZanView;
//        //Card One特有属性
//        private LinearLayout mProductLayout;
//        //Card Two特有属性
//        private ImageView mProductView;
//        //Card Three特有属性
//        private ViewPager mViewPager;
    }
}
