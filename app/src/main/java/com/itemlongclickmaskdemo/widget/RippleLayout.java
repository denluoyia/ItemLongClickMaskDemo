package com.itemlongclickmaskdemo.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;


/**
 * 水波纹从中心扩散布局
 * Created by denluoyia on 2018/06/06
 */
public class RippleLayout extends LinearLayout{

    private int centerX = -1;
    private int centerY = -1;
    private float radius = 0;
    private float maxRadius = 0;
    private boolean running = false;
    private float startValue = 0;
    private float endValue = 1;

    private ObjectAnimator radiusAnimator;
    private Animator.AnimatorListener animatorListener;
    private Path ripplePath;

    public RippleLayout(Context context) {
        super(context);
        init();
    }

    public RippleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RippleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        ripplePath = new Path();
        radiusAnimator = ObjectAnimator.ofFloat(this, "animValue", startValue, endValue);
        radiusAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        radiusAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                running = true;
                if (animatorListener != null) {
                    animatorListener.onAnimationStart(animator);
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                running = false;
                if (animatorListener != null) {
                    animatorListener.onAnimationEnd(animator);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                if (animatorListener != null) {
                    animatorListener.onAnimationCancel(animator);
                }
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                if (animatorListener != null) {
                    animatorListener.onAnimationRepeat(animator);
                }
            }
        });

    }

    public void setAnimatorListener(Animator.AnimatorListener animatorListener) {
        this.animatorListener = animatorListener;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        initCenter(left, top, right, bottom);
        maxRadius = maxRadius(left, top, right, bottom);
        super.onLayout(changed, left, top, right, bottom);
    }

    private void initCenter(int left, int top, int right, int bottom) {
        if (centerX == -1 && centerY == -1) {
            centerX = (right - left) / 2;
            centerY = (bottom - top) / 2;
        }
    }

    private float maxRadius(int left, int top, int right, int bottom) {
        float x = Math.max(Math.abs(centerX - left), Math.abs(centerX - right));
        float y = Math.max(Math.abs(centerY - top), Math.abs(centerY - bottom));
        return (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public void setAnimValue(float radius) {
        this.radius = radius * maxRadius;
        invalidate();
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        if (running) {
            final int state = canvas.save();
            ripplePath.reset();
            ripplePath.addCircle(centerX, centerY, radius, Path.Direction.CW);
            canvas.clipPath(ripplePath);
            boolean invalidated = super.drawChild(canvas, child, drawingTime);
            canvas.restoreToCount(state);
            return invalidated;
        }
        return super.drawChild(canvas, child, drawingTime);
    }

    public ObjectAnimator getRadiusAnimator() {
        return radiusAnimator;
    }
}
