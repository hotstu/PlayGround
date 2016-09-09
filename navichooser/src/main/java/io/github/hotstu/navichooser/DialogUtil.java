package io.github.hotstu.navichooser;

import android.app.Activity;
import android.app.Dialog;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class DialogUtil {
    /**
     * 显示位置在屏幕中央
     *
     * @param context
     * @param view
     * @return
     */
    public static Dialog getCenterDialog(Activity context, View view) {
        return getMenuDialog(context, view, Gravity.CENTER);
    }

    /**
     * dialog 下为dim的浮动窗口， 类似modal窗口
     *
     * @param context
     * @param view
     * @param gravity 设置dialog显示的位置 see {@link Gravity}
     * @param width   in px, or WARP_CONTENT MATCH_PERENT
     * @param height  in px, or WARP_CONTENT MATCH_PERENT
     * @return
     */
    public static Dialog getMenuDialog(Activity context, View view, int gravity, int width, int height) {
        final Dialog dialog = new Dialog(context, R.style.MenuDialogStyle_Floating);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = width;
        lp.height = height;
        window.setGravity(gravity); // 此处可以设置dialog显示的位置
        window.setWindowAnimations(R.style.MenuDialogAnimation); // 添加动画
        return dialog;
    }

    /**
     * @param context
     * @param view
     * @param gravity 设置dialog显示的位置 see {@link Gravity}
     * @return
     */
    public static Dialog getMenuDialog(Activity context, View view, int gravity) {
        final Dialog dialog = new Dialog(context, R.style.MenuDialogStyle);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        int[] screenWH = new int[2];
        getScreenWidthAndHeight(context, screenWH);
        lp.width = screenWH[0];
        lp.height = screenWH[1];
        if (lp.width > lp.height) {
            lp.width *= .5f;
        } else {
            lp.height *= .5f;
        }
        window.setGravity(gravity); // 此处可以设置dialog显示的位置
        //window.setWindowAnimations(R.style.MenuDialogAnimation); // 添加动画
        return dialog;
    }

    public static void getScreenWidthAndHeight(Activity context, int[] holder) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        holder[0] = dm.widthPixels;
        holder[1] = dm.heightPixels;
    }

    public static int getScreenWidth(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static int getScreenHeight(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }


}
