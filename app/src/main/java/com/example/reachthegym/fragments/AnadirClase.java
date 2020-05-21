package com.example.reachthegym.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.UnicodeSetSpanner;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.reachthegym.OnFragmentInteractionList;
import com.example.reachthegym.R;
import com.example.reachthegym.objetos.ClaseGimnasio;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnadirClase#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnadirClase extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.img_crear_clase)
    ImageView imgCrearClase;
    @BindView(R.id.nombre_crear_clase)
    TextInputEditText nombreCrearClase;
    @BindView(R.id.descripcion_crear_clase)
    TextInputEditText descripcionCrearClase;
    @BindView(R.id.aula_crear_clase)
    TextInputEditText aulaCrearClase;
    @BindView(R.id.dia_crear_clase)
    TextInputEditText diaCrearClase;
    @BindView(R.id.hora_crear_clase)
    TextInputEditText horaCrearClase;
    @BindView(R.id.hora_final_clase)
    TextInputEditText horaFinalClase;
    @BindView(R.id.anadir_clase)
    MaterialButton anadirClase;
    private final static int seleccionar_img=1;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionList mListener;
    private DatabaseReference ref;
    private StorageReference sto;
    private Uri img_url;


    public AnadirClase() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnadirClase.
     */
    // TODO: Rename and change types and number of parameters
    public static AnadirClase newInstance(String param1, String param2) {
        AnadirClase fragment = new AnadirClase();
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
        View vista = inflater.inflate(R.layout.fragment_anadir_clase, container, false);
        ButterKnife.bind(this, vista);

        ref = FirebaseDatabase.getInstance().getReference();
        sto = FirebaseStorage.getInstance().getReference();

            if (nombreCrearClase.getText().toString().trim().isEmpty() && descripcionCrearClase.getText().toString().trim().isEmpty()
            && horaCrearClase.getText().toString().trim().isEmpty() && horaFinalClase.getText().toString().trim().isEmpty()
            && aulaCrearClase.getText().toString().trim().isEmpty() && diaCrearClase.getText().toString().trim().isEmpty()){

                Toast.makeText(getContext(), "Rellene todos los campos", Toast.LENGTH_SHORT).show();

            }else{

                String nombre,descripcion,aula,dia;
                int hora_ini,hora_final;
                nombre = nombreCrearClase.getText().toString().trim();
                descripcion = descripcionCrearClase.getText().toString().trim();
                hora_ini = Integer.parseInt(horaCrearClase.getText().toString());
                hora_final = Integer.parseInt(horaFinalClase.getText().toString());
                aula = aulaCrearClase.getText().toString().trim();
                dia = diaCrearClase.getText().toString().trim();


                String id = ref.child("centro").child("clases").push().getKey();
                ClaseGimnasio pojo_clase = new ClaseGimnasio(id,nombre,descripcion,aula,hora_ini,hora_final,dia);

                ref.child("centro").child("clases").child(id).setValue(pojo_clase);

                sto.child("centro").child("imagenes").child("imagenes_clase").child(id).putFile(img_url);

                Toast.makeText(getContext(), "Clase añadida correctamente", Toast.LENGTH_SHORT).show();



            }

        imgCrearClase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i,seleccionar_img);
            }
        });


        return vista;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentMessage("", "");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionList) {
            mListener = (OnFragmentInteractionList) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void onActivityResult (int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == seleccionar_img && resultCode == RESULT_OK ) {
            img_url = data.getData();
            imgCrearClase.setImageURI(img_url);
            Toast.makeText(getContext(), "Imagen seleccionada", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Imagen no seleccionada", Toast.LENGTH_SHORT).show();
        }

    }

}
