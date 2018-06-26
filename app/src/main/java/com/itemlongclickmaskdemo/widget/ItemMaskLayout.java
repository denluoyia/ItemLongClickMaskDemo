package com.itemlongclickmaskdemo.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.itemlongclickmaskdemo.R;

/**
 * Created by denluoyia
 * Date 2018/06/26
 */
public class ItemMaskLayout extends LinearLayout{

    public ItemMaskLayout(Context context) {
        this(context, null);
    }

    public ItemMaskLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemMaskLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_item_mask, this, true);
    }

}
