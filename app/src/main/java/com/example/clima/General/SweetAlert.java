package com.example.clima.General;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clima.R;

public class SweetAlert implements View.OnClickListener {

    Context context;
    AlertDialog.Builder builder;
    LayoutInflater inflater;
    View viewAlert;

    public ImageView ivDialogo;
    public TextView tvDialogo;
    public Button btnDialogo;

    public AlertDialog alertDialog;

    public SweetAlert(Context context){

        this.context = context;

        builder = new AlertDialog.Builder(this.context);
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewAlert = inflater.inflate(com.example.clima.R.layout.dialogo_dulce, null);
        ivDialogo = viewAlert.findViewById(R.id.dialogoDulceIvIcono);
        tvDialogo = viewAlert.findViewById(R.id.dialogoDulceTvTitulo);
        btnDialogo = viewAlert.findViewById(R.id.dialogoDulceBtnOk);
        builder.setView(viewAlert);
        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btnDialogo.setOnClickListener(this);

    }

    public void textoSweetAlert(String texto){

        tvDialogo.setText(texto);

    }

    public void imagenSweetAlert(int imagen){

        ivDialogo.setImageResource(imagen);

    }

    public void mostrarSweetAlert(){

        alertDialog.show();

    }

    public void ocultarSweetAlert(){

        alertDialog.hide();
        alertDialog.dismiss();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.dialogoDulceBtnOk:
                ocultarSweetAlert();
                break;

            default:
                break;

        }

    }

}
