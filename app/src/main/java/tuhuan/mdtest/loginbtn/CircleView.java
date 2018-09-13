package tuhuan.mdtest.loginbtn;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hulixia[email:hulixia@sctuhuan.com] on 2018/9/12.
 * button缩小以后显示 的圆形圈圈
 */

public class CircleView extends View {
    public void setRadius(int radius) {
        this.radius = radius;
    }

    private int radius;//半径
    private int stroke = 3;//圆圈的粗细
    private Paint paint;
    private int startAngle = 0;//开始画的角度
    private int radian = 270;//弧形的弧度
    private int centerX;
    private int centerY;
    private boolean shouldAnim = true;

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(stroke);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * 这是一个居中的圆
         */
        float x = (getWidth() - getHeight() / 2) / 2;
        float y = getHeight() / 4;
        RectF rect = new RectF(x, y,
                getWidth() - x, getHeight() - y);
//        RectF rect = new RectF(0, 0,
//                getWidth() , getHeight() );
        canvas.drawArc(rect, startAngle, radian, false, paint);
        startAngle += 6;
    }

    private void startToSwap() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (shouldAnim) {
                    postInvalidate();
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {


                    }
                }
            }
        }).start();
    }

    public void stop() {
        setVisibility(GONE);
        shouldAnim = false;
    }

    public void start() {
        setVisibility(VISIBLE);
        shouldAnim = true;
        startToSwap();
    }
}
