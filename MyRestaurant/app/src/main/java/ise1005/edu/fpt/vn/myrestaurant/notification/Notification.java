package ise1005.edu.fpt.vn.myrestaurant.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

import ise1005.edu.fpt.vn.myrestaurant.R;
import ise1005.edu.fpt.vn.myrestaurant.config.Constants;
import ise1005.edu.fpt.vn.myrestaurant.util.LoginActivity;

/**
 * Created by DungNA on 10/22/17.
 */

public class Notification extends Service implements SubscriptionEventListener {
    private Pusher pusher;
    private Channel channel;
    private String eventName;

    public Notification() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle extras = intent.getExtras();
        PusherOptions options = new PusherOptions();
        options.setCluster("ap1");
        pusher = new Pusher(Constants.PUSHER_KEY, options);
        channel = pusher.subscribe(Constants.PUSHER_CHANNEL);
        channel.bind(extras.getString("eventName"), this);
        Log.d("NotificationService","Event name: "+extras.getString("eventName"));
        connect();
//        return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void connect() {
        pusher.connect();
        Log.d("NotificationService", "Connected to pusher");
    }

    @Override
    public void onEvent(String channelName, String eventName, final String data) {
        Log.d("NotificationService", "Received event");
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());
        mBuilder.setSmallIcon(R.mipmap.ic_shop);
        mBuilder.setContentTitle("Notification Alert, Click Me!");
        mBuilder.setContentText("Hi, This is Android Notification Detail!");
        mNotificationManager.notify(100, mBuilder.build());
    }
}
