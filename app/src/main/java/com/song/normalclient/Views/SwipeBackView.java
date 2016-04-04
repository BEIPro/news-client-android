package com.song.normalclient.Views;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import com.song.normalclient.R;

/**
 * Created by songsubei on 04/04/16.
 */
public class SwipeBackView extends FrameLayout {

    private Drawable leftShadow;
    private FragmentActivity fragmentActivity;
    private boolean swipeEnabled = true;
    private boolean swipeAnyWhere = true;
    private boolean swipeFinished = false;
    private Fragment fragment;

    private boolean canSwipe = false;
    private boolean ignoreSwipe = false;
    private View content;
    private int sideWidthInDP = 16;
    private int sideWidth = 72;
    private int screenWidth = 1080;
    private VelocityTracker tracker;

    private float downX;
    private float downY;
    private float lastX;
    private float currentX;
    private float currentY;

    private int touchSlopDP = 20;
    private int touchSlop = 60;

    public SwipeBackView(Context context) {
        super(context);
        this.fragmentActivity = (FragmentActivity) context;
    }

    public SwipeBackView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.fragmentActivity = (FragmentActivity) context;
    }

    public SwipeBackView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.fragmentActivity = (FragmentActivity) context;
    }

    public void replaceLayer(View rootView) {
        leftShadow = fragmentActivity.getResources().getDrawable(R.drawable.left_shadow);
        touchSlop = (int) (touchSlopDP * fragmentActivity.getResources().getDisplayMetrics().density);
        sideWidth = (int) (sideWidthInDP * fragmentActivity.getResources().getDisplayMetrics().density);
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) fragmentActivity.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        setClickable(true);
        content = rootView;
        ViewGroup.LayoutParams params = content.getLayoutParams();
        this.addView(content, params);
    }

    @Override
    protected boolean drawChild(@NonNull Canvas canvas, @NonNull View child, long drawingTime) {
        boolean result = super.drawChild(canvas, child, drawingTime);
        final int shadowWidth = leftShadow.getIntrinsicWidth();
        int left = (int) (getContentX()) - shadowWidth;
        leftShadow.setBounds(left, child.getTop(), left + shadowWidth, child.getBottom());
        leftShadow.draw(canvas);
        return result;
    }

    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent ev) {

        if (swipeEnabled && !canSwipe && !ignoreSwipe) {
            if (swipeAnyWhere) {
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = ev.getX();
                        downY = ev.getY();
                        currentX = downX;
                        currentY = downY;
                        lastX = downX;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float dx = ev.getX() - downX;
                        float dy = ev.getY() - downY;
                        if (dx * dx + dy * dy > touchSlop * touchSlop) {
                            if (dy == 0f || Math.abs(dx / dy) > 1) {
                                downX = ev.getX();
                                downY = ev.getY();
                                currentX = downX;
                                currentY = downY;
                                lastX = downX;
                                canSwipe = true;
                                tracker = VelocityTracker.obtain();
                                return true;
                            } else {
                                ignoreSwipe = true;
                            }
                        }
                        break;
                }
            } else if (ev.getAction() == MotionEvent.ACTION_DOWN && ev.getX() < sideWidth) {
                canSwipe = true;
                tracker = VelocityTracker.obtain();
                return true;
            }
        }
        if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL) {
            ignoreSwipe = false;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return canSwipe || super.onInterceptTouchEvent(ev);
    }

    boolean hasIgnoreFirstMove;

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (canSwipe) {
            tracker.addMovement(event);
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    downX = event.getX();
                    downY = event.getY();
                    currentX = downX;
                    currentY = downY;
                    lastX = downX;
                    break;
                case MotionEvent.ACTION_MOVE:
                    currentX = event.getX();
                    currentY = event.getY();
                    float dx = currentX - lastX;
                    if (dx != 0f && !hasIgnoreFirstMove) {
                        hasIgnoreFirstMove = true;
                        dx = dx / dx;
                    }
                    if (getContentX() + dx < 0) {
                        setContentX(0);
                    } else {
                        setContentX(getContentX() + dx);
                    }
                    lastX = currentX;
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    tracker.computeCurrentVelocity(10000);
                    tracker.computeCurrentVelocity(1000, 20000);
                    canSwipe = false;
                    hasIgnoreFirstMove = false;
                    int mv = screenWidth * 3;
                    if (Math.abs(tracker.getXVelocity()) > mv) {
                        animateFromVelocity(tracker.getXVelocity());
                    } else {
                        if (getContentX() > screenWidth / 3) {
                            animateFinish(false);
                        } else {
                            animateBack(false);
                        }
                    }
                    tracker.recycle();
                    break;
                default:
                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    ObjectAnimator animator;

    public void cancelPotentialAnimation() {
        if (animator != null) {
            animator.removeAllListeners();
            animator.cancel();
        }
    }

    public void setContentX(float x) {
        int ix = (int) x;
        content.setX(ix);
        invalidate();
    }

    public float getContentX() {
        return content.getX();
    }

    private void animateBack(boolean withVel) {
        cancelPotentialAnimation();
        animator = ObjectAnimator.ofFloat(this, "contentX", getContentX(), 0);
        int tmpDuration = withVel ? ((int) (duration * getContentX() / screenWidth)) : duration;
        if (tmpDuration < 100) {
            tmpDuration = 100;
        }
        animator.setDuration(tmpDuration);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }

    private void animateFinish(boolean withVel) {
        cancelPotentialAnimation();
        animator = ObjectAnimator.ofFloat(this, "contentX", getContentX(), screenWidth);
        int tmpDuration = withVel ? ((int) (duration * (screenWidth - getContentX()) / screenWidth)) : duration;
        if (tmpDuration < 100) {
            tmpDuration = 100;
        }
        animator.setDuration(tmpDuration);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                swipeFinished = true;
                fragmentActivity.getSupportFragmentManager().popBackStack();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }
        });
        animator.start();
    }

    private final int duration = 200;

    private void animateFromVelocity(float v) {
        if (v > 0) {
            if (getContentX() < screenWidth / 3 && v * duration / 1000 + getContentX() < screenWidth / 3) {
                animateBack(false);
            } else {
                animateFinish(true);
            }
        } else {
            if (getContentX() > screenWidth / 3 && v * duration / 1000 + getContentX() > screenWidth / 3) {
                animateFinish(false);
            } else {
                animateBack(true);
            }
        }

    }
}

