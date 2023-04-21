package com.example.clima.General;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;

import com.example.clima.R;

public class Ubicacion implements LocationListener {

    Context context;

    LocationManager manejadorLocalizacion;
    Location location;

    InterfaceUbicacion interfaceUbicacion;

    @SuppressLint("MissingPermission")
    public Ubicacion(Context context, InterfaceUbicacion interfaceUbicacion){

        this.context = context;
        this.interfaceUbicacion = interfaceUbicacion;

        manejadorLocalizacion = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        //boolean  permisoUbicacion = permisosUsuario.permisosUbicacion();

        boolean  permisoUbicacion = permisosUbicacion();

        if(permisoUbicacion){

            manejadorLocalizacion.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, (LocationListener) this);
            manejadorLocalizacion.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, (LocationListener) this);

            if (verificarGPSHabilitado() == true) {

                location = manejadorLocalizacion.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                if (location == null) {

                    location = manejadorLocalizacion.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                }

                interfaceUbicacion.obtenerUbicacion(location);

            }

        }

    }

    @Override
    public void onLocationChanged(Location location) {

        this.location = location;
        interfaceUbicacion.obtenerUbicacion(location);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public boolean verificarGPSHabilitado(){

        try {

            int verificarGPSActivado = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            if(verificarGPSActivado == 0){

                return false;

            }
            else{

                return true;

            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }

    }

    public void mostrarAlertaGPS(){

        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
        dialogo1.setTitle("Encender GPS");
        dialogo1.setMessage(context.getString(R.string.errorUbicacion));
        dialogo1.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

                interfaceUbicacion.respuestaEncender(true);

            }
        });
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                interfaceUbicacion.respuestaEncender(false);

            }
        });
        dialogo1.show();

    }

    public boolean permisosUbicacion(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            if(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

                interfaceUbicacion.solicitarPermisos();

                return false;

            }else{

                return true;

            }

        }else{

            return true;

        }

    }

}
