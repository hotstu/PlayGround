package io.github.hotstu.navichooser;

import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import io.github.hotstu.navichooser.navichooser.QueryMap;
import io.github.hotstu.navichooser.navichooser.State;
import io.github.hotstu.navichooser.navichooser.StateImpl_1_RoomName;
import io.github.hotstu.navichooser.navichooser.StateProcessProxy;
import io.github.hotstu.navichooser.navichooser.StateProcessProxyImpl;
import io.github.hotstu.navichooser.navichooser.ViewHolder;

/**
 * Created by hotstuNg on 2016/9/7.
 */

public class NaviView extends FrameLayout implements StateProcessProxy {
    StateProcessProxy delegate;

    public NaviView(Context context) {
        super(context);
        init();
    }

    public NaviView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NaviView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View child = inflater.inflate(R.layout.dialog_listview, this, false);
        addView(child);
        delegate = new StateProcessProxyImpl(getContext());
        initDelegate();
    }

    private void initDelegate() {
        final ListView list = (ListView) findViewById(android.R.id.list);
        ImageView btn_nav_back = (ImageView) findViewById(R.id.btn_nav_back);
        ImageView btn_nav_froward = (ImageView) findViewById(R.id.btn_nav_forward);
        ViewHolder holder = new ViewHolder();
        holder.tv_title = (TextView) findViewById(android.R.id.title);
        holder.btn_back = btn_nav_back;
        holder.btn_forward = btn_nav_froward;
        holder.listView = list;
        holder.progress = findViewById(android.R.id.progress);
        holder.empty = findViewById(android.R.id.empty);
        holder.summary = findViewById(android.R.id.summary);
        holder.tv_summary_msg = (TextView) holder.summary.findViewById(R.id.tv_summary_info);
        holder.btn_summary_reselect = holder.summary.findViewById(R.id.btn_summary_reselect);
        holder.btn_summary_ok = holder.summary.findViewById(R.id.btn_summary_ok);
        holder.tv_empty_msg = (TextView) holder.empty.findViewById(R.id.tv_empty);
        holder.btn_empty_refresh = (Button) holder.empty.findViewById(R.id.btn_empty);
        setTag(holder);
        btn_nav_back.setImageDrawable(DrawableUtil.tintDrawable(getResources().getDrawable(R.drawable.left_round), getResources().getColorStateList(R.color.seletor_simple_tintstatecolor)));
        btn_nav_froward.setImageDrawable(DrawableUtil.tintDrawable(getResources().getDrawable(R.drawable.right_round), getResources().getColorStateList(R.color.seletor_simple_tintstatecolor)));
        btn_nav_back.setEnabled(false);
        btn_nav_froward.setEnabled(false);
        delegate.setView(this);
        QueryMap paramsMap = new QueryMap();
        paramsMap.put("method", "queryNew");
        new StateImpl_1_RoomName(delegate);
        delegate.rootState().setQueryMap(paramsMap);
        btn_nav_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delegate.back();
                if (delegate.getViewHolder().summary != null && delegate.getViewHolder().summary.getVisibility() != View.GONE) {
                    delegate.getViewHolder().summary.setVisibility(View.GONE);
                }
            }
        });
        btn_nav_froward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delegate.forward();
                if (delegate.getViewHolder().summary != null && delegate.getViewHolder().summary.getVisibility() != View.GONE) {
                    delegate.getViewHolder().summary.setVisibility(View.GONE);
                }
            }
        });
        holder.btn_empty_refresh.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                delegate.refresh();
            }
        });
    }


    @Override
    public void setState(State state) {
        delegate.setState(state);
    }

    @Override
    public void load() {
        delegate.load();
    }

    @Override
    public void cancel() {
        delegate.cancel();
    }

    @Override
    public void forward() {
        delegate.forward();
    }

    @Override
    public void back() {
        delegate.back();
    }

    @Override
    public void refresh() {
        delegate.refresh();
    }

    @Override
    public ViewHolder getViewHolder() {
        return delegate.getViewHolder();
    }

    @Override
    public void setView(View container) {
        delegate.setView(container);
    }

    @Override
    public void setSubmitAction(Callback action) {
        delegate.setSubmitAction(action);
    }

    @Override
    public Callback getSubmitAction() {
        return delegate.getSubmitAction();
    }

    @Override
    public State currentState() {
        return delegate.currentState();
    }

    @Override
    public State rootState() {
        return delegate.rootState();
    }

    @Override
    public boolean isLoadFirstTime() {
        return delegate.isLoadFirstTime();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        delegate.onDismiss(dialog);
    }
}
