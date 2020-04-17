package com.example.reachthegym.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.reachthegym.OnFragmentInteractionList;
import com.example.reachthegym.R;
import com.example.reachthegym.objetos.Usuario;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import static android.app.Activity.RESULT_OK;


public class FragmentRegistro extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextInputEditText dni;
    private TextInputEditText nombre,apellidos,telefono,contraseña,direccion,email;
    private MaterialButton registro,tomar_foto;
    private ImageView img_usuario;
    private Uri img_url;
    private DatabaseReference ref;
    private StorageReference sto;
    private final static int seleccionar_img=1;
    String currentPhotoPath;
    private final static int REQUEST_IMAGE_CAPTURE = 2;


    private OnFragmentInteractionList mListener;

    public FragmentRegistro() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentRegistro.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentRegistro newInstance(String param1, String param2) {
        FragmentRegistro fragment = new FragmentRegistro();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View vista = inflater.inflate(R.layout.fragment_registro, container, false);
        img_url = null;

        dni = (TextInputEditText) vista.findViewById(R.id.dni_registro);
        email = (TextInputEditText)vista.findViewById(R.id.email_registro);
        nombre = (TextInputEditText)vista.findViewById(R.id.nombre_registro);
        apellidos = (TextInputEditText)vista.findViewById(R.id.apellidos_registro);
        telefono = (TextInputEditText)vista.findViewById(R.id.telefono_registro);
        direccion = (TextInputEditText)vista.findViewById(R.id.direccion_registro);
        contraseña = (TextInputEditText)vista.findViewById(R.id.contrasena_registro);
        registro = (MaterialButton)vista.findViewById(R.id.bton_reg_hecho);
        tomar_foto = (MaterialButton)vista.findViewById(R.id.tomar_foto_registro);
        img_usuario = (ImageView)vista.findViewById(R.id.img_usuario_registro);
        ref = FirebaseDatabase.getInstance().getReference();
        sto = FirebaseStorage.getInstance().getReference();
        checkPermissions();

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String denei,em,nom,ape,tel,direc,contra,f_alta;
                denei = dni.getText().toString().trim();
                em = email.getText().toString().trim();
                nom = nombre.getText().toString().trim();
                ape = apellidos.getText().toString().trim();
                tel = telefono.getText().toString().trim();
                direc = direccion.getText().toString().trim();
                contra = contraseña.getText().toString().trim();

                Calendar c = Calendar.getInstance();
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                f_alta = format1.format(c.getTime());

                ref.child("centro")
                        .child("usuarios")
                        .orderByChild("email")
                        .equalTo(em)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (!dataSnapshot.hasChildren()){


                                    if (validarEmail(em)){
                                        if (!denei.isEmpty() && !em.isEmpty() && !nom.isEmpty() && !ape.isEmpty() && !tel.isEmpty() && !direc.isEmpty() && !contra.isEmpty() ){

                                            if (img_url!=null){
                                                Usuario nuevo_usuario = new Usuario(denei,nom,ape,tel,direc,contra,em,f_alta);
                                                String clave  = ref.child("centro").child("usuarios").push().getKey();
                                                nuevo_usuario.setId(clave);
                                                ref.child("centro").child("usuarios").child(clave).setValue(nuevo_usuario);
                                                sto.child("centro").child("imagenes").child(clave).putFile(img_url);

                                                Toast.makeText(getContext(), "Usuario creado correctamente", Toast.LENGTH_SHORT).show();
                                                cerrarFragment();
                                            }else{
                                                Toast.makeText(getContext(), "Seleccione una foto", Toast.LENGTH_SHORT).show();
                                            }

                                        }else{
                                            Toast.makeText(getContext(), "Rellene todos los campos", Toast.LENGTH_SHORT).show();
                                        }

                                    }else{
                                        Toast.makeText(getContext(), "Email incorrecto", Toast.LENGTH_SHORT).show();
                                    }

                                }else{
                                    Toast.makeText(getContext(), "El email ya está en uso", Toast.LENGTH_SHORT).show(); }
                                }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });



            }
        });

        tomar_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tomarFoto(v);
            }
        });


        img_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i,seleccionar_img);
            }
        });


        return vista;
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "PNG_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".png",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEgit W intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
    public void tomarFoto(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "com.example.reachthegym.fileprovider",
                        photoFile);
                img_url= photoURI;

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private void cerrarFragment(){
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }
    public void checkPermissions(){

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
        }

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

    }
    @Override
    public void onActivityResult (int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == seleccionar_img && resultCode == RESULT_OK ) {
            img_url = data.getData();
            img_usuario.setImageURI(img_url);
            Toast.makeText(getContext(), "Imagen seleccionada", Toast.LENGTH_SHORT).show();
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
            img_usuario.setImageBitmap(bitmap);
        } else {
            Toast.makeText(getContext(), "Imagen no seleccionada", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean validarEmail(String email){
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

}
