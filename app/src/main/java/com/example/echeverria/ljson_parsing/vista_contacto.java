package com.example.echeverria.ljson_parsing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class vista_contacto extends Activity {

    private final String KEY_ID_A = "id_A";
    private final String KEY_NOMBRE = "nombre";
    private final String KEY_APELLIDOP = "apellido_p";
    private final String KEY_APELLIDOM = "apellido_m";
    private final String KEY_EMAIL = "email";
    private final String KEY_TELEFONO = "telefono";
    private final String KEY_EDAD = "edad";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_contacto);

        Intent in = getIntent();
        // Get JSON values from previous intent
        String id = in.getStringExtra(KEY_ID_A);
        String nombre = in.getStringExtra(KEY_NOMBRE);
        String apep = in.getStringExtra(KEY_APELLIDOP);
        String apem = in.getStringExtra(KEY_APELLIDOM);
        String email = in.getStringExtra(KEY_EMAIL);
        String tel = in.getStringExtra(KEY_TELEFONO);
        String edad = in.getStringExtra(KEY_EDAD);

        // Displaying all values on the screen
        TextView lblid = (TextView) findViewById(R.id.id);
        TextView lblnombre = (TextView) findViewById(R.id.nombre);
        TextView lblapep = (TextView) findViewById(R.id.apllidop);
        TextView lblapem = (TextView) findViewById(R.id.apellidom);
        TextView lblemail = (TextView) findViewById(R.id.email);
        TextView lbltel = (TextView) findViewById(R.id.tel);
        TextView lbledad = (TextView) findViewById(R.id.edad);

        lblid.setText(Html.fromHtml("<b>Id:</b> " + id));
        lblnombre.setText(Html.fromHtml("<b>Nombre:</b> " + nombre));
        lblapep.setText(Html.fromHtml("<b>A Paterno:</b> " + apep));
        lblapem.setText(Html.fromHtml("<b>A Materno:</b> " + apem));
        lblemail.setText(Html.fromHtml("<b>Email:</b> " + email));
        lbltel.setText(Html.fromHtml("<b>Telefono:</b> " + tel));
        lbledad.setText(Html.fromHtml("<b>Edad:</b> " + edad));
    }
}
