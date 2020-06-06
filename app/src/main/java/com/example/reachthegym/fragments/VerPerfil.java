package com.example.reachthegym.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.reachthegym.Login;
import com.example.reachthegym.OnFragmentInteractionList;
import com.example.reachthegym.R;
import com.example.reachthegym.objetos.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VerPerfil#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VerPerfil extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.imageView)
    CircleImageView imageView;
    @BindView(R.id.textView8)
    TextView textView8;
    @BindView(R.id.textView10)
    TextView textView10;
    @BindView(R.id.textView12)
    TextView textView12;
    @BindView(R.id.textView11)
    TextView textView11;
    @BindView(R.id.textView13)
    TextView textView13;
    @BindView(R.id.textView14)
    TextView textView14;
    @BindView(R.id.textView15)
    TextView textView15;
    @BindView(R.id.nombre_ver_perfil)
    TextView nombreVerPerfil;
    @BindView(R.id.apellidos_ver_perfil)
    TextView apellidosVerPerfil;
    @BindView(R.id.telefono_ver_perfil)
    TextView telefonoVerPerfil;
    @BindView(R.id.fecha_ver_pefil)
    TextView fechaVerPefil;
    @BindView(R.id.email_ver_perfil)
    TextView emailVerPerfil;
    @BindView(R.id.direccion_ver_perfil)
    TextView direccionVerPerfil;
    @BindView(R.id.dni_ver_perfil)
    TextView dniVerPerfil;
    @BindView(R.id.modificar_perfil)
    Button modificarPerfil;
    @BindView(R.id.cerrar_sesion_perfil)
    Button cerrarSesionPerfil;
    private DatabaseReference ref;
    private StorageReference sto;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionList mListener;

    public VerPerfil() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VerPerfil.
     */
    // TODO: Rename and change types and number of parameters
    public static VerPerfil newInstance(String param1, String param2) {
        VerPerfil fragment = new VerPerfil();
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
        View vista = inflater.inflate(R.layout.fragment_ver_perfil, container, false);
        ButterKnife.bind(this, vista);
        ref = FirebaseDatabase.getInstance().getReference();
        if (isAdded()){

            if (mParam1!=null){

                ref.child("centro").child("usuarios").child(mParam1).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()){

                            Usuario pojo_usuario = dataSnapshot.getValue(Usuario.class);

                            nombreVerPerfil.setText(pojo_usuario.getNombre());
                            apellidosVerPerfil.setText(pojo_usuario.getApellidos());
                            telefonoVerPerfil.setText(pojo_usuario.getTelefono());
                            emailVerPerfil.setText(pojo_usuario.getEmail());
                            fechaVerPefil.setText(pojo_usuario.getFecha_alta());
                            dniVerPerfil.setText(pojo_usuario.getDni());
                            direccionVerPerfil.setText(pojo_usuario.getDireccion());



                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                cerrarSesionPerfil.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(), Login.class);
                        getActivity().startActivity(i);

                    }
                });

                modificarPerfil.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ModificarPerfil modificarPerfil = ModificarPerfil.newInstance(mParam1,"");
                        getActivity().getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).setCustomAnimations(R.animator.fade_in,R.animator.fade_out).replace(R.id.frame_principal,modificarPerfil).addToBackStack(null).commit();


                    }
                });

            }

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

}
