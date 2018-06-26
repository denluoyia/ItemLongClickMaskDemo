package com.itemlongclickmaskdemo.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by denluoyia
 * Date 2018/06/26
 */
public class ScrollCallbackRecyclerView extends RecyclerView {
    public ScrollCallbackRecyclerView(Context context) {
        super(context);
    }

    public ScrollCallbackRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollCallbackRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private static final int TOUCH_SLOP = ViewConfiguration.getTouchSlop() * 2;

    float lastY;
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastY = event.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                if (Math.abs(event.getY() - lastY) > TOUCH_SLOP){
                    if (mScrollCallback != null){
                        mScrollCallback.scrollChanged();
                    }
                    lastY = event.getY();
                }
                break;

            case MotionEvent.ACTION_UP:
                break;
        }


        return super.dispatchTouchEvent(event);
    }

    private ScrollCallbackListener mScrollCallback;

    public void setScrollCallbackListener(ScrollCallbackListener scrollCallbackListener){
        this.mScrollCallback = scrollCallbackListener;
    }

    public interface ScrollCallbackListener{
        void scrollChanged();
    }
}

