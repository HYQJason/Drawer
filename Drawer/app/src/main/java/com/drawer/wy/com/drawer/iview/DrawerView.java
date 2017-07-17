package com.drawer.wy.com.drawer.iview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.drawer.wy.com.drawer.R;


public class DrawerView extends RelativeLayout {
    public static String TAG = "DrawerView";

    private Scroller mScroller;//scroller拖动类
    private int mScreenHeight;
    private boolean isMoving;//是否还在移动
    private int mViewHeight = 0;//布局的高度
    private boolean isShow = false;//是否打开
    public boolean mOutsideTouchable = true;//点击外面是否关闭该界面
    private int mDuration = 500;//执行动画时间
    private OndismissClickListener ondismissClickListener;

    /**
     * 关闭的监听
     */
    public void setOndismissClickListener(OndismissClickListener ondismissClickListener) {
        this.ondismissClickListener = ondismissClickListener;
    }

    public DrawerView(Context context) {
        super(context, null, 0);
        init(context);

    }

    public DrawerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.outsidestyle);
        mOutsideTouchable = ta.getBoolean(R.styleable.outsidestyle_outside, true);
        init(context);
    }

    public DrawerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    View view = null;

    private void init(Context context) {

        setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);
        setFocusable(true);
        mScroller = new Scroller(context);
        //背景设置成透明
        this.setBackgroundColor(Color.argb(0, 0, 0, 0));
        //此处改为你自己的布局文件

        view = LayoutInflater.from(context).inflate(R.layout.view_drawer, null);
        // 如果不给他设这个，它的布局的MATCH_PARENT就不知道该是多少
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        this.addView(view, params);
        view.post(new Runnable() {

            @Override
            public void run() {
                mViewHeight = view.getHeight();
            }
        });
        this.scrollTo(0, mScreenHeight);
    }

    //


    /**
     * 打开界面
     **/
    public void show() {
        if (!isShow && !isMoving) {
            this.startMoveAnim(0, -mViewHeight, mDuration);
            isShow = true;
        }
    }

    /**
     * 关闭界面
     **/
    public void dismiss() {
        if (isShow && !isMoving) {
            this.startMoveAnim(-mViewHeight, +mViewHeight, mDuration);
            isShow = false;
            if (ondismissClickListener != null) {
                ondismissClickListener.Ondismiss();
            }
        }
    }


    @Override
    public boolean dispatchTouchEvent(final MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // Notify touch outside listener if user tapped outside a given view
            if (mOutsideTouchable && view != null
                    && view.getVisibility() == View.VISIBLE) {
                Rect viewRect = new Rect();
                view.getGlobalVisibleRect(viewRect);
                if (!viewRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
                    dismiss();

                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 拖动动画
     *
     * @param startY
     * @param dy       移动到某点的Y坐标距离
     * @param duration 时间
     */
    private void startMoveAnim(int startY, int dy, int duration) {
        Log.d(TAG, "startMoveAnim==============================" + startY + "======================================" + dy);
        isMoving = true;
        mScroller.startScroll(0, startY, 0, dy, duration);
        invalidate();//通知UI线程的更新
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            Log.d(TAG, "computeScroll==============================" + mScroller.getCurrY());
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            // 更新界面
            postInvalidate();
            isMoving = true;
        } else {
            isMoving = false;
        }
        super.computeScroll();
    }

    public interface OndismissClickListener {
        void Ondismiss();


    }
}
