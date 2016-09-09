package io.github.hotstu.navichooser.navichooser;

import android.content.Context;
import android.content.DialogInterface.OnDismissListener;
import android.view.View;

import java.util.Map;


/**
 * 定义一个下钻任务， 包含分页功能
 */
public interface StateProcessProxy extends OnDismissListener {
    interface Callback {
        public void onResult(Map<String, String> userChoosedDict);
    }

    void setState(State state);

    /**
     * 加载
     */
    void load();

    /**
     * 取消
     */
    void cancel();

    /**
     * 前进
     */
    void forward();

    /**
     * 后退
     */
    void back();

    /**
     * 刷新
     */
    void refresh();

    /**
     * 不建议直接持有view对象, 请使用getViewHolder方法
     *
     * @return
     */
    ViewHolder getViewHolder();

    void setView(View container);

    void setSubmitAction(Callback action);

    Callback getSubmitAction();

    State currentState();

    State rootState();

    Context getContext();

    boolean isLoadFirstTime();

}
