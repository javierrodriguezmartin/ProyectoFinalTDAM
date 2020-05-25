package com.example.reachthegym.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.reachthegym.OnFragmentInteractionList;
import com.example.reachthegym.R;
import com.example.reachthegym.adaptadores.ListEjerciciosAdapter;
import com.example.reachthegym.objetos.Ejercicio;
import com.example.reachthegym.objetos.EjercicioEmpleado;
import com.example.reachthegym.objetos.Rutina;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CrearRutina#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrearRutina extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.nombre_edit_rutina)
    TextInputEditText nombreEditRutina;
    @BindView(R.id.listview_ejercicios)
    ListView listviewEjercicios;
    @BindView(R.id.bton_crear_rutina)
    MaterialButton btonCrearRutina;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionList mListener;
    private ArrayList<EjercicioEmpleado> lista_ejercicios = new ArrayList<>();
    private ArrayList<EjercicioEmpleado> ejercicios_rutina = new ArrayList<>();
    private DatabaseReference ref;
    private ListEjerciciosAdapter adapter;



    public CrearRutina() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CrearRutina.
     */
    // TODO: Rename and change types and number of parameters
    public static CrearRutina newInstance(String param1, String param2) {
        CrearRutina fragment = new CrearRutina();
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
        View vista = inflater.inflate(R.layout.fragment_crear_rutina, container, false);
        ButterKnife.bind(this, vista);

        SharedPreferences prefs = getActivity().getSharedPreferences("datos_usuario",Context.MODE_PRIVATE);
        String id_usuario = prefs.getString("id_usuario","");

        ref = FirebaseDatabase.getInstance().getReference();
        ref.child("centro").child("ejercicios_empleado").orderByChild("id_empleado").equalTo(id_usuario).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot hijo: dataSnapshot.getChildren()){
                    EjercicioEmpleado ejercicioEmpleado = hijo.getValue(EjercicioEmpleado.class);
                    lista_ejercicios.add(ejercicioEmpleado);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter = new ListEjerciciosAdapter(getContext(),lista_ejercicios);
        listviewEjercicios.setAdapter(adapter);
        listviewEjercicios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ejercicios_rutina.add(lista_ejercicios.get(position));
                String nombre_ejer = lista_ejercicios.get(position).getNombre();
                Toast.makeText(getContext(), "Ejercicio "+nombre_ejer+" añadido correctamente", Toast.LENGTH_SHORT).show();
            }
        });

        btonCrearRutina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nombreEditRutina.getText().toString().trim().isEmpty() && ejercicios_rutina.isEmpty()){

                    Toast.makeText(getContext(), "Debes añadir un nombre para la rutina y seleccionar los ejercicios para añadir", Toast.LENGTH_SHORT).show();

                }else{

                    String clave = ref.child("centro").child("rutinas").push().getKey();
                    String nombre_rut = nombreEditRutina.getText().toString();
                    Rutina rutina = new Rutina(clave,nombre_rut,ejercicios_rutina);

                    ref.child("centro").child("rutinas").child(clave).setValue(rutina);

                    Toast.makeText(getContext(), "Rutina añadida con éxito", Toast.LENGTH_SHORT).show();
                    cerrarFragment();
                }
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

    private void cerrarFragment(){
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

}
