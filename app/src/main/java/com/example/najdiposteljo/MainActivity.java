package com.example.najdiposteljo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    private TextView prenocisca;
    private String url = "link do prenočišč";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        prenocisca = (TextView) findViewById(R.id.prenocisca);
    }

    public void prikaziPrenocisca(View view) {
        if (view != null) {
            JsonArrayRequest request = new JsonArrayRequest(url, jsonArraylistener, errorListener) {
                @Override
                public Map<String,String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("ApiKey", "SecretKey");
                    return params;
                }
            };
            requestQueue.add(request);
        }
    }

    private Response.Listener<JSONArray> jsonArraylistener = new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            ArrayList<String> data = new ArrayList<>();
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject object = response.getJSONObject(i);
                    String naziv = object.getString("Naziv");
                    String cena = object.getString("CenaNaPrenocitev");
                    String naslov = object.getString("Naslov");
                    String postnast = object.getString("PostnaStevilka");

                    data.add(naziv +" "+ cena +" "+ naslov +" "+ postnast);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }
            }
            prenocisca.setText("");

            for (String row : data) {
                String currentText = prenocisca.getText().toString();
                prenocisca.setText(currentText + "\n\n" + row);
            }
        }
    };

    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("REST error", error.getMessage());
        }
    };

    public static final String EXTRA_MESSAGE = "com.example.najdiposteljo.MESSAGE";

    public void addPrenocisceActivity(View view) {
        Intent intent = new Intent(this, AddPrenocisceActivity.class);
        String message = "Dodaj prenočišče v ponudbo";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}