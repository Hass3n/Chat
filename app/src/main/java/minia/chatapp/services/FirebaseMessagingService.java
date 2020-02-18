package minia.chatapp.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

import minia.chatapp.R;
import minia.chatapp.views.EmployeeChatActivity;
import minia.chatapp.views.UserChatActivity;


public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    //---opening the application on recieving notification---
    private final String ADMIN_CHANNEL_ID ="admin_channel";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        try {

            if (remoteMessage.getData() != null) {
                Map<String, String> data = remoteMessage.getData();
                final String roomKey = data.get("roomKey");
                final String userId = data.get("userId");
                final String empId = data.get("empId");
                final String content = data.get("content");
                final String type = data.get("type");


                //--CLICK ACTION IS PROVIDED---
                Intent intent;
                if(type != null && type.equals("fromUserToEmployee")){
                    intent = new Intent(this, EmployeeChatActivity.class);
                    intent.putExtra("roomKey", roomKey);
                    intent.putExtra("userId", userId);
                    intent.putExtra("empId", empId);
                }else {
                    intent = new Intent(this, UserChatActivity.class);
                    intent.putExtra("roomKey", roomKey);
                    intent.putExtra("userId", userId);
                    intent.putExtra("empId", empId);
                }


                NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                int notificationID = new Random().nextInt(3000);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    setupChannels(notificationManager);
                }

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this , 0, intent,
                        PendingIntent.FLAG_ONE_SHOT);


                Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
                        .setSmallIcon(R.drawable.notify)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(content)
                        .setAutoCancel(true)
                        .setSound(notificationSoundUri)
                        .setContentIntent(pendingIntent);

                //Set notification color to match your app color template
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    notificationBuilder.setColor(getResources().getColor(R.color.colorPrimaryDark));
                }
                notificationManager.notify(notificationID, notificationBuilder.build());
            }
        } catch (Exception e) {
            Log.e("Exception is ", e.toString());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupChannels(NotificationManager notificationManager){
        CharSequence adminChannelName = "New notification";
        String adminChannelDescription = "Device to devie notification";

        NotificationChannel adminChannel;
        adminChannel = new NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_HIGH);
        adminChannel.setDescription(adminChannelDescription);
        adminChannel.enableLights(true);
        adminChannel.setLightColor(Color.RED);
        adminChannel.enableVibration(true);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(adminChannel);
        }
    }
}
