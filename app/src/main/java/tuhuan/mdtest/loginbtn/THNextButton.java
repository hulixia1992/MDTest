package tuhuan.mdtest.loginbtn;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import tuhuan.mdtest.R;

/**
 * Created by hulixia[email:hulixia@sctuhuan.com] on 2018/9/12.
 */

public class THNextButton extends RelativeLayout {
    private Button btnNext;
    private RelativeLayout rlNext;
    private int textColor;
    private int textSize;
    private String textContent;
    private int height;
    private int btnWidth;
    private boolean isRound = false;
    private Context mContext;
    private CircleView cvProgress;
    private int screenW;
    private int screenH;
    private int minWidth;

//    public Button getBtnShare() {
//        return btnShare;
//    }

//    private Button btnShare;

    public CallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    private CallBack callBack;

    public THNextButton(Context context) {
        super(context);
    }

    public THNextButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initArrayType(context, attrs);
        initView(context);
    }

    public Button getBtnNext() {
        return btnNext;
    }

    public THNextButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initArrayType(context, attrs);
        initView(context);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private void initArrayType(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.THNextButton);
        textColor = typedArray.getColor(R.styleable.THNextButton_text_color, Color.BLACK);
        textContent = typedArray.getString(R.styleable.THNextButton_text);
        textSize = typedArray.getDimensionPixelSize(R.styleable.THNextButton_text_size, dip2px(context, 14));
        height = typedArray.getDimensionPixelSize(R.styleable.THNextButton_height, dip2px(context, 27));
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取屏幕宽高
     */
    private void initSreenData() {
        WindowManager manager = ((Activity) mContext).getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        screenW = outMetrics.widthPixels;
        screenH = outMetrics.heightPixels;
    }

    private void initView(Context context) {
        mContext = context;

        LayoutInflater.from(context).inflate(R.layout.layout_th_next_button, this, true);
        btnNext = findViewById(R.id.btn_next);
        // btnShare = findViewById(R.id.btn_share);
        cvProgress = findViewById(R.id.cv_progress);
        rlNext = findViewById(R.id.rl_next);
        RelativeLayout.LayoutParams proressLP = new RelativeLayout.LayoutParams(height, height);
        proressLP.addRule(RelativeLayout.CENTER_IN_PARENT);
        cvProgress.setLayoutParams(proressLP);
        btnNext.setText(textContent);
        btnNext.setTextColor(textColor);
        btnNext.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        btnNext.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        cvProgress.setRadius(height * 2 / 3);
        minWidth = height + dip2px(mContext, 10);
        btnNext.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                btnNext.getViewTreeObserver().removeOnPreDrawListener(this);
                initSreenData();
                btnWidth = btnNext.getWidth();
                return false;
            }
        });
        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToRound();
            }
        });
    }

    private void changeToRound() {
        if (!isRound) {//正在转圈圈
            btnNext.setText("");//让字体消失
            isRound = true;

            int changeWidth = btnWidth - minWidth;
            final float onePercent = changeWidth / 100.0f;
            ValueAnimator animator = ValueAnimator.ofInt(0, 100);
            animator.setDuration(500);
            animator.setRepeatCount(0);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int currentValue = (int) animation.getAnimatedValue();
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(btnWidth - Math.round(onePercent * currentValue), height);
                    layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                    btnNext.setLayoutParams(layoutParams);
                    if (currentValue == 100) {
                        //      btnShare.setVisibility(VISIBLE);
                        //     btnShare.setLayoutParams(layoutParams);
                        cvProgress.setVisibility(VISIBLE);
                        cvProgress.start();
                    }
                }
            });
            animator.start();

        }
    }

    /**
     * 成功之后执行中转动画
     */
    public void onSuccess() {
        cvProgress.stop();
        // btnShare.setVisibility(VISIBLE);
        //   btnNext.setVisibility(GONE);
//        int maxRadius = screenH > screenW ? screenH : screenW;
//        ValueAnimator animator = ValueAnimator.ofInt(0, 100);
//        animator.setDuration(500);
//        animator.setRepeatCount(0);
//        final float onePercent = (maxRadius - minWidth) / 100.0f;
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                int currentPercent = (int) animation.getAnimatedValue();
//                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(minWidth + Math.round(onePercent * currentPercent), minWidth + Math.round(onePercent * currentPercent));
//                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
//                layoutParams.removeRule(RelativeLayout.BELOW);
//                btnNext.setLayoutParams(layoutParams);
//                if (currentPercent == 100) {
//                    if (callBack != null) {
//                        callBack.onSuccess();
//                    }
//                }
//            }
//        });
//        animator.start();
    }

    /**
     * 失败之后执行复原动画
     */
    public void onFail() {
        cvProgress.stop();
        backAnim();
    }

    private void backAnim() {
        if (isRound) {
            isRound = !isRound;
            int changeWidth = btnWidth - minWidth;
            final float onePercent = changeWidth / 100.0f;
            ValueAnimator animator = ValueAnimator.ofInt(0, 100);
            animator.setDuration(500);
            animator.setRepeatCount(0);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int currentValue = (int) animation.getAnimatedValue();
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(minWidth + Math.round(onePercent * currentValue), height);
                    layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                    btnNext.setLayoutParams(layoutParams);
                    if (currentValue == 100) {
                        //    btnShare.setVisibility(getBaseline());
                        btnNext.setText(textContent);
                        //   btnShare.setLayoutParams(layoutParams);
                        // cvProgress.setVisibility(VISIBLE);
                        //   cvProgress.start();
                    }
                }
            });
            animator.start();
        }
    }

    public interface CallBack {
        void onSuccess();

        void onFail();
    }
}
