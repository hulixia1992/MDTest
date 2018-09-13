package tuhuan.mdtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SecendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secend);
    }

    @Override
    protected void onResume() {
        super.onResume();
        findViewById(R.id.btn_share).setBackgroundResource(R.drawable.bg_share);
//        TimerTask task = new TimerTask() {
//            @Override
//            public void run() {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                });
//            }
//        };
//        Timer timer = new Timer();
//        timer.schedule(task, 500);
        //
    }
}
