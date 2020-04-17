package com.example.reachthegym.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.reachthegym.R;
import com.example.reachthegym.objetos.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VerUsuarioAdmin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VerUsuarioAdmin extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.img_usu_ver)
    ImageView imgUsuVer;
    @BindView(R.id.nombre_usu_ver)
    TextView nombreUsuVer;
    @BindView(R.id.apellidos_usu_ver)
    TextView apellidosUsuVer;
    @BindView(R.id.telefono_usu_ver)
    TextView telefonoUsuVer;
    @BindView(R.id.email_usu_vevr)
    TextView emailUsuVevr;
    @BindView(R.id.direccion_usu_ver)
    TextView direccionUsuVer;
    @BindView(R.id.fechaalta_usu_ver)
    TextView fechaaltaUsuVer;
    @BindView(R.id.cliente_usu_ver)
    TextView clienteUsuVer;
    @BindView(R.id.tipo_usu_ver)
    Spinner tipoUsuVer;
    @BindView(R.id.mod_usu_ver)
    Button modUsuVer;
    @BindView(R.id.borrar_usu_ver)
    Button borrarUsuVer;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private DatabaseReference ref;
    private StorageReference sto;

    public VerUsuarioAdmin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VerUsuarioAdmin.
     */
    // TODO: Rename and change types and number of parameters
    public static VerUsuarioAdmin newInstance(String param1, String param2) {
        VerUsuarioAdmin fragment = new VerUsuarioAdmin();
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
        View v = inflater.inflate(R.layout.fragment_ver_usuario_admin, container, false);
        ref = FirebaseDatabase.getInstance().getReference();
        sto = FirebaseStorage.getInstance().getReference();


        if(mParam1.toString().trim().isEmpty()){
            Toast.makeText(getContext(), "La info no llega correctamente", Toast.LENGTH_SHORT).show();
        }else{
            ref.child("centro").
                    child("usuarios").
                    child(mParam1)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(!dataSnapshot.hasChildren()){
                                Toast.makeText(getContext(), "Data snatchot no tiene children bro ", Toast.LENGTH_SHORT).show();
                            }else{
                                final Usuario pojo_usuario = dataSnapshot.getValue(Usuario.class);
                                nombreUsuVer.setText(pojo_usuario.getNombre());
                                apellidosUsuVer.setText(pojo_usuario.getApellidos());
                                telefonoUsuVer.setText(pojo_usuario.getTelefono());
                                emailUsuVevr.setText(pojo_usuario.getEmail());
                                direccionUsuVer.setText(pojo_usuario.getDireccion());
                                fechaaltaUsuVer.setText(pojo_usuario.getFecha_alta());

                                borrarUsuVer.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(pojo_usuario.getTipo().toLowerCase().equals("empleado")){
                                            Toast.makeText(getContext(), "El usuario no puede borrarse, puede que tenga clientes a su cargo", Toast.LENGTH_SHORT).show();
                                        }else{
                                            ref.child("centro").child("usuarios").child(pojo_usuario.getId()).removeValue();
                                            Toast.makeText(getContext(), "Usuario borrado correctamente", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getContext(), "Algo falla en la consulta", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        unbinder = ButterKnife.bind(this,v);
        return v;
    }
}
