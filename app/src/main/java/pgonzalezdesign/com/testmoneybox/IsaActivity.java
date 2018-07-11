package pgonzalezdesign.com.testmoneybox;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by cex on 11/07/2018.
 */

public class IsaActivity extends AppCompatActivity{

    private static String AUTH_TOKEN = "";
    private static int INVESTOR_PRODUCT_ID = 0;

    private TextView accountState;
    private TextView moneyboxState;
    private Button addMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.isa_activity);

        AUTH_TOKEN = getSharedPreferences("JSON_RESPONSE", 0).getString("AuthToken", "");

        accountState = (TextView) findViewById(R.id.accountState);
        accountState.setText("Stocks & Shares ISA  ");
        moneyboxState = (TextView) findViewById(R.id.moneyboxState);
        moneyboxState.setText("Your Moneybox:");
        addMoney = (Button) findViewById(R.id.addMoney);

        RequestParams params = new RequestParams("InvestorProductType", "Isa");

        MoneyboxRestClient.addHeader("Authorization", "Bearer " + AUTH_TOKEN);

        MoneyboxRestClient.get("/investorproduct/thisweek", params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    JSONArray jsonProducts = response.getJSONArray("Products");
                    JSONObject userProducts = jsonProducts.getJSONObject(0);
                    accountState.setText("Stocks & Shares ISA  " + userProducts.getString("PlanValue"));
                    moneyboxState.setText("Your Moneybox: £" + userProducts.getInt("Moneybox"));
                    INVESTOR_PRODUCT_ID = userProducts.getInt("InvestorProductId");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("Callback", "onSuccess response");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                super.onFailure(statusCode, headers, e, response);
                Log.d("Callback", "onFailure responseString");
            }
        });

        addMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject jsonParams = new JSONObject();
                    jsonParams.put("Amount", 10);
                    jsonParams.put("InvestorProductId", INVESTOR_PRODUCT_ID);
                    StringEntity entity = new StringEntity(jsonParams.toString());

                    MoneyboxRestClient.addHeader("Authorization", "Bearer " + AUTH_TOKEN);

                    MoneyboxRestClient.post(IsaActivity.this, "/oneoffpayments", entity, "application/json", new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);

                            try {
                                moneyboxState.setText("Your Moneybox: £" + response.getInt("Moneybox"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Log.d("Callback", "onSuccess response");
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                            super.onSuccess(statusCode, headers, response);
                            Log.d("Callback", "onSuccess response");
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                            super.onFailure(statusCode, headers, e, response);
                            Log.d("Callback", "onFailure responseString");
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
