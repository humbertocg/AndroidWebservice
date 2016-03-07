package com.medicaltab.humberto.androidwebservice;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.medicaltab.humberto.androidwebservice.webservice.WebService;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edUsuario, edPass;
    Button btnConsulta;
    TextView txtResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        edUsuario= (EditText) findViewById(R.id.edUsuario);
        edPass= (EditText) findViewById(R.id.edPass);
        btnConsulta= (Button) findViewById(R.id.btnConsulta);
        btnConsulta.setOnClickListener(this);
        txtResultado= (TextView) findViewById(R.id.txtResultado);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnConsulta:
                int i=0;
                String Usuario=edUsuario.getText().toString();
                String Pass= edPass.getText().toString();
                //Consumir el WebService requiere de operaciones de red, esto se debe hacer
                // en una tarea asincrona para una vez que termine, el resultado pueda ser
                // mostrado en threat principal
                new LoginAsync().execute(Usuario,Pass);
                break;
        }
    }

    public String Login(String Usuario, String Pass) {
        int i = 0;
        String Resultado="Resultado:\n";
        try {
            WebService webService= new WebService();
            //Configurar la conexión al WebService
            webService.WebService_LogIn("get_Login",Usuario, Pass);
            //Consumir WebService
            webService
                    .WebService_EjecutarServicio("www.WebServiceEjemplo.com.mx#get_Login");
            //Obtener la cadena JSON de respuesta del WebService
            for (i = 0; i < webService.jsonMainNode.length(); i++) {
                webService.jsonChildNode = webService.jsonMainNode
                        .getJSONObject(i);
                Resultado+="id: "+webService.jsonChildNode.optString("id")+
                        "  Nombre: "+webService.jsonChildNode.optString("Nombre")+
                        "  Apellidos: "+webService.jsonChildNode.optString("Apellidos")+
                        "  Edad: "+webService.jsonChildNode.optString("Edad")+
                        "  TipoUsuario: "+webService.jsonChildNode.optString("TipoUsuario")+
                        "  User: "+webService.jsonChildNode.optString("User")+
                        "  Pass: "+webService.jsonChildNode.optString("Pass");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Resultado+="Error de Login ";
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            Resultado+="Error de Login ";
        } catch (JSONException e) {
            e.printStackTrace();
            Resultado+="Error de Login ";
        }
        return Resultado;
    }

    private class LoginAsync extends
            AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            txtResultado.setText("Resultado:\nEspere un momento...");
        }

        @Override
        protected String doInBackground(String... Parametros) {

            String resultado =Login(Parametros[0], Parametros[1]);
            return resultado;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            // TODO Auto-generated method stub

        }

        @Override
        protected void onPostExecute(String Resultado) {
            txtResultado.setText(Resultado);
        }
    }


}
