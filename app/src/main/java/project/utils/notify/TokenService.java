/*
package project.utils.notify;

import android.util.Log;

import com.fcmpanel.app.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

import ir.mrmilad.utility.DevTools;

public class TokenService extends FirebaseInstanceIdService {

    private void updateToken(String token) {
        Log.d("fcm : ", "fcm token is refreshed");

        //Update device token on server . every device token updated by device id and app key
        synchronized (this) {
            try {
                String appKey = getResources().getString(R.string.appKey);
                String baseUrl = getResources().getString(R.string.baseUrl) + "/refreshtoken";
                String devId = DevTools.getDeviceID(getApplicationContext());
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(baseUrl);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                nameValuePairs.add(new BasicNameValuePair("devId", devId));
                nameValuePairs.add(new BasicNameValuePair("appKey", appKey));
                nameValuePairs.add(new BasicNameValuePair("token", token));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httpPost);
                String result = EntityUtils.toString(response.getEntity());
                Log.i("result : ", result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        String currentToken = FirebaseInstanceId.getInstance().getToken();
        if (currentToken != null && !currentToken.equals("")) {
            یupdateToken(currentToken);
        } else {
            onTokenRefresh();
        }
    }

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        if (refreshedToken != null && !refreshedToken.equals("")) {
            updateToken(refreshedToken);
        }
    }

}
*/
