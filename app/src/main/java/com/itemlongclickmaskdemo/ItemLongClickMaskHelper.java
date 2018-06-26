package com.itemlongclickmaskdemo;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.itemlongclickmaskdemo.widget.ItemMaskLayout;
import com.itemlongclickmaskdemo.widget.RippleLayout;

/**
 * Created by denluoyia
 * Date 2018/06/26
 */
public class ItemLongClickMaskHelper {

    private FrameLayout mRootFrameLayout; //列表Item根布局FrameLayout
    private ItemMaskLayout mMaskItemLayout;
    private AnimatorSet mAnimSet;
    private ItemMaskClickListener mItemMaskClickListener;

    public ItemLongClickMaskHelper(Context context){
        mMaskItemLayout = new ItemMaskLayout(context);
        TextView btnFindSame = mMaskItemLayout.findViewById(R.id.btn_find_same);
        TextView btnCollect = mMaskItemLayout.findViewById(R.id.btn_collect);

        mAnimSet = new AnimatorSet();
        ObjectAnimator anim1 = ((RippleLayout)mMaskItemLayout.findViewById(R.id.mask_bg)).getRadiusAnimator();
        int dip55 = dip2px(context, 55);
        int dip45 = dip2px(context, 45);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(btnFindSame, "translationX", -dip55, -dip45);
        mAnimSet.play(anim1)
                .with(ObjectAnimator.ofFloat(btnFindSame, "translationX", 0, -dip55))
                .with(ObjectAnimator.ofFloat(btnCollect, "translationX", 0, dip55));
        mAnimSet.play(anim2).with(ObjectAnimator.ofFloat(btnCollect, "translationX", dip55, dip45));
        mAnimSet.play(anim1).before(anim2);
        mAnimSet.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimSet.setDuration(200);

        mMaskItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissMaskLayout();
            }
        });

        mMaskItemLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                dismissMaskLayout();
                return true;
            }
        });

        btnFindSame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemMaskClickListener != null){
                    dismissMaskLayout();
                    mItemMaskClickListener.findSame();
                }
            }
        });

        btnCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemMaskClickListener != null){
                    dismissMaskLayout();
                    mItemMaskClickListener.collect();
                }
            }
        });
    }

    public synchronized void setRootFrameLayout(FrameLayout frameLayout){
        if (mRootFrameLayout != null){
            mRootFrameLayout.removeView(mMaskItemLayout);
        }
        mRootFrameLayout = frameLayout;
        mRootFrameLayout.addView(mMaskItemLayout);
        mAnimSet.start();
    }

    public void dismissMaskLayout(){
        if (mRootFrameLayout != null){
            mRootFrameLayout.removeView(mMaskItemLayout);
        }
    }

    public void setMaskItemListener(ItemMaskClickListener listener){
        this.mItemMaskClickListener = listener;
    }

    public interface ItemMaskClickListener{
        void findSame();
        void collect();
    }

    private int dip2px(Context context, float dip){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (scale * dip + 0.5f);
    }
}
