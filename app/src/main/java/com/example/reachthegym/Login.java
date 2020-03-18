package com.example.reachthegym;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Login extends AppCompatActivity {
    private EditText correo,contra;
    private ImageView img_login;
    private Button ini_sesion,registro;
    private DatabaseReference ref;
    private StorageReference sto;
    private Uri img_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        correo = (EditText)findViewById(R.id.correo_login);
        contra = (EditText)findViewById(R.id.contrasena_login);
        ini_sesion = (Button)findViewById(R.id.ini_sesion);
        registro = (Button)findViewById(R.id.registro_login);
        img_login = (ImageView)findViewById(R.id.img_login);
        ref = FirebaseDatabase.getInstance().getReference();
        sto = FirebaseStorage.getInstance().getReference();

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}
