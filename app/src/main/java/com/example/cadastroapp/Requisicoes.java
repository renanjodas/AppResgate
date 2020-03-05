package com.example.cadastroapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class Requisicoes extends AppCompatActivity {

    DatabaseReference reference;
    TextView textViewStatus;
    RecyclerView recyclerView;
    List<LocalizacaoDoUsuario> listaRequisicoes = new ArrayList<>();
    RequisicoesAdapter adapter;
    LocalizacaoDoUsuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requisicoes);

        textViewStatus = findViewById(R.id.textViewSemRequisicoes);
        recyclerView = findViewById(R.id.recyclerViewRequisicoes);

        usuario = new LocalizacaoDoUsuario();

        adapter = new RequisicoesAdapter(listaRequisicoes, getApplicationContext(), usuario);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

    }
}
