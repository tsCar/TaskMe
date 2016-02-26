package com.example.mpetk.taskme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;

public class IzmjenaKorisnika  extends AppCompatActivity implements View.OnClickListener{

    String[] podaci =new String[33];
    int[] idtxt = new int[] { R.id.izmjena_korisnika_ime, R.id.izmjena_korisnika_prezime, R.id.izmjena_korisnika_OIB,
            R.id.izmjena_korisnika_broj_osobne,R.id.izmjena_korisnika_adresa,R.id.izmjena_korisnika_telefon,
            R.id.izmjena_korisnika_mail,R.id.izmjena_korisnika_username,
            R.id.izmjena_korisnika_pass, R.id.izmjena_korisnika_datum_zap};
    // public ArrayList list;
    public String stariUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grga);
        Button promijeni = (Button) findViewById(R.id.button_modify_user);
        promijeni.setOnClickListener(this);
        stariUser = getIntent().getStringExtra(PretragaKorisnika.id_extra);
    }

    @Override
    protected void onResume() {
        super.onResume();
        defaultValues();
    }

    private void defaultValues () {
        System.out.println("usao u default");
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Config.LOGIN_WAMP_URL+"taskmePodaciKorisnik.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        podaci = response.split("\\|t",-1);
                        for (int i = 0; i < podaci.length-1; i++) {//zadnje je spinner, ne edittext
                            EditText tmp = (EditText) findViewById(idtxt[i]);
                            tmp.setText(podaci[i]);
                        }
                        Spinner tmp= (Spinner)findViewById(R.id.spinner_tip_korisnika);
                        String myString = podaci[podaci.length-1].trim();//"some value"; //the value you want the position for
                        ArrayAdapter myAdap = (ArrayAdapter) tmp.getAdapter(); //cast to an ArrayAdapter
                        int spinnerPosition = myAdap.getPosition(myString);
                        tmp.setSelection(spinnerPosition);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("error: "+error);
                    }
                })
            {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(Config.KEY_KOR_IME, stariUser);
                return params;
            }
        };
       RequestQueue requestQueue = Volley.newRequestQueue(this);
       System.out.println("request " + stringRequest);
       requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                "http://whackamile.byethost3.com/taskme/taskmeModifikacijaKorisnika.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(IzmjenaKorisnika.this, response, Toast.LENGTH_LONG).show();
                        Intent intentPretraga = new Intent(getApplicationContext(), PretragaKorisnika.class);
                        startActivity(intentPretraga);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("error: "+error);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("STARO_IME", stariUser);
                params.put("IME", ((EditText)findViewById(idtxt[0])).getText().toString());
                params.put("PREZIME", ((EditText) findViewById(idtxt[1])).getText().toString());
                params.put("JMBG", ((EditText) findViewById(idtxt[2])).getText().toString());
                params.put("BR_LK", ((EditText) findViewById(idtxt[3])).getText().toString());
                params.put("ADRESA", ((EditText) findViewById(idtxt[4])).getText().toString());
                params.put("TELEFON", ((EditText) findViewById(idtxt[5])).getText().toString());
                params.put("EMAIL", ((EditText) findViewById(idtxt[6])).getText().toString());
                params.put("KORISNICKO_IME", ((EditText) findViewById(idtxt[7])).getText().toString());
                params.put("LOZINKA", ((EditText) findViewById(idtxt[8])).getText().toString());
                params.put("DATUM_ZAPOSLENJA", ((EditText) findViewById(idtxt[9])).getText().toString());
                params.put("TIP_KORISNIKA", ((Spinner)findViewById(R.id.spinner_tip_korisnika)).getSelectedItem().toString() );
                return params;
            }
        };
        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        System.out.println("request " + stringRequest);
        requestQueue.add(stringRequest);
    }
}

