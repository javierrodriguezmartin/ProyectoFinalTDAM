package com.example.reachthegym.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.reachthegym.OnFragmentInteractionList;
import com.example.reachthegym.R;
import com.example.reachthegym.objetos.ClaseGimnasio;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VerClase#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VerClase extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.img_clase_ver)
    ImageView imgClaseVer;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.textView6)
    TextView textView6;
    @BindView(R.id.textView9)
    TextView textView9;
    @BindView(R.id.textView7)
    TextView textView7;
    @BindView(R.id.nombre_clase_ver)
    TextView nombreClaseVer;
    @BindView(R.id.aula_clase_ver)
    TextView aulaClaseVer;
    @BindView(R.id.dia_ver_clase)
    TextView diaVerClase;
    @BindView(R.id.descripcion_ver_clase)
    TextView descripcionVerClase;
    @BindView(R.id.bton_asistir_clase)
    Button btonAsistirClase;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionList mListener;
    private DatabaseReference ref;
    private StorageReference sto;

    public VerClase() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VerClase.
     */
    // TODO: Rename and change types and number of parameters
    public static VerClase newInstance(String param1, String param2) {
        VerClase fragment = new VerClase();
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
        View vista = inflater.inflate(R.layout.fragment_ver_clase, container, false);
        ButterKnife.bind(this, vista);

        ref = FirebaseDatabase.getInstance().getReference();
        sto = FirebaseStorage.getInstance().getReference();

        SharedPreferences prefs = getActivity().getSharedPreferences("datos_usuario",Context.MODE_PRIVATE);
        final String id_usuario = prefs.getString("id_usuario","nada");
        final ClaseGimnasio[] pojo_clase_gym = new ClaseGimnasio[1];

        ref.child("centro").child("clases").child(mParam1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ClaseGimnasio pojo_clase = dataSnapshot.getValue(ClaseGimnasio.class);
                pojo_clase_gym[0] = dataSnapshot.getValue(ClaseGimnasio.class);

                nombreClaseVer.setText(pojo_clase.getNombre());
                aulaClaseVer.setText(pojo_clase.getAula());
                diaVerClase.setText(pojo_clase.getDia());
                descripcionVerClase.setText(pojo_clase.getDescripcion());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        sto.child("centro").child("imagenes").child("imaganes_clase").child(mParam1).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Glide.with(getContext()).load(uri).into(imgClaseVer);

            }
        });

        btonAsistirClase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> lista_participantes = pojo_clase_gym[0].getClientes_apuntados();

                if (lista_participantes.size()>20){
                    Toast.makeText(getContext(), "La clase está completa", Toast.LENGTH_SHORT).show();
                }else if(estaEnLista(lista_participantes,id_usuario)){
                    Toast.makeText(getContext(), "Ya estás apuntado a esta clase", Toast.LENGTH_SHORT).show();
                }else{
                    lista_participantes.add(id_usuario);
                    pojo_clase_gym[0].setClientes_apuntados(lista_participantes);
                    ref.child("centro").child("clases").child(pojo_clase_gym[0].getId_clase()).setValue(pojo_clase_gym[0]);
                    Toast.makeText(getContext(), "Te has apuntado a la clase correctamente", Toast.LENGTH_SHORT).show();
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

    public boolean estaEnLista(ArrayList<String> lista, String id){
        boolean res=false;

        for (String item: lista){
            if (item.equalsIgnoreCase(id)){
                res = true;
            }
        }

        return res;
    }

}
