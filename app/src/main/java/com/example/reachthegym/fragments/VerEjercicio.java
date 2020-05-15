package com.example.reachthegym.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.reachthegym.OnFragmentInteractionList;
import com.example.reachthegym.R;
import com.example.reachthegym.objetos.Ejercicio;
import com.example.reachthegym.objetos.EjercicioEmpleado;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VerEjercicio#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VerEjercicio extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.bannerEjercicios)
    BannerSlider bannerEjercicios;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.textView4)
    TextView textView4;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.nombre_ver_ejercicio)
    TextView nombreVerEjercicio;
    @BindView(R.id.zona_ver_Ejercicio)
    TextView zonaVerEjercicio;
    @BindView(R.id.objetivo_ver_ejercicio)
    TextView objetivoVerEjercicio;
    @BindView(R.id.descripcion_ver_ejercicio)
    TextView descripcionVerEjercicio;
    @BindView(R.id.btn_borrar_ejercicio)
    Button btnBorrarEjercicio;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionList mListener;
    private DatabaseReference ref;
    private StorageReference sto;


    public VerEjercicio() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VerEjercicio.
     */
    // TODO: Rename and change types and number of parameters
    public static VerEjercicio newInstance(String param1, String param2) {
        VerEjercicio fragment = new VerEjercicio();
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
        View vista = inflater.inflate(R.layout.fragment_ver_ejercicio, container, false);
        ButterKnife.bind(this,vista);
        ref = FirebaseDatabase.getInstance().getReference();
        sto = FirebaseStorage.getInstance().getReference();
        final Ejercicio pojo_ejercicio;
        final EjercicioEmpleado[] pojo_ejercicio_empleado = new EjercicioEmpleado[1];

        SharedPreferences pref = getActivity().getSharedPreferences("datos_usuario",Context.MODE_PRIVATE);
        String tipo_usuario = pref.getString("tipo_usuario","no hay nada");

        List<Banner> banners = new ArrayList<>();
        ArrayList <Uri> array_imagenes = new ArrayList<>();

        for (int i=0; i<4; i++){
            sto.child("centro").child("imagenes").child("ejercicios_empleado").child(mParam1).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {

                    array_imagenes.add(uri);

                }
            });
        }

        for (int i=0; i<array_imagenes.size(); i++){
            banners.add(new RemoteBanner(array_imagenes.get(i).toString()));
        }

        bannerEjercicios.setBanners(banners);



        if (tipo_usuario.equalsIgnoreCase("empleado")){

            ref.child("centro")
                    .child("ejercicios_empleado")
                    .child(mParam1)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            EjercicioEmpleado pojo_ejercicio = dataSnapshot.getValue(EjercicioEmpleado.class);
                            nombreVerEjercicio.setText(pojo_ejercicio.getNombre());
                            zonaVerEjercicio.setText(pojo_ejercicio.getZona());
                            objetivoVerEjercicio.setText(pojo_ejercicio.getObjetivo());
                            descripcionVerEjercicio.setText(pojo_ejercicio.getDescripcion());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

        }else{

            ref.child("centro")
                    .child("ejercicios")
                    .child(mParam1)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Ejercicio pojo_ejercicio = dataSnapshot.getValue(Ejercicio.class);
                            nombreVerEjercicio.setText(pojo_ejercicio.getNombre());
                            zonaVerEjercicio.setText(pojo_ejercicio.getZona());
                            objetivoVerEjercicio.setText(pojo_ejercicio.getObjetivo());
                            descripcionVerEjercicio.setText(pojo_ejercicio.getDescripcion());

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

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

}


