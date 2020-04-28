package com.example.reachthegym;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

public class actiPrueba extends AppCompatActivity {

    private EditText texto;
    private Button añadir;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acti_prueba);

        texto = (EditText)findViewById(R.id.tipo_cliente);
        añadir = (Button) findViewById(R.id.añadir_tipo);
        ref = FirebaseDatabase.getInstance().getReference();
        añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.child("centro").child("tipos").push().setValue(texto.getText().toString());
            }
        });

    }
}
