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
import android.widget.BaseAdapter;
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
    public ArrayAdapter adapterTask;

    Runnable run = new Runnable() {
        public void run() {
            //reload content
            list.clear();
            adapterTask.notifyDataSetChanged();
            listview.invalidateViews();
            listview.refreshDrawableState();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pretraga_zadataka);
        final Button dodajZadatak = (Button) findViewById(R.id.pretraga_add_task);
        dodajZadatak.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentNewTask = new Intent(PretragaZadataka.this, KreiranjeZadatak.class);
                startActivity(intentNewTask);
            }
        });
        list = new ArrayList();




    }

    @Override
    protected void onResume() {
        super.onResume();
        showList();

        adapterTask=new ArrayAdapter<String>(this, R.layout.activity_listview, list);
        listview = (ListView) findViewById(R.id.lista_taskova);

        registerForContextMenu(listview); //za meni nesto
        listview.setAdapter(adapterTask);

        runOnUiThread(run);

                /*-----------SEARCH liste--------------*/
        inputSearch = (EditText) findViewById(R.id.inputSearch_tasks);
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                PretragaZadataka.this.adapterTask.getFilter().filter(cs);
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
                Config.LOGIN_WAMP_URL+"taskmeBazaCitanjeZadataka.php", //ToDo
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
            inflater.inflate(R.menu.menu_task, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index =info.position;
        String t = list.get(index);
        switch(item.getItemId()) {
            case R.id.prikazZadatka:
                Intent intent_show = new Intent(getApplicationContext(), PrikazZadatka.class);
                intent_show.putExtra(id_extra,t);
                startActivity(intent_show);
                return true;

            case R.id.modifyZadatka:
                Intent intent_izmjena_task = new Intent(getApplicationContext(), IzmjenaZadatka.class);
                intent_izmjena_task.putExtra(id_extra,t.trim());
                startActivity(intent_izmjena_task);
                return true;

            case R.id.dodjelaZadatka:
                Intent intent_dodjela_task = new Intent(getApplicationContext(), DodjelaZadatka.class);
                intent_dodjela_task.putExtra(id_extra,t.trim());
                startActivity(intent_dodjela_task);
                return true;

            case R.id.deleteZadatka:
                Intent intent_delete_task= new Intent(getApplicationContext(), BrisanjeZadatka.class);
                intent_delete_task.putExtra(id_extra,t.trim());
                startActivity(intent_delete_task);
                return true;


            default:
                return super.onContextItemSelected(item);
        }
    }
}