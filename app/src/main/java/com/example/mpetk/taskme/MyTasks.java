package com.example.mpetk.taskme;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tmilicic on 2/23/16.
 */
public class MyTasks extends AppCompatActivity {

    ArrayList<String> podaci;
    public ListView listview;
    public ArrayAdapter adapter;
    String user;

    Runnable run = new Runnable() {
        public void run() {
            //reload content
            podaci.clear();
            adapter.notifyDataSetChanged();
            listview.invalidateViews();
            listview.refreshDrawableState();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zadaci_zaposlenika);
        podaci = new ArrayList<>();

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        user = sharedPreferences.getString(Config.IME_SHARED_PREF,"Not Available");

    }

    @Override
    protected void onResume() {
        super.onResume();
        showList();
        adapter=new ArrayAdapter<String>(this, R.layout.activity_listview, podaci);
        listview = (ListView) findViewById(R.id.lista_poslova);
        registerForContextMenu(listview);
        listview.setAdapter(adapter);
        runOnUiThread(run);
    }

    private void showList () {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Config.LOGIN_WAMP_URL+"taskmeMytasks.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.print("Response "+response);
                        List<String> tasks = Arrays.asList(response.split("\\|t"));
                        podaci.addAll(tasks);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("KORISNICKO_IME", user);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        System.out.println("request " + stringRequest);
        requestQueue.add(stringRequest);
    }

}
