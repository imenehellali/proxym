package com.example.hella.proxym.Util;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // TODO(developer): Handle FCM messages here.
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {

            if ( true) {
                scheduleJob();
            } else {
                handleNow();
            }

        }
        if (remoteMessage.getNotification() != null) {
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    private void handleNow() {
        //ToDo implementation
    }

    private void scheduleJob() {
        //ToDo implementation
    }
}
