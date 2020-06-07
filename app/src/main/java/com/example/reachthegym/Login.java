package com.example.reachthegym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.reachthegym.objetos.Usuario;
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
                         .equalTo(correo.getText().toString().trim())
                         .addListenerForSingleValueEvent(new ValueEventListener() {
                             @Override
                             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                 if(dataSnapshot.hasChildren()){

                                     DataSnapshot hijo = dataSnapshot.getChildren().iterator().next();
                                     String pw = hijo.getValue(Usuario.class).getContrasena();
                                     String ide = hijo.getValue(Usuario.class).getId();
                                     String tipo = hijo.getValue(Usuario.class).getTipo().toLowerCase();
                                     String nom = hijo.getValue(Usuario.class).getNombre();
                                     Toast.makeText(Login.this, ""+nom+"", Toast.LENGTH_SHORT).show();
                                     Toast.makeText(Login.this, ""+ide+"", Toast.LENGTH_SHORT).show();

                                     if (pw.equals(contra.getText().toString().trim())){

                                         SharedPreferences prefs = getSharedPreferences("datos_usuario", Context.MODE_PRIVATE);
                                         SharedPreferences.Editor editor = prefs.edit();
                                         editor.putString("id_usuario",ide);
                                         editor.putString("tipo_usuario",tipo);
                                         editor.putString("nombre_usuario",nom);

                                         editor.commit();

                                         Intent i = new Intent(getApplicationContext(),MenuPrincipal.class);
                                         startActivity(i);

                                     }else{
                                         Toast.makeText(Login.this, "Las credenciales no coinciden", Toast.LENGTH_SHORT).show();
                                     }

                                 }else{
                                     Toast.makeText(Login.this, "El usuario no está registrado" , Toast.LENGTH_SHORT).show();
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
