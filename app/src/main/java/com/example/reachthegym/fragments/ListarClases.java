package com.example.reachthegym.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reachthegym.OnFragmentInteractionList;
import com.example.reachthegym.R;
import com.example.reachthegym.adaptadores.AdapterListarClases;
import com.example.reachthegym.objetos.ClaseGimnasio;
import com.example.reachthegym.objetos.Ejercicio;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListarClases#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListarClases extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.spinner_listar_clases)
    Spinner spinnerListarClases;
    @BindView(R.id.recy_listar_clases)
    RecyclerView recyListarClases;
    @BindView(R.id.pretextoSpinner)
    TextView pretextoSpinner;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionList mListener;
    private AdapterListarClases adapter;
    private ArrayList<ClaseGimnasio> lista_clases;
    private DatabaseReference ref;


    public ListarClases() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListarClases.
     */
    // TODO: Rename and change types and number of parameters
    public static ListarClases newInstance(String param1, String param2) {
        ListarClases fragment = new ListarClases();
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
        View vista = inflater.inflate(R.layout.fragment_listar_clases, container, false);
        ButterKnife.bind(this, vista);
        ref = FirebaseDatabase.getInstance().getReference();
        lista_clases = new ArrayList<>();
        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(getContext(),R.array.dias_semana,R.layout.support_simple_spinner_dropdown_item);
        adapterSpinner.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerListarClases.setAdapter(adapterSpinner);
        spinnerListarClases.setSelection(0);


        ref.child("centro").child("clases").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lista_clases.clear();
                for (DataSnapshot hijo: dataSnapshot.getChildren()){
                    ClaseGimnasio pojo_clase = hijo.getValue(ClaseGimnasio.class);
                    lista_clases.add(pojo_clase);
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter = new AdapterListarClases(lista_clases,getContext());
        recyListarClases.setLayoutManager(new LinearLayoutManager(getContext()));
        recyListarClases.setAdapter(adapter);

        spinnerListarClases.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String dia = parent.getItemAtPosition(position).toString();
                buscarSpinner(dia);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

    public void buscarSpinner(String texto) {

        ArrayList<ClaseGimnasio> filtrolista = new ArrayList<>();
        for (ClaseGimnasio claseGimnasio: lista_clases){

            if (claseGimnasio.getDia().toLowerCase().equalsIgnoreCase(texto)){
                filtrolista.add(claseGimnasio);
            }

        }

        Collections.sort(filtrolista, new Comparator<ClaseGimnasio>() {
            @Override
            public int compare(ClaseGimnasio o1, ClaseGimnasio o2) {
                return new Integer(o1.getHora_inicio()).compareTo(new Integer(o2.getHora_inicio()));
            }
        });

        adapter.filtrar(filtrolista);

    }

}
