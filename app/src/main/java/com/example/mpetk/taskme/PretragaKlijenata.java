package com.example.mpetk.taskme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import android.widget.ArrayAdapter;
import android.widget.Button;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class PretragaKlijenata  extends AppCompatActivity{

    public ArrayList<String> klijent;
    public ListView listview;
    public static final String id_extra = "com.example.mpetk.taskme._ID";
    public EditText inputSearch;
    public ArrayAdapter adapter1;

    Runnable run = new Runnable() {
        public void run() {
            //reload content
            list.clear();
            adapter1.notifyDataSetChanged();
            listview.invalidateViews();
            listview.refreshDrawableState();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pretraga_klijenata);
        final Button dodaj = (Button) findViewById(R.id.pretraga_add_klijent);
        dodaj.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentNewUser = new Intent(PretragaKlijenata.this, KreiranjeKlijenta.class);
                startActivity(intentNewUser);
            }
        });
        klijent = new ArrayList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showList();
        adapter1=new ArrayAdapter<String>(this, R.layout.activity_listview, klijent);
        listview = (ListView) findViewById(R.id.lista_klijenata);
        registerForContextMenu(listview); //za meni nesto
        listview.setAdapter(adapter1);
        runOnUiThread(run);

                /*-----------SEARCH liste--------------*/
        inputSearch = (EditText) findViewById(R.id.inputSearch_klijent);
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                PretragaKlijenata.this.adapter1.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

    }


    private void showList () {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Config.LOGIN_WAMP_URL+"taskmeKijentCitanje.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<String> klijenti = Arrays.asList(response.split(","));
                        klijent.addAll(klijenti);
                        System.out.print(Arrays.asList(klijent));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return null;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        System.out.println("request " + stringRequest);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.lista_klijenata) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_klijent, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index =info.position;
        String stariklijent = klijent.get(index);
        switch(item.getItemId()) {
            case R.id.show:
                Intent intent_show = new Intent(getApplicationContext(), PrikazKlijenata.class);
                intent_show.putExtra(id_extra,stariklijent);
                startActivity(intent_show);
                return true;

            case R.id.modify:
                Intent intent_izmjena_klijenta = new Intent(getApplicationContext(), IzmjenaKlijenata.class);
                intent_izmjena_klijenta.putExtra(id_extra,stariklijent);
                startActivity(intent_izmjena_klijenta);

                return true;
            case R.id.delete:
                Intent intent_delete_user = new Intent(getApplicationContext(), BrisanjeKlijenata.class);
                intent_delete_user.putExtra(id_extra,stariklijent);
                startActivity(intent_delete_user);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }
}