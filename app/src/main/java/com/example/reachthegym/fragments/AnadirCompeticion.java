package com.example.reachthegym.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.reachthegym.OnFragmentInteractionList;
import com.example.reachthegym.R;
import com.example.reachthegym.objetos.Competicion;
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
 * Use the {@link AnadirCompeticion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnadirCompeticion extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.img_anadir_competicion)
    ImageView imgAnadirCompeticion;
    @BindView(R.id.nombre_primer_ejercicio)
    TextInputEditText nombrePrimerEjercicio;
    @BindView(R.id.nombre_segundo_ejercicio)
    TextInputEditText nombreSegundoEjercicio;
    @BindView(R.id.descripcion_anadir_competicion)
    EditText descripcionAnadirCompeticion;
    @BindView(R.id.bton_anadir_competicion)
    MaterialButton btonAnadirCompeticion;
    @BindView(R.id.nombre_competicion)
    TextInputEditText nombreCompeticion;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionList mListener;
    private Uri img_url;
    private DatabaseReference ref;
    private StorageReference sto;
    private final static int seleccionar_img = 1;

    public AnadirCompeticion() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnadirCompeticion.
     */
    // TODO: Rename and change types and number of parameters
    public static AnadirCompeticion newInstance(String param1, String param2) {
        AnadirCompeticion fragment = new AnadirCompeticion();
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
        View vista = inflater.inflate(R.layout.fragment_anadir_competicion, container, false);
        ButterKnife.bind(this, vista);
        if (isAdded()) {

            ref = FirebaseDatabase.getInstance().getReference();
            sto = FirebaseStorage.getInstance().getReference();


            SharedPreferences prefs = getActivity().getSharedPreferences("datos_usuario", Context.MODE_PRIVATE);
            final String tipo_usuario = prefs.getString("tipo_usuario", "nada");


            btonAnadirCompeticion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!nombrePrimerEjercicio.getText().toString().trim().isEmpty() && !nombreSegundoEjercicio.getText().toString().trim().isEmpty()
                            && img_url != null && !descripcionAnadirCompeticion.getText().toString().trim().isEmpty() && !nombreCompeticion.getText().toString().trim().isEmpty()) {

                        String nombre1, nombre2, descripcion,nombre;
                        nombre1 = nombrePrimerEjercicio.getText().toString();
                        nombre2 = nombreSegundoEjercicio.getText().toString();
                        descripcion = descripcionAnadirCompeticion.getText().toString();
                        nombre = nombreCompeticion.getText().toString();

                        String id = ref.child("centro").child("competiciones").push().getKey();
                        Competicion pojo_competicion = new Competicion(id, nombre1, nombre2, descripcion,nombre);


                        ref.child("centro").child("competiciones").child(id).setValue(pojo_competicion);

                        sto.child("centro").child("imagenes").child("imagenes_competicion").child(id).putFile(img_url);

                        Toast.makeText(getContext(), "Competicion añadida correctamente", Toast.LENGTH_SHORT).show();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                cerrarFragment();
                            }
                        },100);



                    } else {
                        Toast.makeText(getContext(), "Rellene todos los campos", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            imgAnadirCompeticion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_PICK);
                    i.setType("image/*");
                    startActivityForResult(i, seleccionar_img);
                }
            });


        }


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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == seleccionar_img && resultCode == RESULT_OK) {
            img_url = data.getData();
            imgAnadirCompeticion.setImageURI(img_url);
            Toast.makeText(getContext(), "Imagen seleccionada", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Imagen no seleccionada", Toast.LENGTH_SHORT).show();
        }

    }

    private void cerrarFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }
}
