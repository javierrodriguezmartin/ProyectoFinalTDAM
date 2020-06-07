package com.example.reachthegym.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.MacAddress;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.reachthegym.ObservableInteger;
import com.example.reachthegym.OnFragmentInteractionList;
import com.example.reachthegym.OnIntegerChangeListener;
import com.example.reachthegym.R;
import com.example.reachthegym.objetos.AuxiliarRanking;
import com.example.reachthegym.objetos.Competicion;
import com.example.reachthegym.objetos.Ranking;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
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
import java.util.Observable;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VerCompeticion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VerCompeticion extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionList mListener;
    @BindView((R.id.img_ver_competicion))
    ImageView imgVerCompeticion;
    @BindView((R.id.borrar_competicion))
    MaterialButton borrarCompeticion;
    @BindView((R.id.bton_apuntarse_competicion))
    MaterialButton btonApuntarseCompeticion;
    @BindView((R.id.nombre_ver_competicion))
    MaterialTextView nombreVerCompeticion;
    @BindView((R.id.nombre_eje1_competicion))
    MaterialTextView nomEje1Competicion;
    @BindView((R.id.nombre_eje2_competicion))
    MaterialTextView nomEje2Competicion;
    @BindView((R.id.descripcion_ver_competicion))
    MaterialTextView descripcionVerCompeticion;
    @BindView((R.id.puntos_ejer1))
    TextInputEditText puntosEjer1;
    @BindView((R.id.puntos_ejer2))
    TextInputEditText puntosEjer2;
    @BindView((R.id.bton_añadir_participacion))
    MaterialButton btonAnadirParticipacion;
    @BindView((R.id.bton_ranking_competicion))
    MaterialButton btonRankingCompeticion;
    @BindView((R.id.participantes_competicion))
    MaterialTextView participantesCompeticion;
    private DatabaseReference ref;
    private StorageReference sto;






    public VerCompeticion() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VerCompeticion.
     */
    // TODO: Rename and change types and number of parameters
    public static VerCompeticion newInstance(String param1, String param2) {
        VerCompeticion fragment = new VerCompeticion();
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

        View vista = inflater.inflate(R.layout.fragment_ver_competicion, container, false);
        ButterKnife.bind(this,vista);
        SharedPreferences prefs = getActivity().getSharedPreferences("datos_usuario",Context.MODE_PRIVATE);
        final String tipo_usuario = prefs.getString("tipo_usuario","");
        ref = FirebaseDatabase.getInstance().getReference();
        sto = FirebaseStorage.getInstance().getReference();
        final String[] ide_competi = new String[1];
        final String ide_usuario = prefs.getString("id_usuario","");
        final Competicion[] pojo_competi = {new Competicion()};
        final boolean[] apuntado = {false};



        if (isAdded()){




            if (tipo_usuario.equalsIgnoreCase("empleado")){
                puntosEjer1.setClickable(false);
                puntosEjer1.setEnabled(false);
                puntosEjer2.setClickable(false);
                puntosEjer2.setEnabled(false);
            }else if(tipo_usuario.equalsIgnoreCase("admin")){
                puntosEjer1.setClickable(false);
                puntosEjer1.setEnabled(false);
                puntosEjer2.setClickable(false);
                puntosEjer2.setEnabled(false);
            }else if(tipo_usuario.equalsIgnoreCase("cliente")){
                borrarCompeticion.setClickable(false);
                borrarCompeticion.setEnabled(false);
                borrarCompeticion.setVisibility(View.GONE);
            }






            if (mParam1!=null){



                ref.child("centro").child("competiciones").child(mParam1).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()){

                            Competicion pojo_competicion = dataSnapshot.getValue(Competicion.class);
                            pojo_competi[0] =pojo_competicion;
                            nombreVerCompeticion.setText(pojo_competicion.getNombre());
                            nomEje1Competicion.setText(pojo_competicion.getNombre_ejercicio1());
                            nomEje2Competicion.setText(pojo_competicion.getNombre_ejercicio2());
                            descripcionVerCompeticion.setText(pojo_competicion.getDescripcion());
                            ide_competi[0] = pojo_competicion.getId_competicion();


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                if (pojo_competi[0].getLista_participantes()==null){
                    participantesCompeticion.setText("0");
                }else if(pojo_competi[0].getLista_participantes().size()==0){
                    participantesCompeticion.setText("0");
                }else{

                    participantesCompeticion.setText(""+pojo_competi[0].getLista_participantes().size()+"");
                }

                sto.child("centro").child("imagenes").child("imagenes_competicion").child(mParam1).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(getContext()).load(uri).into(imgVerCompeticion);
                    }
                });


                btonApuntarseCompeticion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (tipo_usuario.equalsIgnoreCase("empleado")){
                            Toast.makeText(getContext(), "Los empleados no pueden apuntarse a competiciones", Toast.LENGTH_SHORT).show();
                        }else{


                            if (pojo_competi[0].getLista_participantes()==null){
                                ArrayList<AuxiliarRanking> lista = new ArrayList<>();
                                AuxiliarRanking pojo_auxiliar = new AuxiliarRanking(ide_usuario);
                                lista.add(pojo_auxiliar);
                                Ranking pojo_ranking = new Ranking(pojo_competi[0].getId_competicion(),lista);
                                ref.child("centro").child("ranking").child(pojo_competi[0].getId_competicion()).setValue(pojo_ranking);

                                anadirAlista(pojo_competi[0].getId_competicion(),ide_usuario);

                                Toast.makeText(getContext(), "Te has apuntado correctamente", Toast.LENGTH_SHORT).show();
                                apuntado[0] = true;


                            }else if (apuntado[0]==true){

                                ArrayList<String> list = new ArrayList<>();
                                list = pojo_competi[0].getLista_participantes();

                                if (!estaEnLista(list,ide_usuario)){

                                    ref.child("centro").child("ranking").child(pojo_competi[0].getId_competicion()).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            if (dataSnapshot.hasChildren()){

                                                Ranking pojo_ranking = dataSnapshot.getValue(Ranking.class);
                                                AuxiliarRanking aux = new AuxiliarRanking(ide_usuario);
                                                ArrayList<AuxiliarRanking> lista  = pojo_ranking.getLista_usuarios();
                                                lista.add(aux);
                                                pojo_ranking.setLista_usuarios(lista);
                                                anadirAlista(pojo_competi[0].getId_competicion(),ide_usuario);

                                                ref.child("centro").child("ranking").child(pojo_competi[0].getId_competicion()).setValue(pojo_ranking);
                                                Toast.makeText(getActivity(), "Te has apuntado correctamente", Toast.LENGTH_SHORT).show();
                                                apuntado[0]=true;

                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                }else{
                                    Toast.makeText(getContext(), "Ya estás apuntado", Toast.LENGTH_SHORT).show();
                                }

                            }

                        }




                    }
                });


                btonRankingCompeticion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        ref.child("centro").child("ranking").child(ide_competi[0]).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChildren()){

                                    VerRanking verRanking = VerRanking.newInstance(ide_competi[0],pojo_competi[0].getNombre());
                                    getActivity().getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).setCustomAnimations(R.animator.fade_in,R.animator.fade_out).replace(R.id.frame_principal,verRanking).addToBackStack(null).commit();

                                }else{

                                    Toast.makeText(getActivity(), "Aún nadie se ha apuntado a la competicion", Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                });






            }

            borrarCompeticion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ref.child("centro").child("competiciones").child(ide_competi[0]).removeValue();
                    Toast.makeText(getContext(), "Competicion borrada correctamente", Toast.LENGTH_SHORT).show();
                }
            });





            btonAnadirParticipacion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!apuntado[0]){

                        Toast.makeText(getContext(), "No hay nadie apuntado en la competición", Toast.LENGTH_SHORT).show();


                    }else{
                        if (puntosEjer1.getText().toString().trim().isEmpty() && puntosEjer2.getText().toString().trim().isEmpty()){

                            Toast.makeText(getContext(), "Rellene ambos campos", Toast.LENGTH_SHORT).show();
                        }else{

                            ref.child("centro").child("ranking").child(ide_competi[0]).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.hasChildren()){

                                        int puntos1 = Integer.parseInt(puntosEjer1.getText().toString());
                                        int puntos2 = Integer.parseInt(puntosEjer2.getText().toString());

                                        Ranking pojo_ranking = dataSnapshot.getValue(Ranking.class);
                                        ArrayList<AuxiliarRanking> lista = pojo_ranking.getLista_usuarios();

                                        for (AuxiliarRanking item:lista){

                                            if (item.getId_usuario().equals(ide_usuario)){
                                                int total = item.getTotal_puntos();

                                                total = puntos1+puntos2+total;
                                                item.setTotal_puntos(total);


                                            }

                                        }

                                        Collections.sort(lista, new Comparator<AuxiliarRanking>() {
                                            @Override
                                            public int compare(AuxiliarRanking o1, AuxiliarRanking o2) {
                                                return new Integer(o2.getTotal_puntos()).compareTo(new Integer(o1.getTotal_puntos()));
                                            }
                                        });

                                        pojo_ranking.setLista_usuarios(lista);
                                        ref.child("centro").child("ranking").child(ide_competi[0]).setValue(pojo_ranking);


                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                    }



                }
            });





        }




        return vista;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentMessage("","");
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

    public boolean estaEnLista(ArrayList<String> lista , String id){
        boolean res = false;

        for (String item:lista){
            if (item.equals(id)){
                res = true;
            }
        }

        return res;
    }

    public void anadirAlista(String id_competi,String id_participante){

        ref = FirebaseDatabase.getInstance().getReference();

        ref.child("centro").child("competiciones").child(id_competi).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChildren()){

                    Competicion pojo_competi = dataSnapshot.getValue(Competicion.class);

                    if (pojo_competi.getLista_participantes()==null){

                        ArrayList<String> lista = new ArrayList<>();
                        lista.add(id_participante);
                        pojo_competi.setLista_participantes(lista);
                        ref.child("centro").child("competiciones").child(id_competi).setValue(pojo_competi);

                    }else{

                        ArrayList<String> lista = pojo_competi.getLista_participantes();

                       if (!estaEnLista(lista,id_participante)){

                            lista.add(id_participante);

                        }
                        pojo_competi.setLista_participantes(lista);
                        ref.child("centro").child("competiciones").child(id_competi).setValue(pojo_competi);


                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

}
