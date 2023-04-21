package com.example.clima.General;

import android.location.Location;

public interface InterfaceUbicacion {

    void solicitarPermisos();
    void obtenerUbicacion(Location location);
    void respuestaEncender(Boolean respuesta);
    void calcularDistancia();

}
