package com.example.reachthegym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.reachthegym.fragments.FragmentRegistro;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

        ini_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 ref.child("centro")
                         .child("usuarios")
                         .orderByChild("email")
                         .addListenerForSingleValueEvent(new ValueEventListener() {
                             @Override
                             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                 DataSnapshot hijo = dataSnapshot.getChildren().iterator().next();
                                 String con,em;
                                 em = correo.getText().toString().trim();
                                 con = contra.getText().toString().trim();

                                 if (){


                                 }else{

                                 }

                             }

                             @Override
                             public void onCancelled(@NonNull DatabaseError databaseError) {

                             }
                         });

            }
        });

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentRegistro fragmentRegistro = FragmentRegistro.newInstance("","");
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frame_login,fragmentRegistro);
                ft.commit();

            }
        });

        img_url = null;

    }
}
