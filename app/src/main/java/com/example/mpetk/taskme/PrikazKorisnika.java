package com.example.mpetk.taskme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mpetk on 15.2.2016..
 */
public class PrikazKorisnika  extends AppCompatActivity{

    String[] podaci =new String[33];
    int[] idtxt = new int[] { R.id.prikaz_korisnika_ime, R.id.prikaz_korisnika_prezime, R.id.prikaz_korisnika_OIB,
            R.id.prikaz_korisnika_broj_osobne,R.id.prikaz_korisnika_adresa,R.id.prikaz_korisnika_telefon,
            R.id.prikaz_korisnika_mail,R.id.prikaz_korisnika_username,
            R.id.prikaz_korisnika_pass, R.id.prikaz_korisnika_datum_zap};


    // public ArrayList list;
    public String stariUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prikaz_korisnika);
        stariUser = getIntent().getStringExtra(PretragaKorisnika.id_extra);

    }

    @Override
    protected void onResume() {
        super.onResume();
        defaultValues();
        //   populate();
    }

    public void defaultValues () {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://whackamile.byethost3.com/taskme/taskmePodaciKorisnik.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        System.out.println("response " );System.out.println(response);
                        podaci = response.split("\\|t",-1);

                        System.out.println("useri: ");   System.out.println(java.util.Arrays.toString(podaci));
                        System.out.println("podaci.length: ");   System.out.println(podaci.length);

                        for (int i = 0; i < podaci.length-1; i++) {//zadnje je spinner, ne edittext
                            TextView tmp= (TextView) findViewById(idtxt[i]);
                            tmp.setText(podaci[i]);
                            System.out.println("text iz view:"+tmp.getText());
                        }
                        TextView tmp= (TextView)findViewById(R.id.prikaz_korisnika_tip);
                        tmp.setText(podaci[podaci.length-1].trim());//"some value"; //the value you want the position for
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("error: "+error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(Config.KEY_KOR_IME, stariUser);
                return params;

            }


        };
        //  setContentView(R.layout.activity_pretraga_korisnika);
        //Adding the string request to the queue

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        System.out.println("request " + stringRequest);
        requestQueue.add(stringRequest);
    }



}