package com.example.clima.models;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class SolicitudClima extends StringRequest {

    public SolicitudClima(String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(Method.GET, url, listener, errorListener);
    }

}
