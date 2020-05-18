package com.example.reachthegym.adaptadores;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.reachthegym.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterImgHorizontal extends RecyclerView.Adapter<AdapterImgHorizontal.ViewHolder> {


    private ArrayList<Uri> img_url;
    private Context mContext;

    public AdapterImgHorizontal(ArrayList<Uri> img_url, Context mContext) {
        this.mContext = mContext;
        this.img_url = img_url;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpager_ejercicios_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        ButterKnife.bind(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(mContext).load(img_url.get(position)).into(holder.imgItemEjercicio);

    }

    @Override
    public int getItemCount() {
        return img_url.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_item_ejercicio)
        ImageView imgItemEjercicio;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mContext = itemView.getContext();
        }
    }
}
