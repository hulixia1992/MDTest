package tuhuan.mdtest.weiget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import tuhuan.mdtest.R;

/**
 * Created by hulixia[email:hulixia@sctuhuan.com] on 2018/9/11.
 * 带动画效果的edittext
 */

public class THEditText extends LinearLayout {
    private THInnerEditText etContent;
    private View vLine;
    private String hint;
    private int textColor = Color.BLACK;
    private int hintTextColor = Color.GRAY;
    private int lineColor = Color.parseColor("#562912");
    private int etContentWidth;
    private Context mContext;
    private boolean isFull;

    public THEditText(Context context) {
        super(context);

        initView(context);
    }

    public THEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initTypeArray(context, attrs);
        initView(context);
    }

    public THEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTypeArray(context, attrs);
        initView(context);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private void whenFocus() {
        if (isFull)
            return;
        isFull = true;
        ValueAnimator animator = ValueAnimator.ofInt(0, 100);
        animator.setDuration(500);
        animator.setRepeatCount(0);
        vLine.setLayoutParams(new LinearLayout.LayoutParams(0, dip2px(mContext, 1)));
        final float onePercent = etContentWidth / 100.0f;
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentValue = (int) animation.getAnimatedValue();
                vLine.setLayoutParams(new LinearLayout.LayoutParams(Math.round(onePercent * currentValue), dip2px(mContext, 1)));
            }
        });
        animator.start();
    }

    public void whenSoftInputHide() {
        whenNotFocus();
    }

    private void whenNotFocus() {
        if (!isFull)
            return;
        isFull = false;
        ValueAnimator animator = ValueAnimator.ofInt(100, 0);
        animator.setDuration(500);
        animator.setRepeatCount(0);
        final int onePercent = etContentWidth / 100;
        vLine.setLayoutParams(new LinearLayout.LayoutParams(etContentWidth, dip2px(mContext, 1)));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentValue = (int) animation.getAnimatedValue();
                vLine.setLayoutParams(new LinearLayout.LayoutParams(onePercent * currentValue, dip2px(mContext, 1)));
            }
        });
        animator.start();
    }

    private void initTypeArray(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.THEditText);
        hint = typedArray.getString(R.styleable.THEditText_hint);
        textColor = typedArray.getColor(R.styleable.THEditText_text_color, Color.BLACK);
        hintTextColor = typedArray.getColor(R.styleable.THEditText_hint_text_color, Color.GRAY);
        lineColor = typedArray.getColor(R.styleable.THEditText_line_color, Color.BLACK);
    }

    private void initView(final Context context) {
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.layout_th_edit, this, true);

        etContent = findViewById(R.id.et_contents);
        vLine = findViewById(R.id.v_line);
        etContent.setHint(hint);
        etContent.setTextColor(textColor);
        etContent.setHintTextColor(hintTextColor);
        vLine.setBackgroundColor(lineColor);
        etContent.setOnSoftInputHide(new THInnerEditText.OnSoftInputHide() {
            @Override
            public void onSoftInputHide() {
                whenNotFocus();
            }
        });
        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                etContent.getViewTreeObserver().removeOnPreDrawListener(this);
                etContentWidth = getWidth();
                //    etContentWidth = etContent.getWidth();
                return false;
            }
        });
        etContent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!isFull) {
//                    isFull = true;
                whenFocus();
//                } else {
//                    isFull = false;
//                    whenNotFocus();
//                }

            }
        });

        etContent.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (etContentWidth != 0) {
                        whenFocus();
                    }
                } else {
                    whenNotFocus();
                }

            }
        });
    }
}
