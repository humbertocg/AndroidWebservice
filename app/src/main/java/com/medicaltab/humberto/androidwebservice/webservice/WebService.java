package com.medicaltab.humberto.androidwebservice.webservice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;

/**
 * Created by Humberto on 07/03/2016.
 */
public class WebService {

    //Namespace con el que se creo el WebService
    private final String NAMESPACE = "www.WebServiceEjemplo.com.mx";
    //Modificar esta linea por el ip del servidor y por la ruta de carpeta donde esta montado el WebService
    private String URL = "http://192.148.7.100/WebServiceSOAP_MySQL/WebService.php?wsdl";
    private final String QUERYRESULT="Query_result";
    public SoapObject request;
    public SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
            SoapEnvelope.VER11);
    public HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
    public JSONObject jsonResponse;
    public JSONArray jsonMainNode;
    public JSONObject jsonChildNode;

    public void WebService_LogIn(String FuncionPHP,String usuario, String pass)
    {
        //Configurar objeto SOAP para la petición al WebService
        //FuncionPHP es la función get_Login declarada en el WebService.
        SoapObject request = new SoapObject(NAMESPACE, FuncionPHP);
        //Configuracion de parametros de entrada
        //Usuario como primer parametro del tipo cadena y Pass como segundo parametro del tipo cadena
        PropertyInfo Usuario = new PropertyInfo();
        //Configurar nombre (como se declaro en la función get_Login del WebService)
        Usuario.setName("Usuario");
        //Valor del parametro
        Usuario.setValue(usuario);
        //Configurar tipo
        Usuario.setType(String.class);
        //Agregar el parametro a la petición
        request.addProperty(Usuario);
        //Configurar parametro Pass
        PropertyInfo Pass = new PropertyInfo();
        //Configurar nombre (como se declaro en la función get_Login del WebService)
        Pass.setName("Pass");
        //Valor del parametro
        Pass.setValue(pass);
        //Configurar tipo
        Pass.setType(String.class);
        //Agregar el parametro a la petición
        request.addProperty(Pass);
        envelope.dotNet = true;
        //Configurar la salidad como Objeto SOAP
        envelope.setOutputSoapObject(request);
        androidHttpTransport = new HttpTransportSE(URL);
    }

    public void WebService_EjecutarServicio(String FuncionSoapAction) throws IOException, XmlPullParserException, JSONException
    {
        androidHttpTransport.debug = true;
        androidHttpTransport.call(FuncionSoapAction, envelope);
        //Debug. Verificar la petición al WebService.
        String consulta= androidHttpTransport.requestDump;
        //Debug. Verificar la respuesta del WebService.
        String respuesta=androidHttpTransport.responseDump;
        jsonResponse = new JSONObject(envelope.getResponse().toString());
        jsonMainNode = jsonResponse.optJSONArray(QUERYRESULT);
    }

}