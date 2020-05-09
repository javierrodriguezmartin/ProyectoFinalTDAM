package com.example.reachthegym.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.reachthegym.OnFragmentInteractionList;
import com.example.reachthegym.R;
import com.example.reachthegym.objetos.Ejercicio;
import com.example.reachthegym.objetos.EjercicioEmpleado;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.OptionalInt;

import ahmed.easyslider.EasySlider;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnadirEjercicioAdmin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnadirEjercicioAdmin extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.img_ejer_1)
    ImageView imgEjer1;
    @BindView(R.id.img_ejer_2)
    ImageView imgEjer2;
    @BindView(R.id.img_ejer_3)
    ImageView imgEjer3;
    @BindView(R.id.img_ejer_4)
    ImageView imgEjer4;
    @BindView(R.id.nombre_ejercicio)
    EditText nombreEjercicio;
    @BindView(R.id.zona_ejercicio)
    EditText zonaEjercicio;
    @BindView(R.id.objetivo_ejercicio)
    EditText objetivoEjercicio;
    @BindView(R.id.descripcion_ejercicio)
    EditText descripcionEjercicio;
    @BindView(R.id.anadir_ejercicio)
    Button anadirEjercicio;
    private final static int seleccionar_img=1;
    private Unbinder unbinder;
    private Uri img_url,img_url1,img_url2,img_url3;
    private ArrayList<Uri> array_imagenes = new ArrayList<>();
    private DatabaseReference ref;
    private StorageReference sto;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EasySlider easySlider;
    private OnFragmentInteractionList mListener;

    public AnadirEjercicioAdmin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnadirEjercicioAdmin.
     */
    // TODO: Rename and change types and number of parameters
    public static AnadirEjercicioAdmin newInstance(String param1, String param2) {
        AnadirEjercicioAdmin fragment = new AnadirEjercicioAdmin();
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
        View vista = inflater.inflate(R.layout.fragment_anadir_ejercicio_admin, container, false);
        unbinder = ButterKnife.bind(this,vista);
        img_url=null;
        img_url1=null;
        img_url2=null;
        img_url3=null;
        ref = FirebaseDatabase.getInstance().getReference();
        sto = FirebaseStorage.getInstance().getReference();
        final String tipo_usuario;
        final String id_usuario;
        SharedPreferences prefs = getActivity().getSharedPreferences("datos_usuario",Context.MODE_PRIVATE);
        tipo_usuario = prefs.getString("tipo_usuario","No llega nada");
        id_usuario = prefs.getString("id_usuario","no hay nada");



        imgEjer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i,seleccionar_img);
            }
        });
        imgEjer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i,seleccionar_img);
            }
        });
        imgEjer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i,seleccionar_img);
            }
        });
        imgEjer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i,seleccionar_img);
            }
        });

        anadirEjercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.child("centro").child("ejercicios_empleado").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (nombreEjercicio.getText().toString().trim().isEmpty() && zonaEjercicio.getText().toString().trim().isEmpty() && descripcionEjercicio.getText().toString().trim().isEmpty()
                                && objetivoEjercicio.getText().toString().isEmpty() && img_url==null){

                            Toast.makeText(getActivity(), "Debe rellenar los campos y añadir cuatro fotos.", Toast.LENGTH_SHORT).show();

                        }else{
                            String nombre = nombreEjercicio.getText().toString().trim();
                            String zona =  zonaEjercicio.getText().toString().trim();
                            String descripcion = descripcionEjercicio.getText().toString().trim();
                            String objetivo = objetivoEjercicio.getText().toString().trim();

                            if (tipo_usuario.equals("admin")){

                                String id_ejercicio = ref.child("centro").child("ejercicios").push().getKey();
                                Ejercicio pojo_ejercicio = new Ejercicio(id_ejercicio,nombre,zona,objetivo,descripcion);
                                ref.child("centro").child("ejercicios").child(id_ejercicio).setValue(pojo_ejercicio);

                                for (Uri item:array_imagenes){
                                    sto.child("centro").child("imagenes").child("ejercicios").child(id_ejercicio).putFile(item);
                                }
                                Toast.makeText(getActivity(), "Ejercicio administrador añadido", Toast.LENGTH_SHORT).show();
                                cerrarFragment();

                            }else if (tipo_usuario.equals("empleado")){

                                String id_ejercicio_emp = ref.child("centro").child("ejercicios_empleados").push().getKey();
                                Ejercicio pojo_ejercicio = new EjercicioEmpleado(id_ejercicio_emp,nombre,zona,objetivo,descripcion,id_usuario);
                                ref.child("centro").child(" ejercicios_empleado").child(id_ejercicio_emp).setValue(pojo_ejercicio);

                                for (Uri item : array_imagenes){
                                    String clave = ref.child("centro").push().getKey();
                                    sto.child("centro").child("imagenes").child("ejercicios_empleado").child(id_ejercicio_emp).child(clave).putFile(item);
                                }
                                Toast.makeText(getActivity(), "Ejercicio añadido a tu lista de ejercicios", Toast.LENGTH_SHORT).show();
                                cerrarFragment();

                            }else{
                                Toast.makeText(getActivity(), "No entra a los if de tipos de usuario", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
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


    @Override
    public void onActivityResult (int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == seleccionar_img && resultCode == RESULT_OK ) {
            img_url = data.getData();
            array_imagenes.add(img_url);
            if (array_imagenes.size()==1){
                imgEjer1.setImageURI(array_imagenes.get(0));
            }else if(array_imagenes.size()==2) {
                imgEjer2.setImageURI(array_imagenes.get(1));
            }else if(array_imagenes.size()==3){
                imgEjer3.setImageURI(array_imagenes.get(2));
            }else if(array_imagenes.size()==4){
                imgEjer4.setImageURI(array_imagenes.get(3));
            }else{
                Toast.makeText(getContext(), "No entra en los if", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(getContext(), "Imagen seleccionada", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getContext(), "Imagen no seleccionada", Toast.LENGTH_SHORT).show();
        }

    }

    private void cerrarFragment(){
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }


}
