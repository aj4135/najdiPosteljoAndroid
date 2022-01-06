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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    private TextView ponudniki;
    private String url = "link do ponudnikov";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        ponudniki = (TextView) findViewById(R.id.ponudniki);
    }

    public void prikaziPonudnike(View view) {
        if (view != null) {
            JsonArrayRequest request = new JsonArrayRequest(url, jsonArraylistener, errorListener);
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
                    String email = object.getString("Email");
                    String telefon = object.getString("Telefon");
                    String naslov = object.getString("Naslov");
                    String postnast = object.getString("PostnaStevilka");

                    data.add(naziv +" "+ email +" "+ telefon +" "+ naslov +" "+ postnast);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }
            }
            ponudniki.setText("");

            for (String row : data) {
                String currentText = ponudniki.getText().toString();
                ponudniki.setText(currentText + "\n\n" + row);
            }
        }
    };

    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("REST error", error.getMessage());
        }
    };
}