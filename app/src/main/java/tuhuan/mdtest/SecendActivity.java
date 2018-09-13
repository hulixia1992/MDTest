package tuhuan.mdtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SecendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secend);
        findViewById(R.id.btn_share).setBackgroundResource(R.drawable.bg_share);
    }


}
