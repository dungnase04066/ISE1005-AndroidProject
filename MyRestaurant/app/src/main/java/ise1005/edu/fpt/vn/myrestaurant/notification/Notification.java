package ise1005.edu.fpt.vn.myrestaurant.notification;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v7.app.NotificationCompat;

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

public class Notification {
    private Pusher pusher;
    private Channel channel;

    public Notification() {
        PusherOptions options = new PusherOptions();
        options.setCluster("ap1");
        pusher = new Pusher(Constants.PUSHER_KEY, options);
        channel = pusher.subscribe(Constants.PUSHER_CHANNEL);
    }

    public void setEvent(String eventName) {
        channel.bind(eventName, new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {
//                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
//                mBuilder.setSmallIcon(R.mipmap.ic_shop);
//                mBuilder.setContentTitle("Notification Alert, Click Me!");
//                mBuilder.setContentText("Hi, This is Android Notification Detail!");
//                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                mNotificationManager.notify(100, mBuilder.build());
            }
        });
    }

    public void connect() {
        pusher.connect();
    }
}
