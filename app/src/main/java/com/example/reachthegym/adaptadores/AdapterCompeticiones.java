package com.example.reachthegym.adaptadores;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.reachthegym.R;
import com.example.reachthegym.fragments.VerCompeticion;
import com.example.reachthegym.objetos.Competicion;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterCompeticiones extends RecyclerView.Adapter<AdapterCompeticiones.ViewHolder> {


    private ArrayList<Competicion> lista_competiciones;
    private Context mContext;
    private DatabaseReference ref;

    public AdapterCompeticiones(Context mContext, ArrayList<Competicion> lista_competiciones) {
        this.mContext = mContext;
        this.lista_competiciones = lista_competiciones;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listar_competicion, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        ButterKnife.bind(v);




        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ref = FirebaseDatabase.getInstance().getReference();

        final Competicion pojo_competicion = lista_competiciones.get(position);
        Glide.with(mContext).load(pojo_competicion.getImg_url()).into(holder.imgCompeticion);
        holder.nombreCompeticion.setText(pojo_competicion.getNombre());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                VerCompeticion verCompeticion = VerCompeticion.newInstance(pojo_competicion.getId_competicion(),"");
                AppCompatActivity activity = (AppCompatActivity)mContext;
                activity.getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).setCustomAnimations(R.animator.fade_in,R.animator.fade_out).replace(R.id.frame_principal,verCompeticion).commit();



            }
        });


    }

    @Override
    public int getItemCount() {
        return lista_competiciones.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_competicion)
        ImageView imgCompeticion;
        @BindView(R.id.back_gradient)
        ImageView backGradient;
        @BindView(R.id.nombre_competicion)
        TextView nombreCompeticion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            ButterKnife.bind(this, itemView);
        }
    }
}
