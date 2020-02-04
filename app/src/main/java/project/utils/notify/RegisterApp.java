/*
package project.utils.notify;

import android.app.IntentService;
import android.content.Intent;
import androidx.annotation.Nullable;
import android.text.TextUtils;

import com.fcmpanel.app.R;
import com.google.firebase.iid.FirebaseInstanceId;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ir.mrmilad.utility.DevTools;


public class RegisterApp extends IntentService {

    public RegisterApp() {
        super("RegisterApp");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        synchronized (intent) {
            //Register App To Server
            int registerState = Integer.parseInt(DevTools.getPreferences_String(getApplication(), "appPrefs", "reg", "0"));
            String appKey = getResources().getString(R.string.appKey);
            String baseUrl = getResources().getString(R.string.baseUrl) + "/register";
            String devModel = DevTools.getDeviceModel();
            String devBrand = DevTools.getDeviceBrand();
            String devId = DevTools.getDeviceID(getApplicationContext());
            String devOs = DevTools.getDeviceSdkVersion();
            String operator = DevTools.getNetworkOperator(getApplicationContext());
            String token = null;

            //looping while fcm token is filled
            while (TextUtils.isEmpty(token)) {
                token = FirebaseInstanceId.getInstance().getToken();
            }

            if (!token.equals("")) {
                try {
                    //check device is registered on server or not
                    if (registerState < 1) {
                        try {
                            HttpClient httpclient = new DefaultHttpClient();
                            HttpPost httpPost = new HttpPost(baseUrl);
                            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(7);
                            nameValuePairs.add(new BasicNameValuePair("devModel", devModel));
                            nameValuePairs.add(new BasicNameValuePair("devBrand", devBrand));
                            nameValuePairs.add(new BasicNameValuePair("devId", devId));
                            nameValuePairs.add(new BasicNameValuePair("devOs", devOs));
                            nameValuePairs.add(new BasicNameValuePair("appKey", appKey));
                            nameValuePairs.add(new BasicNameValuePair("operator", operator));
                            nameValuePairs.add(new BasicNameValuePair("token", token));
                            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                            HttpResponse response = httpclient.execute(httpPost);
                            String result = EntityUtils.toString(response.getEntity());
                            DevTools.setPreferences_String(getApplicationContext(), "appPrefs", "reg", result);
                        } catch (ClientProtocolException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }
    }
}
*/
