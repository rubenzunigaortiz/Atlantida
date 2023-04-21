package com.example.clima;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.clima.General.InterfaceClima;
import com.example.clima.General.InterfaceUbicacion;
import com.example.clima.General.SweetAlert;
import com.example.clima.General.Ubicacion;
import com.example.clima.models.SolicitudClima;

import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity implements InterfaceUbicacion, InterfaceClima {

    Ubicacion ubicacion;
    Location location;

    Boolean booleanPermisos = false;
    Boolean booleanSolicitud = false;

    final Integer IntPermisoUbicacion = 1;
    final String api_key = "2538e637fb80d1652c4f05a432121c08";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ubicacion = new Ubicacion(this, this);

        booleanPermisos = ubicacion.permisosUbicacion();


        if(booleanPermisos && ubicacion.verificarGPSHabilitado() == true){

            //Toast.makeText(this, "EXITO", Toast.LENGTH_LONG).show();

            SolicitarClima(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), "daily");

        }else{

            ubicacion.mostrarAlertaGPS();

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            booleanPermisos = true;

        }else{

            SweetAlert sweetAlert = new SweetAlert(this);
            sweetAlert.textoSweetAlert(getString(R.string.permisosUbicacion));
            sweetAlert.imagenSweetAlert(R.drawable.aviso_amarillo);
            sweetAlert.mostrarSweetAlert();
            sweetAlert = null;

        }

    }

    @Override
    public void solicitarPermisos() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }

    @Override
    public void obtenerUbicacion(Location location) {

        this.location = location;

        if(location != null){

            SolicitarClima(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), "daily");

        }

    }

    @Override
    public void respuestaEncender(Boolean respuesta) {

    }

    @Override
    public void calcularDistancia() {

    }

    @Override
    public void SolicitarClima(String latitud, String longitud, String exclude) {

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String respuesta = response;

                booleanSolicitud = true;

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                String strRespuesta;

                try {

                    strRespuesta = new String(error.networkResponse.data, StandardCharsets.UTF_8);

                }catch (Exception e){

                    strRespuesta = "{\"error\":\"invalid_grant\",\"error_description\":\"Sin conexion\"}";

                }

                booleanSolicitud = true;

            }
        };

        if(booleanSolicitud = false){

            String url = "https://api.openweathermap.org/data/3.0/onecall?lat="+latitud+"&lon="+longitud+"&exclude="+exclude+"&appid="+api_key;
            SolicitudClima solicitudClima = new SolicitudClima(url, listener, errorListener);
            RequestQueue queue  = Volley.newRequestQueue(this);
            queue.add(solicitudClima);

        }

    }
}