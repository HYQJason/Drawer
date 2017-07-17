package com.drawer.wy.com.drawer;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.drawer.wy.com.drawer.iview.DrawerView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageView img_qq;
    //下拉布局的高度
    private int rlTopShareHeight;



    private RelativeLayout relat_top_share;
    private ImageButton left_top_share;
    private ImageButton ibtnClose;





    private RelativeLayout realt_bottom_share;
    private ImageButton right_bottom_close;
    private ImageButton right_bottom_share;
    private ObjectAnimator topUpAnimation;
    private ObjectAnimator topPullAnimation;

    private ObjectAnimator bottomUpAnimation;
    private ObjectAnimator bottomPullAnimation;
    private static final String TAG = "-SONG-";


   DrawerView drawerView;

    Button btdismiss;

    Button start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();

    }



    /**
     * 初始化View
     */
    private void initView() {
        drawerView= (DrawerView) findViewById(R.id.shareView);
        start= (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerView.show();

            }
        });


        btdismiss= (Button) findViewById(R.id.btdismiss);
        btdismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerView.dismiss();
            }
        });
        img_qq = (ImageView) findViewById(R.id.img_qq);
        left_top_share = (ImageButton) findViewById(R.id.left_top_share);
        ibtnClose = (ImageButton) findViewById(R.id.ibtn_close);


        right_bottom_close = (ImageButton) findViewById(R.id.right_bottom_close);
        right_bottom_share = (ImageButton) findViewById(R.id.right_bottom_share);

        relat_top_share = (RelativeLayout) findViewById(R.id.relat_top_share);
        realt_bottom_share = (RelativeLayout) findViewById(R.id.realt_bottom_share);
        //解决在onCreate中不能获取高度的问题
        relat_top_share.post(new Runnable() {
            @Override
            public void run() {

                rlTopShareHeight = relat_top_share.getHeight();
                initAnimation();
            }
        });
    }

    /**
     * 注册事件
     */
    private void setListener() {

        img_qq.setOnClickListener(this);
        ibtnClose.setOnClickListener(this);
        left_top_share.setOnClickListener(this);
        right_bottom_close.setOnClickListener(this);
        right_bottom_share.setOnClickListener(this);
    }

    /**
     * 初始化Animation
     */
    private void initAnimation() {
        /**
         * 顶部动画
         */
        //打开动画
        topPullAnimation = ObjectAnimator.ofFloat(
                relat_top_share,"translationY",rlTopShareHeight);
        topPullAnimation.setDuration(500);
        topPullAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        //关闭动画
        topUpAnimation = ObjectAnimator.ofFloat(
                relat_top_share,"translationY",-rlTopShareHeight);
        topUpAnimation.setDuration(500);
        topUpAnimation.setInterpolator(new AccelerateDecelerateInterpolator());


        /**
         * 底部动画
         */
        //打开动画
        bottomUpAnimation = ObjectAnimator.ofFloat(
                realt_bottom_share, "translationY", -rlTopShareHeight);
        bottomUpAnimation.setDuration(500);
        bottomUpAnimation.setInterpolator(new AccelerateDecelerateInterpolator());

        //关闭动画
        bottomPullAnimation = ObjectAnimator.ofFloat(
                realt_bottom_share, "translationY", rlTopShareHeight);
        bottomPullAnimation.setDuration(500);
        bottomPullAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
    }

    /**
     * 单击事件
     * @param v
     */
    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.left_top_share:
                //click share btn
                if(!topPullAnimation.isRunning()) {
                    topPullAnimation.start();
                }
                break;
            case R.id.ibtn_close:
                //click close btn
                if(!topUpAnimation.isRunning()) {
                    topUpAnimation.start();
                }
                break;
            case R.id.right_bottom_share:
                //click close btn
                if(!bottomUpAnimation.isRunning()) {
                    bottomUpAnimation.start();
                }
                break;
            case R.id.right_bottom_close:
                //click close btn
                if(!bottomPullAnimation.isRunning()) {
                    bottomPullAnimation.start();
                }
                break;
            default:
                break;
        }
    }
}
