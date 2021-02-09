package com.example.mitelecomunicaciones;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //variable int, para posteriormente pedir permiso de llamada
    private static  final int REQUEST_CALL =1;
    //variable para traer el Edit Text de la interfaz
    private EditText edNumero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //edit Text para poder manipularlo
        edNumero = findViewById(R.id.etNumero);
        //image view para poder manipularlo
        ImageView imgLlamar = findViewById(R.id.ivLlamar);
        // le asignamos a el image view una accion cuando se haga click en el, en este caso llamar a makePhoneCall()
        imgLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearLlamadaTelefonica();
            }
        });
    }

    private void crearLlamadaTelefonica(){
        //extraemos el numero del editText
        String number = edNumero.getText().toString();
        if (number.trim().length() >0){
            //si tiene algo
            if(ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                //si no tiene los permisos concedidos pregunta en el momento
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            }else {
                //si tiene los permisos concedidos crea una nueva string con "tel: " al principio
                String NumMarcar = "tel: "+ number;
                //crea el intent que va a llamar con el numero adjunto
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(NumMarcar)));
            }
        }else{
            //si no tiene nada muestra un toast diciendo que ponga algo
            Toast.makeText(MainActivity.this,"Ingresa un numero de telefono", Toast.LENGTH_LONG).show();
        }
    }
 // sobreescribimos el metodo onRequestPermissionsResult para saber si los el usuario preciona
 // aceptar o rechazar cuando se le pide que acepte el permiso de llamada si dice si se llama
 // el metodo crearLlamadaTelefonica(), si dice no, se manda un toast diciendo permisos denegados
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                crearLlamadaTelefonica();
            }else {
                Toast.makeText(this,"Permisos DENEGADOS", Toast.LENGTH_LONG).show();
            }
        }
    }
}
