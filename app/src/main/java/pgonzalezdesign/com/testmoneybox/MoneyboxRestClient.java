package pgonzalezdesign.com.testmoneybox;

/**
 * Created by cex on 09/07/2018.
 */

import android.content.Context;
import com.loopj.android.http.*;
import cz.msebera.android.httpclient.client.CookieStore;
import cz.msebera.android.httpclient.entity.StringEntity;

public class MoneyboxRestClient {
    private static final String BASE_URL = "https://api-test00.moneyboxapp.com";
    private static final String APP_ID = "3a97b932a9d449c981b595";
    private static final String CONTENT_TYPE = "application/json";
    private static final String APP_VERSION = "4.11.0";
    private static final String API_VERSION = "3.0.0";
    private static final int DEFAULT_TIMEOUT = 20 * 1000;

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void setTimeOut(int time){
        client.setTimeout(time);
    }

    public static void setPersistanceCookies(CookieStore store){
        client.setCookieStore(store);
    }

    public static void addHeader(String header, String value){
        client.addHeader(header, value);
    }

    public static void setBasicAuth(String user, String password){
        client.setBasicAuth(user, password);
    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.setTimeout(DEFAULT_TIMEOUT);
        client.addHeader("AppId", APP_ID);
        client.addHeader("Content-Type", CONTENT_TYPE);
        client.addHeader("appVersion", APP_VERSION);
        client.addHeader("apiVersion", API_VERSION);
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.setTimeout(DEFAULT_TIMEOUT);
        client.addHeader("AppId", APP_ID);
        client.addHeader("Content-Type", CONTENT_TYPE);
        client.addHeader("appVersion", APP_VERSION);
        client.addHeader("apiVersion", API_VERSION);
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(Context context, String url, StringEntity entity, String type, AsyncHttpResponseHandler responseHandler) {
        client.setTimeout(DEFAULT_TIMEOUT);
        client.addHeader("AppId", APP_ID);
        client.addHeader("Content-Type", CONTENT_TYPE);
        client.addHeader("appVersion", APP_VERSION);
        client.addHeader("apiVersion", API_VERSION);
        client.post(context, getAbsoluteUrl(url), entity, type, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
