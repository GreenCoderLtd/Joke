package greencoder.com.joke;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class DelayedMessageService extends IntentService {

    public static final String EXTRA_MESSAGE = "message";
    public static final String LOG_TAG = DelayedMessageService.class.getSimpleName();



    public DelayedMessageService() {
        super("DelayedMessageService");
    }

    @Override
    public void onCreate() {

        super.onCreate();

        Log.i(LOG_TAG, "Service Started");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        try {

            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String message = intent.getStringExtra(EXTRA_MESSAGE);

        showMessage(message);



    }

    public void showMessage(String message) {

        Intent intent=new Intent(this,MainActivity.class);

        TaskStackBuilder stackBuilder= TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);

        //PendingIntent pendingIntent=PendingIntent.getActivity(this,0, intent,PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification= new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_SOUND)
                .build();

        NotificationManager manager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        manager.notify(0, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "Service Destroyed");
    }
}
