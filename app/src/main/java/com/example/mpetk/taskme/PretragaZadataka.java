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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.List;
import java.util.Map;

/**
 * Created by mpetk on 15.2.2016..
 */
public class PretragaZadataka extends AppCompatActivity{

    public ArrayList<String> list;
    public ListView listview;
    public static final String id_extra = "com.example.mpetk.taskme._ID";
    public EditText inputSearch;
    public ArrayAdapter adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pretraga_korisnika);
        final Button dodaj = (Button) findViewById(R.id.pretraga_add_task);
        dodaj.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentNewUser = new Intent(PretragaZadataka.this, KreiranjeZadatak.class);
                startActivity(intentNewUser);
            }
        });
        list = new ArrayList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showList();
        adapter1=new ArrayAdapter<String>(this, R.layout.activity_listview, list);
        listview = (ListView) findViewById(R.id.lista_taskova);
        registerForContextMenu(listview); //za meni nesto
        listview.setAdapter(adapter1);


                /*-----------SEARCH liste--------------*/
        inputSearch = (EditText) findViewById(R.id.inputSearch_tasks);
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                PretragaZadataka.this.adapter1.getFilter().filter(cs);
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
                "http://whackamile.byethost3.com/taskme/taskmeBazaCitanjeZadataka.php", //ToDo
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        List<String> taskovi = Arrays.asList(response.split(","));
                        list.addAll(taskovi);
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
        requestQueue.add(stringRequest);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.lista_taskova) {
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