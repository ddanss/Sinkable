package com.ddanss.sinkable;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ddanss on 11/13/2014.
 */
public class Sink implements View.OnTouchListener{

    /**
     * Adds sink animation to view with default values.
     * elevation reaches 0 (pixels).
     * scale shrinks to 0.95.
     * animation is 150ms.
     * @param view
     */
    public static void MakeSinkable(View view){
        MakeSinkable(view, 0, 0.95f, 150);
    }

    /**
     * Adds sink animation to view.
     * @param view view to add animation to.
     * @param sunkElevation elevation value (in pixels) to lower view to on touch.
     * @param sunkScale scale to shrink view to on touch.
     * @param animationDuration animation duration for view to float back up.
     */
    public static void MakeSinkable(View view, float sunkElevation, float sunkScale, long animationDuration){
        Sink sink = new Sink();
        sink.makeSinkable(view, sunkElevation, sunkScale, animationDuration);
    }

    private View mThisView;
    private float mSunkElevation;
    private float mSunkScale;
    private long mAnimationDuration;
    private float mOriginalElevation;
    private AnimatorSet mSinkAnimationSet;

    private void makeSinkable(View view, float sunkElevation, float sunkScale, long animationDuration){
        mThisView = view;
        mThisView.setOnTouchListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mOriginalElevation = view.getElevation();
        }
        mSunkElevation = sunkElevation;
        mSunkScale = sunkScale;
        mAnimationDuration = animationDuration;

        mSinkAnimationSet = new AnimatorSet();
        ObjectAnimator scaleXAnimation = ObjectAnimator.ofFloat(mThisView, "scaleX", 1);
        scaleXAnimation.setDuration(mAnimationDuration);
        ObjectAnimator scaleYAnimation = ObjectAnimator.ofFloat(mThisView, "scaleY", 1);
        scaleYAnimation.setDuration(mAnimationDuration);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ObjectAnimator elevationAnimation = ObjectAnimator.ofFloat(mThisView, "elevation", mSunkElevation, mOriginalElevation);
            elevationAnimation.setDuration(mAnimationDuration);
            mSinkAnimationSet.playTogether(scaleXAnimation, scaleYAnimation, elevationAnimation);
        } else {
            mSinkAnimationSet.playTogether(scaleXAnimation, scaleYAnimation);
        }
        mThisView.invalidate();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mThisView.setElevation(mSunkElevation);
                }
                mThisView.setScaleX(mSunkScale);
                mThisView.setScaleY(mSunkScale);
                break;
            case MotionEvent.ACTION_UP:
                mSinkAnimationSet.start();
                break;
            default:
                break;
        }
        return true;
    }
}
