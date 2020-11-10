package org.zeba.quick.frame.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class NotificationUtil {

    private static int MyID=1000;

    public static NotificationCompat.Builder createBuilder(Context context, NotificationManager manager, String channelId, String channelName){
        if(context==null){
            return null;
        }
        if (Build.VERSION.SDK_INT>=26){
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
            return new NotificationCompat.Builder(context,channelId);
        }else{
            return new NotificationCompat.Builder(context);
        }
    }

    public static void sendNotify(Context context, String title, String content, int icon){
        sendNotify(context,MyID++,title,content,icon);
    }

    public static void sendNotify(Context context, String title, String content, int icon, PendingIntent pendingIntent){
        sendNotify(context,MyID++,title,content,icon,pendingIntent);
    }

    public static void sendNotify(Context context, int id, String title, String content, int icon){
        sendNotify(context,id,title,content,icon,null);
    }

    public static void sendNotify(Context context, int id, String title, String content, int icon, PendingIntent pendingIntent){
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder= createBuilder(context,manager,"defaultId","defaultName")
                .setAutoCancel(true)
                .setContentText(content)
                .setContentTitle(title)
                .setSmallIcon(icon);
        if(pendingIntent!=null){
            builder.setContentIntent(pendingIntent);
        }
        manager.notify(id,builder.build());
    }
}

