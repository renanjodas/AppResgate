package com.example.cadastroapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RequisicoesAdapter extends RecyclerView.Adapter<RequisicoesAdapter.MyViewHolder> {

    private List<LocalizacaoDoUsuario> requisicoes;
    private Context context;
    private LocalizacaoDoUsuario usuario;

    public RequisicoesAdapter(List<LocalizacaoDoUsuario> requisicoes, Context context, LocalizacaoDoUsuario usuario){
        this.requisicoes = requisicoes;
        this.context = context;
        this.usuario = usuario;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_requisicoes, parent, false);

        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        LocalizacaoDoUsuario localizacaoDoUsuario = requisicoes.get(position);

        holder.nome.setText(localizacaoDoUsuario.getUsuario());
        holder.longitude.setText(localizacaoDoUsuario.getLongitude());
        holder.latitude.setText(localizacaoDoUsuario.getLatitude());
        holder.status.setText(localizacaoDoUsuario.getStatus());
    }

    @Override
    public int getItemCount(){
        return requisicoes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nome, status, latitude, longitude;

        public  MyViewHolder(View itemView){
            super(itemView);

            nome = itemView.findViewById(R.id.textViewRequisicoesUsuario);
            status = itemView.findViewById(R.id.textViewRequisicoesStatus);
            latitude = itemView.findViewById(R.id.textViewRequisicoesLatitude);
            longitude = itemView.findViewById(R.id.textViewRequisicoesLongitude);
        }
    }
}



