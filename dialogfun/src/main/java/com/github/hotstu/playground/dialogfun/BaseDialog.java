package com.github.hotstu.playground.dialogfun;

import android.app.Dialog;
import android.content.Context;
import androidx.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * @author hglf
 * @since 2018/4/2
 */
public class BaseDialog extends Dialog {
    public BaseDialog(@NonNull Context context) {
        this(context, getResId(context));
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        ScrollView mRootView = new ScrollView(context){
            @Override
            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) (getScreenHeight(getContext()) * .5f), MeasureSpec.AT_MOST);
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        };
        TextView child = new TextView(context);
        child.setText("hello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\n");
        mRootView.addView(child);
        this.addContentView(mRootView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public static int getResId(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.dialogfun_config_dilogStyle, typedValue, false);
        return typedValue.resourceId;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }


}
