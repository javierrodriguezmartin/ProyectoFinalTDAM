package com.example.reachthegym.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reachthegym.OnFragmentInteractionList;
import com.example.reachthegym.R;
import com.example.reachthegym.adaptadores.AdaptadorBuscarCompanero;
import com.example.reachthegym.objetos.BuscarCompañero;
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
 * Use the {@link ListarBuscarCompanero#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListarBuscarCompanero extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recy_buscar_compi)
    RecyclerView recyBuscarCompi;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionList mListener;
    private ArrayList<BuscarCompañero> lista_anuncios = new ArrayList<>();
    private DatabaseReference ref;
    private AdaptadorBuscarCompanero adapter;

    public ListarBuscarCompanero() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListarBuscarCompanero.
     */
    // TODO: Rename and change types and number of parameters
    public static ListarBuscarCompanero newInstance(String param1, String param2) {
        ListarBuscarCompanero fragment = new ListarBuscarCompanero();
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

        View vista = inflater.inflate(R.layout.fragment_listar_buscar_companero, container, false);
        ButterKnife.bind(this,vista);
        ref = FirebaseDatabase.getInstance().getReference();
        if (isAdded()){

            ref.child("centro").child("buscar_compañero").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    lista_anuncios.clear();
                    if (dataSnapshot.hasChildren()){

                        for (DataSnapshot hijo : dataSnapshot.getChildren()){

                            BuscarCompañero pojo_buscar = hijo.getValue(BuscarCompañero.class);
                            lista_anuncios.add(pojo_buscar);
                            adapter.notifyDataSetChanged();


                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            adapter = new AdaptadorBuscarCompanero(lista_anuncios,getContext());
            recyBuscarCompi.setLayoutManager(new LinearLayoutManager(getContext()));
            recyBuscarCompi.setAdapter(adapter);

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
}
