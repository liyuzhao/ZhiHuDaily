package com.sdust.zhihudaily.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sdust.zhihudaily.R;
import com.sdust.zhihudaily.util.DensityUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class AvatarsView extends HorizontalScrollView {
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.llAvatarContainer)
    LinearLayout llAvatarContainer;
    private DisplayImageOptions mOptions;

    public AvatarsView(Context context) {
        this(context, null);
    }

    public AvatarsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AvatarsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOverScrollMode(OVER_SCROLL_NEVER);
        LayoutInflater.from(getContext()).inflate(R.layout.view_avatars, this, true);
        ButterKnife.inject(this);
        mOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.comment_avatar)
                .showImageForEmptyUri(R.drawable.comment_avatar)
                .showImageOnFail(R.drawable.comment_avatar)
                .displayer(new CircleBitmapDisplayer())
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();
    }

    public void bindData(String name, List<String> images) {
        title.setText(name);
        int hw = DensityUtils.dipToPx(getContext(), 36);
        int margin = DensityUtils.dipToPx(getContext(), 8);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(hw, hw);
        lp.setMargins(margin, margin, margin, margin);
        llAvatarContainer.removeAllViews();
        if (images != null && images.size() > 0) {
            for (String url : images) {
                ImageView imageView = new ImageView(getContext());
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setLayoutParams(lp);
                llAvatarContainer.addView(imageView);
                ImageLoader.getInstance().displayImage(url, imageView, mOptions);
            }
        }
    }

    private float mDownX;
    private float mDownY;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX();
                mDownY = ev.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(ev.getX() - mDownX) > Math.abs(ev.getY() - mDownY)) {

                    //不允许拦截事件，自己处理
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
