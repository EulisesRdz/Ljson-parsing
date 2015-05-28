package com.example.echeverria.ljson_parsing;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends ListActivity {

    private ProgressDialog pDialog;
    //URL de donde se obtendran los datos del JSON
    private static String url = "http://eulisesrdz.260mb.net/android/aabbcc.json";

    //Nombres de los nodos de JSON
    private final String KEY_CONTACTO = "contacts";
    private final String KEY_ID_A = "id_A";
    private final String KEY_NOMBRE = "nombre";
    private final String KEY_APELLIDOP = "apellido_p";
    private final String KEY_APELLIDOM = "apellido_m";
    private final String KEY_EMAIL = "email";
    private final String KEY_TELEFONO = "telefono";
    private final String KEY_EDAD = "edad";

    //se hace un arreglo y se inicializa en nulo.
    JSONArray contacts = null;
    // Hashmap for ListView
    ArrayList<HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactList = new ArrayList<HashMap<String, String>>();
        ListView lv = getListView();
        //Listview - cuando se hace click en una opcion para entrar a su contenido.
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                //Obtiene los valores seleccionados del ListItem
                String id_A = ((TextView) view.findViewById(R.id.id)).getText().toString();
                String nombre = ((TextView) view.findViewById(R.id.nombre)).getText().toString();
                String apep = ((TextView) view.findViewById(R.id.apllidop)).getText().toString();
                String apem = ((TextView) view.findViewById(R.id.apellidom)).getText().toString();
                String email = ((TextView) view.findViewById(R.id.email)).getText().toString();
                String tel = ((TextView) view.findViewById(R.id.tel)).getText().toString();
                String edad = ((TextView) view.findViewById(R.id.edad)).getText().toString();

                //Se abrira otra actividad.
                Intent in = new Intent(getApplicationContext(),vista_contacto.class);
                //Se pasaran los siguientes datos a la actividad nueva.
                in.putExtra(KEY_ID_A, id_A);
                in.putExtra(KEY_NOMBRE, nombre);
                in.putExtra(KEY_APELLIDOP, apep);
                in.putExtra(KEY_APELLIDOM, apem);
                in.putExtra(KEY_EMAIL, email);
                in.putExtra(KEY_TELEFONO, tel);
                in.putExtra(KEY_EDAD, edad);
                //Se inicia la actividad.
                startActivity(in);
            }
        });
        // Se manda llamar al metodo, para obtener los datos.
        new GetContacts().execute();
    }
    /**
     * Async task class to get json by making HTTP call
     * */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //muestra un cuadro de dialogo con proceso.
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            //Creando un servio handler e instanciando la clase.
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
            Log.d("Response: ", "> " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    //Obteniendo los nodos del arreglo JSON
                    contacts = jsonObj.getJSONArray(KEY_CONTACTO);
                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        String id = c.getString(KEY_ID_A);
                        String name = c.getString(KEY_NOMBRE);
                        String apep = c.getString(KEY_APELLIDOP);
                        String apem = c.getString(KEY_APELLIDOM);
                        String email = c.getString(KEY_EMAIL);
                        String tel = c.getString(KEY_TELEFONO);
                        String edad = c.getString(KEY_EDAD);
                        // tmp hashmap for single contact
                        HashMap<String, String> contact = new HashMap<String, String>();
                        // adding each child node to HashMap key => value
                        contact.put(KEY_ID_A, id);
                        contact.put(KEY_NOMBRE, name);
                        contact.put(KEY_APELLIDOP, apep);
                        contact.put(KEY_APELLIDOM, apem);
                        contact.put(KEY_EMAIL, email);
                        contact.put(KEY_TELEFONO, tel);
                        contact.put(KEY_EDAD, edad);

                        //añadiendo el contacto a lista de contactos.
                        contactList.add(contact);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                //Mensaje para indicar si se presenta un error al obtener la URL.
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            //actualizando el parser JSON de los datos del listview
             ListAdapter adapter = new SimpleAdapter(MainActivity.this, contactList,
                    R.layout.lista_contactos, new String[] {KEY_ID_A, KEY_NOMBRE, KEY_APELLIDOP, KEY_APELLIDOM, KEY_EMAIL, KEY_TELEFONO, KEY_EDAD},
                    new int[] { R.id.id,R.id.nombre,R.id.apllidop,R.id.apellidom, R.id.email, R.id.tel, R.id.edad });
            setListAdapter(adapter);
        }
    }
}