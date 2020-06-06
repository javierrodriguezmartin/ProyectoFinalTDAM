package com.example.reachthegym.adaptadores;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.reachthegym.R;
import com.example.reachthegym.objetos.AuxiliarRanking;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> {



    private ArrayList<AuxiliarRanking> lista_ranking = new ArrayList<>();
    private Context mContext;
    private StorageReference sto;

    public RankingAdapter(ArrayList<AuxiliarRanking> lista_ranking,Context mContext){
        this.lista_ranking=lista_ranking;
        this.mContext=mContext;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ranking, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        ButterKnife.bind(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        sto = FirebaseStorage.getInstance().getReference();

        AuxiliarRanking pojo_auxranking = lista_ranking.get(position);

        holder.totalPuntos.setText(pojo_auxranking.getTotal_puntos());

        sto.child("centro").child("imagenes").child(pojo_auxranking.getId_usuario()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Glide.with(mContext).load(uri).into(holder.imgItemRanking);

            }
        });

        holder.posicionRank.setText(position+1);


    }

    @Override
    public int getItemCount() {
        return lista_ranking.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_item_ranking)
        CircleImageView imgItemRanking;
        @BindView(R.id.puntos)
        MaterialTextView puntos;
        @BindView(R.id.total_puntos)
        MaterialTextView totalPuntos;
        @BindView(R.id.posicion_rank)
        TextView posicionRank;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
