package com.example.mpetk.taskme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

public class PretragaKorisnika  extends AppCompatActivity{

    public ArrayList<String> list;
    public ListView listview;
    public static final String id_extra = "com.example.mpetk.taskme._ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pretraga_korisnika);
        final Button dodaj = (Button) findViewById(R.id.pretraga_add_korisnik);
        dodaj.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("idem");
                Intent intentNewUser = new Intent(PretragaKorisnika.this, KreiranjeKorisnika.class);
                startActivity(intentNewUser);
            }
        });
        list = new ArrayList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showList();
        ArrayAdapter adapter1=new ArrayAdapter<String>(this, R.layout.activity_listview, list);
        listview = (ListView) findViewById(R.id.lista_usera);
        registerForContextMenu(listview); //za meni nesto
        listview.setAdapter(adapter1);
    }


    private void showList () {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                "http://whackamile.byethost3.com/taskme/taskmeBazaCitanjeKorisnika.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        List<String> useri = Arrays.asList(response.split(","));
                        list.addAll(useri);
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
        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        // RequestQueue requestQueue = new RequestQueue(new NoCache(), new BasicNetwork(new HurlStack()));
        System.out.println("request " + stringRequest);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.lista_usera) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_list, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index =info.position;
        String user = list.get(index);
        switch(item.getItemId()) {
            case R.id.show:
                Intent intent_show = new Intent(getApplicationContext(), PrikazKorisnika.class);
                intent_show.putExtra(id_extra,user);
                startActivity(intent_show);
                return true;

            case R.id.modify:
                Intent intent_izmjena_korisnika = new Intent(getApplicationContext(), IzmjenaKorisnika.class);
                intent_izmjena_korisnika.putExtra(id_extra,user);
                startActivity(intent_izmjena_korisnika);

                return true;
            case R.id.delete:
                Intent intent_delete_user = new Intent(getApplicationContext(), BrisanjeKorisnika.class);
                intent_delete_user.putExtra(id_extra,user);
                startActivity(intent_delete_user);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }
}