package com.example.reachthegym.adaptadores;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.reachthegym.R;
import com.example.reachthegym.fragments.SeleccionarRutinaCliente;
import com.example.reachthegym.objetos.PedirRutina;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterPublicaciones extends RecyclerView.Adapter<AdapterPublicaciones.ViewHolder> {


    private DatabaseReference ref;
    private StorageReference sto;
    private Context mContext;
    private ArrayList<PedirRutina> lista_anuncios;

    public AdapterPublicaciones(ArrayList<PedirRutina> lista_anuncios, Context mContext) {

        this.mContext = mContext;
        this.lista_anuncios = lista_anuncios;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_publicacion, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        ButterKnife.bind(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        sto = FirebaseStorage.getInstance().getReference();
        ref = FirebaseDatabase.getInstance().getReference();


        final PedirRutina pojo_pedir = lista_anuncios.get(position);
        holder.descripcionPedirRut.setText(pojo_pedir.getDescripcion());

        sto.child("centro").child("imagenes").child(pojo_pedir.getId_creador()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Glide.with(mContext).load(uri).into(holder.imgPublic);

            }
        });

        holder.btonAnadirRutPubli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.child("centro").child("pedir_rutinas").child(pojo_pedir.getId_anuncio()).removeValue();
                SeleccionarRutinaCliente  seleccionarRutinaCliente = SeleccionarRutinaCliente.newInstance(pojo_pedir.getId_creador(),"");
                AppCompatActivity activity = (AppCompatActivity)holder.itemView.getContext();
                activity.getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).setCustomAnimations(R.animator.fade_in,R.animator.fade_out).replace(R.id.frame_principal,seleccionarRutinaCliente).addToBackStack(null).commit();
            }
        });



    }

    @Override
    public int getItemCount() {
        return lista_anuncios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_public)
        CircleImageView imgPublic;
        @BindView(R.id.descripcion_pedir_rut)
        TextView descripcionPedirRut;
        @BindView(R.id.bton_anadir_rut_publi)
        Button btonAnadirRutPubli;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mContext = itemView.getContext();
        }
    }
}
