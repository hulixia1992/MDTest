package tuhuan.mdtest.weiget;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.KeyEvent;

/**
 * Created by hulixia[email:hulixia@sctuhuan.com] on 2018/9/11.
 */

public class THInnerEditText extends AppCompatEditText {


    public OnSoftInputHide getOnSoftInputHide() {
        return onSoftInputHide;
    }

    public void setOnSoftInputHide(OnSoftInputHide onSoftInputHide) {
        this.onSoftInputHide = onSoftInputHide;
    }

    private OnSoftInputHide onSoftInputHide;

    public THInnerEditText(Context context) {
        super(context);
    }

    public THInnerEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public THInnerEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == 1) {
            onSoftInputHide.onSoftInputHide();
            super.onKeyPreIme(keyCode, event);
            return false;
        }
        return super.onKeyPreIme(keyCode, event);
    }

    public interface OnSoftInputHide {
        void onSoftInputHide();
    }
}
