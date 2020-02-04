/*
package project.utils.notify;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.fcmpanel.app.R;
import com.fcmpanel.app.activities.MyDialog;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;


public class PushService extends FirebaseMessagingService {

    //send received action to server
    private void receivedPush(String pushId) {
        String baseUrl = getResources().getString(R.string.baseUrl) + "/delivered";
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(baseUrl);
        try {
            //add data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("pushId", pushId));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httpPost);

        } catch (ClientProtocolException e) {

        } catch (IOException e) {

        }
    }

    //open popup dialog (direct)
    private void openDialog(RemoteMessage remoteMessage) {
        final String msg = remoteMessage.getData().get("msg");
        final String img = remoteMessage.getData().get("img");
        final String btnText = remoteMessage.getData().get("btn");
        final String url = remoteMessage.getData().get("url");
        Intent intent = new Intent(getApplicationContext(), MyDialog.class);
        intent.putExtra("img", img);
        intent.putExtra("url", url);
        intent.putExtra("body", msg);
        intent.putExtra("btn", btnText);
        startActivity(intent);
    }

    //open any url (direct)
    private void popupDirect(RemoteMessage remoteMessage) {
        final String url = remoteMessage.getData().get("url");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    //handle Custom json data
    private void handleJson(RemoteMessage remoteMessage) {
        final String data = remoteMessage.getData().get("data");
        try {
            String json = "";
            json = URLDecoder.decode(data, "UTF-8");
            JSONObject jsonObject = new JSONObject(json);
            String myWebsite = jsonObject.optString("myWebsite");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e("myFCM", remoteMessage.getMessageId());
        PushAction.remoteMessage = remoteMessage;
        String pushId = remoteMessage.getData().get("pushId");
        int pushMode = Integer.parseInt(remoteMessage.getData().get("mode"));
        receivedPush(pushId);
        try {
            switch (pushMode) {
                case 9:
                    openDialog(remoteMessage);
                    break;

                case 10:
                    popupDirect(remoteMessage);
                    break;

                case 11:
                    handleJson(remoteMessage);
                    break;

                default:
                    String title = remoteMessage.getData().get("title");
                    final String msg = remoteMessage.getData().get("msg");
                    sendNotification(title, msg);
                    break;

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void sendNotification(String title, String body) {
        Intent intent = new Intent(this, PushAction.class);
        PendingIntent onClickPendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 0, intent, 0);
        String channelId = "1234";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(onClickPendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 */
/* ID of notification *//*
, notificationBuilder.build());


    }

}
*/
