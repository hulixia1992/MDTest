package tuhuan.mdtest;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import tuhuan.mdtest.loginbtn.THNextButton;
import tuhuan.mdtest.weiget.THEditText;

public class MainActivity extends AppCompatActivity implements View.OnLayoutChangeListener {
    //Activity最外层的Layout视图
    private View activityRootView;
    //屏幕高度
    private int screenHeight = 0;
    //软件盘弹起后所占高度阀值
    private int keyHeight = 0;
    private THEditText thEditText1;
    private THEditText thEditText2;

    private THNextButton nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityRootView = findViewById(R.id.root_layout);
        thEditText1 = findViewById(R.id.t_one);
        thEditText2 = findViewById(R.id.t_two);
        nextButton = findViewById(R.id.th_next);
        //获取屏幕高度
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        keyHeight = screenHeight / 3;
        findViewById(R.id.btn_success).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  nextButton.onSuccess();
                nextButton.getBtnShare().setVisibility(View.VISIBLE);
                Intent intent = new Intent(MainActivity.this, SecendActivity.class);
                startActivity(intent,
                        ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, findViewById(R.id.btn_share), "shareName").toBundle());
            }
        });
        findViewById(R.id.btn_fail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextButton.onFail();
//                nextButton.getBtnShare().setVisibility(View.VISIBLE);
//                Intent intent = new Intent(MainActivity.this, SecendActivity.class);
//                startActivity(intent,
//                        ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, findViewById(R.id.btn_share), "shareName").toBundle());
            }
        });
        nextButton.setCallBack(new THNextButton.CallBack() {
            @Override
            public void onSuccess() {

                //   startActivity(intent);
            }

            @Override
            public void onFail() {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityRootView.addOnLayoutChangeListener(this);
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {

            //  Toast.makeText(MainActivity.this, 监听到软键盘弹起..., Toast.LENGTH_SHORT).show();

        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
            thEditText1.whenSoftInputHide();
            thEditText2.whenSoftInputHide();
            // Toast.makeText(MainActivity.this, 监听到软件盘关闭..., Toast.LENGTH_SHORT).show();

        }

    }
}
