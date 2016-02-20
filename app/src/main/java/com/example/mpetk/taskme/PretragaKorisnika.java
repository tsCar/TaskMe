package com.example.mpetk.taskme;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
//import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.Volley;

/**
 * Created by mpetk on 15.2.2016..
 */
public class PretragaKorisnika  extends AppCompatActivity{//} implements View.OnClickListener{

    public ArrayList<String> list;
    public ListView listview;
    public static final String id_extra = "com.example.mpetk.taskme._ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pretraga_korisnika);
        Button dodaj = (Button) findViewById(R.id.pretraga_add_korisnik);
        dodaj.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("idem");
                Intent intentNewUser = new Intent(PretragaKorisnika.this, Login.class);
                startActivity(intentNewUser);
            }
        });
        System.out.println("dodaj  " + dodaj + "   is clickable?" +dodaj.isClickable());
        list = new ArrayList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showList();
       // String[] lista={"Android","IPhone","WindowsMobile","Blackberry","WebOS","Ubuntu","Windows7","Max OS X"};
        //String[] lista = getResources().getStringArray(R.array.lista_proba);
        ArrayAdapter adapter1=new ArrayAdapter<String>(this, R.layout.activity_listview, list);
        listview = (ListView) findViewById(R.id.lista_usera);
        registerForContextMenu(listview); //za meni nesto
        listview.setAdapter(adapter1);
        //setContentView(R.layout.activity_pretraga_korisnika);
    }


    private void showList () {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://192.168.178.50/taskmeBazaCitanjeKorisnika.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server

                        //System.out.print(response);
                        List<String> useri = Arrays.asList(response.split(","));
                        list.addAll(useri);
                      //  System.out.print("useri: " + lista[0]);
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
                return null;
            }


        };
        setContentView(R.layout.activity_pretraga_korisnika);
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
                // add stuff here

                Intent intent_show = new Intent(getApplicationContext(), PrikazKorisnika.class);
                startActivity(intent_show);
                return true;
            case R.id.modify:
                // edit stuff here
                Intent intent_izmjena_korisnika = new Intent(getApplicationContext(), IzmjenaKorisnika.class);
                intent_izmjena_korisnika.putExtra(id_extra,user);
                startActivity(intent_izmjena_korisnika);

                return true;
            case R.id.delete:
                // remove stuff here
                Intent intent_delete_user = new Intent(getApplicationContext(), BrisanjeKorisnika.class);
                startActivity(intent_delete_user);

                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }



}