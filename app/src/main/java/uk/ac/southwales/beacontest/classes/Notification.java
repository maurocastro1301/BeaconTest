package uk.ac.southwales.beacontest.classes;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;

import uk.ac.southwales.beacontest.BaseActivity;
import uk.ac.southwales.beacontest.R;

/**
 * Created by nwillia2 on 19/01/2015.
 */
public class Notification {
    private BaseActivity activity;
    private String title;
    private String text;
    private int notificationId;
    private boolean isSticky;

    public Notification(BaseActivity activity, String title, String text) {
        this.activity = activity;
        this.title = title;
        this.notificationId = 1;
    }

    public Notification(BaseActivity activity, String title, String text, boolean isSticky) {
        this(activity, title, text);
        this.isSticky = isSticky;
    }

    public Notification(BaseActivity activity, String title, String text, boolean isSticky, int notificationId) {
        this(activity, title, text, isSticky);
        this.notificationId = notificationId;
    }

    public void makeNotification() {
        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                NotificationManager notificationManager = (NotificationManager)
                        activity.getSystemService(Context.NOTIFICATION_SERVICE);

                Intent intent = new Intent(activity, activity.getClass());

                PendingIntent pendingIntent = PendingIntent.getActivity(activity,
                        notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                android.app.Notification.Builder builder = new android.app.Notification.Builder(activity)
                        .setContentTitle(title)
                        .setContentText(text)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_launcher));

                android.app.Notification n;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    n = builder.build();
                } else {
                    n = builder.getNotification();
                }

                if (isSticky)
                    n.flags |= android.app.Notification.FLAG_NO_CLEAR | android.app.Notification.FLAG_ONGOING_EVENT;

                notificationManager.notify(notificationId, n);
            }
        });
    }
}
