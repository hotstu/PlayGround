package io.github.hotstu.navichooser;

import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

import io.github.hotstu.navichooser.navichooser.QueryMap;
import io.github.hotstu.navichooser.navichooser.StateImpl_1_RoomName;
import io.github.hotstu.navichooser.navichooser.StateProcessProxy;
import io.github.hotstu.navichooser.navichooser.StateProcessProxyImpl;
import io.github.hotstu.navichooser.navichooser.ViewHolder;

import static io.github.hotstu.navichooser.DialogUtil.getScreenWidthAndHeight;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private View drawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private StateProcessProxy processor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        processor = new StateProcessProxyImpl(this);
        mDrawerToggle  = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                toolbar.setTitle("onDrawerClosed");
                processor.onDismiss(null);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                toolbar.setTitle("onDrawerOpened");
                if (processor.isLoadFirstTime()) {
                    processor.load();
                }
            }

        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        drawerList = findViewById(R.id.navdrawer);
        init(drawerList);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //toolbar.setNavigationIcon(R.drawable.ic_launcher);
        //toolbar.setTitle("toolbar测试");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void init(View v) {
        final ListView list = (ListView) v.findViewById(android.R.id.list);
        ImageView btn_nav_back = (ImageView) v.findViewById(R.id.btn_nav_back);
        ImageView btn_nav_froward = (ImageView) v.findViewById(R.id.btn_nav_forward);
        ViewHolder holder = new ViewHolder();
        holder.tv_title =  (TextView) v.findViewById(android.R.id.title);
        holder.btn_back = btn_nav_back;
        holder.btn_forward  = btn_nav_froward;
        holder.listView = list;
        holder.progress = v.findViewById(android.R.id.progress);
        holder.empty = v.findViewById(android.R.id.empty);
        holder.summary = v.findViewById(android.R.id.summary);
        holder.tv_summary_msg  =(TextView) holder.summary.findViewById(R.id.tv_summary_info);
        holder.btn_summary_reselect = holder.summary.findViewById(R.id.btn_summary_reselect);
        holder.btn_summary_ok = holder.summary.findViewById(R.id.btn_summary_ok);
        holder.tv_empty_msg = (TextView) holder.empty.findViewById(R.id.tv_empty);
        holder.btn_empty_refresh = (Button) holder.empty.findViewById(R.id.btn_empty);
        v.setTag(holder);
        btn_nav_back.setImageDrawable(DrawableUtil.tintDrawable(getResources().getDrawable(R.drawable.left_round), getResources().getColorStateList(R.color.seletor_simple_tintstatecolor)));
        btn_nav_froward.setImageDrawable(DrawableUtil.tintDrawable(getResources().getDrawable(R.drawable.right_round), getResources().getColorStateList(R.color.seletor_simple_tintstatecolor)));
        btn_nav_back.setEnabled(false);
        btn_nav_froward.setEnabled(false);
        processor.setView(v);
        QueryMap paramsMap = new QueryMap();
        paramsMap.put("method", "queryNew");
        new StateImpl_1_RoomName(processor);
        processor.rootState().setQueryMap(paramsMap);
        btn_nav_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processor.back();
                if (processor.getViewHolder().summary != null && processor.getViewHolder().summary.getVisibility() != View.GONE) {
                    processor.getViewHolder().summary.setVisibility(View.GONE);
                }
            }
        });
        btn_nav_froward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processor.forward();
                if (processor.getViewHolder().summary != null && processor.getViewHolder().summary.getVisibility() != View.GONE) {
                    processor.getViewHolder().summary.setVisibility(View.GONE);
                }
            }
        });
        holder.btn_empty_refresh.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                processor.refresh();
            }
        });
    }

    NaviView dialogNavi = null;
    public void openDialog(View view) {
        if (dialogNavi == null) {
            dialogNavi = new NaviView(this);
            dialogNavi.setLayoutParams(
                    new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));
            dialogNavi.setBackground(ContextCompat.getDrawable(this, R.drawable.popup_background));

        }
        if (dialogNavi.getParent() != null) {
            ((ViewGroup)dialogNavi.getParent()).removeView(dialogNavi);
        }
        final Dialog d = DialogUtil.getMenuDialog(this, dialogNavi, Gravity.CENTER);
        d.setCanceledOnTouchOutside(true);
        dialogNavi.setSubmitAction(new StateProcessProxy.Callback() {
            @Override
            public void onResult(Map<String, String> userChoosedDict) {
                StringBuilder sb = new StringBuilder();
                for (String s : userChoosedDict.keySet()) {
                    sb.append(s + " = " + userChoosedDict.get(s));
                    d.dismiss();
                }
                Toast.makeText(MainActivity.this, sb.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        d.show();
        dialogNavi.load();
    }

    public void openMenuSheet(View view) {
        if (dialogNavi == null) {
            dialogNavi = new NaviView(this);
            dialogNavi.setLayoutParams(
                    new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));
            dialogNavi.setBackground(ContextCompat.getDrawable(this, R.drawable.popup_background));
        }
        if (dialogNavi.getParent() != null) {
            ((ViewGroup)dialogNavi.getParent()).removeView(dialogNavi);
        }
        int[] screenWH = new int[2];
        getScreenWidthAndHeight(this, screenWH);
        final Dialog d = DialogUtil.getMenuDialog(this, dialogNavi, Gravity.BOTTOM, screenWH[0], (int) (screenWH[1]*.7f));
        d.setCanceledOnTouchOutside(true);
        dialogNavi.setSubmitAction(new StateProcessProxy.Callback() {
            @Override
            public void onResult(Map<String, String> userChoosedDict) {
                StringBuilder sb = new StringBuilder();
                for (String s : userChoosedDict.keySet()) {
                    sb.append(s).append(" = ").append(userChoosedDict.get(s));
                    d.dismiss();
                }
                Toast.makeText(MainActivity.this, sb.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        d.show();
        dialogNavi.load();
    }

    public void selectFromDrawer(View view) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
    }
}
