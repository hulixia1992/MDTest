package tuhuan.mdtest.loginbtn;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import tuhuan.mdtest.R;

/**
 * Created by hulixia[email:hulixia@sctuhuan.com] on 2018/9/13.
 * 过渡动画
 */

public class RoundView extends View {
    private Paint paint;
    private Context context;
    private int screenW;
    private int screenH;
    private int centerX;
    private int centerY;

    public void setEndListener(OnEndListener endListener) {
        this.endListener = endListener;
    }

    private OnEndListener endListener;

    public void setLocation(int[] location) {
        centerX = location[0];
        centerY = location[1];
    }


    public RoundView(Context context) {
        super(context);
        init(context);
    }

    public RoundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RoundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 获取屏幕宽高
     */
    private void initSreenData() {
        WindowManager manager = ((Activity) context).getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        screenW = outMetrics.widthPixels;
        screenH = outMetrics.heightPixels;
    }

    private void init(Context context) {
        this.context = context;
        initSreenData();
        int[] gradientColor = new int[]{context.getResources().getColor(R.color.light_blue), context.getResources().getColor(R.color.weight_blue)};
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT
                , gradientColor);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(context.getResources().getColor(R.color.light_blue));

    }

    public void setRedius(float redius) {
        this.redius = redius;
    }

    float redius = 0.0f;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = new RectF(centerX - redius, centerY - redius, centerX + redius, centerY + redius);
        canvas.drawOval(rectF, paint);
        redius += 70;
    }

    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (screenH - centerY - redius + 300 > 0) {
                    postInvalidate();
                    try {
                        Thread.sleep(40);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (endListener != null) {
                    endListener.OnEnd();
                }
            }
        }).start();
    }

    public interface OnEndListener {
        void OnEnd();
    }
}
