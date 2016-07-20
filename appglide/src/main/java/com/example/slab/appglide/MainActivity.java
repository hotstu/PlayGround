package com.example.slab.appglide;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class MainActivity extends AppCompatActivity {

    private static final int NOTIFICATION_ID = 233;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Glide.with(this)
                .load("http://img13.360buyimg.com/n0/jfs/t2287/44/1515846482/119958/e7670fbe/56b16a69N6d8f1de7.jpg")
                .placeholder(R.mipmap.ic_launcher)
                .skipMemoryCache(true)
                //.crossFade(500)
                .centerCrop()
                .into((ImageView) findViewById(R.id.image));

        Glide.with(this)
                .load("http://img13.360buyimg.com/n0/jfs/t2287/44/1515846482/119958/e7670fbe/56b16a69N6d8f1de7.jpg")
                .placeholder(R.mipmap.ic_launcher)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                //.crossFade(500)
                .animate(R.anim.slide_left_in)
                .fitCenter()
                .into((ImageView) findViewById(R.id.image2));

        //createNotification(this);
    }

    private void createNotification(Context context) {
        final RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.custom_notification);

        rv.setImageViewResource(R.id.remoteview_notification_icon, R.mipmap.ic_launcher);

        rv.setTextViewText(R.id.remoteview_notification_headline, "Headline");
        rv.setTextViewText(R.id.remoteview_notification_short_message, "Short Message");

        // build notification
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Content Title")
                .setContentText("Content Text")
                //.setContent(rv)
                .setPriority( NotificationCompat.PRIORITY_MIN);

        final Notification notification = mBuilder.build();

        // set big content view for newer androids
        if (android.os.Build.VERSION.SDK_INT >= 16) {
            notification.bigContentView = rv;
        }

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID, notification);

//        NotificationTarget notificationTarget =  new NotificationTarget(
//                context,
//                rv,
//                R.id.remoteview_notification_icon,
//                notification,
//                NOTIFICATION_ID);
//        Glide
//            .with( context.getApplicationContext() ) // safer!
//            .load( "http://img13.360buyimg.com/n0/jfs/t2287/44/1515846482/119958/e7670fbe/56b16a69N6d8f1de7.jpg" )
//            .asBitmap()
//            .into( notificationTarget );
    }
}
