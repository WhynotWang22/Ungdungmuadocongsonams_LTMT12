package com.whynotquang.ungdungmuadocongsonam_ltmt12.model;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.MainActivity;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;

public class PushNotificationService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        Log.d("bbb", "From: " + message.getFrom());
        // Check if message contains a data payload.
        if (message.getData().size() > 0) {
            Log.d("bbb", "Message data payload: " + message.getData());
        }

        // Check if message contains a notification payload.
        if (message.getNotification() != null) {
            Log.d("bbb", "Message Notification Title: " + message.getNotification().getTitle());
            Log.d("bbb", "Message Notification Body: " + message.getNotification().getBody());
            Log.d("bbb", "Message Notification img: " + message.getNotification().getImageUrl());
            Log.d("bbb", "Message Notification id: " + message.getNotification().getChannelId());


        }
        sendNotification(message.getNotification().getTitle(),
                message.getNotification().getBody(),
                String.valueOf(message.getNotification().getImageUrl())
        );
    }

    private void sendNotification(String messageTitle, String messageBody,String messageImage) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        String channelId = "My channel ID";
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.iconhome)
                        .setContentTitle(messageTitle)
                        .setContentText(messageBody)
//                        .setLargeIcon()
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String imageUrl = messageImage;
        Glide.with(getApplicationContext())
                .asBitmap()
                .load(imageUrl)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        //largeIcon
                        notificationBuilder.setLargeIcon(resource);
                        //Big Picture
                        notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(resource));
                        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });


        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
