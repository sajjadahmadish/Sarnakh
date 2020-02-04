/*
package project.utils.notify;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.fcmpanel.app.R;
import com.fcmpanel.app.activities.MainActivity;
import com.google.firebase.messaging.RemoteMessage;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PushAction extends BroadcastReceiver {

    public static RemoteMessage remoteMessage;
    String baseUrl = "";

    private void openUrl(Context context) {
        final String url = remoteMessage.getData().get("url");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

    //close app
    private void closeApp(Context context) {
        System.exit(0);
    }

    //open app
    private void openApp(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    //join to telegram
    private void joinTelegram(Context context) {
        final String channel = remoteMessage.getData().get("channel");
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tg://resolve?domain=" + channel));
        context.startActivity(intent);
    }

    //open market url
    private void openMarket(Context context) {
        final String url = remoteMessage.getData().get("url");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

    //open dial pad
    private void openDialpad(Context context) {
        final String number = remoteMessage.getData().get("number");
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        context.startActivity(intent);
    }

    //open sms intent
    private void openSMS(Context context) {
        final String number = remoteMessage.getData().get("number");
        final String body = remoteMessage.getData().get("body");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("sms:" + number));
        intent.putExtra("sms_body", body);
        context.startActivity(intent);
    }

    //open email intent
    private void openEmail(Context context) {
        final String email = remoteMessage.getData().get("email");
        final String subject = remoteMessage.getData().get("subject");
        final String body = remoteMessage.getData().get("body");
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", email, null));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        context.startActivity(intent);
    }

    //send clicked event to server
    private class handleClicked extends AsyncTask<String, String, String> {
        protected String doInBackground(String... urls) {

            final String pushId = remoteMessage.getData().get("pushId");
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(baseUrl);

            try {
                //add data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("action", "clicked"));
                nameValuePairs.add(new BasicNameValuePair("pushId", pushId));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httpPost);

            } catch (ClientProtocolException e) {

            } catch (IOException e) {

            }
            return "";
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        baseUrl = context.getResources().getString(R.string.baseUrl) + "/clicked";
        new handleClicked().execute();
        int pushMode = Integer.parseInt(remoteMessage.getData().get("mode"));
        switch (pushMode) {
            case 1:
                closeApp(context);
                break;

            case 2:
                openApp(context);
                break;

            case 3:
                joinTelegram(context);
                break;

            case 4:
                openUrl(context);
                break;

            case 5:
                openMarket(context);
                break;

            case 6:
                openDialpad(context);
                break;

            case 7:
                openSMS(context);
                break;

            case 8:
                openEmail(context);
                break;
        }
    }
}
*/
